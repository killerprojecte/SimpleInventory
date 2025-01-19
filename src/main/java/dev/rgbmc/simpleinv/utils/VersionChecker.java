package dev.rgbmc.simpleinv.utils;

public interface VersionChecker {
    boolean atLeast(String version);

    static VersionChecker getInstance() {
        return VersionCheckerManager.getInstance();
    }

    static void setInstance(final VersionChecker instance) {
        VersionCheckerManager.setInstance(instance);
    }

    class DefaultImpl implements VersionChecker {

        @Override
        public boolean atLeast(String version) {
            return false;
        }
    }
}
