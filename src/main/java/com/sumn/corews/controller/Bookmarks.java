package com.sumn.corews.controller;

import com.sumn.corews.dto.BookmarkDTO;
import com.sumn.corews.dto.UserDTO;
import com.sumn.corews.dao.BookmarkDAO;
import com.sumn.corews.service.BookmarkService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class Bookmarks {

    private BookmarkService bookmarkService;

    public Bookmarks(BookmarkService bookmarkService){
        this.bookmarkService = bookmarkService;
    }

    @PostMapping("/bookmark")
    public ResponseEntity<Void> addBookMark(@RequestBody BookmarkDTO bookmarkDTO, HttpServletRequest request){
        UserDTO currentUserDTO = (UserDTO) request.getAttribute("userDetails");
        bookmarkDTO.setUser(currentUserDTO);
        return bookmarkService.createBookmark(bookmarkDTO.getUrl(),bookmarkDTO.getTags(),currentUserDTO)? new ResponseEntity<>(HttpStatus.OK):  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/bookmark")
    public ResponseEntity<List<BookmarkDTO>> getBookMark(HttpServletRequest request) throws ExecutionException, InterruptedException {
        UserDTO currentUserDTO = (UserDTO) request.getAttribute("userDetails");
        List<BookmarkDTO> bookmarks = bookmarkService.getBookmarksByUser(currentUserDTO);
        return bookmarks != null? new ResponseEntity<>(bookmarks,HttpStatus.OK): new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/bookmark/{tag}")
    public ResponseEntity<List<BookmarkDTO>> getBookmarkByTag(@PathVariable String tag,HttpServletRequest request){
        UserDTO currentUserDTO = (UserDTO) request.getAttribute("userDetails");
        List<BookmarkDTO> bookmarks = bookmarkService.getBookmarkByUserTags(currentUserDTO,tag);
        return bookmarks != null? new ResponseEntity<>(bookmarks,HttpStatus.OK): new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
