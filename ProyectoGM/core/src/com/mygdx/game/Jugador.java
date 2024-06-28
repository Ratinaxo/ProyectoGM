package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Jugador {
	private static Jugador _pinstance;
	private PersonajeStrategy personaje;
	private List<PersonajeStrategy> poolPersonajes;
	private int lifeMultiplier;
	private int damageMultiplier;
	private float posX;
	private int velX;
	private int vidas;
	private int points;
	private boolean herido;
	private Rectangle hitbox;

	private int tiempoHeridoMax=50;
	private int tiempoHerido;
	
	private Jugador(){
		poolPersonajes = new ArrayList<>();
		lifeMultiplier = 1;
		damageMultiplier = 1;
		posX = -1;
		vidas = 5;
		points = 0;
		herido = false;
		hitbox = null;
		generatePool();
	}
	
	public static Jugador getInstance() {
		if (_pinstance == null) {
			_pinstance = new Jugador();
		}
		return _pinstance;
	}
	
	public void moveLeft() {
		System.out.println("Player Moving left");
		hitbox.x -= velX * Gdx.graphics.getDeltaTime();
		if(hitbox.x < 0) hitbox.x = 0;
		if(hitbox.x > 800 - hitbox.width) hitbox.x = 800 - hitbox.width;
		personaje.setHitboxX(hitbox.x);
		
	}
	public void moveRight() {
		System.out.println("Player Moving right");
		hitbox.x += velX * Gdx.graphics.getDeltaTime();
		if(hitbox.x < 0) hitbox.x = 0;
		if(hitbox.x > 800 - hitbox.width) hitbox.x = 800 - hitbox.width;
		personaje.setHitboxX(hitbox.x);
	}
	
	public void dibujarConTiempoHerido(SpriteBatch batch){
		
		if (!GameLluvia.estadoHerido()) {
			tiempoHerido = tiempoHeridoMax;
			batch.draw(personaje.getTexture(), hitbox.x, hitbox.y);
		}
		else {
			batch.draw(personaje.getTexture(), hitbox.x+ MathUtils.random(-5,5), hitbox.y + MathUtils.random(-5,5));
			tiempoHerido--;
				
			if (tiempoHerido<=0)
				GameLluvia.actualizarEstadoHerido(false);
				
			}
		}
	private void generatePool() {
		poolPersonajes.add(new Homero());
		poolPersonajes.add(new HomeroNino());
		poolPersonajes.add(new HomeroMuumuu());
	}
	
	public boolean isHerido() {
		return herido;
	}
	
	public void initPlayer() {
		personaje = poolPersonajes.get(0);
		personaje.crear();
		hitbox = personaje.getHitbox();
		velX = personaje.getVelX();
	}
	
	public Texture getTexture() {
		return personaje.getTexture();
	}
	
	public void cambiarPersonje() {
		Random rand = new Random();
		PersonajeStrategy nPersonaje = poolPersonajes.get(rand.nextInt(poolPersonajes.size()));
		posX = personaje.getPosX();
		personaje.destroy();
		personaje = nPersonaje;
		hitbox.x = posX;
		velX = personaje.getVelX();
		
	}
	
	public void draw(SpriteBatch batch) {
		batch.draw(personaje.getTexture(), hitbox.x, hitbox.y);
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public int getVidas() {
		return vidas;
	}
	
	public void reset() {
	    lifeMultiplier = 1;
	    damageMultiplier = 1;
	    posX = -1;
	    velX = 0;
	    vidas = 5;
	    points = 0;
	    herido = false;
	    tiempoHeridoMax = 50;
	    tiempoHerido = tiempoHeridoMax;
	    
	    if (personaje != null) {
	        personaje.destroy();
	    }
	    personaje = poolPersonajes.get(0);
	    personaje.crear();
	    hitbox = personaje.getHitbox();
	    velX = personaje.getVelX();
	}
	
	public void destroy() {
		
	}
}
