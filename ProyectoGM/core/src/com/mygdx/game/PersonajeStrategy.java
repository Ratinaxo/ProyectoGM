package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public interface PersonajeStrategy {

	public void crear();
	
	
	public float getPosX();
	
	public void setPosX(float posX);
	
	public Rectangle getHitbox();
	
	public void destroy();

	public void setHitboxX(float x);

	public Texture getTexture();

	public int getVelX();

	public int getPuntajeMultiplier();

	public int getLifeMultiplier();

	public int getDamageMultiplier();


	int getId();
}
