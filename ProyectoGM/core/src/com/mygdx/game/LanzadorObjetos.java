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
	private Array<Integer> objectDropsType;
    private Texture donaBuenaTexture;
    private Texture donaMalaTexture;
    private long lastDropTime;
    private Sound dropSound;
    private Music rainMusic;
	   
    private DonaBuena donaBuena;
    private DonaMala donaMala;
    
	public LanzadorObjetos(Texture donaBuenaTexture, Texture donaMalaTexture, Sound ss, Music mm) {
		rainMusic = mm;
		dropSound = ss;
		this.donaBuenaTexture = donaBuenaTexture;
		this.donaMalaTexture = donaMalaTexture;
	}
	
	public void crear() {
		objectDropsPos = new Array<Rectangle>();
		objectDropsType = new Array<Integer>();
		crearObjeto();
		//start the playback of the background music immediately
	    rainMusic.setLooping(true);
	    rainMusic.play();
	}
	
	private void crearObjeto() {
		if (MathUtils.random(1,100) < 70) { //70% dona normal 	    	  
			objectDropsType.add(0);
			donaBuena = new DonaBuena(donaBuenaTexture);
		}
		else {
			objectDropsType.add(1);
			donaMala = new DonaMala(donaMalaTexture);
		}
		//Gota gota = new Gota(this.gotaBuena, 0, new Rectangle());
		Rectangle hitbox = new Rectangle();
		hitbox.x = MathUtils.random(0, 800-64);
		hitbox.y = 480;
		hitbox.width = 64;
		hitbox.height = 64;
		objectDropsPos.add(hitbox);
		// ver el tipo de gota
		if (MathUtils.random(1,100) > 30)	    	  
			objectDropsType.add(0);
		else 
			objectDropsType.add(1);
		lastDropTime = TimeUtils.nanoTime();
	}
	
	public void actualizarMovimiento(Homero tarro) { 
		// generar gotas de lluvia 
		if(TimeUtils.nanoTime() - lastDropTime > 100000000)
			crearObjeto();
	  
		// revisar si las gotas cayeron al suelo o chocaron con el tarro
		for (int i=0; i < objectDropsPos.size; i++ ) {
			Rectangle raindrop = objectDropsPos.get(i);
			raindrop.y -= 300 * Gdx.graphics.getDeltaTime();
			//cae al suelo y se elimina
			if(raindrop.y + 64 < 0) {
				objectDropsPos.removeIndex(i); 
				objectDropsType.removeIndex(i);
			}
			if(raindrop.overlaps(tarro.getArea())) { //la gota choca con el tarro
				if(objectDropsType.get(i)==1) { // gota dañina
					tarro.dañar();
	    	  
					objectDropsPos.removeIndex(i);
					objectDropsType.removeIndex(i);
				}
				else { // gota a recolectar
					tarro.sumarPuntos(10);
					dropSound.play();
					objectDropsPos.removeIndex(i);
					objectDropsType.removeIndex(i);
				}
			}
		}   
	}
   
	public void actualizarDibujoLluvia(SpriteBatch batch) { 
	   
		for (int i=0; i < objectDropsPos.size; i++ ) {
			Rectangle raindrop = objectDropsPos.get(i);
			if(objectDropsType.get(i)==1) // gota dañina
				batch.draw(donaMala.getTexture(), raindrop.x, raindrop.y); 
			else
				batch.draw(donaBuena.getTexture(), raindrop.x, raindrop.y); 
		}
	}
	public void destruir() {
		dropSound.dispose();
		rainMusic.dispose();
	}
}
