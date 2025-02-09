package pro.sky.recommendation_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import pro.sky.recommendation_service.service.ManagementService;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@RestController
@RequestMapping("/management")
public class ManagementController {

    private final ManagementService managementService;

    public ManagementController(ManagementService managementService) {
        this.managementService = managementService;
    }

    @Operation(summary = "Reset all cache")
    @PostMapping("/clear-caches")
    public void clearCaches() {
        managementService.clearCaches();
    }

    @Operation(summary = "Application info")
    @GetMapping("/info")

    public String info() throws IOException, SAXException, ParserConfigurationException {
        return managementService.getInfo();
    }
}
