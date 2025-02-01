package pro.sky.recommendation_service.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.domain.Statistic;
import pro.sky.recommendation_service.dto.StatisticObject;

import java.util.List;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    public void deleteByDynamicRule(DynamicRule dynamicRule);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Statistic " +
            "SET counts = counts + 1 " +
            "WHERE dynamicRule = ?1")
    public void incrCount(DynamicRule dynamicRule);

}
