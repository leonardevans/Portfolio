package com.lemutugi.model;

import com.lemutugi.audit.Auditable;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class Location extends Auditable<String> {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private int postalCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private long latitude;

    @Column(nullable = false)
    private long longitude;

    @Column(nullable = false)
    private String fullAddress;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",  referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private Long userId;

    public Location() {
    }
}
