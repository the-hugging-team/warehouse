package com.the.hugging.team.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "notification_types", indexes = {
        @Index(name = "fk_notification_type_notification_template_id_idx", columnList = "notification_template_id")
})
public class NotificationType {
    public static final String PRODUCT_OUT_OF_STOCK = "notification_types.product_out_of_stock";
    public static final String PRODUCT_REACHED_MIN = "notification_types.product_reached_minimum_amount";
    public static final String CASH_REGISTER_OUT_OF_MONEY = "notification_types.cash_register_out_of_money";
    public static final String CASH_REGISTER_REACHED_MIN = "notification_types.cash_register_reached_minimum_amount";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "slug", nullable = false, length = 45)
    private String slug;

    @ManyToOne(optional = false)
    @JoinColumn(name = "notification_template_id", nullable = false)
    private NotificationTemplate notificationTemplate;

    @ManyToMany(mappedBy = "notificationTypes")
    @ToString.Exclude
    private Set<Role> roles;
}