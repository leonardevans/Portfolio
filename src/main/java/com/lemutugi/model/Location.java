package com.lemutugi.model;

import com.lemutugi.audit.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@NoArgsConstructor
@Getter
@Setter
public class Location extends Auditable<Long> {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private int postalCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private long latitude;

    @Column(nullable = false)
    private long longitude;

    @Column(nullable = false)
    private String fullAddress;
}
