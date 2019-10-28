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
import macbeth.scripturepower.model.Verse;
import macbeth.scripturepower.model.Volume;

public class SearchPresenter {
    private Library library;
    private List<Verse> searchResults;
    private String prevVolumeFilter;
    private String prevTerm;

    public SearchPresenter(MainPresenter presenter) {
        searchResults = new ArrayList<Verse>();
        library = presenter.getLibrary();
        prevVolumeFilter = null;
        prevTerm = null;
    }

    // Functions for Fragment requests

    public void searchTerm(String term, String volumeFilter) {

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
                                searchResults.add(verse);
                            }
                        }
                    }
                }
            }
        }
        else {
            List<Verse> updateList = new ArrayList<>();
            for (Verse verse : searchResults) {
                if (verse.getText().toUpperCase().contains(term.toUpperCase())) {
                    updateList.add(verse);
                }
            }
            searchResults.clear();
            searchResults.addAll(updateList);
            Log.d("SearchPresenter","Size of SearchResults: "+searchResults.size());
        }
        prevTerm = term;
        prevVolumeFilter = volumeFilter;
    }

    // Functions for Fragment Adapters

    public List<Verse> getSearchResults() {
        return searchResults;
    }

    public List<String> getVolumeTitles() {
        return library.getVolumeTitles();
    }

}
