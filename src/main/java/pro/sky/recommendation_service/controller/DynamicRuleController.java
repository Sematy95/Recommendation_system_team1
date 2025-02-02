package pro.sky.recommendation_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.tomcat.util.digester.Rule;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendation_service.domain.Condition;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.service.DynamicRuleService;

import java.util.UUID;

/**
 * This controller provides endpoints for operations outside.
 */
@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService) {
        this.dynamicRuleService = dynamicRuleService;
    }

    /**
     * Adds a new dynamic rule.
     *
     * @param dynamicRule The DynamicRule object representing the rule to be added, received in the request body.
     * @return The added DynamicRule object.
     */
    @Operation(
            summary = "Adding a new rule",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Adding a new rule",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = DynamicRuleService.class)
                                    )
                            )
                    )
            },
            tags = "Manage rules")
    @PostMapping
    public DynamicRule addRule(@RequestBody DynamicRule dynamicRule) {
        return dynamicRuleService.addRule(dynamicRule);
    }

    /**
     * Deletes a dynamic rule by its ID.
     *
     * @param id The UUID of the rule to be deleted, received as a path variable.
     * @return A String indicating the result of the deletion (currently a placeholder).
     */
    @Operation(
            summary = "Deleting a new rule",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleting a new rule",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = DynamicRuleService.class)
                                    )
                            )
                    )
            },
            tags = "Manage rules")
    @DeleteMapping("/rule/{rule_id}")
    public String deleteRule(@PathVariable("rule_id") UUID id) {
        return "controller test delete rule";
    }

    /**
     * Gets all dynamic rules.
     *
     * @return A String representing all rules (currently a placeholder).
     */
    @Operation(
            summary = "Getting all the rules",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Getting all the rules",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = DynamicRuleService.class)
                                    )
                            )
                    )
            },
            tags = "Manage rules")
    @GetMapping("/rule")
    public String getAllRules() {
        return "controller test getAllRules";
    }
}
