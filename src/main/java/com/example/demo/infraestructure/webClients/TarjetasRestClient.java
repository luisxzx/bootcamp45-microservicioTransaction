package com.example.demo.infraestructure.webClients;

import com.example.demo.model.CreditCardDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
@Component
public class TarjetasRestClient {

    @Value("${ntt.data.bootcamp.s01-credit-service}")
    private String creditServiceUrl;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Flux<CreditCardDetails> getCreditCardsByClientId(String clientId) {
        WebClient webClient = webClientBuilder.baseUrl(creditServiceUrl).build();
        return webClient.get()
                .uri("/credit-cards/by-client/{clientId}", clientId)
                .retrieve()
                .bodyToFlux(CreditCardDetails.class);


    }


}
