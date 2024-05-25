package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class DonaBuena extends ObjetoCayendo{
	
	public DonaBuena(Texture texturaDonaBuena) {
		super(texturaDonaBuena);
		this.velY = MathUtils.random(250 , 350);
		this.tipoObjeto = 0;
		this.puntajeObjeto = 5;
		crearObjetoHitBox();
	}

	
	@Override
	public void crearObjetoHitBox() {
		Rectangle donutHitbox = new Rectangle();
        donutHitbox.x = MathUtils.random(0, 800 - 70);
        donutHitbox.y = 480;
        donutHitbox.width = 70;
        donutHitbox.height = 71;
        this.hitboxObjeto = donutHitbox;
	}


	@Override
	public void colisionar(Personaje personaje) {
		personaje.sumarPuntos(puntajeObjeto);
		
	}


	
	
}
