package com.lemutugi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lemutugi.audit.Auditable;
import com.lemutugi.payload.request.LocationRequest;
import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class Location extends Auditable<String> {
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private int postalCode;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column(nullable = false)
    private String fullAddress;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "user_id",  referencedColumnName = "id")
    private User user;

    public Location setData(LocationRequest locationRequest){
        this.setPostalCode(locationRequest.getPostalCode());
        this.setCountry(locationRequest.getCountry());
        this.setCity(locationRequest.getCity());
        this.setLatitude(locationRequest.getLatitude());
        this.setLongitude(locationRequest.getLongitude());
        this.setFullAddress(locationRequest.getFullAddress());
        return this;
    }
}
