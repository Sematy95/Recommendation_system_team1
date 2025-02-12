package pro.sky.recommendation_service.service;

import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.dto.StatisticObject;

import java.util.List;

/**
 * Interface defining the contract for managing Statistic entities.
 * This interface specifies methods for adding, deleting, retrieving,
 * and incrementing the count of statistics.
 */
public interface StatisticService {
    /**
     * Adds a new statistic.
     *
     * @param dynamicRule The DynamicRule to associate with the statistic.
     * @param count       The initial count for the statistic.
     */
    public void addStat(DynamicRule dynamicRule, long count);

    /**
     * Deletes a statistic by ID.
     *
     * @param id The ID of the statistic to delete.
     */
    public void deleteStat(long id);

    /**
     * Retrieves all statistics.
     *
     * @return A list of StatisticObject DTOs representing the statistics.
     */
    public List<StatisticObject> getAllStats();

    /**
     * Increments the count of a statistic by ID.
     *
     * @param id The ID of the statistic to increment.
     */
    public void incrementCount(long id);
}
