package pro.sky.recommendation_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendation_service.domain.DynamicRule;
import pro.sky.recommendation_service.dto.StatisticObject;
import pro.sky.recommendation_service.service.DynamicRuleService;
import pro.sky.recommendation_service.service.impl.StatisticServiceImpl;

import java.util.Collection;
import java.util.List;

/**
 * This controller provides endpoints for rule operations.
 */
@RestController
@RequestMapping("/rule")
public class DynamicRuleController {
    private final DynamicRuleService dynamicRuleService;
    private final StatisticServiceImpl statisticService;

    public DynamicRuleController(DynamicRuleService dynamicRuleService, StatisticServiceImpl statisticService) {
        this.dynamicRuleService = dynamicRuleService;
        this.statisticService = statisticService;
    }

    /**
     * Adds a new dynamic rule.
     *
     * @param dynamicRule The DynamicRule object representing the rule to be added, received in the request body.
     * @return The added DynamicRule object.
     */
    @Operation(
            summary = "Adding a new dynamic rule",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Adding a new dynamic rule",
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
            summary = "Deleting a dynamic rule by id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Deleting a dynamic rule by id",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = DynamicRuleService.class)
                                    )
                            )
                    )
            },
            tags = "Manage rules")
    @DeleteMapping("/{rule_id}")
    public ResponseEntity deleteRule(@PathVariable("rule_id") Long id) {
        dynamicRuleService.deleteRule(id);
        return ResponseEntity.status(204).build();
    }

    /**
     * Gets all dynamic rules.
     *
     * @return A String representing all rules (currently a placeholder).
     */
    @Operation(
            summary = "Getting list of all the rules",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Getting list of all the rules",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = DynamicRuleService.class)
                                    )
                            )
                    )
            },
            tags = "Manage rules")
    @GetMapping
    public Collection<DynamicRule> getAllRules() {
        return dynamicRuleService.getAllDynamicRules();
    }

    /**
     * Gets all dynamic rules.
     *
     * @return A String representing all rules (currently a placeholder).
     */
    @Operation(
            summary = "Getting statistics of dynamic rules",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Getting statistics of dynamic rules",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = DynamicRuleService.class)
                                    )
                            )
                    )
            },
            tags = "Manage rules")
    @GetMapping("/stats")
    public List<StatisticObject> getStatistic() {
        return statisticService.getAllStats();
    }
}
