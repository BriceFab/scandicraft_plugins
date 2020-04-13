package net.scandicraft.settings;

import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.scandicraft.Config;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.File;

public class ScandiCraftSettings {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new Gson();
    public KeyBinding[] keyBindings;

    protected Minecraft mc;
    private File optionsFile;
    public String language;

    public KeyBinding keyBindForward = new KeyBinding("test1", 17, "scandicraft.options.categorie.test", KeyBindingType.SCANDICRAFT);
    public KeyBinding keyBindLeft = new KeyBinding("test2", 30, "scandicraft.options.categorie.test", KeyBindingType.SCANDICRAFT);

    public ScandiCraftSettings(Minecraft mcIn, File file) {
        this.language = "en_US";
        this.mc = mcIn;
        this.optionsFile = new File(file, "scandicraft_options.txt");
        this.keyBindings = ArrayUtils.addAll(new KeyBinding[]{this.keyBindForward, this.keyBindLeft});
        this.loadOptions();
    }

    public void loadOptions() {
        Config.print_debug("Load scandicraft options");

        try {
            if (!this.optionsFile.exists()) {
                return;
            }

            //TODO !

        } catch (Exception exception1) {
            LOGGER.error((String) "Failed to load scandicraft options", (Throwable) exception1);
        }

    }

    public void saveOptions() {
        Config.print_debug("Save scandicraft options");

        try {
            //TODO
        } catch (Exception exception) {
            LOGGER.error((String) "Failed to save scandicraft options", (Throwable) exception);
        }

        // this.sendSettingsToServer();
    }

    public void resetSettings() {
        //TODO
        this.mc.refreshResources();
        this.saveOptions();
    }

    /**
     * Sets a key binding and then saves all settings.
     */
    public void setOptionKeyBinding(KeyBinding p_151440_1_, int p_151440_2_) {
        p_151440_1_.setKeyCode(p_151440_2_);
        this.saveOptions();
    }

    /**
     * Represents a key or mouse button as a string. Args: key
     */
    public static String getKeyDisplayString(int p_74298_0_)
    {
        return p_74298_0_ < 0 ? I18n.format("key.mouseButton", new Object[] {Integer.valueOf(p_74298_0_ + 101)}): (p_74298_0_ < 256 ? Keyboard.getKeyName(p_74298_0_) : String.format("%c", new Object[] {Character.valueOf((char)(p_74298_0_ - 256))}).toUpperCase());
    }

}
