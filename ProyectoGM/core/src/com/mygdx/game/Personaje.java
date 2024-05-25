package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class Personaje implements PersonajeInterfaz{
	protected Texture texturaPersonaje;
	protected Rectangle hitboxPersonaje;
	protected Sound sonidoHerido;
	protected int idPersonaje;
	protected int velX;
	protected int vidas;
	
	public Personaje(Texture tt, Sound ss, int id) {
		this.texturaPersonaje = tt;
		this.sonidoHerido = ss;
		this.idPersonaje = id;
		this.vidas = 3;
	}
	
	public abstract boolean estaHerido();
	
	public abstract void danar(int dano);
	
	public abstract void sumarPuntos(int pp);
	
	public abstract void actualizarMovimiento();
	
	public abstract void cambiarPersonaje(Personaje personajeEntrante);
	
	public void destruir() {
		texturaPersonaje.dispose();
	}
	
	public Rectangle getHitbox() {
		return this.hitboxPersonaje;
	}
	
	public Texture getTexture() {
		return texturaPersonaje;
	}
	
	public Sound getSound() {
		return sonidoHerido;
	}
	
	public int getIdPersonaje() {
		return idPersonaje;
	}
	
	public int getVidas() {
		return vidas;
	}
	
}
