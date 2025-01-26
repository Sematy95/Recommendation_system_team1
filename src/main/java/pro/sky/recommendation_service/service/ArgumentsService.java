//package pro.sky.recommendation_service.service;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Service;
//import pro.sky.recommendation_service.repository.ArgumentsRepository;
//
//import java.util.Collection;
//
//import static pro.sky.recommendation_service.domain.enums.CompareType.*;
//import static pro.sky.recommendation_service.domain.enums.ProductType.*;
//import static pro.sky.recommendation_service.domain.enums.TransactionName.DEPOSIT;
//import static pro.sky.recommendation_service.domain.enums.TransactionName.WITHDRAW;
//
//@Service
//public class ArgumentsService {
//
//    private static final Logger log = LoggerFactory.getLogger(ArgumentsService.class);
//    private final ArgumentsRepository argumentsRepository;
//
//    public ArgumentsService(ArgumentsRepository argumentsRepository) {
//        this.argumentsRepository = argumentsRepository;
//    }
//
//    public Arguments addArguments(String productType, String transactionName, String compareType, Integer compareValue) {
//        ifArgumentsIsExist(productType, transactionName, compareType, compareValue);
//        argumentsCheck(productType, transactionName, compareType, compareValue);
//        log.info("Was invoked method for adding arguments");
//        Arguments argumentsToAdd = new Arguments(productType, transactionName, compareType, compareValue);
//        return argumentsRepository.save(argumentsToAdd);
//    }
//
//
//    public void deleteArguments(long id) {
//        Arguments argumentsForDelete = argumentsRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find arguments with id=" + id));
//        argumentsRepository.delete(argumentsForDelete);
//        log.info("Was invoked method for removing arguments with id={} ", argumentsForDelete.getId());
//    }
//
//    public Collection<Arguments> getAllArguments() {
//        return argumentsRepository.findAll();
//    }
//
//    private void ifArgumentsIsExist(String productType, String transactionName, String compareType, Integer compareValue) {
//        if (argumentsRepository.existsByProductTypeAndTransactionNameAndCompareTypeAndCompareValue(productType, transactionName, compareType, compareValue)) {
//            throw new IllegalArgumentException("Such arguments already exist");
//        }
//    }
//
//    private void argumentsCheck(String productType, String transactionName, String compareType, Integer compareValue) {
//        if (!(productType.equals(DEBIT)
//                || productType.equals(CREDIT)
//                || productType.equals(INVEST)
//                || productType.equals(SAVING))) {
//            throw new IllegalArgumentException("Provided productType isn't correct");
//        }
//        if (transactionName != null && (!(transactionName.equals(WITHDRAW) || transactionName.equals(DEPOSIT)))) {
//            throw new IllegalArgumentException("Provided transactionName isn't correct");
//        }
//        if (compareType != null && (!(compareType.equals(BIGGER)
//                || compareType.equals(SMALLER)
//                || compareType.equals(EQUAL)
//                || compareType.equals(BIGGER_OR_EQUAL)
//                || compareType.equals(SMALLER_OR_EQUAL)))) {
//            throw new IllegalArgumentException("Provided compareType isn't correct");
//        }
//        if (compareValue != null && (!(compareValue >= 0))) {
//            throw new IllegalArgumentException("Provided compareValue isn't correct");
//        }
//
//    }
//}