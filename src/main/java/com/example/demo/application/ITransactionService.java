package com.example.demo.application;

import com.example.demo.domain.document.TransactionEntity;
import com.example.demo.model.SummaryResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ITransactionService {
    Mono<TransactionEntity> saveTransaction(final TransactionEntity transaction);
    Flux<TransactionEntity> getTransactionsByClientId(final String clientId);
    Mono<SummaryResponse> transactionsSummaryDailyClientIdGet(final String clientId);
}
