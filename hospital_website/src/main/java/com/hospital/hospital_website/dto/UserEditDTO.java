package com.hospital.hospital_website.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class UserEditDTO {

    private Long id;

    private String username;

    private String email;
}
