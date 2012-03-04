package org.newdawn.slick.muffin;

import android.content.Context;
import android.os.Environment;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import org.newdawn.slick.util.Log;

/**
*
* @author mangelok
*/
public class AndroidMuffin implements Muffin {

    public static Context app;

    @Override
    public void saveFile(HashMap data, String fileName) throws IOException {
        String userHome = Environment.getDataDirectory().getPath();
        File file = new File(userHome);
        file = new File(file, ".java");
        if (!file.exists()) {
            file.mkdir();
        }

        file = new File(file, fileName);
        FileOutputStream fos = app.openFileOutput(file.getName(), Context.MODE_PRIVATE);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        // save hashMap
        oos.writeObject(data);

        oos.close();
    }

    @Override
    public HashMap loadFile(String fileName) throws IOException {
        HashMap hashMap = new HashMap();
        FileInputStream fis = null;
        try {
            fis = app.openFileInput(fileName);
        } catch (FileNotFoundException fe) {
            Log.error("SavedState files not found!");
            return hashMap;
        }

        try {
            ObjectInputStream ois = new ObjectInputStream(fis);
            hashMap = (HashMap) ois.readObject();
            ois.close();
        } catch (EOFException e) {
            // End of the file reached, do nothing
        } catch (ClassNotFoundException e) {
            Log.error(e);
            throw new IOException("Failed to pull state from store - class not found");
        }

    return hashMap;
    }

   

}