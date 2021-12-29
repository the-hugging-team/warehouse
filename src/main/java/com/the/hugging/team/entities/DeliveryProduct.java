package com.the.hugging.team.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "delivery_has_products", indexes = {
        @Index(name = "fk_product_quantity_type_id_idx", columnList = "product_quantity_type_id"),
        @Index(name = "fk_product_id_idx", columnList = "product_id"),
        @Index(name = "fk_delivery_id_idx", columnList = "delivery_id")
})
public class DeliveryProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Double quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_quantity_type_id", nullable = false)
    private ProductQuantityType productQuantityType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryProduct that = (DeliveryProduct) o;
        return id.equals(that.id) && quantity.equals(that.quantity) && productQuantityType.equals(that.productQuantityType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, productQuantityType);
    }
}
