package pro.sky.recommendation_service.dto;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing statistics for a dynamic rule.
 * This DTO is used to transfer statistic data between layers of the application.
 * It's a simplified representation of the 'Statistic' entity, suitable for exposing via an API.
 * This class is immutable, as its fields are final.
 */
public class StatisticObject {
    private final long id;
    private final long count;

    /**
     * Constructs a new StatisticObject.
     *
     * @param id    The ID of the statistic.
     * @param count The count of the statistic.
     */
    public StatisticObject(long id, long count) {
        this.id = id;
        this.count = count;
    }

    public long getId() {
        return id;
    }

    public long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticObject that = (StatisticObject) o;
        return id == that.id && count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, count);
    }

    @Override
    public String toString() {
        return "StatisticObject{" +
                "id=" + id +
                ", count=" + count +
                '}';
    }
}
