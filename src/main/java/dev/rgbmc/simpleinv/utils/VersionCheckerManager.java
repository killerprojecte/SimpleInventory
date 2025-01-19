package dev.rgbmc.simpleinv.utils;

class VersionCheckerManager {
    protected static VersionChecker instance = null;

    protected static VersionChecker getInstance() {
        if (instance == null) {
            instance = new VersionChecker.DefaultImpl();
        }
        return instance;
    }

    protected static void setInstance(VersionChecker instance) {
        VersionCheckerManager.instance = instance;
    }
}
