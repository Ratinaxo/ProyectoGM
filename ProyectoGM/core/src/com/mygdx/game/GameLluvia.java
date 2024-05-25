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
import com.badlogic.gdx.utils.ScreenUtils;



public class GameLluvia extends ApplicationAdapter {
       private OrthographicCamera camera;
	   private SpriteBatch batch;	   
	   private BitmapFont font;
	   private int puntos;
	   private Homero homero;
	   private Rectangle hitboxObjeto;
	   
	   private LanzadorObjetos lanzador;
	@Override
	public void create () {
		 font = new BitmapFont(); // use libGDX's default Arial font
		 
		  // load the images for the droplet and the bucket, 64x64 pixels each 	     
		  Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("dou.mp3"));
		  homero = new Homero(new Texture(Gdx.files.internal("homero.png")),hurtSound);
          
	      // load the drop sound effect and the rain background "music" 
          Texture donaBuena = new Texture(Gdx.files.internal("donut.png"));
          Texture donaMala = new Texture(Gdx.files.internal("donutBad.png"));
          
          Sound dropSound = Gdx.audio.newSound(Gdx.files.internal("eructo.mp3"));
         
	      Music rainMusic = Gdx.audio.newMusic(Gdx.files.internal("simpsong.mp3"));
          lanzador = new LanzadorObjetos(donaBuena, donaMala, dropSound, rainMusic);
	      
	      // camera
	      camera = new OrthographicCamera();
	      camera.setToOrtho(false, 800, 480);
	      batch = new SpriteBatch();
	      // creacion del tarro
	      homero.crear();
	      
	      // creacion de la lluvia
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
		font.draw(batch, "Gotas totales: " + homero.getPuntos(), 5, 475);
		font.draw(batch, "Vidas : " + homero.getVidas(), 720, 475);
		
		if (!homero.estaHerido()) {
			// movimiento del tarro desde teclado
	        homero.actualizarMovimiento();        
			// caida de la lluvia 
	        lanzador.actualizarMovimiento(homero);	   
		}
		
		homero.dibujar(batch);
		lanzador.actualizarDibujoLluvia(batch);
		
		batch.end();	
		
	}
	
	@Override
	public void dispose () {
	      homero.destruir();
          lanzador.destruir();
	      batch.dispose();
	      font.dispose();
	}
}