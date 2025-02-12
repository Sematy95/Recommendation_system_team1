package pro.sky.recommendation_service.service.enums;

/**
 * Enum representing predefined messages for the Telegram bot.
 */
public enum TelegramMessage {
    MESSAGE_IS_EMPTY("Ошибка: текст команды не может быть пустым."),
    MESSAGE_GREETING("Привет! Это StarBank assistant. Используйте команду /recommend <имя_пользователя> для получения рекомендации"),
    MESSAGE_NOT_FOUND("Ошибка: пользователь не найден.");

    private final String value;

    TelegramMessage(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}