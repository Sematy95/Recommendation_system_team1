package pro.sky.recommendation_service.service;

import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.recommendation_service.model.NotificationTask;
import pro.sky.recommendation_service.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class responsible for sending notifications based on scheduled tasks.
 */
@Service
public class NotificationService {
    private final ListenerService listener;
    private final TaskRepository taskRepository;

    public NotificationService(ListenerService listener, TaskRepository taskRepository) {
        this.listener = listener;
        this.taskRepository = taskRepository;
    }

    /**
     * Sends notifications for tasks whose notification date is within the last minute.
     * This method retrieves tasks from the database, creates SendMessage objects for each task,
     * and uses the ListenerService to send the notifications via Telegram.
     */
    public void sendNotificationByDate() {
        List<NotificationTask> tasks = taskRepository.findByNotificationDateBetween(
                LocalDateTime.now().minusSeconds(60),
                LocalDateTime.now());
        Set<SendMessage> messages = tasks.stream()
                .map(t -> new SendMessage(t.getChatId(), t.getMessage()))
                .collect(Collectors.toSet());
        listener.execute(messages);
    }
}
