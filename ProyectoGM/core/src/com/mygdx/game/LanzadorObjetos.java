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
    
	public LanzadorObjetos(Array<ObjetoCayendo> objetos) {
		this.donaBuena = (DonaBuena)objetos.get(0);
		this.donaMala = (DonaMala)objetos.get(1);
		this.corazon = (Corazon)objetos.get(2);
		this.pez = (PezRadioactivo)objetos.get(3);
		crear();
	}
	
	public void crear() {
		objetosPos = new Array<Rectangle>();
		objetosType = new Array<ObjetoCayendo>();
		crearObjeto();
	}
	
	private void crearObjeto() {
		int randNum = MathUtils.random(1,100);
		
		if (randNum <= 60) { //55% dona normal
			DonaBuena oo = new DonaBuena(donaBuena.getTexture(), donaBuena.getSound());
			Rectangle hitbox = oo.crearObjetoHitbox();
			objetosPos.add(hitbox);
			objetosType.add(oo);

		}
		else if (randNum <= 90){ // 30% dona dañina
			DonaMala oo = new DonaMala(donaMala.getTexture(), donaMala.getSound());
			Rectangle hitbox = oo.crearObjetoHitbox();
			objetosPos.add(hitbox);
			objetosType.add(oo);

		}
		else if (randNum <= 95){ // 10% Vida
			Corazon oo = new Corazon(corazon.getTexture(), corazon.getSound());
			Rectangle hitbox = oo.crearObjetoHitbox();
			objetosPos.add(hitbox);
			objetosType.add(oo);

		}
		else {
			PezRadioactivo oo = new PezRadioactivo(pez.getTexture());
			Rectangle hitbox = oo.crearObjetoHitbox();
			objetosPos.add(hitbox);
			objetosType.add(oo);
		}

		
		lastDropTime = TimeUtils.nanoTime();
	}
	
	public void actualizarMovimiento(Rectangle hitboxPersonaje) { 
		// generar objetos en caida
		if(TimeUtils.nanoTime() - lastDropTime >  (Long.parseLong("100000000")))
			crearObjeto();
	  
		// revisar si los objetos cayeron al suelo o chocaron contra el personaje
		for (int i=0; i < objetosPos.size; i++ ) {
			
			objetosPos.get(i).y -= objetosType.get(i).getVelY() * Gdx.graphics.getDeltaTime();
			
			//cae al suelo y se elimina
			if(objetosPos.get(i).y + 64 < 0) {

				objetosType.get(i).eliminar();
				objetosType.removeIndex(i); 
				objetosPos.removeIndex(i);
			}
			
			if(objetosPos.get(i).overlaps(hitboxPersonaje)) { //el objeto choca contra el personaje
				//Acciones de la colision
				objetosType.get(i).colisionar();
				objetosType.removeIndex(i);
				objetosPos.removeIndex(i);

			}
		}
			
	}   
	
	public void actualizarDibujoLluvia(SpriteBatch batch) { 
		for (int i=0; i < objetosPos.size; i++ ) {
			batch.draw(objetosType.get(i).getTexture(), objetosPos.get(i).x, objetosPos.get(i).y);
		}
	}
	
	public void destruir() {
		
	}
}
