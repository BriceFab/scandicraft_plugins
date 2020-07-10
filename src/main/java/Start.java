import net.scandicraft.config.Config;
import net.scandicraft.client.Main;
import net.scandicraft.utils.ArrayUtils;

public class Start {
    public static void main(String[] args) {
        Main.main(ArrayUtils.concat(new String[]{
                "--version", Config.GameConfig.version,
                "--assetsDir", Config.GameConfig.assetsDir,
                "--assetIndex", Config.GameConfig.assetIndex
        }, args));
    }
}
