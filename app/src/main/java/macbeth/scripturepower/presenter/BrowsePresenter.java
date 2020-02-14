package macbeth.scripturepower.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import macbeth.scripturepower.model.Book;
import macbeth.scripturepower.model.Chapter;
import macbeth.scripturepower.model.Library;
import macbeth.scripturepower.model.Verse;
import macbeth.scripturepower.model.Volume;

public class BrowsePresenter {
    private Library library;
    private Volume selectedVolume;
    private Book selectedBook;
    private Chapter selectedChapter;
    private List<String> validBooks;
    private List<String> validChapters;
    private List<Verse> validVerses;

    public BrowsePresenter() {
        library = null; // The library is set by the Fragment when the MainPresenter sends notification that the data is ready.
        selectedVolume = null;
        selectedBook = null;
        selectedChapter = null;
        validBooks = new ArrayList<String>();
        validChapters = new ArrayList<String>();
        validVerses = new ArrayList<Verse>();
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    // Functions for Fragment Requests

    public void selectScripture(String reference) {
        String[] split = reference.split(":");
        int verse = Integer.parseInt(split[1]);
        int posChapter = split[0].lastIndexOf(" ");
        String chapter = split[0].substring(posChapter+1);
        String book = split[0].substring(0,posChapter);
        Log.d("split",book + " : " + chapter + " : " + verse);
    }

    public void selectVolume(String volume) {
        if (library == null) {
            return;
        }
        selectedVolume = library.getVolume(volume);
        if (selectedVolume != null) {
            validBooks.clear();
            validChapters.clear();
            validVerses.clear();
            validBooks.addAll(selectedVolume.getBookNames());
        }
    }

    public void selectBook(String book) {
        if (library == null) {
            return;
        }
        if (selectedVolume == null) {
            return;
        }
        selectedBook = selectedVolume.getBook(book);
        if (selectedBook != null) {
            validChapters.clear();
            validVerses.clear();
            validChapters.addAll(selectedBook.getChapterNames());
        }
    }

    public void selectChapter(String chapterStr) {
        if (library == null) {
            return;
        }
        int chapter = 0;
        try {
            chapter = Integer.parseInt(chapterStr);
        }
        catch (NumberFormatException nfe) {
            return;
        }
        if (selectedBook == null) {
            return;
        }
        selectedChapter = selectedBook.getChapter(chapter);
        validVerses.addAll(selectedChapter.getVerses());
    }

    // Functions for Fragment Adapters

    public List<String> getValidVolumes() {
        return library.getVolumeTitles();
    }

    public List<String> getValidBooks() {
        return validBooks;
    }

    public List<String> getValidChapters() {
        return validChapters;
    }

    public List<Verse> getValidVerses() {
        return validVerses;
    }

}
