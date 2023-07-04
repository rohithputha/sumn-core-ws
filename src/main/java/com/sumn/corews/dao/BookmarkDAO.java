package com.sumn.corews.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.sumn.corews.dto.BookmarkDTO;
import com.sumn.corews.dto.UserDTO;
import com.sumn.corews.service.HashService;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class BookmarkDAO {
    private Firestore dbStore;

    private HashService hashService;

    public BookmarkDAO (Firestore dbStore, HashService hashService){
        this.dbStore = dbStore;
        this.hashService = hashService;
    }

    public boolean createBookmark(BookmarkDTO bookmarkDTO){
        DocumentReference docRef = this.dbStore.collection("Bookmarks").document(bookmarkDTO.getBookmarkId());
        docRef.set(bookmarkDTO);
        return true;
    }

    public List<BookmarkDTO> getBookmarkByUser(UserDTO currentUser) throws ExecutionException, InterruptedException {
        CollectionReference collectionReference = this.dbStore.collection("Bookmarks");
        Query query = collectionReference.whereEqualTo("user",currentUser);
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        QuerySnapshot querySnapshot = querySnapshotApiFuture.get();
        return querySnapshot.toObjects(BookmarkDTO.class);
    }

    public BookmarkDTO getBookmarkById(String bookmarkId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = this.dbStore.collection("Bookmarks").document(bookmarkId);
        ApiFuture<DocumentSnapshot> querySnapshotApiFuture= documentReference.get();
        DocumentSnapshot documentSnapshot = querySnapshotApiFuture.get();
        return documentSnapshot.toObject(BookmarkDTO.class);
    }
}
