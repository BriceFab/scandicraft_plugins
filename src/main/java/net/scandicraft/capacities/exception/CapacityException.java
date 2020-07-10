package net.scandicraft.capacities.exception;

import net.minecraft.util.EnumChatFormatting;

public class CapacityException extends Exception {

    private String playerErrorMessage = "";

    public CapacityException(String message, String playerErrorMessage) {
        super(message);

        this.playerErrorMessage = playerErrorMessage;
    }

    /**
     * @return erreur que l'on peut afficher au joueur
     */
    public String getPlayerErrorMessage() {
        return EnumChatFormatting.RED + playerErrorMessage;
    }

    public static String NO_TARGET_PLAYER = "Aucun joueur visé.";
}
