package com.lemutugi.payload.request;

import com.lemutugi.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class LocationRequest {
    @NotNull(message = "Postal code is required")
    @Digits(integer = 5, fraction = 0, message = "Invalid postal code")
    @Min(value = 1)
    private Integer postalCode;

    @NotBlank(message = "Country is required")
    @Size(max = 35, min = 3, message = "Country should be at least 3 and at most 35 characters")
    private String country;

    @NotBlank(message = "City is required")
    @Size(max = 35, min = 3, message = "City should be at least 3 and at most 35 characters")
    private String city;

    @NotNull(message = "Latitude is required")
    @Digits(integer = 2, fraction = 4, message = "Invalid postal latitude")
    @Min(value = -90, message = "Invalid latitude")
    @Max(value = 90, message = "Invalid latitude")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    @Digits(integer = 3, fraction = 4, message = "Invalid longitude")
    @Min(value = -180, message = "Invalid longitude")
    @Max(value = 180, message = "Invalid longitude")
    private Double longitude;

    @NotBlank(message = "Full address is required")
    @Size( min = 10, message = "Full address should be at least 10 characters")
    private String fullAddress;

    private Long id;
}
