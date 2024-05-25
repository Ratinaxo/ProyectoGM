package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class DonaMala extends ObjetoCayendo{
	private int hurts;
	
	public DonaMala(Texture texturaDonaMala) {
		super(texturaDonaMala);
		this.velY = MathUtils.random(400, 700);;
		this.tipoObjeto = 1;
		this.puntajeObjeto = -15;
		this.hurts = 1;
		crearObjetoHitBox();
	}

	public void crearObjetoHitBox() {
		Rectangle donutHitbox = new Rectangle();
        donutHitbox.x = MathUtils.random(0, 800 - 45);
        donutHitbox.y = 480;
        donutHitbox.width = 45;
        donutHitbox.height = 46;
        this.hitboxObjeto = donutHitbox;
	}


	public void colisionar(Personaje personaje) {
		personaje.danar(hurts);
		personaje.sumarPuntos(puntajeObjeto);
	}
	
	
	
	
}
