package com.samplecodeapp.roomdb.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.samplecodeapp.roomdb.model.BhagavadGita;

import java.util.List;

@Dao
public interface BhagwatGeetaDao {

    @Insert
    void insert(BhagavadGita bhagavadGita);

    @Update
    void update(BhagavadGita bhagavadGita);

    @Delete
    void delete(BhagavadGita bhagavadGita);

    @Query("DELETE FROM bhagavad_gita_table")
    void deleteAllNotes();

    @Query("SELECT * FROM bhagavad_gita_table ORDER BY priority ASC")
    LiveData<List<BhagavadGita>> getAllNotes();

    @Query("SELECT * FROM bhagavad_gita_table ORDER BY priority DESC")
    List<BhagavadGita> getAllNotesCount();

    @Query("SELECT * FROM bhagavad_gita_table ORDER BY priority ASC LIMIT 20")
    List<BhagavadGita> getAllNotesN();

}
