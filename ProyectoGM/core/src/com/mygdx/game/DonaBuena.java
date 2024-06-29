package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class DonaBuena extends ObjetoCayendo{
    private int puntajeObjeto;

    public DonaBuena(Texture texturaDonaBuena, Sound sonidoObjeto) {
        super(texturaDonaBuena, sonidoObjeto);
        this.hitboxObjeto = new Rectangle();
        this.hitboxObjeto.height = 0;
        this.hitboxObjeto.width = 0;
        this.velY = MathUtils.random(250, 650);
        this.puntajeObjeto = 5;
    }

    public static DonaBuena crearDonaBuena(Texture textura, Sound sonido) {
        return new DonaBuena(textura, sonido);
    }

    public Rectangle crearObjetoHitbox() {
        hitboxObjeto.x = MathUtils.random(0, 800 - 70);
        hitboxObjeto.y = 480;
        hitboxObjeto.width = 70;
        hitboxObjeto.height = 71;
        return hitboxObjeto;
    }

    public void colisionar(JugadorSingleton jugador) {
        jugador.actualizarPuntaje(puntajeObjeto);
        GameLluvia.aumentarCombo();
        sonidoObjeto.play();
        eliminar();
    }
}
