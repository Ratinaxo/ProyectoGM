package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class LanzadorObjetos {
	private Array<Rectangle> objectDropsPos;
	private Array<ObjetoCayendo> objectDropsType;
    private Texture donaBuenaTexture;
    private Texture donaMalaTexture;
    private Texture pezTexture;
    private long lastDropTime;
    private Sound dropSound;
    private Music rainMusic;
	   
    private DonaBuena donaBuena;
    private DonaMala donaMala;
    private PezRadioactivo pez;
    
    private int velY;
    
	public LanzadorObjetos(Texture donaBuenaTexture, Texture donaMalaTexture, Texture pezTexture, Sound ss, Music mm) {
		rainMusic = mm;
		dropSound = ss;
		this.donaBuenaTexture = donaBuenaTexture;
		this.donaMalaTexture = donaMalaTexture;
		this.pezTexture = pezTexture;
	}
	
	public void crear() {
		objectDropsPos = new Array<Rectangle>();
		objectDropsType = new Array<ObjetoCayendo>();
		crearObjeto();
		//start the playback of the background music immediately
	    rainMusic.setLooping(true);
	    rainMusic.play();
	}
	
	private void crearObjeto() {
		int randNum = MathUtils.random(1,100);
		 
		if (randNum <= 60) { //60% dona normal 	    	  
			donaBuena = new DonaBuena(donaBuenaTexture);
			objectDropsPos.add(donaBuena.getHitbox());
			objectDropsType.add(donaBuena);
			velY = donaBuena.getVelY();
			
		}else
		if (randNum <= 85){ // 25% dona dañina
			donaMala = new DonaMala(donaMalaTexture);
			objectDropsPos.add(donaMala.getHitbox());
			objectDropsType.add(donaMala);
			velY = donaMala.getVelY();
		} else
		if (randNum <= 100 ){ //15% pez radioactivo (Cambio de personaje)
			pez = new PezRadioactivo(pezTexture);
			objectDropsPos.add(pez.getHitbox());
			objectDropsType.add(pez);
			velY = pez.getVelY();
		}
		
		lastDropTime = TimeUtils.nanoTime();
	}
	
	public void actualizarMovimiento(Homero homero) { 
		// generar objetos en caida
		if(TimeUtils.nanoTime() - lastDropTime > 97500000)
			crearObjeto();
	  
		// revisar si los objetos cayeron al suelo o chocaron con el tarro
		for (int i=0; i < objectDropsPos.size; i++ ) {
			
			Rectangle hitbox = objectDropsPos.get(i);
			hitbox.y -= velY * Gdx.graphics.getDeltaTime();
			//cae al suelo y se elimina
			if(hitbox.y + 64 < 0) {
				objectDropsPos.removeIndex(i); 
				objectDropsType.removeIndex(i);
			}
			if(hitbox.overlaps(homero.getHitbox())) { //el objeto choca contra homero
				
				if(objectDropsType.get(i).getTipoObjeto()==0) { // gota normal
					homero.sumarPuntos(10);
					dropSound.play();
					objectDropsPos.removeIndex(i);
					objectDropsType.removeIndex(i);
					
				}
				if(objectDropsType.get(i).getTipoObjeto()==1) { // gota dañina
					homero.dañar();
					objectDropsPos.removeIndex(i);
					objectDropsType.removeIndex(i);
				}
				if(objectDropsType.get(i).getTipoObjeto()==2) { // pez radioactivo (cambio de personaje)
					
					objectDropsPos.removeIndex(i);
					objectDropsType.removeIndex(i);
				}
				
			}
		}   
	}
   
	public void actualizarDibujoLluvia(SpriteBatch batch) { 
	   
		for (int i=0; i < objectDropsPos.size; i++ ) {
			batch.draw(objectDropsType.get(i).getTexture(), objectDropsPos.get(i).x, objectDropsPos.get(i).y);
		}
	}
	public void destruir() {
		dropSound.dispose();
		rainMusic.dispose();
	}
}
