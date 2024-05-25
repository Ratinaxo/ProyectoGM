package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public abstract class HomeroAbs {
	protected Rectangle homeroHitbox;
	protected Texture homeroImage;
	protected Sound sonidoHerido;
	protected int vidas = 3;
	protected int puntos = 0;
	private int tipoHomeroActivo;
	protected boolean herido = false;
	protected int tiempoHeridoMax=50;
	protected int tiempoHerido;
	   
	public HomeroAbs(Texture homeroTextura, Sound sonidoHerido, int tipoHomeroActivo) {
		this.homeroImage = homeroTextura;
		this.sonidoHerido = sonidoHerido;
		this.tipoHomeroActivo = tipoHomeroActivo;
	}
	
	public abstract void crear();
	
	public int getVidas() {
		return vidas;
	}
	
	public int getPuntos() {
		return puntos;
	}
	public Rectangle getArea() {
		return homeroHitbox;
	}
	public abstract void sumarPuntos();
	
	
	public abstract void danar();
	
			

	public void dibujar(SpriteBatch batch) {
		if (!herido)  
			batch.draw(homeroImage, homeroHitbox.x, homeroHitbox.y);
		else {	
			batch.draw(homeroImage, homeroHitbox.x, homeroHitbox.y+ MathUtils.random(-5,5));
			tiempoHerido--;
			if (tiempoHerido<=0) herido = false;				
		}
	}
	
	public abstract void actualizarMovimiento();
	    

	public void destruir() {
		homeroImage.dispose();
	}
	
	public  boolean estaHerido() {
		return herido;
	}
	
}