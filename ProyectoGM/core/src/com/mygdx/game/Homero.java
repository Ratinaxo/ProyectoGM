package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Homero implements PersonajeInterfaz{
	   private Rectangle homeroHitbox;
	   private Texture homeroTexture;
	   private Sound sonidoHerido;
	   private int vidas = 3;
	   private int puntos = 0;
	   private int velx = 400;
	   private boolean herido = false;
	   private int tiempoHeridoMax=50;
	   private int tiempoHerido;
	   
	   public Homero(Texture homeroTexture, Sound sonidoHerido) {
		   this.homeroTexture = homeroTexture;
		   this.sonidoHerido = sonidoHerido;
	   }
	   
		public int getVidas() {
			return vidas;
		}
	
		public int getPuntos() {
			return puntos;
		}
		public Rectangle getHitbox() {
			return homeroHitbox;
		}
		public void sumarPuntos(int pp) {
			puntos+=pp;
		}
		
	
	   public void crear() {

		   homeroHitbox = new Rectangle();
		   homeroHitbox.x = 800 / 2 - 64 / 2;
		   homeroHitbox.y = 20;
		   homeroHitbox.width = 64;
		   homeroHitbox.height = 64;
	   }
	   public void dañar() {
		  vidas--;
		  herido = true;
		  tiempoHerido=tiempoHeridoMax;
		  sonidoHerido.play();
	   }
	   
	   public void dibujar(SpriteBatch batch) {
		 if (!herido)  
		   batch.draw(homeroTexture, homeroHitbox.x, homeroHitbox.y);
		 else {
		
		   batch.draw(homeroTexture, homeroHitbox.x, homeroHitbox.y+ MathUtils.random(-5,5));
		   tiempoHerido--;
		   if (tiempoHerido<=0) herido = false;
		 }
	   } 
	   
	   
	   public void actualizarMovimiento() { 
		   //movimiento desde teclado
		   if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) homeroHitbox.x -= velx * Gdx.graphics.getDeltaTime();
		   if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) homeroHitbox.x += velx * Gdx.graphics.getDeltaTime();
		   // que no se salga de los bordes izq y der
		   if(homeroHitbox.x < 0) homeroHitbox.x = 0;
		   if(homeroHitbox.x > 800 - 64) homeroHitbox.x = 800 - 64;
	   }
	    

	public void destruir() {
		homeroTexture.dispose();
	   }
	
   public boolean estaHerido() {
	   return herido;
   }

   @Override
   public int sumarPuntos() {
	   // TODO Auto-generated method stub
	   return 0;
   }

   @Override
   public void danar() {
	   // TODO Auto-generated method stub
	
   }

@Override
public void cambiarPersonaje() {
	// TODO Auto-generated method stub
	
}
	   
}
