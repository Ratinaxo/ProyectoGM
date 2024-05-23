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

    private Tarro tarro;
    private Lluvia gotaBuenaLluvia;
    private Lluvia gotaMalaLluvia;

    @Override
    public void create() {
        font = new BitmapFont();

        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));
        tarro = new Tarro(new Texture(Gdx.files.internal("bucket.png")), hurtSound);

        Texture gotaBuenaTexture = new Texture(Gdx.files.internal("drop.png"));
        Texture gotaMalaTexture = new Texture(Gdx.files.internal("dropBad.png"));

        Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
        Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

        gotaBuenaLluvia = new GotaBuena(gotaBuenaTexture, dropSound, rainMusic);
        gotaMalaLluvia = new GotaMala(gotaMalaTexture, dropSound, rainMusic);

        // Camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        tarro.crear();

        gotaBuenaLluvia.crear();
        gotaMalaLluvia.crear();
    }

    @Override
    public void render()
    {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();      
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Gotas totales: " + tarro.getPuntos(), 5, 475);
        font.draw(batch, "Vidas : " + tarro.getVidas(), 720, 475);

        if (!tarro.estaHerido()) {
            tarro.actualizarMovimiento();
            gotaBuenaLluvia.actualizarMovimiento(tarro);
            gotaMalaLluvia.actualizarMovimiento(tarro);
        }

        tarro.dibujar(batch);
        gotaBuenaLluvia.actualizarDibujoLluvia(batch);
        gotaMalaLluvia.actualizarDibujoLluvia(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        tarro.destruir();
        gotaBuenaLluvia.destruir();
        gotaMalaLluvia.destruir();
        batch.dispose();
        font.dispose();
    }
}