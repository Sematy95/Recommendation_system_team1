package pro.sky.recommendation_service.configuration;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.DeleteMyCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up exchanging with telegram bot.
 */
@Configuration
public class TelegramBotConfiguration {
    /**
     * The Telegram bot token, injected from the application properties
     * using the 'telegram.bot.token' key.
     */
    @Value("${telegram.bot.token}")
    private String token;

    /**
     * Creates and returns a TelegramBot instance.
     * This method reads the bot token and initializes a new TelegramBot object.
     * It also executes a DeleteMyCommands request to clear any existing bot commands.
     * This bean can then be injected into other Spring components that need to
     * interact with the Telegram Bot API.
     *
     * @return A TelegramBot instance configured with the bot token.
     */
    @Bean
    public TelegramBot telegramBot() {
        TelegramBot bot = new TelegramBot(token);
        bot.execute(new DeleteMyCommands());
        return bot;
    }
}
