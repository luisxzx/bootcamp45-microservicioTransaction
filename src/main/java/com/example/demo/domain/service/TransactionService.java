package com.example.demo.domain.service;
import com.example.demo.application.ITransactionService;
import com.example.demo.domain.document.TransactionEntity;
import com.example.demo.common.mapper.TransactionMapper;
import com.example.demo.infraestructure.webClients.CuentasRestClient;
import com.example.demo.infraestructure.webClients.TarjetasRestClient;
import com.example.demo.model.*;
import com.example.demo.domain.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService implements ITransactionService {
    /**
     * Repositorio para acceder a la BD de TransactionRepository.
     */

    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CuentasRestClient cuentasRestClient;
    @Autowired
    private TarjetasRestClient tarjetasRestClient;
    /**
     * Método para guardar una transacción.
     *
     * @param transaction El objeto transacción (debe ser final).
     * @return Retorna la transacción guardada.
     */
    @Override
    public Mono<TransactionEntity> saveTransaction(final TransactionEntity transaction) {
        return transactionRepository.save(transaction);
    }

    /**
     * Buscar por ClientId.
     * @param clientId Variable de cliente.
     * @return RettransactionRepositoryorna del clientId.
     */
    @Override
    public Flux<TransactionEntity> getTransactionsByClientId(final String clientId) {
        return transactionRepository.findByClientId(clientId);
    }

    /**
     * Buscar por ClientId.
     * @param clientId Variable de cliente.
     * @return summaryResponse para SummaryResponse.
     */

    @Override
    public Mono<SummaryResponse> transactionsSummaryDailyClientIdGet(final String clientId) {
        LocalDateTime currentDate = LocalDateTime.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        WebClient webClient = webClientBuilder.baseUrl("http://localhost:8081").build();

        return webClient.post()
                .uri("/accounts/byList")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(Arrays.asList(clientId)))
                .retrieve()
                .bodyToFlux(AccountDetails.class)
                .collectList()
                .flatMapMany(accounts -> Flux.fromIterable(accounts).flatMap(account -> getTransactionsByClientId(clientId)
                        .filter(transaction ->
                                transaction.getTransactionDate().getMonthValue() == currentMonth &&
                                        transaction.getTransactionDate().getYear() == currentYear
                        )
                        .collectList()
                        .map(transactions -> {
                            Map<OffsetDateTime, Double> dailyBalances = transactions.stream()
                                    .map(TransactionMapper::toModel)
                                    .collect(Collectors.groupingBy(
                                            Transaction::getTransactionDate,
                                            Collectors.summingDouble(Transaction::getAmount)
                                    ));

                            double averageDailyBalance = dailyBalances.values().stream()
                                    .mapToDouble(balance -> balance)
                                    .average()
                                    .orElse(0.0);

                            Summary summary = new Summary();
                            summary.setAccountId(account.getId());
                            summary.setAverageDailyBalance(averageDailyBalance);
                            return summary;
                        })))
                .collectList()
                .map(summaries -> {
                    SummaryResponse summaryResponse = new SummaryResponse();
                    summaryResponse.setClientId(clientId);
                    summaryResponse.setSummaries(summaries);
                    return summaryResponse;
                });
    }

    public Mono<Report> getTransactionsReportByClientId(String clientId) {
        Mono<List<AccountDetails>> accountsMono = cuentasRestClient.getAccountDetailsByClientId(clientId)
                .collectList();

        Mono<List<CreditCardDetails>> creditCardsMono = tarjetasRestClient.getCreditCardsByClientId(clientId)
                .collectList();

        return Mono.zip(accountsMono, creditCardsMono)
                .map(tuple -> {
                    Report report = new Report();
                    report.setClientId(clientId);
                    report.setAccounts(tuple.getT1());
                    report.setCreditCards(tuple.getT2());
                    return report;
                })
                .defaultIfEmpty(new Report().clientId(clientId));
    }
}

