package com.sumn.corews.dao;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.sumn.corews.dto.UserDTO;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserDAO {

    private Firestore dbStore;
    public UserDAO(Firestore dbStore){
        this.dbStore = dbStore;
    }
    public boolean upsertUser(UserDTO currentUserDTO){
        Firestore dbStore = FirestoreClient.getFirestore();
        DocumentReference docRef = dbStore.collection("Users").document(currentUserDTO.getUserId());
        docRef.set(currentUserDTO);
        return true;
    }
    public boolean logoutUser(HttpServletRequest request) throws ServletException {
        request.logout();
        return true;
    }
}
