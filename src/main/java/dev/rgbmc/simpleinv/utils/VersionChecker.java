package dev.rgbmc.simpleinv.utils;

import com.cryptomorin.xseries.reflection.XReflection;

public class VersionChecker {
    public static boolean atLeast(String version) {
        String[] parts = version.split("\\.");
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            int ver = Integer.parseInt(part);
            switch (i) {
                case 0: {
                    if (ver >= XReflection.MAJOR_NUMBER) break;
                    return false;
                }
                case 1: {
                    if (ver >= XReflection.MINOR_NUMBER) break;
                    return false;
                }
                case 2: {
                    if (ver >= XReflection.PATCH_NUMBER) break;
                    return false;
                }
                default: {
                    throw new IllegalArgumentException("Unsupported version format: " + version);
                }
            }
        }
        return true;
    }
}
