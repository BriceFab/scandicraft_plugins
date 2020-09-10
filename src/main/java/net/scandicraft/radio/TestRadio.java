package net.scandicraft.radio;

public class TestRadio {
    public static void test() {
        try {
            RadioPlayer radioPlayer = RadioPlayer.getInstance();
            radioPlayer.setRadioType(RadioType.RHONEFM);
            radioPlayer.start();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
