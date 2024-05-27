package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
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
	private static int idPersonaje;
	
	public void create () {
		scoreMultiplier = 1;
		lifeMultiplier = 1;
		puntos = 0;
		vidas = 5;
		herido = false;
		personajes = new Array<Personaje>();
		font = new BitmapFont(); // use libGDX's default Arial font
		
		Array<ObjetoCayendo> objetos = new Array<ObjetoCayendo>();
		
		// load the sounds effect and the background "music"
		objetos.add(new DonaBuena(new Texture(Gdx.files.internal("donutGood.png")), Gdx.audio.newSound(Gdx.files.internal("eructo.mp3"))));
		objetos.add(new DonaMala(new Texture(Gdx.files.internal("donutBad.png")), Gdx.audio.newSound(Gdx.files.internal("dou.mp3"))));
		objetos.add(new Corazon(new Texture(Gdx.files.internal("vida.png")), Gdx.audio.newSound(Gdx.files.internal("homeroWohoo.mp3"))));
		objetos.add(new PezRadioactivo(new Texture(Gdx.files.internal("pezRadioactivo.png"))));
		
		
		Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("simpsong.mp3"));
		lanzador = new LanzadorObjetos(objetos, rainMusic);
		personajes.add(new Homero(new Texture(Gdx.files.internal("homero.png"))));
		personajes.add(new HomeroNino(new Texture(Gdx.files.internal("homeroNino.png"))));
		personajes.add(new HomeroMuumuu(new Texture(Gdx.files.internal("homeroMuumuu.png"))));
		idPersonaje = 0; //HomeroSimpson
		personajeActual = personajes.get(idPersonaje);
		// camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		// creacion del personaje 
		personajeActual.crear();
		// creacion del lanzador de objetos
		lanzador.crear();
		
	}
	
	public static int getPuntos() {
		return puntos;
	}
	
	public static void actualizarPuntaje(int pp) {
		puntos += pp*scoreMultiplier;
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
		//limpia la pantalla con color azul cielo.
		ScreenUtils.clear(Color.SKY);
		
		//actualizar matrices de la cámara
		camera.update();
		
		//actualizar 
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		//dibujar textos
		font.draw(batch, "Puntos totales: " + puntos, 5, 475);
		font.draw(batch, "Vidas : " + vidas, 720, 475);
		
		
		if (!estadoHerido()) {
			// movimiento del tarro desde teclado
			personajeActual.actualizarMovimiento();
			
			// caida de los objetos
			lanzador.actualizarMovimiento(personajeActual.getHitbox());	   
		}

		lanzador.actualizarDibujoLluvia(batch);
		personajeActual.dibujar(batch);
		
		batch.end();
		
	}
	
	@Override
	public void dispose () {
		personajeActual.destroy();
		lanzador.destruir();
		batch.dispose();
		font.dispose();
	}

	
}