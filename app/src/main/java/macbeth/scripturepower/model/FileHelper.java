package macbeth.scripturepower.model;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {
    private Context context;

    public FileHelper(Context context) {
        this.context = context;
    }

    public String readFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(context.openFileInput(filename)));
            String data = reader.readLine();
            reader.close();
            Log.d("FileHelper", "Finished reading from file: "+filename);
            return data;
        }
        catch (IOException ioe) {
            Log.e("FileHelper","Error reading from file ("+filename+"): "+ioe);
            return null;
        }
    }

    public boolean writeFile(String filename, String data) {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE)));
            writer.write(data);
            writer.close();
            Log.d("FileHelper", "Finished writing to file: "+filename);
            return true;
        }
        catch (IOException ioe) {
            Log.e("FileHelper","Error writing to file ("+filename+"): "+ioe);
            return false;
        }
    }
}
