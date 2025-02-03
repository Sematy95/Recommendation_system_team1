package pro.sky.recommendation_service.dto;

import java.util.Objects;

public class StatisticObject {

    private final long id;
    private final long count;

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
