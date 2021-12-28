package com.the.hugging.team.entities;

import com.the.hugging.team.repositories.CompanyRepository;
import com.the.hugging.team.utils.Session;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Table(name = "invoices", indexes = {
        @Index(name = "fk_created_by_idx", columnList = "created_by")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Invoice {
    @Column(name = "buyer", nullable = false)
    String buyer;
    @Column(name = "seller", nullable = false)
    String seller;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;
    @Column(name = "base_price", nullable = false)
    private Double basePrice;
    @Column(name = "dds", nullable = false)
    private Double dds;
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;
    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;
    @ManyToOne(optional = false)
    @JoinColumn(name = "company_one_id", nullable = false)
    private Company companyOne;
    @ManyToOne(optional = false)
    @JoinColumn(name = "company_two_id", nullable = false)
    private Company companyTwo;

    @PrePersist
    public void prePersist() {
        if (createdBy == null) {
            createdBy = Session.getInstance().getUser();
        }

        createdAt = new Timestamp(System.currentTimeMillis());

        if (companyOne != null && companyOne.getId() == null) {
            CompanyRepository.getInstance().save(companyOne);
        }

        if (companyTwo != null && companyTwo.getId() == null) {
            CompanyRepository.getInstance().save(companyTwo);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Invoice invoice = (Invoice) o;
        return id != null && Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totalPrice, createdAt, createdBy);
    }
}