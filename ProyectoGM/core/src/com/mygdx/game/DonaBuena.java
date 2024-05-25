package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class DonaBuena extends ObjetoCayendo{
	
	public DonaBuena(Texture texturaDonaBuena) {
		super(texturaDonaBuena);
		this.velY = 300;
		this.tipoObjeto = 0;
		crearObjetoHitBox();
	}

	
	@Override
	public void crearObjetoHitBox() {
		Rectangle donutHitbox = new Rectangle();
        donutHitbox.x = MathUtils.random(0, 800 - 64);
        donutHitbox.y = 480;
        donutHitbox.width = 64;
        donutHitbox.height = 64;
        this.hitboxObjeto = donutHitbox;

	}
	
	
}
