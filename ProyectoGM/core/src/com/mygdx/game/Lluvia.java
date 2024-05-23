package com.mygdx.game;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public abstract class Lluvia {
    protected Array<Rectangle> rainDropsPos;
    protected long lastDropTime;
    protected Texture gotaTexture;
    protected Sound dropSound;
    protected Music rainMusic;

    public Lluvia(Texture gotaTexture, Sound ss, Music mm) {
        this.gotaTexture = gotaTexture;
        dropSound = ss;
        rainMusic = mm;
    }

    public void crear() {
        rainDropsPos = new Array<Rectangle>();
        crearGotaDeLluvia();
        rainMusic.setLooping(true);
        rainMusic.play();
    }

    protected abstract void crearGotaDeLluvia();

    public abstract boolean actualizarMovimiento(Tarro tarro);

    public abstract void actualizarDibujoLluvia(SpriteBatch batch);

    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }

    public void pausar() {
        rainMusic.stop();
    }

    public void continuar() {
        rainMusic.play();
    }
}
