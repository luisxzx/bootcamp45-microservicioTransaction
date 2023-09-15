package com.example.demo.domain.document;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

/**
 * Clase que representa una entidad de TransactionEntity.
 */
@Data
@Document(collection = "transactions")
public class TransactionEntity {
    /**
     * Identificador único de la id.
     */
    @Id
    private String id;

    /**
     * Identificador único de la clientId.
     */
    private String clientId;

    /**
     * Identificador único de la sourceType.
     */

    private String idProductTypeSource;

    /**
     * Identificador único de la sourceNumber.
     */
    private String sourceNumber;

    /**
     * Identificador único de la destinyType.
     */
    private String idProductTypeDestiny;

    /**
     * Identificador único de la destinyNumber.
     */
    private String destinyNumber;

    /**
     * Identificador único de la amount.
     */
    private Double amount;

    /**
     * Identificador único de la description.
     */
    private String description;

    /**
     * Identificador único de la transacti  onDate.
     */
    private LocalDateTime transactionDate;

    /**
     * Identificador único de la transactionType.
     */

    private String idTransactionType;
}

