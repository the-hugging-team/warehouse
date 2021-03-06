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
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "sales", indexes = {
        @Index(name = "fk_sales_invoice_id_idx", columnList = "invoice_id"),
        @Index(name = "fk_cash_register_id_idx", columnList = "cash_register_id"),
        @Index(name = "fk_sales_transaction_id_idx", columnList = "transaction_id"),
        @Index(name = "fk_created_by_idx", columnList = "created_by")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Sale implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cash_register_id", nullable = false)
    private CashRegister cashRegister;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @OneToMany(mappedBy = "sale")
    @ToString.Exclude
    private Set<SaleProduct> saleProducts = new HashSet<>();

    public String getCreatedAtFormatted() {
        return new SimpleDateFormat("dd.MM.yyyy").format(createdAt);
    }

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
        Sale sale = (Sale) o;
        return id != null && Objects.equals(id, sale.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, invoice, cashRegister, createdAt, createdBy, transaction, saleProducts);
    }
}