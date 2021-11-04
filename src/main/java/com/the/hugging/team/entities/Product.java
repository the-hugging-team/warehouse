package com.the.hugging.team.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Table(name = "products", indexes = {
        @Index(name = "fk_product_quantity_type_id_idx", columnList = "product_quantity_type_id"),
        @Index(name = "fk_room_id_idx", columnList = "room_id"),
        @Index(name = "fk_shelf_id_idx", columnList = "shelf_id"),
        @Index(name = "nomenclature_UNIQUE", columnList = "nomenclature", unique = true),
        @Index(name = "fk_product_category_id_idx", columnList = "product_category_id")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @Column(name = "nomenclature", nullable = false, length = 45)
    private String nomenclature;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategory;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_quantity_type_id", nullable = false)
    private ProductQuantityType productQuantityType;

    @Column(name = "retail_price", nullable = false)
    private Double retailPrice;

    @Column(name = "wholesale_price", nullable = false)
    private Double wholesalePrice;

    @Column(name = "delivery_price", nullable = false)
    private Double deliveryPrice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "shelf_id", nullable = false)
    private Shelf shelf;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return id != null && Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nomenclature, productCategory, quantity, productQuantityType, retailPrice, wholesalePrice, deliveryPrice, shelf);
    }
}