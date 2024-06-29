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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;


public class GameLluvia extends ApplicationAdapter {
    
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    
    private static JugadorSingleton jugador;
    private LanzadorObjetos lanzador;
    
    public static int combo;
    private static int comboMax;
    
    private boolean paused;
    private float time;
    private int min;
    private Music backgroundMusic;
    private Music pauseMenuMusic;
    private Music gameoverMusic;
    

    public void create () {
        paused = false;
        jugador = JugadorSingleton.getInstance();
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

        

        // camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        // creacion del personaje
        jugador.initPlayer();
        // creacion del lanzador de objetos
        lanzador.crear();
        backgroundMusic.setLooping(true);
        gameoverMusic.setLooping(true);
        backgroundMusic.play();
    }
    
    public static void aumentarCombo() {
        combo += 1;
    }

    public static void resetCombo() {
        if (combo > comboMax)
            comboMax = combo;
        combo = 0;
    }

    @Override
    public void render () {
    	if (jugador.getVidas() <= 0) {
    	        System.out.println("Player life 0");
    	        setGameOver();
    	        return;
    	    }
    	if (!paused) {
    		
    		//limpia la pantalla con color azul cielo.
            ScreenUtils.clear(Color.SKY);

            //actualizar matrices de la cámara
            camera.update();
            //actualizar 
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            
            
            handleLogic();
            
            batch.end();
    	}
    	else if (paused) {
    		if (Gdx.input.isKeyJustPressed(Keys.P)) {
    			resume();
    		}
    	}
    }
    
    private void handleInput() {
    	if (Gdx.input.isKeyPressed(Keys.LEFT)) {
    		jugador.moveLeft();
	    }
	    if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
	        jugador.moveRight();
	    }
        if (Gdx.input.isKeyJustPressed(Keys.P)) 
			pause();
    }
    
    private void handleLogic() {
    	System.out.println(jugador.getVidas());
    	if (jugador.getVidas() <= 0) {
    		setGameOver();
    	}
    	time += Gdx.graphics.getDeltaTime();
    	min += MathUtils.floor(time / 60);
        // dibujar textos
        generateFont();
        handleInput();
        lanzador.actualizarMovimiento(jugador.getHitbox());
        lanzador.actualizarDibujoLluvia(batch);
        jugador.draw(batch);
    }
    
    private void generateFont() {
    	font.draw(batch, "Tiempo " + min + ":" + MathUtils.floor(time), 700, 430);
        font.draw(batch, "Puntos totales: " + jugador.getPuntaje(), 5, 475);
        font.draw(batch, "COMBO X" + combo, 5, 455);
        font.draw(batch, "Vidas : " + jugador.getVidas(), 720, 475);
    }
    
    public static void cambiarPersonaje() {
    	jugador.cambiarPersonajeRandom();
    }

    @Override
    public void dispose() {
        jugador.destroy();
        pauseMenuMusic.dispose();
        backgroundMusic.pause();
        lanzador.destruir();
        batch.dispose();
        font.dispose();
    }

    private void resetGame() {
        combo = 1;
        jugador.reset();
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
        font.draw(batch, "Puntos totales: " + jugador.getPuntaje(), 350, 240);
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
    
    @Override
    public void pause() {
        if (!paused) {  // Only pause if not already paused
            backgroundMusic.pause();
            pauseMenuMusic.play();;
            font.draw(batch, "Juego Pausado", 350, 240);
            paused = true;
        }
    }

    @Override
    public void resume() {
        if (paused) {  // Only resume if paused
            pauseMenuMusic.stop();
            backgroundMusic.play();
            paused = false;
        }
    }
}