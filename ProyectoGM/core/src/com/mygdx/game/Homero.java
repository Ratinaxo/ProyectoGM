package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Homero extends Personaje{
	
	private int velx = 400;
	private boolean herido = false;
	private int tiempoHeridoMax=50;
	private int tiempoHerido;
	private int idPersonaje;
	
	public Homero(Texture homeroTexture, Sound sonidoHerido, int idPersonaje) {
		super(homeroTexture, sonidoHerido, idPersonaje);
		crear();
	}
	
   
	public int getIdPersonaje() {
		return idPersonaje;
	}

	public void sumarPuntos(int pp) {
		GameLluvia.puntos+=pp;
	}
		
	
	public void crear() {

		hitboxPersonaje = new Rectangle();
		hitboxPersonaje.x = 800 / 2 - 64 / 2;
		hitboxPersonaje.y = 20;
		hitboxPersonaje.width = 64;
		hitboxPersonaje.height = 64;
	}

	public void danar(int n) {
		vidas-=n;
		herido = true;
		tiempoHerido=tiempoHeridoMax;
		sonidoHerido.play();
	}
	   
	public void dibujar(SpriteBatch batch) {
		if (!herido)  
			batch.draw(texturaPersonaje, hitboxPersonaje.x, hitboxPersonaje.y);
		else {
			batch.draw(texturaPersonaje, hitboxPersonaje.x, hitboxPersonaje.y+ MathUtils.random(-5,5));
			tiempoHerido--;
				
			if (tiempoHerido<=0)
				herido = false;
			}
		} 
	   
	   
	public void actualizarMovimiento() { 
		//movimiento desde teclado
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) hitboxPersonaje.x -= velx * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) hitboxPersonaje.x += velx * Gdx.graphics.getDeltaTime();
		// que no se salga de los bordes izq y der
		if(hitboxPersonaje.x < 0) hitboxPersonaje.x = 0;
		if(hitboxPersonaje.x > 800 - 64) hitboxPersonaje.x = 800 - 64;
	}
	    
	
	public boolean estaHerido() {
		return herido;
	}
	
	public void cambiarPersonaje(Personaje personajeEntrante) {
		this.texturaPersonaje = personajeEntrante.getTexture();
		this.hitboxPersonaje = personajeEntrante.getHitbox();
		this.idPersonaje = personajeEntrante.getIdPersonaje();
	}

	
}
