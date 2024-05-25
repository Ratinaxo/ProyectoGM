package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public interface PersonajeInterfaz {
	public void crear();
	public Rectangle getHitbox();
	public int sumarPuntos();
	public void danar();
	public void actualizarMovimiento();
	public void cambiarPersonaje();
}
