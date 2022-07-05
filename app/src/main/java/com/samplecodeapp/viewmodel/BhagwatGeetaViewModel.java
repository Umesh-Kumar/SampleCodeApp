package com.samplecodeapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.samplecodeapp.roomdb.model.BhagavadGita;
import com.samplecodeapp.roomdb.repos.BhagwatGeetaRepository;

import java.util.List;

public class BhagwatGeetaViewModel extends AndroidViewModel {

    private BhagwatGeetaRepository repository;
    private LiveData<List<BhagavadGita>> allNotes;
    private List<BhagavadGita> allNotesCount;

    public BhagwatGeetaViewModel(@NonNull Application application) {
        super(application);
        repository = new BhagwatGeetaRepository(application);
        allNotes = repository.getAllNotes();
        allNotesCount = repository.getAllNotesCount();
    }

    public void insert(BhagavadGita bhagavadGita) {
        repository.insert(bhagavadGita);
    }

    public void update(BhagavadGita bhagavadGita) {
        repository.update(bhagavadGita);
    }

    public void delete(BhagavadGita bhagavadGita) {
        repository.delete(bhagavadGita);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<BhagavadGita>> getAllNotes() {
        return repository.getAllNotes();
    }

    public List<BhagavadGita> allNotesCount() {
        return repository.getAllNotesCount();
    }
}
