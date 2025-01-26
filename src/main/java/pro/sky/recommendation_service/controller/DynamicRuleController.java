package pro.sky.recommendation_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.service.DynamicRuleService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {

    private final DynamicRuleService dynamicRuleService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
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

    @GetMapping
    public Collection<DynamicRule> getAllRules() {
        return dynamicRuleService.getAllDynamicRules();
    }
}
