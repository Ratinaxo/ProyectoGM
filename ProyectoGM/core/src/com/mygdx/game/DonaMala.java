package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class DonaMala extends ObjetoCayendo{
	
	public DonaMala(Texture texturaDonaMala) {
		super(texturaDonaMala);
		this.velY = 325;
		this.tipoObjeto = 1;
	}

	
	public void crearObjetoHitBox() {
		Rectangle donutHitbox = new Rectangle();
        donutHitbox.x = MathUtils.random(0, 800 - 64);
        donutHitbox.y = 480;
        donutHitbox.width = 64;
        donutHitbox.height = 64;
	}
	
}
