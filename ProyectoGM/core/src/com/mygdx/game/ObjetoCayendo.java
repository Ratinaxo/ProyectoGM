package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoCayendo {
	protected Rectangle hitboxObjeto;
	protected Texture texturaObjeto;
	protected Sound sonidoObjeto;
	protected int velY;
	
	public ObjetoCayendo(Texture texturaObjeto, Sound sonidoObjeto) {
		this.texturaObjeto = texturaObjeto;
		this.sonidoObjeto = sonidoObjeto;
	}
	public ObjetoCayendo(Texture texturaObjeto) {
		this.texturaObjeto = texturaObjeto;
		this.sonidoObjeto = null;
	}

	public Rectangle getHitbox() {
		return hitboxObjeto;
	}
	
	public Texture getTexture() {
		return texturaObjeto;
	}

	public Sound getSound() {
		return sonidoObjeto;
	}
		
	public abstract Rectangle crearObjetoHitbox();
	
	public abstract void colisionar();
	
	public void destroy() {
		if (!texturaObjeto.equals(null)) texturaObjeto.dispose();
		if (!sonidoObjeto.equals(null)) sonidoObjeto.dispose();
	}	

	public int getVelY() {
		return velY;
	}
	
	public void eliminar() {
		hitboxObjeto.width = 0;
		hitboxObjeto.height = 0;
	}
}