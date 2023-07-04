package com.sumn.corews.dto;


import lombok.*;
import org.apache.catalina.User;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDTO {
    private String url;
    private List<String> tags;
    private String bookmarkId;
    private String baseUrl;
    @Setter
    private UserDTO user;
}
