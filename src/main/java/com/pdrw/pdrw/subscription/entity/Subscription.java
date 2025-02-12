package com.pdrw.pdrw.subscription.entity;

import com.pdrw.pdrw.security.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "email")
@Entity
@Builder
public class Subscription {

    @Id
    @UuidGenerator
    private UUID id;

    private String email;

    @Enumerated(EnumType.STRING)
    private Format format;

    @Enumerated(EnumType.STRING)
    private Frequency frequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @Column(insertable = false)
    private Boolean active;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createDate;

    @PrePersist
    public void prePersist() {
        setCreateDate(LocalDateTime.now());
    }
}