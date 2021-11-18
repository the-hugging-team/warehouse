package com.the.hugging.team.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Role implements Serializable {
    public static final String ROLE_ADMIN = "roles.admin";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 45)
    private String name;

    @NaturalId
    @Column(name = "slug", nullable = false, length = 45)
    private String slug;

    @ManyToMany
    @JoinTable(
            name = "role_has_permissions",
            joinColumns = { @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id") }
    )
    @ToString.Exclude
    private Set<Permission> permissions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return slug != null && Objects.equals(slug, role.slug);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slug);
    }
}