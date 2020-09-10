package net.scandicraft.radio;

public enum RadioType {
    RHONEFM("Rhône FM", "https://rhonefm.ice.infomaniak.ch/rhonefm-high.mp3"),
    NRJ("NRJ", "http://cdn.nrjaudio.fm/audio1/fr/30001/mp3_128.mp3?origine=fluxradios"),
    FUNRADIO("Fun Radio", "http://streaming.radio.funradio.fr/fun-1-48-192"),
    RADIOFQ("Radio FQ", "http://radiofg.impek.com/fg");

    private final String name;
    private final String flux;

    RadioType(String name, String flux) {
        this.name = name;
        this.flux = flux;
    }

    public String getName() {
        return name;
    }

    public String getFlux() {
        return flux;
    }
}
