package net.scandicraft.skins;

import net.scandicraft.Config;
import net.scandicraft.logs.LogManagement;
import org.apache.logging.log4j.LogManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImgThread extends Thread {

    public CustomSkin skin;

    public ImgThread(CustomSkin skin) {
        this.skin = skin;
    }

    public void run() {
        LogManagement.info("Download " + this.skin.getPseudo() + " skin...");

        try {
            URL url = new URL(Config.SKINS_PATH + this.skin.getPseudo() + ".png");
            URLConnection con = url.openConnection();
            InputStream inputStream = con.getInputStream();
            BufferedImage img = ImageIO.read(inputStream);

            this.skin.setBuffer(img);
        } catch (FileNotFoundException e) {
            LogManager.getLogger().warn("[ScandiCraft] Skin not found for " + this.skin.getPseudo());
        } catch (IOException e) {
            e.printStackTrace();
        }

        LogManagement.info("Download " + this.skin.getPseudo() + " skin successful.");
    }

}