package macbeth.scripturepower.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Library {
    private List<Volume> volumes;
    private Gson gson;
    private HTTPHelper httpHelper;
    private FileHelper fileHelper;

    public Library(Context context) {
        volumes = new ArrayList<Volume>();
        gson = new Gson();
        httpHelper = new HTTPHelper();
        fileHelper = new FileHelper(context);
    }

    public void loadVolume(String title, String url, boolean hasBooks) {
        Log.d("Library", "Attempting to read "+title+" from file");
        String data = fileHelper.readFile(title);
        if (data == null) {
            Log.d("Library","Attempting to read "+title+" from network");
            data = httpHelper.readHTTP(url);
        }
        Volume volume = null;
        if (hasBooks) {
            try {
                volume = gson.fromJson(data, Volume.class);
            }
            catch (JsonSyntaxException jse) { // Redownload if the JSON file is corrupted
                data = httpHelper.readHTTP(url);
                volume = gson.fromJson(data, Volume.class);
            }
        }
        else {
            Book book = null;
            try {
                book = gson.fromJson(data, Book.class);
            }
            catch (JsonSyntaxException jse) {
                data = httpHelper.readHTTP(url);
                book = gson.fromJson(data, Book.class);
            }
            if (book != null) {
                volume = new Volume();
                volume.setTitle(title);
                volume.getBooks().add(book);
            }
        }
        if (volume != null) {
            Log.d("Library", "Writing "+title+" to file");
            fileHelper.writeFile(title, data);
            volume.setTitle(title);
            volumes.add(volume);
        }
        else {
            Log.d("Library","Error converting "+title+" from JSON");
        }
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public Volume getVolume(String title) {
        for (Volume volume : volumes) {
            if (volume.getTitle().toUpperCase().equals(title.toUpperCase())) {
                return volume;
            }
        }
        return null;
    }

    public List<String> getVolumeTitles() {
        List<String> titles = new ArrayList<String>();
        for (Volume volume : volumes) {
            titles.add(volume.getTitle());
        }
        return titles;
    }

}
