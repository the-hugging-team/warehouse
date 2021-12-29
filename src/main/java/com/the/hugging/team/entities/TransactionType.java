package com.the.hugging.team.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Table(name = "transaction_types")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class TransactionType implements Serializable {

    public static final String SELL = "transaction_types.sell";
    public static final String DELIVERY = "transaction_types.buy";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @NaturalId
    @Column(name = "slug", nullable = false, length = 45)
    private String slug;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TransactionType that = (TransactionType) o;
        return slug != null && Objects.equals(slug, that.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug);
    }
}