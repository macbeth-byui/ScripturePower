package macbeth.scripturepower.presenter;

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

    public BrowsePresenter(MainPresenter presenter) {
        library = presenter.getLibrary();
        selectedVolume = null;
        selectedBook = null;
        selectedChapter = null;
        validBooks = new ArrayList<String>();
        validChapters = new ArrayList<String>();
        validVerses = new ArrayList<Verse>();
    }

    // Functions for Fragment Requests

    public void selectVolume(String volume) {
        selectedVolume = library.getVolume(volume);
        if (selectedVolume != null) {
            validBooks.clear();
            validChapters.clear();
            validVerses.clear();
            validBooks.addAll(selectedVolume.getBookNames());
        }
    }

    public void selectBook(String book) {
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
