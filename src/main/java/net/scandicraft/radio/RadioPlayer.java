package net.scandicraft.radio;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.JavaSoundAudioDevice;
import javazoom.jl.player.PlayerApplet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.scandicraft.Environnement;
import net.scandicraft.config.Config;

import java.net.URL;

public class RadioPlayer extends PlayerApplet {

    private static RadioPlayer instance = null;
    private JavaSoundAudioDevice radioAudioDeviceInstance = null;

    private final boolean testRadio = Config.ENV == Environnement.DEV;
    private RadioType radioType = null;
    private boolean isPlaying = false;

    public static RadioPlayer getInstance() {
        if (instance == null) {
            instance = new RadioPlayer();
        }

        return instance;
    }

    @Override
    public void start() {
        super.start();

        this.isPlaying = true;
    }

    @Override
    public void stop() {
        super.stop();

        this.isPlaying = false;
    }

    @Override
    protected AudioDevice getAudioDevice() throws JavaLayerException {
        float initVolume = Minecraft.getMinecraft().gameSettings.getSoundLevel(SoundCategory.RADIO);

        if (this.radioAudioDeviceInstance == null) {
            if (testRadio) {
                new RadioAudioDevice(initVolume).test();
            }
            radioAudioDeviceInstance = new RadioAudioDevice(initVolume);
        }

        return this.radioAudioDeviceInstance;
    }

    public void setVolume(float volume) {
        try {
            AudioDevice audioDevice = this.getAudioDevice();
            if (audioDevice instanceof RadioAudioDevice) {
                RadioAudioDevice radioAudioDevice = (RadioAudioDevice) audioDevice;
                radioAudioDevice.setLineGain(volume);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected URL getAudioURL() {
        String urlString = this.getFileName();
        URL url = null;

        if (urlString != null) {
            try {
                url = new URL(urlString);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return url;
    }

    public void setRadioType(RadioType radioType) {
        this.radioType = radioType;

        setFileName(radioType.getFlux());
    }

    public RadioType getRadioType() {
        return this.radioType;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
