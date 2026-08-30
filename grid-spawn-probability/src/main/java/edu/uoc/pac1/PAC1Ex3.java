package edu.uoc.pac1;

public class PAC1Ex3 {

    public static final int MAX_ROWS = 10;
    public static final int MAX_COLUMNS = 10;
    public static final int CHUNK_SIZE = 256;

    private static final double[][] SPAWN_PROBABILITIES = {
            {0.10, 0.15, 0.22, 0.30, 0.28, 0.25, 0.18, 0.12, 0.08, 0.05},
            {0.14, 0.20, 0.27, 0.35, 0.33, 0.31, 0.23, 0.17, 0.11, 0.07},
            {0.19, 0.26, 0.38, 0.45, 0.43, 0.41, 0.32, 0.24, 0.16, 0.09},
            {0.21, 0.29, 0.42, 0.55, 0.53, 0.50, 0.36, 0.27, 0.19, 0.12},
            {0.25, 0.34, 0.48, 0.70, 0.68, 0.65, 0.45, 0.32, 0.22, 0.14},
            {0.24, 0.33, 0.47, 0.72, 0.75, 0.73, 0.48, 0.35, 0.25, 0.15},
            {0.20, 0.28, 0.40, 0.60, 0.58, 0.55, 0.38, 0.26, 0.18, 0.10},
            {0.16, 0.23, 0.35, 0.50, 0.48, 0.45, 0.30, 0.21, 0.14, 0.08},
            {0.12, 0.18, 0.27, 0.38, 0.36, 0.33, 0.22, 0.16, 0.11, 0.06},
            {0.09, 0.14, 0.20, 0.30, 0.28, 0.25, 0.17, 0.12, 0.08, 0.05}
    };

    public static double getBaseDropProbability(int chunkRow, int chunkColumn) {
        return SPAWN_PROBABILITIES[chunkRow][chunkColumn];
    }

    public static boolean isValidCoordinate(int globalX, int globalY) {
        int maxX = MAX_COLUMNS * CHUNK_SIZE;
        int maxY = MAX_ROWS * CHUNK_SIZE;

        return globalX >= 0 && globalX < maxX && globalY >= 0 && globalY < maxY;
    }

    public static int[] getChunk(int globalX, int globalY) {
        int chunkColumn = globalX / CHUNK_SIZE;
        int chunkRow = globalY / CHUNK_SIZE;

        return new int[]{chunkRow, chunkColumn};
    }

    public static double calculateLevelAdjustment(int mobLevel, int playerLevel) {
        int levelDifference = mobLevel - playerLevel;

        if (levelDifference >= 15) {
            return 1.0;
        } else if (levelDifference <= -15) {
            return 0.0;
        } else {
            return (levelDifference + 15) / 30.0;
        }
    }

    public static double calculateDropProbability(int globalX, int globalY, int mobLevel, int playerLevel) {
        if (!isValidCoordinate(globalX, globalY)) {
            return 0.0;
        }

        int[] chunk = getChunk(globalX, globalY);
        double baseProbability = getBaseDropProbability(chunk[0], chunk[1]);
        double levelAdjustment = calculateLevelAdjustment(mobLevel, playerLevel);

        return baseProbability * levelAdjustment;
    }
}