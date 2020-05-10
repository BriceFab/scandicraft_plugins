package net.scandicraft.settings;

import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.scandicraft.Config;
import net.scandicraft.mods.Mod;
import net.scandicraft.mods.ModInstances;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class ScandiCraftSettings {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new Gson();
    public KeyBinding[] keyBindings;

    protected Minecraft mc;
    public String language;
    private final String optionsFileName = Config.SC_SETTINGS;

    public static KeyBinding OPEN_HUD_MANAGER = new KeyBinding("ScandiCraft Mod Positioning", Keyboard.KEY_RSHIFT, "scandicraft.options.categorie.keyboards", KeyBindingType.SCANDICRAFT);
    public static KeyBinding keyBindTest2 = new KeyBinding("test2", 0, "scandicraft.options.categorie.test", KeyBindingType.SCANDICRAFT);

    public ScandiCraftSettings(Minecraft mcIn) {
        this.language = "en_US";
        this.mc = mcIn;
        this.keyBindings = ArrayUtils.addAll(new KeyBinding[]{OPEN_HUD_MANAGER, keyBindTest2});
        this.loadOptions();
    }

    public void loadOptions() {
        Config.print_debug("Load scandicraft options");

        try {
            Path path = Paths.get(this.optionsFileName);

            if (!Files.exists(path) || Files.notExists(path)) {
                Config.print_debug("set default options");
                return;
            }

            Map<?, ?> options = GSON.fromJson(new FileReader(this.optionsFileName), Map.class);

            for (Map.Entry<?, ?> entry : options.entrySet()) {
//                Config.print_debug("test " + entry.getKey() + " " + entry.getValue() + " " + entry.getKey().getClass());
                if (entry.getKey() instanceof String) {
                    ScandiCraftSettings.Options actOption = ScandiCraftSettings.Options.getOptionByKey((String) entry.getKey());
                    if (actOption != null) {
                        actOption.setOptionValue(entry.getValue());
                    }
                }
            }

        } catch (Exception exception1) {
            LOGGER.error("Failed to load scandicraft options", exception1);
        }

    }

    public void saveOptions() {
        Config.print_debug("Save scandicraft options");

        try {
            Map<String, Object> map = new HashMap<>();

            for (Options actOption : Options.getAllOptions()) {
                map.put(actOption.getKey(), actOption.getValue());
            }

            Writer writer = new FileWriter(this.optionsFileName);
            GSON.toJson(map, writer);
            writer.close();
        } catch (Exception exception) {
            LOGGER.error("Failed to save scandicraft options", exception);
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
    public static String getKeyDisplayString(int p_74298_0_) {
        return p_74298_0_ < 0 ? I18n.format("key.mouseButton", p_74298_0_ + 101) : (p_74298_0_ < 256 ? Keyboard.getKeyName(p_74298_0_) : String.format("%c", (char) (p_74298_0_ - 256)).toUpperCase());
    }

    /**
     * Gets a key binding.
     */
    public String getKeyBinding(ScandiCraftSettings.Options options) {
        String name = options.getName() + ": ";

        if (options.isBoolean()) {
            boolean flag = (boolean) options.getValue();
            return flag ? name + I18n.format("options.on") : name + I18n.format("options.off");
        } else {
            return name;
        }
    }

    public enum Options {
        ITEMS_PHYSICS("scandicraft.options.items_physics", true),
        INVENTORY_POTIONS("scandicraft.options.inventory.potions", false),
        MOD_ARMOR("scandicraft.options.hud.armor", true, ModInstances.getModArmorStatus()),
        MOD_CPS("scandicraft.options.hud.cps", true, ModInstances.getModCPS()),
        MOD_FPS("scandicraft.options.hud.fps", true, ModInstances.getModFPS()),
        MOD_PING("scandicraft.options.hud.ping", true, ModInstances.getModPing()),
        MOD_POTION("scandicraft.options.hud.potion", true, ModInstances.getModPotionStatus()),
        MOD_KEYSTROKE("scandicraft.options.hud.keystroke", true, ModInstances.getModKeystrokes()),
        MOD_COMPASS("scandicraft.options.hud.compass", true, ModInstances.getModCompass()),
        MOD_TOGGLE("scandicraft.options.hud.toggle", true, ModInstances.getModToggleSprintSneak()),
        ;

        private final String key;
        private Object value;
        private final Mod mod;

        Options(String key, Object value, Mod mod) {
            this.key = key;
            this.value = value;
            this.mod = mod;
        }

        Options(String key, Object value) {
            this(key, value, null);
        }

        public void setOptionValue(Object value) {
            this.value = value;
            if (this.mod != null && isBoolean()) {
                mod.setEnabled((Boolean) value);
            }
        }

        public static ScandiCraftSettings.Options getOptionByKey(String key) {
            for (ScandiCraftSettings.Options options : values()) {
                if (options.getKey().equals(key)) {
                    return options;
                }
            }
            return null;
        }

        public static ScandiCraftSettings.Options[] getAllOptions() {
            return values();
        }

        public String getKey() {
            return key;
        }

        public String getName() {
            return I18n.format(key);
        }

        public Object getValue() {
            return value;
        }

        public boolean isBoolean() {
            return value.getClass().toString().toLowerCase().contains("boolean");
        }

        public int returnEnumOrdinal() {
            return this.ordinal();
        }
    }

}
