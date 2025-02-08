package pro.sky.recommendation_service.exception;

public class StatisticNotFoundException extends RuntimeException {

    public StatisticNotFoundException(Long message) {
        super("Such statistic was not found: " + message);
    }

}
