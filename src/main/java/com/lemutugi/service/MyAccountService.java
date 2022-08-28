package com.lemutugi.service;

import com.lemutugi.model.Location;
import com.lemutugi.model.User;
import com.lemutugi.payload.dto.MyAccountDto;
import com.lemutugi.payload.dto.UserDto;
import com.lemutugi.payload.request.LocationRequest;

public interface MyAccountService {
    boolean isMyPassword(String password);

    User updatePassword(String password);

    Location updateLocation(LocationRequest locationRequest);

    Location getMyLocation();

    User getMyDetails();

    User updateMyDetails(MyAccountDto myAccountDto);

    boolean deleteMyLocation();
}
