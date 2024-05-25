package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class DonaMala extends ObjetoCayendo{
	
	public DonaMala(Texture texturaDonaMala) {
		super(texturaDonaMala);
		this.velY = 500;
		this.tipoObjeto = 1;
		crearObjetoHitBox();
	}

	
	@Override
	public void crearObjetoHitBox() {
		Rectangle donutHitbox = new Rectangle();
        donutHitbox.x = MathUtils.random(0, 800 - 16);
        donutHitbox.y = 480;
        donutHitbox.width = 16;
        donutHitbox.height = 16;
        this.hitboxObjeto = donutHitbox;
	}
	
}
