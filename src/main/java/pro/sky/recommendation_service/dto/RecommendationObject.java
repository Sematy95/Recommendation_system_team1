package pro.sky.recommendation_service.dto;

import java.util.Objects;
import java.util.UUID;

/**
 * Represents a recommendation object with an ID, name, and descriptive text.
 * This class is immutable, as its fields are final.
 */
public class RecommendationObject {
    private final UUID id;
    private final String name;
    private final String text;

    public RecommendationObject(UUID id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationObject that = (RecommendationObject) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text);
    }

    @Override
    public String toString() {
        return "RecommendationObject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
