package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class PezRadioactivo extends ObjetoCayendo{

	public PezRadioactivo(Texture texturaObjeto) {
		super(texturaObjeto);
		this.hitboxObjeto = new Rectangle();
		this.hitboxObjeto.height = 0;
		this.hitboxObjeto.width = 0;
		this.velY = MathUtils.random(250, 650);
	}

	public Rectangle crearObjetoHitbox() {
		hitboxObjeto.x = MathUtils.random(0, 800 - 70);
		hitboxObjeto.y = 480;
		hitboxObjeto.width = 70;
		hitboxObjeto.height = 71;
		return hitboxObjeto;
	}


	public void colisionar() {
		GameLluvia.cambiarPersonaje();
		eliminar();
		
	}

}
