package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface PersonajeStrategy {

	public void crear();
	
	public void actualizarMovimiento();
	
	public void dibujar(SpriteBatch batch);
	
	public float getPosX();
	
	public void setPosX(float posX);
	
	public Rectangle getHitbox();
	
	public int getIdPersonaje();
	
	public void destroy();

	public void setHitboxX(float x);

	public Texture getTexture();

	public int getVelX();
}