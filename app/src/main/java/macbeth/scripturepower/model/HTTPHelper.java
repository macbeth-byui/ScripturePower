package macbeth.scripturepower.model;

import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HTTPHelper {

    public String readHTTP(String url) {
        try {
            URL urlObj = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder data = new StringBuilder();
            String line;
            do {
                line = reader.readLine();
                if (line != null) {
                    data.append(line);
                }
            }
            while (line != null);
            return data.toString();
        }
        catch (IOException ioe) {
            Log.e("HTTPHelper", "Exception ("+url+"): "+ioe);
            return null;
        }
    }
}
