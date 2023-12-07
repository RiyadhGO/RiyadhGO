package sa.edu.yamamh.riyadhgo;

public enum KSARegions {//This code defines an enum named KSARegions to represent
    // the four major regions within Saudi Arabia: Riyadh, Eastern, Western, and Northern
    RIYADH,
    EASTERN,
    WESTERN,
    NORTHERN;

    public static KSARegions IsInRegion(double longitude, double latitude) {

                 if (longitude >= 49.5 && longitude <= 50.5 && latitude >= 24.5 && latitude <= 25.5) {
                return EASTERN;
                }

                else if (longitude >= 41.5 && longitude <= 42.5 && latitude >= 24.5 && latitude <= 25.5){
            return NORTHERN;
                }
                else if (longitude >= 43.5 && longitude <= 44.5 && latitude >= 24.5 && latitude <= 25.5){
                    return WESTERN;

        }
        return RIYADH;
    }
}
