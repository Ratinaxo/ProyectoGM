package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameLluvia extends ApplicationAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    private Tarro homero;
    private Lluvia donutBuenaLluvia;
    private Lluvia donutMalaLluvia;

    @Override
    public void create() {
        font = new BitmapFont();

        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("dou.mp3"));
        homero = new Tarro(new Texture(Gdx.files.internal("homero.png")), hurtSound);

        Texture gotaBuenaTexture = new Texture(Gdx.files.internal("donut.png"));
        Texture gotaMalaTexture = new Texture(Gdx.files.internal("donutBad.png"));

        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("eructo.mp3"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("simpsong.mp3"));

        donutBuenaLluvia = new GotaBuena(gotaBuenaTexture, dropSound, rainMusic);
        donutMalaLluvia = new GotaMala(gotaMalaTexture, dropSound, rainMusic);

        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        homero.crear();

        donutBuenaLluvia.crear();
        donutMalaLluvia.crear();
    }

    @Override
    public void render()
    {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();      
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Gotas totales: " + homero.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + homero.getVidas(), 720, 475);

        if (!homero.estaHerido()) {
            homero.actualizarMovimiento();
            donutBuenaLluvia.actualizarMovimiento(homero);
            donutMalaLluvia.actualizarMovimiento(homero);
        }

        homero.dibujar(batch);
        donutBuenaLluvia.actualizarDibujoLluvia(batch);
        donutMalaLluvia.actualizarDibujoLluvia(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        homero.destruir();
        donutBuenaLluvia.destruir();
        donutMalaLluvia.destruir();
        batch.dispose();
        font.dispose();
    }
}