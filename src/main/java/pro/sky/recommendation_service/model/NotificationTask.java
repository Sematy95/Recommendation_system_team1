package pro.sky.recommendation_service.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a notification task to be sent to a user.
 * This entity is stored in the "notification_task" table.
 */
@Entity
//@Table(name = "notification_task")
public class NotificationTask {
    /**
     * The unique identifier for the NotificationTask entity.
     * This field represents the primary key in the "notification_task" table of the database.
     * It is automatically generated using a database sequence named "notification_task_seq".
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "notification_task_seq")
    @SequenceGenerator(name = "notification_task_seq", allocationSize = 1)
    private Long id;
    private String chatId;
    private String message;
    private LocalDateTime creationDate;
    private LocalDateTime notificationDate;

    /**
     * Default constructor for the NotificationTask entity. Required by JPA.
     */
    public NotificationTask() {
    }

    /**
     * Constructs a new NotificationTask object.
     *
     * @param chatId            The chat ID to which the notification should be sent.
     * @param message           The message to be sent in the notification.
     * @param notificationDate  The date and time when the notification should be sent.
     */
    public NotificationTask(String chatId, String message, LocalDateTime notificationDate) {
        this.chatId = chatId;
        this.message = message;
        this.notificationDate = notificationDate;
        creationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(LocalDateTime notificationDate) {
        this.notificationDate = notificationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(chatId, that.chatId) &&
                Objects.equals(message, that.message) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(notificationDate, that.notificationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chatId, message, creationDate, notificationDate);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", chatId='" + chatId + '\'' +
                ", message='" + message + '\'' +
                ", creationDate=" + creationDate +
                ", notificationDate=" + notificationDate +
                '}';
    }
}
