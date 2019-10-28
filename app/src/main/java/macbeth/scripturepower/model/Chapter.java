package macbeth.scripturepower.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Chapter {
    private List<Verse> verses;
    @SerializedName(value="chapter", alternate = {"section"})
    private int chapter;

    public Verse getVerse(int verse) {
        if (verse <= 0 || verse > verses.size())
            return null;
        return verses.get(verse-1);

    }

    public int getChapter() {
        return chapter;
    }

    public List<Verse> getVerses() {
        return verses;
    }
}
