package com.lemutugi.payload.request.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class ProfilePictureRequest {
//    @NotBlank(message = "imageUrl is required")
    private String imageUrl;

//    @NotBlank(message = "Please select a file to upload")
    private MultipartFile profilePic;
}
