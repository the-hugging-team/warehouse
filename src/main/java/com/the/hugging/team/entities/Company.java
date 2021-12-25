package com.the.hugging.team.entities;

import com.the.hugging.team.repositories.AddressRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Table(name = "companies", indexes = {
        @Index(name = "fk_companies_address_id_idx", columnList = "address_id")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Company implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "bulstat", nullable = false, length = 13)
    private String bulstat;

    @Column(name = "dds_number", nullable = false, length = 15)
    private String ddsNumber;

    @Column(name = "mol", nullable = false)
    private String mol;

    @PrePersist
    public void prePersist() {
        if (address != null && address.getId() == null) {
            AddressRepository.getInstance().save(address);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return id.equals(company.id) && name.equals(company.name) && address.equals(company.address) && bulstat.equals(company.bulstat) && ddsNumber.equals(company.ddsNumber) && mol.equals(company.mol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, bulstat, ddsNumber, mol);
    }
}