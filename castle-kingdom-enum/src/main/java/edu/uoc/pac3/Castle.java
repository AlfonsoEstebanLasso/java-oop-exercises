package edu.uoc.pac3;

import java.time.LocalDate;

public class Castle {

    private static final int MIN_NAME_LENGTH = 3;
    private static final double MIN_AREA = 100.0;
    private static final LocalDate MIDDLE_AGES_START = LocalDate.of(500, 1, 1);
    private static final LocalDate MIDDLE_AGES_END = LocalDate.of(1499, 12, 31);
    private static final int MIN_SYMBOLIC_ARTIFACT_LENGTH = 2;

    public static final String INVALID_NAME = "[ERROR] Castle name must have at least 3 characters";
    public static final String INVALID_CONSTRUCTION_DATE = "[ERROR] Construction date cannot be null and must be between 0500-01-01 and 1499-12-31";
    public static final String INVALID_AREA = "[ERROR] Area must be at least 100.0 square meters";
    public static final String INVALID_SYMBOLIC_ARTIFACT = "[ERROR] Symbolic artifact cannot be null, formed by only whitespace and must have at least 2 characters";

    public static final String INVALID_CASTLE_TYPE = "[ERROR] Invalid castle type";

    private int id;
    private static int nextId = 1;

    private String name;
    private LocalDate constructionDate;
    private double area;
    private String symbolicArtifact;
    private Keep keep;

    private CastleType castleType;


    public Castle(String name, LocalDate constructionDate, double area, String symbolicArtifact, CastleType castleType) {
        setName(name);
        setConstructionDate(constructionDate);
        setArea(area);
        setSymbolicArtifact(symbolicArtifact);
        setCastleType(castleType);
        this.keep = null;
        setId();
    }

    public Castle(String name, LocalDate constructionDate, double area, String symbolicArtifact, CastleType castleType,
                  String keepLordName, int keepFloors, double keepHeight, int keepNumGuards, boolean keepHasDungeon) {
        setName(name);
        setConstructionDate(constructionDate);
        setArea(area);
        setSymbolicArtifact(symbolicArtifact);
        setCastleType(castleType);
        try {
            setKeep(keepLordName, keepFloors, keepHeight, keepNumGuards, keepHasDungeon);
        } catch (IllegalArgumentException e) {
            this.keep = null;
        }
        setId();
    }

    private void setId() {
        this.id = nextId;
        incNextId();
    }

    public int getId() {
        return id;
    }

    public static int getNextId() {
        return nextId;
    }

    private static void incNextId() {
        nextId++;
    }

    private void setName(String name) {
        if (name == null || name.trim().length() < MIN_NAME_LENGTH) {
            throw new IllegalArgumentException(INVALID_NAME);
        }
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    private void setConstructionDate(LocalDate constructionDate) {
        if (constructionDate == null
                || constructionDate.isBefore(MIDDLE_AGES_START)
                || constructionDate.isAfter(MIDDLE_AGES_END)) {
            throw new IllegalArgumentException(INVALID_CONSTRUCTION_DATE);
        }
        this.constructionDate = constructionDate;
    }

    public LocalDate getConstructionDate() {
        return constructionDate;
    }

    private void setArea(double area) {
        if (area < MIN_AREA) {
            throw new IllegalArgumentException(INVALID_AREA);
        }
        this.area = area;
    }

    public double getArea() {
        return area;
    }

    private void setSymbolicArtifact(String symbolicArtifact) {
        if (symbolicArtifact == null) {
            throw new IllegalArgumentException(INVALID_SYMBOLIC_ARTIFACT);
        }
        String s = symbolicArtifact.trim();
        if (s.isEmpty() || s.length() < MIN_SYMBOLIC_ARTIFACT_LENGTH) {
            throw new IllegalArgumentException(INVALID_SYMBOLIC_ARTIFACT);
        }
        this.symbolicArtifact = s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    public String getSymbolicArtifact() {
        return symbolicArtifact;
    }

    private void setKeep(String lordName, int floors, double height, int numGuards, boolean hasDungeon) {
        this.keep = new Keep(lordName, floors, height, numGuards, hasDungeon); // Propaga IllegalArgumentException si algo falla
    }

    public Keep getKeep() {
        return keep;
    }

    private void setCastleType(CastleType castleType) {
        if (castleType == null) {
            throw new IllegalArgumentException(INVALID_CASTLE_TYPE);
        }
        this.castleType = castleType;
    }

    public CastleType getCastleType() {
        return castleType;
    }
}
