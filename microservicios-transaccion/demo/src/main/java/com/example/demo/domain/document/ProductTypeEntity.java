package com.example.demo.domain.document;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa una entidad de ProductTypeEntity.
 */
@Data
@Document(collection = "productTypes")
public class ProductTypeEntity {
    /**
     * Identificador único de la idProductType.
     */
    @Id
    private String idProductType;

    /**
     * Identificador único de la name.
     */
    private String name;

    /**
     * Identificador único de la description.
     */
    private String description;
}

