package com.the.hugging.team.entities;

import com.the.hugging.team.utils.Session;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Table(name = "activities", indexes = {
        @Index(name = "fk_user_id_idx", columnList = "user_id"),
        @Index(name = "fk_activity_type_id_idx", columnList = "activity_type_id")
})
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "activity_type_id", nullable = false)
    private ActivityType activityType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null)
            createdAt = new Timestamp(System.currentTimeMillis());

        if (user == null)
            user = Session.getInstance().getUser();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Activity activity = (Activity) o;
        return id != null && Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, activityType, user, createdAt);
    }
}