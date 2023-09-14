package com.example.demo.common.mapper;
import com.example.demo.domain.document.TransactionTypeEntity;
import com.example.demo.model.TransactionType;
import org.springframework.beans.BeanUtils;
public class TransactionTypeMapper {
    /**
     * Método para ProductTypeMapper.
     * @param model parametro de TransactionType.
     * @return entity por ProductTypeEntity.
     */
    public static TransactionTypeEntity toEntity(final TransactionType model) {
        TransactionTypeEntity entity = new TransactionTypeEntity();
        BeanUtils.copyProperties(model, entity);
        return entity;
    }

    /**
     * Método para ProductTypeMapper.
     * @param entity parametro de TransactionTypeEntity.
     * @return model por TransactionType.
     */
    public static TransactionType toModel(final TransactionTypeEntity entity) {
        TransactionType model = new TransactionType();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}


