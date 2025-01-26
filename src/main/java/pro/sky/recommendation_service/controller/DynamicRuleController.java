package pro.sky.recommendation_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.service.DynamicRuleService;

import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class DynamicRuleController {

    private final DynamicRuleService dynamicRuleService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
    }
    @Operation(summary = "Add new rule")
    @PostMapping
    public DynamicRule addRule(@RequestBody DynamicRule dynamicRule) {
        return dynamicRuleService.addRule(dynamicRule);
    }

    @DeleteMapping("/rule/{rule_id}")
    public String deleteRule(@PathVariable("rule_id") UUID id) {
        return "controller test delete rule";
    }

    @GetMapping("/rule")
    public String getAllRules() {
        return "controller test getAllRules";
    }
}
