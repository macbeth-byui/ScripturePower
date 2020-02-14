package macbeth.scripturepower.model;

public class SearchRecord {
    private Volume volume;
    private Book book;
    private Chapter chapter;
    private Verse verse;

    public SearchRecord(Volume volume, Book book, Chapter chapter, Verse verse) {
        this.volume = volume;
        this.book = book;
        this.chapter = chapter;
        this.verse = verse;
    }

    public Volume getVolume() {
        return volume;
    }

    public Book getBook() {
        return book;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public Verse getVerse() {
        return verse;
    }
}
