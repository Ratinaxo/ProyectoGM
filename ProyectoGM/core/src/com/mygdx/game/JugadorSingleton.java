package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class JugadorSingleton {
	
	private static JugadorSingleton _pinstance;
	private PersonajeStrategy personaje;
	private List<PersonajeStrategy> poolPersonajes;
	private int idPersonaje;
	
	
	private int lifeMultiplier;
	private int damageMultiplier;
	private int scoreMultiplier;
	
	private float posX;
	private int velX;
	private int vidas;
	private int puntaje;
	private boolean herido;
	private Rectangle hitbox;
	private int tiempoHeridoMax=50;
	private int tiempoHerido;
	
	private JugadorSingleton(){
		poolPersonajes = new ArrayList<>();
		lifeMultiplier = 1;
		damageMultiplier = 1;
		posX = -1;
		vidas = 5;
		puntaje = 0;
		herido = false;
		hitbox = null;
		generatePool();
	}
	
	public static JugadorSingleton getInstance() {
		if (_pinstance == null) {
			_pinstance = new JugadorSingleton();
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
	
	public void draw(SpriteBatch batch){
		
		if (!herido) {
			tiempoHerido = tiempoHeridoMax;
			batch.draw(personaje.getTexture(), hitbox.x, hitbox.y);
		}
		else {
			batch.draw(personaje.getTexture(), hitbox.x+ MathUtils.random(-5,5), hitbox.y + MathUtils.random(-5,5));
			tiempoHerido--;
				
			if (tiempoHerido<=0)
				herido = false;
				
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
		idPersonaje = 0;
		personaje = poolPersonajes.get(idPersonaje);
		personaje.crear();
		hitbox = personaje.getHitbox();
		velX = personaje.getVelX();
		
		scoreMultiplier = personaje.getPuntajeMultiplier();
		lifeMultiplier = personaje.getLifeMultiplier();
		damageMultiplier = personaje.getDamageMultiplier();
	}
	
	public Texture getTexture() {
		return personaje.getTexture();
	}
	
	public void cambiarPersonajeRandom() {
		int nid = idPersonaje;
		while (nid == idPersonaje) {
			nid = MathUtils.random(0, poolPersonajes.size()-1);
		}
		
		posX = personaje.getPosX();
		personaje.destroy();
		personaje = poolPersonajes.get(nid);
		personaje.crear();
		personaje.setPosX(posX);
		
		hitbox = personaje.getHitbox();
		velX = personaje.getVelX();
		idPersonaje = personaje.getId();
		
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
	    puntaje = 0;
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
	
	public void actualizarVidas(int vv) {
		if (vv > 0) {
			vidas += vv*lifeMultiplier;
		}
		else
			vidas += vv*damageMultiplier;
			herido = true;
	}
	
	public void destroy() {
		
	}

	public void actualizarPuntaje(int pp) {
        puntaje += pp*scoreMultiplier*GameLluvia.combo;
    }

	public void actualizarEstadoHerido(boolean estado) {
		herido = estado;
		
	}

	public int getPuntaje() {
		return puntaje;
	}
	
}
