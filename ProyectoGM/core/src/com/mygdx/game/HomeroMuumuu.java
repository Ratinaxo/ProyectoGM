package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public class HomeroMuumuu implements PersonajeStrategy{

	private Texture textura;
	private Rectangle hitbox;
	
	private int id;
	private int damageMultiplier;
	private int scoreMultiplier;
	private int lifeMultiplier;
	private int velX;
	
	public HomeroMuumuu() {
		id = 2;
		velX = 250;
		scoreMultiplier = 2;
		lifeMultiplier = 0;
		damageMultiplier = 1;
		
		crear();
		
	}
	
	public void crear() {
		textura = new Texture(Gdx.files.internal("homeroMuumuu.png"));
		hitbox = new Rectangle();
		hitbox.x = 800/2 - 64/2;
		hitbox.y = 20;
		hitbox.width = 100;
		hitbox.height = 150;
	}
	
	public Rectangle getHitbox(){
		return hitbox;
	}
	
	
	public Texture getTexture() {
		return textura;
	}
	
	public void destroy() {
		hitbox = null;
		textura.dispose();
	}
	
	public float getPosX() {
		return hitbox.getX();
	}
	
	public void setPosX(float posX) {
		hitbox.x = posX;
	}
	

	@Override
	public void setHitboxX(float x) {
		hitbox.x = x;
		
	}

	@Override
	public int getVelX() {
		return velX;
	}

	@Override
	public int getPuntajeMultiplier() {
		return scoreMultiplier;
	}

	@Override
	public int getLifeMultiplier() {
		return lifeMultiplier;
	}

	@Override
	public int getDamageMultiplier() {
		return damageMultiplier;
	}
	
	@Override
	public int getId() {
		return id;
	}
}
