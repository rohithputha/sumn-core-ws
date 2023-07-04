package com.sumn.corews.service;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.sumn.corews.dao.BookmarkDAO;
import com.sumn.corews.dto.BookmarkDTO;
import com.sumn.corews.dto.TagsDTO;
import com.sumn.corews.dto.UserDTO;
import io.opencensus.tags.TagsComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class BookmarkService {
    private HashService hashService;
    private BookmarkDAO bookmarkDAO;
    private TagService tagService;

    public BookmarkService(HashService hashService, BookmarkDAO bookmarkDAO, TagService tagService){
        this.hashService = hashService;
        this.bookmarkDAO = bookmarkDAO;
        this.tagService = tagService;
    }

    public boolean createBookmark(String url, List<String> tags, UserDTO userDTO)  {
        try{
            URL fullUrl = new URL(url);
            String baseUrl = fullUrl.getProtocol()+"://"+fullUrl.getHost();
            BookmarkDTO newBookmark = BookmarkDTO.builder()
                    .url(url)
                    .baseUrl(baseUrl)
                    .bookmarkId(hashService.getHashVal(url+userDTO.getUserId()))
                    .tags(tags)
                    .user(userDTO)
                    .build();
            bookmarkDAO.createBookmark(newBookmark);
            tags.stream().forEach(s->{
                tagService.createTag(s, Arrays.asList(newBookmark), userDTO);
            });

            return true;
        }
        catch (MalformedURLException me){
            log.error("Error parsing url");
            log.error(me.getMessage());
            return false;
        }
        catch (Exception e){
            log.error("Error saving bookmark");
            log.error(e.getMessage());
            return false;
        }
    }

    public List<BookmarkDTO> getBookmarksByUser(UserDTO userDTO){
        try{
            return bookmarkDAO.getBookmarkByUser(userDTO);
        }
        catch (Exception e){
            log.error("Error getting bookmarks by user");
            log.error(e.getMessage());
            return null;
        }

    }

    public List<BookmarkDTO> getBookmarkByUserTags(UserDTO user, String tag){
        try {
            TagsDTO tagsDTO = tagService.getTagsByName(tag, user);
            List<BookmarkDTO> resultBookmarks = new ArrayList<>();
            for (String bookmarkId: tagsDTO.getBookmarkIds() ){
                resultBookmarks.add(bookmarkDAO.getBookmarkById(bookmarkId));
            }
            return resultBookmarks;
        }
        catch (Exception e){
            log.error("Error fetching bookmarks by tag name");
            log.error(e.getMessage());
            return null;
        }
    }
}
