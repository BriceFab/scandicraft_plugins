package net.scandicraft.classes;

import net.scandicraft.classes.impl.Archer;
import net.scandicraft.classes.impl.Guerrier;
import net.scandicraft.classes.impl.Magicien;

/**
 * Représente la liste des classes (à la place de les mettre en bdd)
 */
public enum ClasseType {
    GUERRIER(0, "Guerrier", new Guerrier()),
    ARCHER(1, "Archer", new Archer()),
    MAGICIEN(2, "Magicien", new Magicien());

    private final int id;
    private final String name;
    private final IClasse iClasse;

    ClasseType(int id, String name, IClasse iClasse) {
        this.id = id;
        this.name = name;
        this.iClasse = iClasse;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public IClasse getIClasse() {
        return iClasse;
    }

    public static ClasseType getClasseTypeFromString(String classeName) {
        for (ClasseType type : values()) {
            if (type.getName().toLowerCase().equals(classeName.toLowerCase())) {
                return type;
            }
        }
        return null;
    }

    public static ClasseType getClasseTypeFromId(int id) {
        for (ClasseType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }
}
