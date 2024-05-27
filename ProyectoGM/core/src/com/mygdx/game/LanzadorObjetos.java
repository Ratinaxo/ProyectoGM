package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class LanzadorObjetos {
	private Array<ObjetoCayendo> objetosType;
	private Array<Rectangle> objetosPos;
    private long lastDropTime;
    private Music backgroundMusic;
    private DonaBuena donaBuena;
    private DonaMala donaMala;
    private Corazon corazon;
    private int cambio;
    
	public LanzadorObjetos(Array<ObjetoCayendo> objetos, Music mm) {
		this.cambio = GameLluvia.getPuntos() ;
		this.donaBuena = (DonaBuena)objetos.get(0);
		this.donaMala = (DonaMala)objetos.get(1);
		this.corazon = (Corazon)objetos.get(2);
		
		this.backgroundMusic = mm;
		crear();
	}
	
	public void crear() {
		objetosPos = new Array<Rectangle>();
		objetosType = new Array<ObjetoCayendo>();
		crearObjeto();
		//start the playback of the background music immediately
		backgroundMusic.setLooping(true);
		backgroundMusic.play();
	}
	
	private void crearObjeto() {
		int randNum = MathUtils.random(1,100);
		
		if (randNum <= 65) { //60% dona normal
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
		else{ // 5% Vida
			Corazon oo = new Corazon(corazon.getTexture(), corazon.getSound());
			Rectangle hitbox = oo.crearObjetoHitbox();
			objetosPos.add(hitbox);
			objetosType.add(oo);

		}

		
		lastDropTime = TimeUtils.nanoTime();
	}
	
	public void actualizarMovimiento(Rectangle hitboxPersonaje) { 
		// generar objetos en caida
		if(TimeUtils.nanoTime() - lastDropTime > 100500000)
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
				cambio = GameLluvia.getPuntos();
				objetosType.get(i).colisionar();
				objetosType.removeIndex(i);
				objetosPos.removeIndex(i);
				
				

				
				
				if ((GameLluvia.getPuntos() % 50) == 0) {
					System.out.println(GameLluvia.getPuntos() % 50);
					GameLluvia.cambiarPersonaje();
				}
			}
		}
			
	}   
	
	public void actualizarDibujoLluvia(SpriteBatch batch) { 
		for (int i=0; i < objetosPos.size; i++ ) {
			batch.draw(objetosType.get(i).getTexture(), objetosPos.get(i).x, objetosPos.get(i).y);
		}
	}
	
	public void destruir() {
		backgroundMusic.dispose();
	}
}
