package pro.sky.recommendation_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.recommendation_service.domain.ResponseForUser;
import pro.sky.recommendation_service.domain.Transaction;
import pro.sky.recommendation_service.service.RecommendationService;

import java.util.List;
import java.util.UUID;

/**
 * This controller provides endpoints for getting information and recommendation about user.
 */
@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;

    }

    /**
     * Get recommendations for a given user ID.
     *
     * @param id The UUID of the user for whom recommendations are requested.
     * @return A list of RecommendationObject instances representing the recommendations.
     */
    @Operation(
            summary = "Getting list of all recommendations by user id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Getting list of all recommendations by user id",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = RecommendationService.class)
                                    )
                            )
                    )
            },
            tags = "Recommendations")
    @GetMapping("/recommendation/{user_id}")
    public ResponseForUser getRecommendation(@PathVariable("user_id") UUID id) {
        return recommendationService.getRecommendations(id);
    }

    /**
     * Get recommendations for a given user ID.
     *
     * @param id The UUID of the user for whom recommendations are requested.
     * @return A list of RecommendationObject instances representing the recommendations.
     */
    @Operation(
            summary = "Getting list of all transactions by user id",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Getting list of all transactions by user id",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = RecommendationService.class)
                                    )
                            )
                    )
            },
            tags = "Transactions")
    @GetMapping("/transaction/{user_id}")
    public List<Transaction> getTransaction(@PathVariable("user_id") UUID id) {
        return recommendationService.getTransaction(id);
    }
}
