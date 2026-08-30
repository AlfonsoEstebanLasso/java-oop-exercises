package edu.uoc.pac3;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Kingdom {

    private static final int MIN_NAME_LENGTH = 3;
    private static final int MAX_NAME_LENGTH = 20;
    private static final int CODE_LENGTH = 3;
    private static final double MIN_TREASURY = -100000.0;

    public static final String INVALID_NAME =
            "[ERROR] Kingdom name must have between 3 and 20 characters";
    public static final String INVALID_CODE =
            "[ERROR] Kingdom code must have exactly 3 uppercase letters";
    public static final String INVALID_LAST_WAR_DATE =
            "[ERROR] Last war date cannot be in the future";
    public static final String INVALID_TREASURY =
            "[ERROR] Treasury cannot be less than -100000.0";

    public static final String KINGDOM_FULL =
            "[ERROR] The number of castles cannot exceed the maximum";
    public static final String CASTLE_NULL =
            "[ERROR] The castle cannot be null";
    public static final String CASTLE_ALREADY_EXISTS =
            "[ERROR] The castle already exists in the kingdom";
    public static final String CASTLE_NOT_FOUND =
            "[ERROR] The castle does not exist in the kingdom";

    private final int MAX_CASTLES;

    private String name;
    private String code;
    private LocalDate lastWarDate;
    private double treasury;
    private boolean isReligious;

    private Castle[] castles;
    private int numCastles;
    private double totalArea;

    public Kingdom(String name, String code, LocalDate lastWarDate, double treasury, boolean isReligious, int MAX_CASTLES) {
        setName(name);
        setCode(code);
        setLastWarDate(lastWarDate);
        setTreasury(treasury);
        setIsReligious(isReligious);

        this.MAX_CASTLES = MAX_CASTLES;
        this.castles = new Castle[this.MAX_CASTLES];
        this.numCastles = 0;
        this.totalArea = 0.0;
    }

    private void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException(INVALID_NAME);
        }
        String t = name.trim();
        if (t.length() < MIN_NAME_LENGTH || t.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException(INVALID_NAME);
        }
        this.name = t;
    }

    private void setCode(String code) {
        if (code == null || !code.matches("[A-Z]{" + CODE_LENGTH + "}")) {
            throw new IllegalArgumentException(INVALID_CODE);
        }
        this.code = code;
    }

    private void setLastWarDate(LocalDate lastWarDate) {
        if (lastWarDate != null && lastWarDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(INVALID_LAST_WAR_DATE);
        }
        this.lastWarDate = lastWarDate;
    }

    private void setTreasury(double treasury) {
        if (treasury < MIN_TREASURY) {
            throw new IllegalArgumentException(INVALID_TREASURY);
        }
        this.treasury = treasury;
    }

    private void setIsReligious(boolean isReligious) {
        this.isReligious = isReligious;
    }

    public String getName() { return name; }
    public String getCode() { return code; }
    public LocalDate getLastWarDate() { return lastWarDate; }
    public double getTreasury() { return treasury; }
    public boolean isReligious() { return isReligious; }

    public long getDaysWithoutWar() {
        if (lastWarDate == null) return Integer.MAX_VALUE;
        return ChronoUnit.DAYS.between(lastWarDate, LocalDate.now());
    }

    public int getMaxCastles() { return MAX_CASTLES; }
    public Castle[] getCastles() { return castles; }
    public int getNumCastles() { return numCastles; }

    private int findCastle(Castle castle) {
        if (castle == null) throw new NullPointerException(CASTLE_NULL);
        int targetId = castle.getId();
        for (int i = 0; i < castles.length; i++) {
            if (castles[i] != null && castles[i].getId() == targetId) return i;
        }
        return -1;
    }

    private int findFirstEmptySlot() {
        for (int i = 0; i < castles.length; i++) {
            if (castles[i] == null) return i;
        }
        return -1;
    }

    public boolean containsCastle(Castle castle) {
        return findCastle(castle) != -1;
    }

    public void addCastle(Castle castle) {
        if (findFirstEmptySlot() == -1) throw new IllegalStateException(KINGDOM_FULL);
        if (castle == null) throw new NullPointerException(CASTLE_NULL);
        if (containsCastle(castle)) throw new IllegalArgumentException(CASTLE_ALREADY_EXISTS);

        int pos = findFirstEmptySlot();
        castles[pos] = castle;
        numCastles++;
        totalArea += castle.getArea();
    }

    public void removeCastle(Castle castle) {
        if (castle == null) throw new NullPointerException(CASTLE_NULL);
        int idx = findCastle(castle);
        if (idx == -1) throw new IllegalArgumentException(CASTLE_NOT_FOUND);

        totalArea -= castles[idx].getArea();
        castles[idx] = null;
        numCastles--;
    }

    public double getAverageArea() {
        if (numCastles == 0) return 0.0;
        return totalArea / numCastles;
    }
}
