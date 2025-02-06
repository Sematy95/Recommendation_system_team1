package pro.sky.recommendation_service.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.exceptions.IsNotValidException;
import pro.sky.recommendation_service.model.NotificationTask;
import pro.sky.recommendation_service.repository.TaskRepository;

//import javax.annotation.PostConstruct;
import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;

import static pro.sky.recommendation_service.service.util.BitsAndBobs.*;

/**
 * Service class that listens for updates from the Telegram bot and processes them.
 */
@Service
public class ListenerService implements UpdatesListener {
    private final static Logger logger = LoggerFactory.getLogger(ListenerService.class);

    private final TelegramBot telegramBot;
    private final TaskRepository taskRepository;

    /**
     * Constructs a new ListenerService.
     *
     * @param telegramBot       The TelegramBot instance to use for interacting with the Telegram API.
     * @param taskRepository    The TaskRepository to use for persisting notification tasks.
     */
    public ListenerService(TelegramBot telegramBot, TaskRepository taskRepository) {
        this.telegramBot = telegramBot;
        this.taskRepository = taskRepository;
    }

    /**
     * Initializes the listener by setting itself as the UpdatesListener for the TelegramBot.
     * This method is called after the bean is constructed.
     */
    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Processes incoming updates from the Telegram bot.
     *
     * @param updates A list of Update objects representing the incoming updates.
     * @return An integer representing the confirmation of processed updates.
     */
    @Override
    public int process(List<Update> updates) {
        logger.info("Was invoked process()");

        updates.forEach(update -> {
            // Get message content
            String text = update.message().text();

            logger.info("Processing update: {}", update);

            // Check message content
            if (!checkMessage(update)) {
                logger.warn("Message is not correct: {}", update);
                return;
            }
            Matcher matcher = DATE_VALIDATION_PATTERN.matcher(text);
            String chatId = getChatId(update);
            SendMessage sendMessage = null;

            // Choice of answer
            if (text.equals(START_COMMAND)) {
                sendMessage = new SendMessage(chatId, GREETING_MESSAGE);
            } else if (!matcher.matches()) {
                sendMessage = new SendMessage(chatId, "Request is incorrect");
                logger.warn("Request is incorrect");
            } else if (matcher.matches()) {
                sendMessage = new SendMessage(chatId, ANSWER_MESSAGE);
                try {
                    // Save the notification task to the database
                    taskRepository.save(
                            new NotificationTask(
                                    chatId,
                                    matcher.group(3),
                                    LocalDateTime.parse(matcher.group(1), DATE_FORMAT)
                            )
                    );
                } catch (DateTimeParseException e) {
                    logger.warn("ChatID: [{}]. Invalid date format: {}", chatId, e.getMessage());
                    sendMessage = new SendMessage(chatId, "Invalid date format. Please use " + DATE_FORMAT.format(LocalDate.now()));
                } catch (DataAccessException e) {
                    logger.error("ChatID: [{}]. Error saving task: {}", chatId, e.getMessage());
                    sendMessage = new SendMessage(chatId, "Error saving your request. Please try again later");
                }
            }

            if (sendMessage != null) {
                telegramBot.execute(sendMessage);
            }
        });

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    /**
     * Checks if the update message is valid (not null and not blank).
     *
     * @param update The Update object to check.
     * @return True if the message is valid, false otherwise.
     */
    private boolean checkMessage(Update update) {
        return update.message() != null && !update.message().text().isBlank();
    }

    /**
     * Executes a collection of SendMessage requests.
     *
     * @param sendMessages The collection of SendMessage requests to execute.
     */
    public void execute(Collection<SendMessage> sendMessages) {
        sendMessages.forEach(telegramBot::execute);
    }

    /**
     * Executes a single SendMessage request.
     *
     * @param sendMessages The SendMessage request to execute.
     */
    public void execute(SendMessage sendMessages) {
        execute(List.of(sendMessages));
    }

    /**
     * Extracts the chat ID from the update.
     *
     * @param update The Update object to extract the chat ID from.
     * @return The chat ID as a String.
     * @throws IllegalArgumentException If the chat ID does not exist in the update.
     */
    private String getChatId(Update update) {
        if (update.message().chat() == null) {
            throw new IsNotValidException(this.getClass(), null, "Called with null chatId");
        }
        return String.valueOf(update.message().chat().id());
    }
}
