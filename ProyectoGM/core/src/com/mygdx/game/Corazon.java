package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Corazon extends ObjetoCayendo{
	private int puntajeObjeto;
	private int heals;
	
	public Corazon(Texture corazonTextura, Sound sonidoCorazon) {
		super(corazonTextura, sonidoCorazon);
		this.hitboxObjeto = new Rectangle();
		this.hitboxObjeto.height = 0;
		this.hitboxObjeto.width = 0;
		this.velY = MathUtils.random(50, 1000);
		this.sonidoObjeto = sonidoCorazon;
		this.puntajeObjeto = 5;
		this.heals = 1;
		
	}

	
	public Rectangle crearObjetoHitbox() {
        hitboxObjeto.x = MathUtils.random(0, 800 - 70);
        hitboxObjeto.y = 480;
        hitboxObjeto.width = 70;
        hitboxObjeto.height = 71;
        return hitboxObjeto;
	}


	
	public void colisionar() {
		GameLluvia.actualizarPuntaje(puntajeObjeto);
		GameLluvia.actualizarVida(heals);
		sonidoObjeto.play();
		
	}
}
