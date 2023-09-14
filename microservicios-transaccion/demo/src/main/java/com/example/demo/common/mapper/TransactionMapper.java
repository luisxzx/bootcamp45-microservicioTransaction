package com.example.demo.common.mapper;
import com.example.demo.domain.document.TransactionEntity;
import com.example.demo.model.Transaction;
import org.springframework.beans.BeanUtils;
import java.time.OffsetDateTime;
public class TransactionMapper {

    /**
     * Método para TransactionMapper.
     * @param model parametro de Transaction.
     * @return entity.
     */
    public static TransactionEntity toEntity(final Transaction model) {
        TransactionEntity entity = new TransactionEntity();
        BeanUtils.copyProperties(model, entity);

        // Convertir la fecha de OffsetDateTime a LocalDateTime
        if (model.getTransactionDate() != null) {
            entity.setTransactionDate(model.getTransactionDate().toLocalDateTime());
        }
        return entity;
    }

    /**
     * Método para TransactionMapper.
     * @param entity parametro de TransactionEntity.
     * @return model.
     */
    public static Transaction toModel(final TransactionEntity entity) {
        Transaction model = new Transaction();
        BeanUtils.copyProperties(entity, model);

        // Convertir la fecha de LocalDateTime a OffsetDateTime
        if (entity.getTransactionDate() != null) {
            model.setTransactionDate(entity.getTransactionDate().atOffset(OffsetDateTime.now().getOffset()));
        }
        return model;
    }
}

