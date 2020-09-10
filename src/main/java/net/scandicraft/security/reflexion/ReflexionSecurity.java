package net.scandicraft.security.reflexion;

import net.scandicraft.Environnement;
import net.scandicraft.config.Config;
import net.scandicraft.logs.LogManagement;

import java.lang.reflect.ReflectPermission;
import java.security.Permission;

public class ReflexionSecurity extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
//        super.checkPermission(perm);

        if (perm instanceof ReflectPermission) {
            if (Config.ENV == Environnement.DEV) {
                LogManagement.warn("checkPermission ReflectPermission " + perm.getActions() + " - " + perm.getName());
            }
        }
        if (perm instanceof RuntimePermission) {
            if (Config.ENV == Environnement.DEV) {
                LogManagement.warn("checkPermission RuntimePermission " + perm.getActions() + " - " + perm.getName());
            }
        }

    }

    @Override
    public void checkPackageAccess(String pkg) {
        super.checkPackageAccess(pkg);

        if (Config.ENV == Environnement.DEV) {
            LogManagement.warn("checkPackageAccess " + pkg);
        }

//        if (pkg.contains("javazoom.jl.decoder")) {
//            Minecraft.getMinecraft().shutdown();
//        }
    }
}
