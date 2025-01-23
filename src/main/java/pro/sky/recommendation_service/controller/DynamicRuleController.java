package pro.sky.recommendation_service.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.recommendation_service.domain.RequestObject;

import java.util.UUID;

@RestController
public class DynamicRuleController {

    @PostMapping("/rule")
    public String addRule(@RequestParam("product_name") String productName,
                        @RequestParam("product_id") UUID productId,
                        @RequestParam("product_text") String productText,
                        @RequestParam("requestObject") RequestObject requestObject) {
        return "controller test add rule";
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
