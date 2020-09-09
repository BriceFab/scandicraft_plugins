package net.scandicraft.config;

import net.minecraft.util.EnumChatFormatting;

public class SecurityConfig {

    public static final String AUTH_KEY = "U;!?M!Zw#8P!B4vU&#j^53;A<sAv\"qQMbuxb\"[,SB*+`*)[Z69a9`]W{Wfa)afFq";
    //    public static final Integer HANDSHAKE = -20062020;  //1.8.8 = 47
    public static final Integer HANDSHAKE = 47;  //1.8.8 = 47
    public static final String OUTDATED_MESSAGE = EnumChatFormatting.RED + "Télécharge le launcher sur https://scandicraft-mc.fr/";
    public static final boolean SEND_AUTH_PACKET = false;   //envoie ou non le pack de vérification de connexion

}
