package com.gawpdevelopers.gawp.domain;

public enum DepartmentType {
    COMPUTERENGINEERING,
    MECHANICALENGINEERING,
    ELECTRONICANDCOMMUNUCATIONENGINNERING,
    FOODENGINEERING,
    CIVILENGINEERING,
    PHYSICS,
    CHEMISTRY,
    MATHEMATICS;

    public String customToString() {
        switch(this) {
            case COMPUTERENGINEERING: return "Computer Engineering";
            case MECHANICALENGINEERING: return "Mechanical Engineering";
            case ELECTRONICANDCOMMUNUCATIONENGINNERING: return "Electronic and Communication Engineering";
            case FOODENGINEERING: return "Food Engineering";
            case CIVILENGINEERING: return "Civil Engineering";
            case PHYSICS: return "Physics";
            case CHEMISTRY: return "Chemistry";
            case MATHEMATICS: return "Mathematics";
            default: throw new IllegalArgumentException();
        }
    }
}
