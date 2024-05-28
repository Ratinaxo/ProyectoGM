package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Homero implements Personaje{

	private Texture texturePersonaje;
	private Rectangle hitbox;
	
	private int damageMultiplier;
	private int scoreMultiplier;
	private int lifeMultiplier;
	private int velX;
	private int tiempoHeridoMax=50;
	private int tiempoHerido;
	private int id;
	
	public Homero(Texture texture) {
		this.texturePersonaje = texture;
		this.velX = 350;
		this.tiempoHerido = tiempoHeridoMax;
		this.hitbox = new Rectangle();
		id = 0;
		scoreMultiplier = 1;
		lifeMultiplier = 1;
		damageMultiplier = 1;
		crear();
		
	}
	
	public void crear() {
		hitbox.x = 800/2 - 64/2;
		hitbox.y = 20;
		hitbox.width = 64;
		hitbox.height = 64;
		GameLluvia.setScoreMultiplier(scoreMultiplier);
		GameLluvia.setLifeMultiplier(lifeMultiplier);
		GameLluvia.setDamageMultiplier(damageMultiplier);
	}

	   
	public void actualizarMovimiento() { 
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			hitbox.x -= velX * Gdx.graphics.getDeltaTime();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			hitbox.x += velX * Gdx.graphics.getDeltaTime();
		}
		// que no se salga de los bordes izq y der
		if(hitbox.x < 0) hitbox.x = 0;
		if(hitbox.x > 800 - hitbox.width) hitbox.x = 800 - hitbox.width;
	}
	
	public void dibujar(SpriteBatch batch){
		
		if (!GameLluvia.estadoHerido()) {
			tiempoHerido = tiempoHeridoMax;
			batch.draw(texturePersonaje, hitbox.x, hitbox.y);
		}
		else {
			batch.draw(texturePersonaje, hitbox.x, hitbox.y+ MathUtils.random(-5,5));
			tiempoHerido--;
				
			if (tiempoHerido<=0)
				GameLluvia.actualizarEstadoHerido(false);
				
			}
		}
	
	public Rectangle getHitbox(){
		return hitbox;
	}
	
	public void destroy() {
		hitbox = null;
		texturePersonaje.dispose();
	}
	
	public float getPosX() {
		return hitbox.getX();
	}
	
	public void setPosX(float posX) {
		hitbox.x = posX;
	}
	
	public int getIdPersonaje() {
		return id;
	}
}
