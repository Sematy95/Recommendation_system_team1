package pro.sky.recommendation_service.service.impl;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.domain.ResponseForUser;
import pro.sky.recommendation_service.repository.RecommendationsRepository;
import pro.sky.recommendation_service.service.RecommendationService;

import java.util.List;

import static pro.sky.recommendation_service.service.enums.TelegramMessage.*;

/**
 * Service class that listens for updates from the Telegram bot and processes them.
 */
@Service
public class TelegramBotListener implements UpdatesListener {
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

    /**
     * Initializes the Telegram bot listener by setting itself as the UpdatesListener
     * for the TelegramBot instance. This method is called after the bean is constructed.
     */
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
                    sendMessage(chatId, MESSAGE_IS_EMPTY.toString());
                    return;
                }

                switch (text) {
                    case "/start":
                        sendMessage(chatId, MESSAGE_GREETING.toString());
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

    /**
     * Handles a recommendation request.
     * This method extracts the username from the command, retrieves recommendations
     * using the RecommendationService, and sends the recommendations back to the user.
     *
     * @param chatId   The chat ID of the user requesting recommendations.
     * @param username The username for whom to retrieve recommendations.
     */
    private void handleRecommendationRequest(long chatId, String username) {
        if (username.isEmpty()) {
            sendMessage(chatId, MESSAGE_NOT_FOUND.toString());
            return;
        }

        ResponseForUser response = recommendationService.getRecommendationsByUsername(username);
        String fullUserName = recommendationsRepository.getFullNameByUsername(username);
        String result = "Здравствуйте " + fullUserName + ". Новые продукты для вас:\n" + response;
        sendMessage(chatId, result);
    }

    /**
     * Sends a message to the specified chat ID.
     *
     * @param chatId The chat ID to send the message to.
     * @param text   The text of the message to send.
     */
    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId, text);
        telegramBot.execute(sendMessage);
    }
}
