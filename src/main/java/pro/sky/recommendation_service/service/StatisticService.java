package pro.sky.recommendation_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.domain.Statistic;

import java.util.List;

public interface StatisticService {

    public void addStat(DynamicRule dynamicRule, long count);

    public void deleteStat(long id);

    public List<Statistic> getAllStats();

}
