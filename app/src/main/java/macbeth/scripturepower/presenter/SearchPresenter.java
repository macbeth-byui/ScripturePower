package macbeth.scripturepower.presenter;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import macbeth.scripturepower.model.Book;
import macbeth.scripturepower.model.Chapter;
import macbeth.scripturepower.model.Library;
import macbeth.scripturepower.model.SearchRecord;
import macbeth.scripturepower.model.SearchResult;
import macbeth.scripturepower.model.Verse;
import macbeth.scripturepower.model.Volume;

public class SearchPresenter {
    private Library library;
    private SearchResult searchResults;
    private String prevVolumeFilter;
    private String prevTerm;

    public SearchPresenter() {
        searchResults = new SearchResult();
        library = null;
        prevVolumeFilter = null;
        prevTerm = null;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    // Functions for Fragment requests

    public void searchTerm(String term, String volumeFilter) {
        if (library == null) {
            return;
        }
        if (volumeFilter == null) {
            volumeFilter = "";
        }
        if (prevTerm == null || term.length() < prevTerm.length() || prevVolumeFilter == null || !prevVolumeFilter.contentEquals(volumeFilter)) {
            searchResults.clear();
            for (Volume volume : library.getVolumes()) {
                if (!volumeFilter.contentEquals("") && !volumeFilter.contentEquals(volume.getTitle()))
                    continue;
                for (Book book : volume.getBooks()) {
                    for (Chapter chapter : book.getChapters()) {
                        for (Verse verse : chapter.getVerses()) {
                            if (verse.getText().toUpperCase().contains(term.toUpperCase())) {
                                searchResults.add(volume, book, chapter, verse);
                            }
                        }
                    }
                }
            }
        }
        else {
            List<SearchRecord> updateList = new ArrayList<>();
            for (SearchRecord record : searchResults.getData()) {
                if (record.getVerse().getText().toUpperCase().contains(term.toUpperCase())) {
                    updateList.add(record);
                }
            }
            searchResults.clear();
            searchResults.getData().addAll(updateList);
            Log.d("SearchPresenter","Size of SearchResults: "+searchResults.getData().size());
        }
        prevTerm = term;
        prevVolumeFilter = volumeFilter;
        searchResults.setSearchTerm(term);
    }

    // Functions for Fragment Adapters

    public SearchResult getSearchResults() {
        return searchResults;
    }

    public List<String> getVolumeTitles() {
        return library.getVolumeTitles();
    }

}
