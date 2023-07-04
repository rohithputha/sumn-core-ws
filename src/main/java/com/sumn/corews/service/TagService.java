package com.sumn.corews.service;

import com.sumn.corews.dao.TagsDAO;
import com.sumn.corews.dto.BookmarkDTO;
import com.sumn.corews.dto.TagsDTO;
import com.sumn.corews.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TagService {
    private HashService hashService;
    private TagsDAO tagsDAO;
    public TagService(HashService hashService, TagsDAO tagsDAO){
        this.hashService = hashService;
        this.tagsDAO = tagsDAO;
    }
    public TagsDTO createTag(String tag, List<BookmarkDTO> bookmarks, UserDTO userDTO){
        List<String> bookmarkIds = bookmarks.stream().map(s-> s.getBookmarkId()).collect(Collectors.toList());
        TagsDTO newTag = TagsDTO.builder()
                .tagId(hashService.getHashVal(tag+userDTO.getUserId()))
                .tag(tag)
                .bookmarkIds(bookmarkIds)
                .userId(userDTO.getUserId())
                .build();
        tagsDAO.createTag(newTag);
        return newTag;
    }

    public TagsDTO getTagsByName(String tag, UserDTO user){
        try{
            return tagsDAO.getTagsByName(hashService.getHashVal(tag+user.getUserId()));
        }
        catch (Exception e){
            log.error("Tags: Error fetching tag by name");
            log.error(e.getMessage());
            return null;
        }

    }
}
