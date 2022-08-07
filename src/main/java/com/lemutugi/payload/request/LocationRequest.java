package com.lemutugi.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class LocationRequest {
    @NotBlank
    private int postalCode;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    private long latitude;

    @NotBlank
    private long longitude;

    private String fullAddress;
}
