package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public abstract class ObjetoCayendo {
    protected Rectangle hitboxObjeto;
    protected Texture texturaObjeto;
    protected Sound sonidoObjeto;
    protected int velY;
    protected Jugador jugador;
    
    public ObjetoCayendo(Texture texturaObjeto, Sound sonidoObjeto) {
        this.texturaObjeto = texturaObjeto;
        this.sonidoObjeto = sonidoObjeto;
        jugador = Jugador.getInstance();
    }

    public ObjetoCayendo(Texture texturaObjeto) {
        this.texturaObjeto = texturaObjeto;
        this.sonidoObjeto = null;
    }

    public Rectangle getHitbox() {
        return hitboxObjeto;
    }

    public Texture getTexture() {
        return texturaObjeto;
    }

    public Sound getSound() {
        return sonidoObjeto;
    }

    public abstract Rectangle crearObjetoHitbox();

    // Template Method
    public void crearObjeto() {
        crearObjetoHitbox();
        colisionar(jugador);
    }

    public void destroy() {
        if (texturaObjeto != null) texturaObjeto.dispose();
        if (sonidoObjeto != null) sonidoObjeto.dispose();
    }

    public int getVelY() {
        return velY;
    }

    public void eliminar() {
        hitboxObjeto.width = 0;
        hitboxObjeto.height = 0;
    }
    public abstract void colisionar(Jugador jugador);
}