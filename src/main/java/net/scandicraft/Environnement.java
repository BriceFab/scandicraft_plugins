package net.scandicraft;

public enum Environnement {

    DEV("dev"),
    TEST("test"),
    PROD("prod");

    private final String name;

    Environnement(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
