package com.harmonygames.engine.audio;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class AudioClip {

    private Clip clip;

    public AudioClip(String path) {
        try {
            InputStream audioSrc = AudioClip.class.getResourceAsStream(path);
            InputStream bufferedInput = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInput);
            AudioFormat baseAudioFormat = audioInputStream.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseAudioFormat.getSampleRate(), 16, baseAudioFormat.getChannels(),
                    baseAudioFormat.getChannels() * 2, baseAudioFormat.getSampleRate(), false);
            AudioInputStream decodedAudioStream = AudioSystem.getAudioInputStream(decodeFormat, audioInputStream);

            clip = AudioSystem.getClip();
            clip.open(decodedAudioStream);
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("[Harmony Engine (Audio Clip): ] Error loading audio clip '" + path + "'");
            e.printStackTrace();
        }
    }

    public void setGain(float gain) { ((FloatControl) this.clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(gain); }
    public boolean isActive() { return this.clip.isRunning(); }
    public boolean isPlaying() { return this.clip.isActive(); }
    public Clip getRawClip() { return this.clip; }

    public void play() {
        if(clip == null) {
            System.err.println("[Harmony Engine (AudioClip)]: Null Clip");
            assert false;
        }

        this.stop();
        this.clip.setFramePosition(0);

        int i = 0;
        while(!clip.isRunning() && i < 10) {
            clip.start();
            i++;
        }
    }

    public void stop() {
        if(clip.isRunning()) this.clip.stop();
    }

    public void close() {
        this.stop();
        this.clip.drain();
        this.clip.close();
    }

    public void startLoop() {
        this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        this.play();
    }

}
