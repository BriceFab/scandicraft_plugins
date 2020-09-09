import net.scandicraft.client.Main;
import net.scandicraft.config.GameConfig;
import net.scandicraft.utils.ArrayUtils;

public class Start {
    public static void main(String[] args) {
        Main.main(ArrayUtils.concat(new String[]{
                "--version", GameConfig.version,
                "--assetsDir", GameConfig.assetsDir,
                "--assetIndex", GameConfig.assetIndex
        }, args));
    }
}