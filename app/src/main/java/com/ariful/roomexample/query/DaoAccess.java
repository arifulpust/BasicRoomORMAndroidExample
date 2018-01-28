package com.ariful.roomexample.query;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ariful.roomexample.model.University;

import java.util.List;

/**
 * Created by Dream71 on 28/01/2018.
 */

@Dao
public interface DaoAccess {

    @Insert
    void insertMultipleRecord(University... universities);

    @Insert
    void insertMultipleListRecord(List<University> universities);
    @Insert
    void insertOnlySingleRecord(University university);

    @Query("SELECT * FROM University")
    List<University> fetchAllData();

    @Query("SELECT * FROM University WHERE clgid =:college_id")
    University getSingleRecord(int college_id);

    @Update
    void updateRecord(University university);


    @Delete
    void deleteRecord(University university);

    @Query("SELECT COUNT(*) from University")
    int countUsers();
}