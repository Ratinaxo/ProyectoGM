package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;


public class GameLluvia extends ApplicationAdapter {
    private static Array<Personaje> personajes;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private static int puntos;
    private static int vidas;
    private static boolean herido;
    private LanzadorObjetos lanzador;
    private static Personaje personajeActual;
    public static int scoreMultiplier;
    public static int lifeMultiplier;
    public static int damageMultiplier;
    public static int combo;
    private static int comboMax;
    private static int idPersonaje;
    private boolean paused;
    private float time;
    private int min;
    private Music backgroundMusic;
    private Music pauseMenuMusic;
    private Music gameoverMusic;

    public void create () {
        scoreMultiplier = 1;
        lifeMultiplier = 1;
        puntos = 0;
        vidas = 1;
        herido = false;
        paused = false;
        personajes = new Array<Personaje>();
        font = new BitmapFont(); // use libGDX's default Arial font
        combo = 1;
        comboMax = 0;
        time = 0;
        min = 0;

        Texture texturaDonaBuena = new Texture(Gdx.files.internal("donutGood.png"));
        Sound sonidoDonaBuena = Gdx.audio.newSound(Gdx.files.internal("eructo.mp3"));

        Texture texturaDonaMala = new Texture(Gdx.files.internal("donutBad.png"));
        Sound sonidoDonaMala = Gdx.audio.newSound(Gdx.files.internal("dou.mp3"));

        Texture texturaCorazon = new Texture(Gdx.files.internal("vida.png"));
        Sound sonidoCorazon = Gdx.audio.newSound(Gdx.files.internal("homeroWohoo.mp3"));

        Texture texturaPezRadioactivo = new Texture(Gdx.files.internal("pezRadioactivo.png"));

        DonaBuena donaBuena = new DonaBuena(texturaDonaBuena, sonidoDonaBuena);
        DonaMala donaMala = new DonaMala(texturaDonaMala, sonidoDonaMala);
        Corazon corazon = new Corazon(texturaCorazon, sonidoCorazon);
        PezRadioactivo pez = new PezRadioactivo(texturaPezRadioactivo);

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("simpsong.mp3"));
        pauseMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("homeroTarareando.mp3"));
        gameoverMusic = Gdx.audio.newMusic(Gdx.files.internal("homeroLlorando.ogg"));
        lanzador = new LanzadorObjetos(donaBuena, donaMala, corazon, pez);

        personajes.add(new Homero(new Texture(Gdx.files.internal("homero.png"))));
        personajes.add(new HomeroNino(new Texture(Gdx.files.internal("homeroNino.png"))));
        personajes.add(new HomeroMuumuu(new Texture(Gdx.files.internal("homeroMuumuu.png"))));
        idPersonaje = 0; // HomeroSimpson
        personajeActual = personajes.get(idPersonaje);

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        // creacion del personaje
        personajeActual.crear();
        // creacion del lanzador de objetos
        lanzador.crear();
        backgroundMusic.setLooping(true);
        gameoverMusic.setLooping(true);
        backgroundMusic.play();
    }

    public static int getPuntos() {
        return puntos;
    }

    public static void actualizarPuntaje(int pp) {
        if (combo != 0) puntos += pp*scoreMultiplier*combo;
        else puntos += pp*scoreMultiplier;
    }

    public static void actualizarVida(int vv) {
        if (vv > 0) {
            vidas += vv*lifeMultiplier;
        }
        else vidas += vv*damageMultiplier;
    }

    public static void actualizarEstadoHerido (boolean estado) {
        herido = estado;
    }

    public static boolean estadoHerido() {
        return herido;
    }

    public static void setScoreMultiplier(int n) {
        scoreMultiplier = n;
    }

    public static void setLifeMultiplier(int n) {
        lifeMultiplier = n;
    }

    public static void setDamageMultiplier(int n) {
        damageMultiplier = n;
    }
    public static void aumentarCombo() {
        combo += 1;
    }

    public static void resetCombo() {
        if (combo > comboMax)
            comboMax = combo;
        combo = 0;
    }

    public static void cambiarPersonaje() {
        float pos = personajeActual.getPosX();
        while(idPersonaje == personajeActual.getIdPersonaje()) {
            personajeActual = personajes.get(MathUtils.random(0,2));

        }
        idPersonaje = personajeActual.getIdPersonaje();
        personajeActual.crear();
        personajeActual.setPosX(pos);
    }

    @Override
    public void render () {
        if (vidas <= 0) {
            // Terminar partida
            setGameOver();
            return;
        }
        if (Gdx.input.isKeyJustPressed(Keys.P)) {
            togglePause();
        }
        //limpia la pantalla con color azul cielo.
        ScreenUtils.clear(Color.SKY);

        //actualizar matrices de la cámara
        camera.update();
        //actualizar 
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        if (paused) {
            font.draw(batch, "Juego Pausado", 350, 240);
        } else {
            time += Gdx.graphics.getDeltaTime();
            min += MathUtils.floor(time / 60);
            // dibujar textos
            font.draw(batch, "Tiempo " + min + ":" + MathUtils.floor(time), 700, 430);
            font.draw(batch, "Puntos totales: " + puntos, 5, 475);
            font.draw(batch, "COMBO X" + combo, 5, 455);
            font.draw(batch, "Vidas : " + vidas, 720, 475);

            if (!estadoHerido()) {
                // movimiento del tarro desde teclado
                personajeActual.actualizarMovimiento();

                // caida de los objetos
                lanzador.actualizarMovimiento(personajeActual.getHitbox());
            }

            lanzador.actualizarDibujoLluvia(batch);
            personajeActual.dibujar(batch);
        }
        batch.end();
    }

    private void togglePause() {
        paused = !paused;

        if (paused) {
            backgroundMusic.pause();
            pauseMenuMusic.play();
        } else {
            pauseMenuMusic.pause();
            backgroundMusic.play();
        }
    }

    @Override
    public void dispose() {
        personajeActual.destroy();
        pauseMenuMusic.dispose();
        backgroundMusic.pause();
        lanzador.destruir();
        batch.dispose();
        font.dispose();
    }

    private void resetGame() {
        vidas = 5;
        puntos = 0;
        combo = 1;
        scoreMultiplier = 1;
        lifeMultiplier = 1;
        damageMultiplier = 1;
        herido = false;
        idPersonaje = 0;
        personajeActual = personajes.get(idPersonaje);
        personajeActual.crear();
        lanzador.crear();
        paused = false;
        time = 0;

        backgroundMusic.play();
        gameoverMusic.stop();
    }

    public void setGameOver() {
        ScreenUtils.clear(Color.BLACK);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        font.draw(batch, "Juego Terminado", 350, 440);
        font.draw(batch, "Tiempo de juego: " + min + ":" + time, 350, 260);
        font.draw(batch, "Puntos totales: " + puntos, 350, 240);
        font.draw(batch, "Combo más alto: " + comboMax, 350, 220);
        font.draw(batch, "Presiona R para reiniciar o Q para salir", 280, 120);

        batch.end();

        backgroundMusic.stop();
        gameoverMusic.play();

        if (Gdx.input.isKeyJustPressed(Keys.R)) {
            resetGame();
        }

        if (Gdx.input.isKeyJustPressed(Keys.Q)) {
            dispose();
            Gdx.app.exit();
        }
    }
}