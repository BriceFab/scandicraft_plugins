package net.scandicraft.security.anti_cheat;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CheatConfig {
    /* Anti auto-click */
    public static final boolean CHECK_AUTOCLICK = true;
    public static final int MAX_CPS = 18;
    public static final int MAX_SUSPECT_AVERAGE = 15;
    public static final int MAX_HISTORY = 14;
    public static final double MIN_DIFF_TIME_BUTTERFLY = 45;            //si plus de 3 clicks sont plus petit que 45 (=butterfly)
    public static final double MIN_DIFF_TIME_AUTOCLICK = 40;            //si plus de 3 clicks sont plus petit que 40 (=autoclick)
    public static final int MAX_SUM_SUSPECT = 3;                        //si plus de x clicks dans la moyenne max
    public static final double MIN_DIFF_TIME_AVERAGE_BUTTERFLY = 65;    //différence minimale de la moyenne des temps = 0.06 seconds en moyenne (=butterfly)
    public static final double MIN_DIFF_TIME_AVERAGE_AUTOCLICK = 55;    //différence minimale de la moyenne des temps = 0.06 seconds en moyenne (=autoclick)
    public static final int MAX_HISTORY_FREQUENCY_AVERAGE = 7;          //Historique max de la fréquence (1 moyenne = chaque 14 clicks [MAX_HISTORY])
    public static final int MAX_FREQUENCY_AVERAGE = 6;                  //Maximum de 5 moyenne les mêmes    //TODO check value
    public static final int MAX_DOWN = 9;                               //Maxium de click LMB false     //TODO check value
    //TODO Add max down

    /* Anti illegal process */
    public static final int initialDelay = 60;    //en secondes
    public static final int periodicDelay = 30;   //en secondes
    public static final List<String> ILLEGAL_PROCESS = Arrays.asList(
            "zenkey.exe*",
            "wonderkeys.exe*",
            "autohotkey.exe*",
            "auto*clic*.exe*",
            "auto*clique*.exe*",
            "auto*klic*.exe*",
            "cheat*engine*.exe*",
            "klick.exe*",
            "click.exe*",
            "clic.exe*",
            "clicker.exe*",
            "cliqueur.exe*",
            "super*rapid*fire*.exe*",
            "speed.exe*",
            "speedhack.exe*",
            "speeder.exe*",
            "speedcheat.exe*",
            "cheatspeed.exe*"
    );

    /* Anti x-ray */
    private final static String BLOCKS_PATH = "textures/blocks/";
    public static final List<String> texturesCheck = new ArrayList<>(Arrays.asList(
            BLOCKS_PATH + "stone.png",
            BLOCKS_PATH + "cobblestone.png",
            BLOCKS_PATH + "snow.png",
            BLOCKS_PATH + "gravel.png",
            BLOCKS_PATH + "sand.png",
            BLOCKS_PATH + "furnace_top.png",
            BLOCKS_PATH + "furnace_side.png",
            BLOCKS_PATH + "stonebrick.png",
            BLOCKS_PATH + "brick.png",
            BLOCKS_PATH + "dirt.png",
            BLOCKS_PATH + "grass_side.png",
            BLOCKS_PATH + "grass_side_snowed.png",
            BLOCKS_PATH + "netherrack.png",
            BLOCKS_PATH + "red_sand.png",
            BLOCKS_PATH + "bedrock.png"
    ));

    private final static String MODELS_PATH = "models/block/";
    public static final Map<String, String> modelsCheck = Stream.of(
            new AbstractMap.SimpleEntry<>(MODELS_PATH + "cube.json", "ed43d9b0a87ce1aefd6da424096f3f6593a12016"),
            new AbstractMap.SimpleEntry<>(MODELS_PATH + "grass.json", "88447d7e37b2450dbaa5646af1c83d6c3cb6e96c"))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
}
