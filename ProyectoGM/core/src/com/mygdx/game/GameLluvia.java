package com.mygdx.game;



import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;



public class GameLluvia extends ApplicationAdapter {
	private Array<Personaje> personajes; //Homero: 0, Homero nino = 1
	private OrthographicCamera camera;
	private SpriteBatch batch;	   
	private BitmapFont font;
	public static int puntos = 0;
	private Personaje personajeActual;
	public static boolean cambiarPersonaje;
	private LanzadorObjetos lanzador;
	
	private void initPersonajes() {
		personajes = new Array<Personaje>();
		//General hurt sound
		Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("dou.mp3"));
		
		//Personajes Homero Simpson: id= 0, Homero Nino: id= 1.
		//Imagenes de los personajes de aproximadamente 64x64
		Homero homeroSimpson = new Homero(new Texture(Gdx.files.internal("homero.png")), hurtSound, 0);
		
		this.personajes.add(homeroSimpson);
		
		HomeroNino homeroNino = new HomeroNino(new Texture(Gdx.files.internal("homeroNino.png")), hurtSound, 1);
		this.personajes.add(homeroNino);
		
		//Los indices del arreglo seran los id de personaje
	}
	
	@Override
	public void create () {
		initPersonajes();
		cambiarPersonaje = false;
		puntos = 0;
		font = new BitmapFont(); // use libGDX's default Arial font
		
		//Personaje inicial es Homero Simpson
		personajeActual = personajes.get(0);
        
		// load the drop sound effect and the rain background "music" 
		Texture donaBuena = new Texture(Gdx.files.internal("donutGood.png"));
		Texture donaMala = new Texture(Gdx.files.internal("donutBad.png"));
		Texture pez = new Texture(Gdx.files.internal("pezRadioactivo.png"));
		Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("eructo.mp3"));
		
		Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("simpsong.mp3"));
		lanzador = new LanzadorObjetos(donaBuena, donaMala, pez, dropSound, rainMusic);
		
		// camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		
		// creacion del personaje 
		personajeActual.crear();
		
		// creacion del lanzador de objetos
		lanzador.crear();
	}

	@Override
	public void render () {
		
		//limpia la pantalla con color azul obscuro.
		ScreenUtils.clear(0, 0, 0.2f, 1);
		
		//actualizar matrices de la cámara
		camera.update();
		
		//actualizar 
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		//dibujar textos
		font.draw(batch, "Puntos totales: " + puntos, 5, 475);
		font.draw(batch, "Vidas : " + personajeActual.getVidas(), 720, 475);
		
		
		if (!personajeActual.estaHerido()) {
			// movimiento del tarro desde teclado
			personajeActual.actualizarMovimiento();
			
			// caida de la lluvia 
			lanzador.actualizarMovimiento(personajeActual);	   
		}
		
		personajeActual.dibujar(batch);
		lanzador.actualizarDibujoLluvia(batch);
		
		batch.end();	
		
	}
	
	@Override
	public void dispose () {
		personajeActual.destruir();
		personajeActual.destruir();
          lanzador.destruir();
	      batch.dispose();
	      font.dispose();
	}
}