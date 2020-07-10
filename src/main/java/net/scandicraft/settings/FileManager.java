package net.scandicraft.settings;

import com.google.gson.Gson;
import net.scandicraft.config.Config;

import java.io.*;

public class FileManager {

    private static final Gson gson = new Gson();

    private static final File MODS_DIR = new File(Config.CONF_FILE_NAME);

    public static void init() {
        if (!MODS_DIR.exists()) {
            MODS_DIR.mkdirs();
        }
    }

    public static Gson getGson() {
        return gson;
    }

    public static File getModsDirectory() {
        return MODS_DIR;
    }

    public static boolean writeJsonToFile(File file, Object object) {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(gson.toJson(object).getBytes());
            outputStream.flush();
            outputStream.close();

            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T> T readFromJsonFile(File file, Class<T> c) {

        if (!file.exists()) {
            return null;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }

            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();

            return gson.fromJson(builder.toString(), c);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
