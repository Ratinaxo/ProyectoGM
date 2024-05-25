package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class HomeroNino extends HomeroAbs {
	private int velX = 800;
	
	public HomeroNino(Texture tex, Sound ss, int tipo) {
		super(tex, ss, tipo);
		
	}

	public void crear() {
		homeroHitbox = new Rectangle();
		homeroHitbox.x = 800 / 2 - 64 / 2;
		homeroHitbox.y = 10;
		homeroHitbox.width = 16;
		homeroHitbox.height = 16;
	}
	
	
	public void danar() 
	{
		vidas -= 2;
		herido = true;
		tiempoHerido=tiempoHeridoMax;
		sonidoHerido.play();
	}	
	
	public void sumarPuntos() {
		this.puntos += 20; 
	}
	
	public void actualizarMovimiento() { 
	//movimiento desde teclado
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) homeroHitbox.x -= velX * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) homeroHitbox.x += velX * Gdx.graphics.getDeltaTime();
		// que no se salga de los bordes izq y der
		if(homeroHitbox.x < 0) homeroHitbox.x = 0;
		if(homeroHitbox.x > 800 - 16) homeroHitbox.x = 800 - 16;
	}	
}
