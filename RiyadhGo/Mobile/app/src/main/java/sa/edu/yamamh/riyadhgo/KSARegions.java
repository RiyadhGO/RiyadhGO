package sa.edu.yamamh.riyadhgo;

public enum KSARegions {
    RIYADH,
    EASTERN,
    WESTERN,
    NORTHERN;

    public static boolean IsInRegion(double longitude, double latitude, KSARegions region) {
        switch (region) {
            case RIYADH:
                return longitude >= 46.5 && longitude <= 47.5 && latitude >= 24.5 && latitude <= 25.5;
            case EASTERN:
                return longitude >= 49.5 && longitude <= 50.5 && latitude >= 24.5 && latitude <= 25.5;
            case WESTERN:
                return longitude >= 41.5 && longitude <= 42.5 && latitude >= 24.5 && latitude <= 25.5;
            case NORTHERN:
                return longitude >= 43.5 && longitude <= 44.5 && latitude >= 24.5 && latitude <= 25.5;
            default:
                return false;
        }
    }
}
