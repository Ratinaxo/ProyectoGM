package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

class DonutBueno extends Lluvia implements LluviaInterfaz {
    public DonutBueno(Texture gotaBuena, Sound ss, Music mm) {
        super(gotaBuena, ss, mm);
    }

    @Override
    protected void crearDonutDeLluvia() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        rainDropsPos.add(raindrop);
        lastDropTime = TimeUtils.nanoTime();
    }

    @Override
    public boolean actualizarMovimiento(Homero tarro) {
        if (TimeUtils.nanoTime() - lastDropTime > 100000000) crearDonutDeLluvia();

        for (int i = 0; i < rainDropsPos.size; i++) {
            Rectangle raindrop = rainDropsPos.get(i);
            raindrop.y -= 300 * Gdx.graphics.getDeltaTime();
            if (raindrop.y + 64 < 0) {
                rainDropsPos.removeIndex(i);
                continue;
            }
            if (raindrop.overlaps(tarro.getArea())) {
                tarro.sumarPuntos(10);
                donutSound.play();
                rainDropsPos.removeIndex(i);
            }
        }
        return true;
    }

    @Override
    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (Rectangle raindrop : rainDropsPos) {
            batch.draw(donutTexture, raindrop.x, raindrop.y);
        }
    }

    @Override
    public void destruir() {
        donutSound.dispose();
        rainMusic.dispose();
    }

    @Override
    public void pausar() {
        rainMusic.stop();
    }

    @Override
    public void continuar() {
        rainMusic.play();
    }
}
