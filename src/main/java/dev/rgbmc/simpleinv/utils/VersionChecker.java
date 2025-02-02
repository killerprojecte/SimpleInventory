package dev.rgbmc.simpleinv.utils;

import org.bukkit.Bukkit;

import java.util.Arrays;

public interface VersionChecker {
    boolean atLeast(String version);

    static VersionChecker getInstance() {
        return VersionCheckerManager.getInstance();
    }

    static void setInstance(final VersionChecker instance) {
        VersionCheckerManager.setInstance(instance);
    }

    class DefaultImpl implements VersionChecker {

        private final String serverVersion = Bukkit.getBukkitVersion().split("-")[0];
        private final int[] serverVersions = Arrays.stream(serverVersion.split("\\.")).mapToInt(Integer::parseInt).toArray();

        @Override
        public boolean atLeast(String requiredVersion) {
            String[] split = requiredVersion.split("\\.");
            int[] versions = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
            for (int i = 0; i < versions.length; i++) {
                if (versions[i] > serverVersions[i]) {
                    return true;
                }
            }
            return false;
        }
    }
}
