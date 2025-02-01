package pro.sky.recommendation_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.domain.Statistic;
import pro.sky.recommendation_service.dto.StatisticObject;
import pro.sky.recommendation_service.service.DynamicRuleService;
import pro.sky.recommendation_service.service.StatisticService;
import pro.sky.recommendation_service.service.impl.StatisticServiceImpl;


import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {

    private final DynamicRuleService dynamicRuleService;
    private final StatisticServiceImpl statisticService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService, StatisticServiceImpl statisticService) {
        this.dynamicRuleService = dynamicRuleService;
        this.statisticService = statisticService;
    }

    @Operation(summary = "Add new dynamic rule")
    @PostMapping
    public DynamicRule addRule(@RequestBody DynamicRule dynamicRule) {
        return dynamicRuleService.addRule(dynamicRule);
    }

    @Operation(summary = "Delete dynamic rule by id")
    @DeleteMapping("/{rule_id}")
    public ResponseEntity deleteRule(@PathVariable("rule_id") Long id) {
        dynamicRuleService.deleteRule(id);
        return ResponseEntity.status(204).build();

    }

    @Operation(summary = "List of all dynamic_rules")
    @GetMapping
    public Collection<DynamicRule> getAllRules() {
        return dynamicRuleService.getAllDynamicRules();
    }

    @Operation(summary = "Get dynamic rules statistics")
    @GetMapping("/stats")
    public List<StatisticObject> getStatistic() {
        return statisticService.getAllStats();
    }
}
