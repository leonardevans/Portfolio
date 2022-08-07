package com.lemutugi.payload.request.account;

import org.springframework.web.multipart.MultipartFile;

public class ProfilePictureRequest {
//    @NotBlank(message = "imageUrl is required")
    private String imageUrl;

//    @NotBlank(message = "Please select a file to upload")
    private MultipartFile profilePic;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MultipartFile getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(MultipartFile profilePic) {
        this.profilePic = profilePic;
    }
}
