package edu.uoc.pac1;

public class PAC1Ex2 {

    // Area of each zone of the spaceship hull (in m²)
    private static final double[] ZONES_AREAS = {4.0, 2.5, 5.8, 3.2, 6.5, 4.9, 3.0, 7.1};

    // Maximum allowed impact density before requiring urgent repairs
    private static final double MAX_IMPACT_DENSITY = 0.25;

    public static double getZoneArea(int zoneIndex) {
        return ZONES_AREAS[zoneIndex];
    }

    public static double calculateImpactDensity(int impacts, int zoneIndex) {
        double area = getZoneArea(zoneIndex);
        return impacts / area;
    }

    public static boolean needsUrgentRepair(int impacts, int zoneIndex) {
        double density = calculateImpactDensity(impacts, zoneIndex);
        return density >= MAX_IMPACT_DENSITY;
    }

    public static int calculateTotalImpacts(int[] impactsArray) {
        int total = 0;
        for (int impacts : impactsArray) {
            total += impacts;
        }
        return total;
    }

    public static int getMostDamagedZone(int[] impactsArray) {
        int mostDamagedZone = 0;
        double highestDensity = calculateImpactDensity(impactsArray[0], 0);

        for (int i = 1; i < impactsArray.length; i++) {
            double currentDensity = calculateImpactDensity(impactsArray[i], i);
            if (currentDensity > highestDensity) {
                highestDensity = currentDensity;
                mostDamagedZone = i;
            }
        }
        return mostDamagedZone;
    }

    public static void printDamageReport(String[] zoneNames, int[] impactsArray) {
        System.out.println("Meteor impact damage report:");

        int urgentRepairsCount = 0;

        for (int i = 0; i < zoneNames.length; i++) {
            double density = calculateImpactDensity(impactsArray[i], i);
            double roundedDensity = Math.round(density * 1000.0) / 1000.0;

            boolean needsUrgent = needsUrgentRepair(impactsArray[i], i);
            String repairStatus = needsUrgent ? "(URGENT REPAIR REQUIRED)" : "(Normal)";

            if (needsUrgent) {
                urgentRepairsCount++;
            }

            System.out.println("\t" + zoneNames[i] + ": DENSITY " + roundedDensity + " " + repairStatus);
        }

        int totalImpacts = calculateTotalImpacts(impactsArray);
        int mostDamagedIndex = getMostDamagedZone(impactsArray);
        String mostDamagedZoneName = zoneNames[mostDamagedIndex];

        System.out.println("Total impacts: " + totalImpacts);
        System.out.println("Most damaged zone: " + mostDamagedZoneName);
        System.out.println("Urgent repairs needed in " + urgentRepairsCount + " zones.");
    }

}
