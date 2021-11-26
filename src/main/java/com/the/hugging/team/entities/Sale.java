package com.the.hugging.team.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "sales", indexes = {
        @Index(name = "fk_client_id_idx", columnList = "client_id"),
        @Index(name = "fk_cash_register_id_idx", columnList = "cash_register_id"),
        @Index(name = "fk_created_by_idx", columnList = "created_by")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "invoice_id")
    private Integer invoiceId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cash_register_id", nullable = false)
    private CashRegister cashRegister;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToMany
    @JoinTable(
            name = "sales_has_products",
            joinColumns = {@JoinColumn(name = "sale_id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id")}
    )
    @ToString.Exclude
    private Set<Product> products = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Sale sale = (Sale) o;
        return id != null && Objects.equals(id, sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, invoiceId, cashRegister, createdAt, createdBy, products);
    }
}