package pro.sky.recommendation_service.domain;

import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a response to be sent to a user, containing a collection of dynamic rules.
 */
public class ResponseForUser {
    private UUID userId;
    private Collection<DynamicRule> dynamicRules;

    /**
     * Default constructor for the ResponseForUser class.  Required for some frameworks.
     */
    public ResponseForUser() {
    }

    /**
     * Constructs a new ResponseForUser object.
     *
     * @param userId       The UUID of the user.
     * @param dynamicRules The collection of dynamic rules.
     */
    public ResponseForUser(UUID userId, Collection<DynamicRule> dynamicRules) {
        this.userId = userId;
        this.dynamicRules = dynamicRules;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Collection<DynamicRule> getDynamicRules() {
        return dynamicRules;
    }

    public void setDynamicRules(Collection<DynamicRule> dynamicRules) {
        this.dynamicRules = dynamicRules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseForUser that = (ResponseForUser) o;
        return Objects.equals(userId, that.userId) && Objects.equals(dynamicRules, that.dynamicRules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, dynamicRules);
    }

    @Override
    public String toString() {
        return "ResponseForUser{" +
                "userId=" + userId +
                ", dynamicRules=" + dynamicRules +
                '}';
    }
}
