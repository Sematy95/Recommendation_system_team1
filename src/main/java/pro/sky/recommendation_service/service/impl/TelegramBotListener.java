package pro.sky.recommendation_service.service.impl;

import pro.sky.recommendation_service.domain.ResponseForUser;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class that listens for updates from the Telegram bot and processes them.
 */
@Service
public class TelegramBotListener implements UpdatesListener {
    private static final String messageIsEmpty = "Ошибка: текст команды не может быть пустым.";
    private static final String messageGreeting = "Привет! Это StarBank assistant. " + "Используйте команду /recommend <имя_пользователя> для получения рекомендаци";
    private static final String messageUserNotFound = "Ошибка: пользователь не найден.";

    private static final Logger logger = LoggerFactory.getLogger(TelegramBotListener.class);

    private final TelegramBot telegramBot;
    private final RecommendationService recommendationService;
    private final RecommendationsRepository recommendationsRepository;

    public TelegramBotListener(TelegramBot telegramBot,
                               RecommendationService recommendationService,
                               RecommendationsRepository recommendationsRepository) {
        this.telegramBot = telegramBot;
        this.recommendationService = recommendationService;
        this.recommendationsRepository = recommendationsRepository;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    /**
     * Processes a list of updates from Telegram. If the update contains a message,
     * the bot checks if the message is a command. If so, the bot processes the command.
     * If the message is not a command, the bot ignores it.
     *
     * @param updates   list of updates to process
     * @return {@link UpdatesListener#CONFIRMED_UPDATES_ALL} to confirm processing of all updates
     */
    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);

            if (update.message() != null) {
                Message message = update.message();
                String text = message.text();
                long chatId = message.chat().id();

                if (text == null) {
                    sendMessage(chatId, messageIsEmpty);
                    return;
                }

                switch (text) {
                    case "/start":
                        sendMessage(chatId, messageGreeting);
                        break;
                    default:
                        if (text.startsWith("/recommend")) {
                            handleRecommendationRequest(chatId, text.substring("/recommend".length()).trim());
                        }
                }
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void handleRecommendationRequest(long chatId, String username) {
        if (username.isEmpty()) {
            sendMessage(chatId, messageUserNotFound);
            return;
        }

        ResponseForUser response = recommendationService.getRecommendationsByUsername(username);
        String fullUserName = recommendationsRepository.getFullNameByUsername(username);
        String result = "Здравствуйте " + fullUserName + ". Новые продукты для вас:\n" + response;
        sendMessage(chatId, result);
    }

    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        telegramBot.execute(sendMessage);
    }
}
