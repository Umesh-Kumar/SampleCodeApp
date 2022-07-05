package com.samplecodeapp.roomdb.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.samplecodeapp.roomdb.dao.BhagwatGeetaDao;
import com.samplecodeapp.roomdb.model.BhagavadGita;

import org.json.JSONException;
import org.json.JSONObject;


@Database(version = 1, entities = {BhagavadGita.class}, exportSchema = false)
public abstract class RoomDataBase extends RoomDatabase {

    private static RoomDataBase instance;

    public abstract BhagwatGeetaDao bhagwatGeetaDao();

    public static synchronized RoomDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), RoomDataBase.class, "bhagwat_geeta_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(roomCllBack)
                    .build();
        }
        return instance;
    }

    private static Callback roomCllBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
//            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private BhagwatGeetaDao bhagwatGeetaDao;

        private PopulateDbAsyncTask(RoomDataBase roomDataBase) {
            bhagwatGeetaDao = roomDataBase.bhagwatGeetaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                JSONObject jsonObject = new JSONObject("");
//                JSONArray jsonArray = jsonObject.getJSONArray("hindi");
                for (int j = 1; j <= jsonObject.length(); j++) {
                    // "श्रीमद भागवत गीता"
//                    bhagwatGeetaDao.insert(new BhagavadGita(/*"श्रीमद भागवत गीता"*/ jsonObject.getString("title"), jsonObject.getString("description"),
//                            jsonObject.getString("chapter"), jsonObject.getString("heading"),
//                            "" + jsonObject.getJSONArray("hindi"), j, 0));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
