package com.sumn.corews.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.sumn.corews.dto.TagsDTO;
import com.sumn.corews.service.HashService;
import io.opencensus.tags.Tag;
import io.opencensus.tags.Tags;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TagsDAO {

    private Firestore dbStore;

    public TagsDAO(Firestore dbStore, HashService hashService){
        this.dbStore = dbStore;

    }

    public boolean createTag(TagsDTO newTag){
        dbStore.runTransaction(t->{
            DocumentReference documentReference = dbStore.collection("Tags").document(newTag.getTagId());
            DocumentSnapshot documentSnapshot = t.get(documentReference).get();
            if(documentSnapshot.exists()){
                t.update(documentReference,"bookmarksIds", FieldValue.arrayUnion(newTag.getBookmarkIds().get(0)));
            }
            else{
                t.set(documentReference,newTag);
            }
            return null;
        });
        return true;
    }

    public TagsDTO getTagsByName(String tagId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = dbStore.collection("Tags").document(tagId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot snapshot = future.get();
        return snapshot.toObject(TagsDTO.class);
    }

}
