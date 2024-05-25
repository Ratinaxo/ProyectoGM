package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface PersonajeInterfaz {
	public void crear();
	public boolean estaHerido();
	public void dibujar(SpriteBatch batch);
	public void sumarPuntos(int p);
	public void actualizarMovimiento();
	public void destruir();
	
}
