package com.example.demo.domain.document;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa una entidad de TransactionTypeEntity.
 */
@Data
@Document(collection = "transactionTypes")
public class TransactionTypeEntity {
    /**
     * Identificador único de la idTransactionType.
     */
    @Id
    private String idTransactionType;

    /**
     * Identificador del cliente asociado a la name.
     */
    private String name;

    /**
     * Identificador del cliente asociado a la description.
     */
    private String description;
}

