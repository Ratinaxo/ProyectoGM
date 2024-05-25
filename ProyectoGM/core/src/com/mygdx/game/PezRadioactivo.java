package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.MathUtils;

public class PezRadioactivo extends ObjetoCayendo{
	
	public PezRadioactivo(Texture texturaDonaMala) {
		super(texturaDonaMala);
		this.velY = MathUtils.random(500, 600);
		this.tipoObjeto = 2;
		this.puntajeObjeto = 15;
		crearObjetoHitBox();
	}

	
	@Override
	public void crearObjetoHitBox() {
		Rectangle donutHitbox = new Rectangle();
        donutHitbox.x = MathUtils.random(0, 800 - 63);
        donutHitbox.y = 480;
        donutHitbox.width = 63;
        donutHitbox.height = 80;
        this.hitboxObjeto = donutHitbox;

	}
	public void cambiarPersonaje(Personaje personaje) {
		personaje.cambiarPersonaje(personaje);
		
	}


	
	public void colisionar(Personaje personaje) {
		personaje.sumarPuntos(puntajeObjeto);
		
	}
	
	
}
