package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoCayendo implements Colisionable {
	
	protected Texture texturaObjeto;
	protected int tipoObjeto;
	protected Rectangle hitboxObjeto;
	protected int velY;
	protected int puntajeObjeto;
	
	public ObjetoCayendo(Texture texturaObjeto) {
		this.texturaObjeto = texturaObjeto;
	}

	public int getPuntos() {
		return puntajeObjeto;
	}
	
	public abstract void crearObjetoHitBox();
	
	public Texture getTexture(){
		return texturaObjeto;
	}
	
	public Rectangle getHitbox() {
		return hitboxObjeto;
	}
	
	public int getTipoObjeto() {
		return tipoObjeto;
	}
	
	public int getVelY() {
		return velY;
	}
	
}