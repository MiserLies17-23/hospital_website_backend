package com.hospital.hospital_website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserResponseDTO {
    private Long id;

    private String username;

    private String password;

    private String email;

    private String role;
}
