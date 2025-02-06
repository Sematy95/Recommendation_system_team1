package pro.sky.recommendation_service.service.util;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Utility class containing constants and helper methods for the Telegram bot.
 * This class defines commonly used strings, regular expressions, and date/time formats.
 */
public class BitsAndBobs {
    /**
     * The command used to start the bot ("/start").
     */
    public static final String START_COMMAND = "/start";

    /**
     * The message sent to the user when the bot is started.
     */
    public static final String GREETING_MESSAGE = "Hello dear user";

    /**
     * The message sent to the user when their request is accepted.
     */
    public static final String ANSWER_MESSAGE = "Request is accepted";

    /**
     * DateTimeFormatter for parsing the date and time from the input string.
     * This formatter is configured to parse dates in the format dd.MM.yyyy HH:mm.
     */
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    /**
     * Regular expression pattern for validating the date and time input.
     * The pattern expects a date in the format dd.MM.yyyy HH:mm followed by
     * a message. It captures the date/time and the message in separate groups.
     */
    public static final Pattern DATE_VALIDATION_PATTERN = Pattern.compile("(\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}:\\d{2})(\\s+)(.+)");
}
