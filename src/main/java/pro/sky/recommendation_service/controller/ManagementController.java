package pro.sky.recommendation_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;
import pro.sky.recommendation_service.service.DynamicRuleService;
import pro.sky.recommendation_service.service.ManagementService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * This controller provides endpoints for management operations.
 */
@RestController
@RequestMapping("/management")
public class ManagementController {
    private final ManagementService managementService;

    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    /**
     * Resets all caches.
     */
    @Operation(
            summary = "Reset all caches",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Reset all caches",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = DynamicRuleService.class)
                                    )
                            )
                    )
            },
            tags = "Management")
    @PostMapping("/clear-caches")
    public void clearCaches() {
        managementService.clearCaches();
    }

    /**
     * Retrieves application information.
     *
     * @return application information as a string
     * @throws IOException                  if an I/O error occurs
     * @throws SAXException                 if a SAX exception occurs
     * @throws ParserConfigurationException if a parser configuration exception occurs
     */
    @Operation(
            summary = "Application info",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Application info",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = DynamicRuleService.class)
                                    )
                            )
                    )
            },
            tags = "Management")
    @GetMapping("/info")
    public String info() throws IOException, SAXException, ParserConfigurationException {
        return managementService.getInfo();
    }
}
