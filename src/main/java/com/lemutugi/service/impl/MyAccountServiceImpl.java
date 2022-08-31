package com.lemutugi.service.impl;

import com.lemutugi.model.Location;
import com.lemutugi.model.User;
import com.lemutugi.payload.dto.MyAccountDto;
import com.lemutugi.payload.request.LocationRequest;
import com.lemutugi.payload.request.account.ProfilePictureRequest;
import com.lemutugi.repository.LocationRepository;
import com.lemutugi.repository.UserRepository;
import com.lemutugi.security.AuthUtil;
import com.lemutugi.service.FileUploadService;
import com.lemutugi.service.MyAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MyAccountServiceImpl implements MyAccountService {
    private UserRepository userRepository;
    private AuthUtil authUtil;
    private PasswordEncoder passwordEncoder;
    private User loggedInUser;
    private LocationRepository locationRepository;
    private FileUploadService fileUploadService;

    @Autowired
    public MyAccountServiceImpl(UserRepository userRepository, AuthUtil authUtil, PasswordEncoder passwordEncoder, LocationRepository locationRepository, FileUploadService fileUploadService) {
        this.userRepository = userRepository;
        this.authUtil = authUtil;
        this.passwordEncoder = passwordEncoder;
        this.locationRepository = locationRepository;
        this.fileUploadService = fileUploadService;
    }

    @Override
    public boolean isMyPassword(String password){
        loggedInUser = authUtil.getLoggedInUser();
        if (passwordEncoder.matches(password, loggedInUser.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public User updatePassword(String password){
        loggedInUser = authUtil.getLoggedInUser();
        loggedInUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(loggedInUser);
    }

    @Override
    public Location updateLocation(LocationRequest locationRequest){
        loggedInUser = authUtil.getLoggedInUser();
        Location location = locationRepository.findByUserId(loggedInUser.getId()).orElse(null);
        if (location == null){
            location = new Location();
            location.setUser(loggedInUser);
        }
        location.setData(locationRequest);

        return locationRepository.save(location);
    }

    @Override
    public String updateProfilePic(ProfilePictureRequest profilePictureRequest) throws IOException {
        loggedInUser = authUtil.getLoggedInUser();

        String filename =  fileUploadService.uploadToLocal(profilePictureRequest.getProfilePic(),"uploads/images/profile/");

        if (filename == null){
            return  null;
        }

        if(loggedInUser.getProfilePic() != null){
            fileUploadService.deleteLocalFile(loggedInUser.getProfilePic().substring(1));
        }

        loggedInUser.setProfilePic("/" + filename);
        userRepository.save(loggedInUser);
        return  filename;
    }

    @Override
    public Location getMyLocation(){
        loggedInUser = authUtil.getLoggedInUser();
        return locationRepository.findByUserId(loggedInUser.getId()).orElse(null);
    }

    @Override
    public User getMyDetails(){
        loggedInUser = authUtil.getLoggedInUser();
        return loggedInUser;
    }

    @Override
    public User updateMyDetails(MyAccountDto myAccountDto) {
        loggedInUser = authUtil.getLoggedInUser();
        loggedInUser.update(myAccountDto);
        return userRepository.save(loggedInUser);
    }

    @Override
    public boolean deleteMyLocation(){
        loggedInUser = authUtil.getLoggedInUser();
        Location location = locationRepository.findByUserId(loggedInUser.getId()).orElse(null);

        try{
            if (location != null){
                locationRepository.deleteById(location.getId());
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteProfilePic(){
        loggedInUser = authUtil.getLoggedInUser();
        boolean deleted = false;

        try{
            if (loggedInUser.getProfilePic() == null){
                deleted = true;
            }
            else if (fileUploadService.deleteLocalFile(loggedInUser.getProfilePic().substring(1))){
                loggedInUser.setProfilePic(null);
                userRepository.save(loggedInUser);
                deleted = true;
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return deleted;
    }
}
