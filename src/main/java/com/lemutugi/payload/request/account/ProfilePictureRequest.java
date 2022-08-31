package com.lemutugi.payload.request.account;

import com.lemutugi.validation.constraint.ValidImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class ProfilePictureRequest {
    @NotNull(message = "Please select a file to upload")
    @ValidImage
    private MultipartFile profilePic;
}
