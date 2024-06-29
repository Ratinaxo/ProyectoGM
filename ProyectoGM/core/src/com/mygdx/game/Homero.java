package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


public class Homero implements PersonajeStrategy{

	private Texture textura;
	private Rectangle hitbox;
	private int id;
	private int damageMultiplier;
	private int scoreMultiplier;
	private int lifeMultiplier;
	private int velX;
	
	public Homero() {
		id = 0;
		velX = 350;
		scoreMultiplier = 1;
		lifeMultiplier = 1;
		damageMultiplier = 1;
		crear();
		
	}
	
	@Override
	public void crear() {
		textura = new Texture(Gdx.files.internal("homero.png"));
		hitbox = new Rectangle();
		hitbox.x = 800/2 - 64/2;
		hitbox.y = 20;
		hitbox.width = 64;
		hitbox.height = 64;
	}
	
	
	@Override
	public Rectangle getHitbox(){
		return hitbox;
	}
	
	@Override
	public void destroy() {
		hitbox = null;
		textura.dispose();
	}
	
	@Override
	public float getPosX() {
		return hitbox.getX();
	}
	
	@Override
	public void setPosX(float posX) {
		hitbox.x = posX;
	}

	@Override
	public void setHitboxX(float x) {
		hitbox.x = x;
		
	}

	@Override
	public Texture getTexture() {
		return textura;
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
