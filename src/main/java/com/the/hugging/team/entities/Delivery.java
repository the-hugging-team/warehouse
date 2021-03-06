package com.the.hugging.team.entities;

import com.the.hugging.team.repositories.InvoiceRepository;
import com.the.hugging.team.repositories.TransactionRepository;
import com.the.hugging.team.utils.Session;
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

@Table(name = "deliveries", indexes = {
        @Index(name = "fk_created_by_idx", columnList = "created_by")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
    @OneToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @OneToMany(mappedBy = "delivery")
    @ToString.Exclude
    private Set<DeliveryProduct> deliveryProducts = new HashSet<>();

    @PrePersist
    public void prePersist() {
        createdAt = new Timestamp(System.currentTimeMillis());
        createdBy = Session.getInstance().getUser();

        if (invoice != null && invoice.getId() == null) {
            InvoiceRepository.getInstance().save(invoice);
        }

        if (transaction != null && transaction.getId() == null) {
            TransactionRepository.getInstance().save(transaction);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Delivery delivery = (Delivery) o;
        return id != null && Objects.equals(id, delivery.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoice, createdAt, createdBy);
    }
}