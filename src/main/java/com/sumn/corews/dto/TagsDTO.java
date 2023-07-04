package com.sumn.corews.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TagsDTO {
    private String tagId;
    private String tag;
    private List<String> bookmarkIds;
    private String userId;

    private void addBookmarkId(String bookmarkId){
        this.bookmarkIds.add(bookmarkId);
    }
}
