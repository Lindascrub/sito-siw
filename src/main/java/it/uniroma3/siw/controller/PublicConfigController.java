package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/** Espone configurazioni pubbliche (non segrete) al frontend React. */
@RestController
@RequestMapping("/api/config")
public class PublicConfigController {

    private final String paypalClientId;

    public PublicConfigController(@Value("${paypal.client-id}") String paypalClientId) {
        this.paypalClientId = paypalClientId;
    }

    @GetMapping
    public Map<String, String> config() {
        return Map.of("paypalClientId", paypalClientId);
    }
}
