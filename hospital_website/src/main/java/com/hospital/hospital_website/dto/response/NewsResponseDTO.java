package com.hospital.hospital_website.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsResponseDTO {

    private long id;

    private String title;

    private String content;

    private String date;
}
