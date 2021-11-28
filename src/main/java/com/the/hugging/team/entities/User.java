package com.the.hugging.team.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Table(name = "users", indexes = {
        @Index(name = "fk_role_id_idx", columnList = "role_id"),
        @Index(name = "username_UNIQUE", columnList = "username", unique = true),
        @Index(name = "fk_created_by_idx", columnList = "created_by"),
        @Index(name = "fk_updated_by_idx", columnList = "updated_by")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "sex", nullable = false)
    private Integer sex;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @ManyToOne(optional = false)
    @JoinColumn(name = "updated_by", nullable = false)
    private User updatedBy;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstName, lastName, role, sex, createdAt, updatedAt, createdBy, updatedBy);
    }

    public boolean can(String permission) {
        Set<Permission> rolePermissions = role.getPermissions();

        return rolePermissions.stream().map(Permission::getSlug).toList().contains(permission);
    }

    public boolean can(List<String> permissions) {
        Set<Permission> rolePermissions = role.getPermissions();

        return rolePermissions.stream().map(Permission::getSlug).toList().containsAll(permissions);
    }
}