package com.samplecodeapp.roomdb.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.samplecodeapp.roomdb.db.RoomDataBase;
import com.samplecodeapp.roomdb.dao.BhagwatGeetaDao;
import com.samplecodeapp.roomdb.model.BhagavadGita;

import java.util.List;

public class BhagwatGeetaRepository {

    private BhagwatGeetaDao bhagwatGeetaDao;
    private LiveData<List<BhagavadGita>> allNotes;
    private List<BhagavadGita> allNotesCount;

    public BhagwatGeetaRepository(Application application) {
        RoomDataBase roomDataBase = RoomDataBase.getInstance(application);
        bhagwatGeetaDao = roomDataBase.bhagwatGeetaDao();
        allNotes = bhagwatGeetaDao.getAllNotes();
        allNotesCount = bhagwatGeetaDao.getAllNotesCount();
    }

    public void insert (BhagavadGita bhagavadGita) {
        new InsertNoteAsyncTask(bhagwatGeetaDao).execute(bhagavadGita);
    }

    public void update (BhagavadGita bhagavadGita) {
        new UpdateNoteAsyncTask(bhagwatGeetaDao).execute(bhagavadGita);
    }

    public void delete (BhagavadGita bhagavadGita) {
        new DeleteNoteAsyncTask(bhagwatGeetaDao).execute(bhagavadGita);
    }

    public void deleteAllNotes () {
        new DeleteAllNotesAsyncTask(bhagwatGeetaDao).execute();
    }

    public LiveData<List<BhagavadGita>> getAllNotes() {
        return allNotes;
    }

    public List<BhagavadGita> getAllNotesCount() {
        return allNotesCount;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<BhagavadGita, Void, Void> {

        private BhagwatGeetaDao bhagwatGeetaDao;

        private InsertNoteAsyncTask (BhagwatGeetaDao bhagwatGeetaDao) {
            this.bhagwatGeetaDao = bhagwatGeetaDao;
        }

        @Override
        protected Void doInBackground(BhagavadGita... bhagavadGitas) {
            bhagwatGeetaDao.insert(bhagavadGitas[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<BhagavadGita, Void, Void> {

        private BhagwatGeetaDao bhagwatGeetaDao;

        private UpdateNoteAsyncTask (BhagwatGeetaDao bhagwatGeetaDao) {
            this.bhagwatGeetaDao = bhagwatGeetaDao;
        }

        @Override
        protected Void doInBackground(BhagavadGita... bhagavadGitas) {
            bhagwatGeetaDao.update(bhagavadGitas[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<BhagavadGita, Void, Void> {

        private BhagwatGeetaDao bhagwatGeetaDao;

        private DeleteNoteAsyncTask (BhagwatGeetaDao bhagwatGeetaDao) {
            this.bhagwatGeetaDao = bhagwatGeetaDao;
        }

        @Override
        protected Void doInBackground(BhagavadGita... bhagavadGitas) {
            bhagwatGeetaDao.delete(bhagavadGitas[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {

        private BhagwatGeetaDao bhagwatGeetaDao;

        private DeleteAllNotesAsyncTask (BhagwatGeetaDao bhagwatGeetaDao) {
            this.bhagwatGeetaDao = bhagwatGeetaDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            bhagwatGeetaDao.deleteAllNotes();
            return null;
        }
    }
}
