package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class LanzadorObjetos {
    private Array<ObjetoCayendo> objetosType;
    private Array<Rectangle> objetosPos;
    private long lastDropTime;
    private DonaBuena donaBuena;
    private DonaMala donaMala;
    private Corazon corazon;
    private PezRadioactivo pez;
    private JugadorSingleton jugador;
    
    public LanzadorObjetos(DonaBuena donaBuena, DonaMala donaMala, Corazon corazon, PezRadioactivo pez) {
    	jugador = JugadorSingleton.getInstance();
        this.donaBuena = donaBuena;
        this.donaMala = donaMala;
        this.corazon = corazon;
        this.pez = pez;
        crear();
    }

    public void crear() {
        objetosPos = new Array<Rectangle>();
        objetosType = new Array<ObjetoCayendo>();
        crearObjeto();
    }

    private void crearObjeto() {
        int randNum = MathUtils.random(1,100);

        ObjetoCayendo objeto;
        if (randNum <= 60) { // 55% dona normal
            objeto = crearDonaBuena();
        }
        else if (randNum <= 90) { // 30% dona dañina
            objeto = crearDonaMala();
        }
        else if (randNum <= 95) { // 10% vida
            objeto = crearCorazon();
        }
        else {
            objeto = crearPezRadioactivo();
        }

        Rectangle hitbox = objeto.crearObjetoHitbox();
        objetosPos.add(hitbox);
        objetosType.add(objeto);

        lastDropTime = TimeUtils.nanoTime();
    }

    private DonaBuena crearDonaBuena() {
        return new DonaBuena(donaBuena.getTexture(), donaBuena.getSound());
    }

    private DonaMala crearDonaMala() {
        return new DonaMala(donaMala.getTexture(), donaMala.getSound());
    }

    private Corazon crearCorazon() {
        return new Corazon(corazon.getTexture(), corazon.getSound());
    }

    private PezRadioactivo crearPezRadioactivo() {
        return new PezRadioactivo(pez.getTexture());
    }

    public void actualizarMovimiento(Rectangle hitboxPersonaje) {
        // generar objetos en caida
        if (TimeUtils.nanoTime() - lastDropTime > 100000000)
            crearObjeto();

        // revisar si los objetos cayeron al suelo o chocaron contra el personaje
        for (int i = 0; i < objetosPos.size; i++) {
            objetosPos.get(i).y -= objetosType.get(i).getVelY() * Gdx.graphics.getDeltaTime();

            // cae al suelo y se elimina
            if (objetosPos.get(i).y + 64 < 0) {
                objetosType.get(i).eliminar();
                objetosType.removeIndex(i);
                objetosPos.removeIndex(i);
            }

            if (objetosPos.get(i).overlaps(hitboxPersonaje)) { // el objeto choca contra el personaje
                // Acciones de la colision
                objetosType.get(i).colisionar(jugador);
                objetosType.removeIndex(i);
                objetosPos.removeIndex(i);
            }
        }
    }

    public void actualizarDibujoLluvia(SpriteBatch batch) {
        for (int i = 0; i < objetosPos.size; i++) {
            batch.draw(objetosType.get(i).getTexture(), objetosPos.get(i).x, objetosPos.get(i).y);
        }
    }

    public void destruir() {
        for (ObjetoCayendo objeto : objetosType) {
            objeto.destroy();
        }
    }
}