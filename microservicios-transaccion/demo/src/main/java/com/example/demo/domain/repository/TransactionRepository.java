package com.example.demo.domain.repository;
import com.example.demo.domain.document.TransactionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

/**
 * Interfaz que define un repositorio para operaciones con las transacciones.
 * */
public interface TransactionRepository extends ReactiveMongoRepository<TransactionEntity, String> {
    /**
     * Busca y devuelve una lista de transacciones para un cliente específico.
     * @param clientId El identificador del cliente para las transacciones.
     * @return Una lista de transacciones asociadas al cliente.
     */
    Flux<TransactionEntity> findByClientId(String clientId);

    /**
     * Busca y devuelve una lista de transacciones para un cliente específico.
     * @param sourceAccountNumber variable de sourceAccountNumber.
     * @return Una lista de transacciones TransactionEntity.
     */
    Flux<TransactionEntity> findBySourceNumber(String sourceAccountNumber);
}

