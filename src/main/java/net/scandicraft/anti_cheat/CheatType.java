package net.scandicraft.anti_cheat;

public enum CheatType {
    XRAY("La triche est interdite sur ScandiCraft. Veuillez supprimer votre ressource pack X-RAY !"),
    AUTOCLICK("Il est interdit de butterfly ou d'autoclick sur ScandiCraft !");

    private String message;

    CheatType() {
        this("La triche est interdite sur ScandiCraft");
    }

    CheatType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
