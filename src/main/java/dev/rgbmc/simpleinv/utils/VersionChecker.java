package dev.rgbmc.simpleinv.utils;

public interface VersionChecker {
    VersionChecker instance = new DefaultImpl();
    boolean atLeast(String version);

    public static VersionChecker getInstance() {
        return instance;
    }

    class DefaultImpl implements VersionChecker {

        @Override
        public boolean atLeast(String version) {
            return false;
        }
    }
}
