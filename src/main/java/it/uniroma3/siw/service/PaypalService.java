package it.uniroma3.siw.service;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.util.Locale;

/**
 * Client minimale per le REST API v2 di PayPal (Orders), usato per creare
 * e catturare un pagamento avviato con i PayPal Smart Payment Buttons.
 */
@Service
public class PaypalService {

    private static final Logger logger = LoggerFactory.getLogger(PaypalService.class);

    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String clientId;
    private final String clientSecret;
    private final String currency;

    public PaypalService(@Value("${paypal.client-id}") String clientId,
                          @Value("${paypal.client-secret}") String clientSecret,
                          @Value("${paypal.base-url}") String baseUrl,
                          @Value("${paypal.currency}") String currency) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.currency = currency;
        this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    }

    private String ottieniAccessToken() {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");

        JsonNode risposta = restClient.post()
                .uri("/v1/oauth2/token")
                .headers(headers -> headers.setBasicAuth(clientId, clientSecret))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .body(JsonNode.class);

        return risposta.get("access_token").asText();
    }

    /** Crea un ordine PayPal per l'importo indicato e ne ritorna l'id. */
    public String creaOrdinePaypal(double importo) {
        String accessToken = ottieniAccessToken();

        ObjectNode amount = objectMapper.createObjectNode();
        amount.put("currency_code", currency);
        amount.put("value", String.format(Locale.ROOT, "%.2f", importo));

        ObjectNode purchaseUnit = objectMapper.createObjectNode();
        purchaseUnit.set("amount", amount);

        ObjectNode richiesta = objectMapper.createObjectNode();
        richiesta.put("intent", "CAPTURE");
        richiesta.putArray("purchase_units").add(purchaseUnit);

        JsonNode risposta = restClient.post()
                .uri("/v2/checkout/orders")
                .headers(headers -> headers.setBearerAuth(accessToken))
                .contentType(MediaType.APPLICATION_JSON)
                .body(richiesta)
                .retrieve()
                .body(JsonNode.class);

        String paypalOrderId = risposta.get("id").asText();
        logger.info("Creato ordine PayPal {}", paypalOrderId);
        return paypalOrderId;
    }

    /** Cattura il pagamento di un ordine PayPal già approvato dall'utente. */
    public boolean catturaOrdinePaypal(String paypalOrderId) {
        String accessToken = ottieniAccessToken();

        JsonNode risposta = restClient.post()
                .uri("/v2/checkout/orders/{id}/capture", paypalOrderId)
                .headers(headers -> {
                    headers.setBearerAuth(accessToken);
                    headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                })
                .retrieve()
                .body(JsonNode.class);

        String stato = risposta.get("status").asText();
        logger.info("Cattura ordine PayPal {}: stato={}", paypalOrderId, stato);
        return "COMPLETED".equals(stato);
    }
}
