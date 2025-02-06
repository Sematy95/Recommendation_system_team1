package pro.sky.recommendation_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.*;
import pro.sky.recommendation_service.dto.RecommendationObject;
import pro.sky.recommendation_service.repository.RuleRepository;
import pro.sky.recommendation_service.service.RecommendationService;

import java.util.List;
import java.util.UUID;

/**
 * This controller provides endpoints for operations outside.
 */
@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService, RuleRepository ruleRepository) {
        this.recommendationService = recommendationService;
    }

    /**
     * Get recommendations for a given user ID.
     *
     * @param id The UUID of the user for whom recommendations are requested.
     * @return A list of RecommendationObject instances representing the recommendations.
     */
    @Operation(
            summary = "Getting recommendation",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Recommendation",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = RecommendationService.class)
                                    )
                            )
                    )
            },
            tags = "Recommendation")
    @GetMapping("/recommendation/{user_id}")
    public List<RecommendationObject> getRecommendation(@PathVariable("user_id") UUID id) {
        return recommendationService.getRecommendations(id);
    }
}
