package net.scandicraft.anti_cheat;

public enum CheatType {
    XRAY("La triche est interdite sur ScandiCraft. Veuillez supprimer votre ressource pack X-RAY !"),
    AUTOCLICK;

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
