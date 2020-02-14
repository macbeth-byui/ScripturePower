package macbeth.scripturepower.model;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {
    private List<SearchRecord> data;
    private String searchTerm;

    public SearchResult() {
        data = new ArrayList<>();
        searchTerm = "";
    }

    public void clear() {
        data.clear();
        searchTerm = "";
    }

    public void add(Volume volume, Book book, Chapter chapter, Verse verse) {
        SearchRecord record = new SearchRecord(volume, book, chapter, verse);
        data.add(record);
    }

    public List<SearchRecord> getData() {
        return data;
    }

    public void setData(List<SearchRecord> data) {
        this.data = data;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

}
