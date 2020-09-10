package net.scandicraft.security.anti_cheat;

public enum CheatType {
    XRAY("La triche est interdite sur ScandiCraft. Veuillez supprimer votre ressource pack X-RAY !"),
    AUTOCLICK("Il est interdit de butterfly ou d'autoclick sur ScandiCraft !"),
    ILLEGAL_PROCESS(),
    OTHER();

    private final String message;

    CheatType() {
        this("Il est interdit de tricher sur ScandiCraft !");
    }

    CheatType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
