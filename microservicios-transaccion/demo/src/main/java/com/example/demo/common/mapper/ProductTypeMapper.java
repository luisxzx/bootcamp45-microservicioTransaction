package com.example.demo.common.mapper;
import com.example.demo.domain.document.ProductTypeEntity;
import com.example.demo.model.ProductType;
import org.springframework.beans.BeanUtils;

public class ProductTypeMapper {
    /**
     * Método para ProductTypeMapper.
     * @param model parametro de ProductType.
     * @return ProductTypeEntity.
     */
    public static ProductTypeEntity toEntity(final ProductType model) {
        ProductTypeEntity entity = new ProductTypeEntity();
        BeanUtils.copyProperties(model, entity);
        return entity;
    }

    /**
     * Método para ProductTypeMapper.
     * @param entity parametro de ProductType.
     * @return ProductTypeEntity.
     */
    public static ProductType toModel(final ProductTypeEntity entity) {
        ProductType model = new ProductType();
        BeanUtils.copyProperties(entity, model);
        return model;
    }
}

