package com.example.demo.api.controller;
import com.example.demo.api.TransactionsApiDelegate;
import com.example.demo.application.ITransactionService;
import com.example.demo.common.mapper.TransactionMapper;
import com.example.demo.model.SummaryResponse;
import com.example.demo.model.Transaction;
import com.example.demo.domain.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class Delegate implements TransactionsApiDelegate {
    /**
     *  Llamada al TransactionService.
     */
    @Autowired
    private ITransactionService transactionService;

    /**
     * Método para transacción de cliente.
     * @param clientId busqueda de transaccion.
     * @return Retorna apiModelTransactions.
     */
    @Override
    public Mono<ResponseEntity<Flux<Transaction>>> transactionsClientClientIdGet(String clientId, ServerWebExchange exchange) {
        return transactionService.getTransactionsByClientId(clientId)
                .map(TransactionMapper::toModel)
                .collectList()
                .flatMap(transactions -> {
                    if (!transactions.isEmpty()) {
                        return Mono.just(ResponseEntity.ok(Flux.fromIterable(transactions)));
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });
    }

    /**
     * Método para transacción.
     * @param transaction busqueda de transaccion.
     * @return Retorna apiModelTransactions.
     */
    @Override
    public Mono<ResponseEntity<Transaction>> transactionsPost(Mono<Transaction> transaction, ServerWebExchange exchange) {
        return transaction
                .map(TransactionMapper::toEntity)
                .flatMap(transactionService::saveTransaction) // Assumed to return Mono<TransactionEntity>
                .map(TransactionMapper::toModel)
                .flatMap(savedTransaction -> {
                    if (savedTransaction != null) {
                        return Mono.just(ResponseEntity.ok(savedTransaction));
                    } else {
                        return Mono.just(ResponseEntity.unprocessableEntity().build());
                    }
                });
    }

    /**
     * Método para SummaryResponse.
     * @param clientId variable.
     * @return TransactionsApiDelegate de cliente.
     */
    @Override
    public Mono<ResponseEntity<SummaryResponse>> transactionsSummaryDailyClientIdGet(String clientId, ServerWebExchange exchange) {
        return TransactionsApiDelegate.super.transactionsSummaryDailyClientIdGet(clientId, exchange);
    }
}

