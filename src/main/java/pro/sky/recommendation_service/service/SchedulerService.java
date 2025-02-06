package pro.sky.recommendation_service.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Service class responsible for scheduling the sending of notifications.
 * This class uses Spring's @Scheduled annotation to trigger the notification sending at regular intervals.
 */
@Component
public class SchedulerService {
    private final NotificationService notificationService;

    public SchedulerService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Scheduled method that triggers the sending of notifications.
     * This method is executed according to the cron expression "0 0/1 * * * *",
     * which means it runs every minute.
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void execute() {
        notificationService.sendNotificationByDate();
    }
}
