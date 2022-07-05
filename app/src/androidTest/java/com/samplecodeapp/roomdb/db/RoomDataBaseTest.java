package com.samplecodeapp.roomdb.db;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.common.truth.Truth;
import com.samplecodeapp.roomdb.dao.BhagwatGeetaDao;
import com.samplecodeapp.roomdb.model.BhagavadGita;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/* sample unit test case */

@RunWith(AndroidJUnit4.class)
public class RoomDataBaseTest extends TestCase {

    private RoomDataBase db;
    private BhagwatGeetaDao dao;
    private Context context;

    @Before
    public void initRoomDB() {
        context = ApplicationProvider.getApplicationContext();
        db = Room.databaseBuilder(context.getApplicationContext(), RoomDataBase.class, "bhagwat_geeta_db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        dao = db.bhagwatGeetaDao();
    }

    @After
    public void closeDb() {
//        db.close();
    }

    @Test
    public void testBhagwatGeetaDao() {
        BhagavadGita bGita = new BhagavadGita("", "", "1", "", "", 1, 0);
        dao.insert(bGita);
        List<BhagavadGita> localBhagavadGita = dao.getAllNotesCount();
        Truth.assertThat(localBhagavadGita.get(0).getChapter().contains(bGita.getChapter())).isTrue();
    }

    @Test
    public void testBhagwatGeetaDaoFailure() {
        BhagavadGita bGita = new BhagavadGita("", "", "", "", "", 0, 0);
        dao.insert(bGita);
        List<BhagavadGita> localBhagavadGita = dao.getAllNotesCount();
        Truth.assertThat(localBhagavadGita.contains(bGita)).isFalse();
    }

    public void testGetInstance() {
    }


}