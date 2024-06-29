package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class DonaMala extends ObjetoCayendo{
    protected int puntajeObjeto;
    private int damage;

    public DonaMala(Texture texturaDonaMala, Sound sonidoObjeto) {
        super(texturaDonaMala, sonidoObjeto);
        this.hitboxObjeto = new Rectangle();
        this.hitboxObjeto.height = 0;
        this.hitboxObjeto.width = 0;
        this.velY = MathUtils.random(450, 800);
        this.puntajeObjeto = -10;
        this.damage = -1;
    }

    public static DonaMala crearDonaMala(Texture textura, Sound sonido) {
        return new DonaMala(textura, sonido);
    }

    public Rectangle crearObjetoHitbox() {
        hitboxObjeto.x = MathUtils.random(0, 800 - 45);
        hitboxObjeto.y = 480;
        hitboxObjeto.width = 45;
        hitboxObjeto.height = 46;
        return hitboxObjeto;
    }

    public void colisionar(JugadorSingleton jugador) {
    	jugador.actualizarVidas(damage);
    	jugador.actualizarPuntaje(puntajeObjeto);
    	jugador.actualizarEstadoHerido(true);
        GameLluvia.resetCombo();
        sonidoObjeto.play();
    }
}