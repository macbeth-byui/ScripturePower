package macbeth.scripturepower.model;

import android.content.Context;

import com.google.gson.Gson;

public class Config {
    private int fontSize;

    public Config() {
        fontSize = 12;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public static Config readConfig(Context context) {
        FileHelper fileHelper = new FileHelper(context);
        String data = fileHelper.readFile("config.txt");
        Gson gson = new Gson();
        Config config = gson.fromJson(data, Config.class);
        if (config == null) {
            config = new Config();
        }
        return config;
    }

    public void saveConfig(Context context) {
        FileHelper fileHelper = new FileHelper(context);
        Gson gson = new Gson();
        String data = gson.toJson(this);
        fileHelper.writeFile("config.txt", data);
    }
}
