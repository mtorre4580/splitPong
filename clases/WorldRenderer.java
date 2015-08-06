/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package app.badlogicgames.splitpong.multiplayer;

import org.json.JSONObject;

import app.badlogicgames.splitpong.Assets;
import app.badlogicgames.splitpong.MainMenuScreen;
import app.badlogicgames.splitpong.Player;
import appwarp.WarpController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class WorldRenderer {
	World world;
	OrthographicCamera cam;
	SpriteBatch batch;
	TextureRegion background;
	public static boolean dibujarPelota= false; //por defecto no la dibuja
	int rotation=0;
	public static Player enemyPlayer;
	
	public WorldRenderer (SpriteBatch batch, World world) {
		this.world = world;
		this.cam = new OrthographicCamera(MainMenuScreen.camX, MainMenuScreen.camY);
		this.cam.position.set(MainMenuScreen.camX/ 2, MainMenuScreen.camY/ 2, 0);
		this.batch = batch;
		enemyPlayer= new Player("ejemplo");
	}

	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1); //esta
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // y esta linea limpian la pantalla		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		renderBackground();
		renderObjects();
	}

	public void renderBackground () {
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.background, 0,0, MainMenuScreen.camX,MainMenuScreen.camY);
		batch.end();
	}

	public void renderObjects () {
		if(enemyPlayer.getName()=="ejemplo"){ //si el nombre del enemigo era "ejemplo" entonces que mande la solicitud de nombre al sv
			requestName();
			if(MainMenuScreen.player.getTimer().segIni== -1) //el tiempo esta en OFF
				sendTime(-1);
		}
		batch.enableBlending(); //sin esto se ve todo feo, probar comentando esta linea
		batch.begin();
		rotation+= (int) Math.pow(Math.pow(World.ball.getVelocity().x, 2)+Math.pow(World.ball.getVelocity().y, 2), 0.5);; //calculo rotacion
		if(dibujarPelota==true){
			//atributitos que se le pasa al batch.draw= (textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation); 
			batch.draw(Assets.ball, World.ball.getRectangle().x, World.ball.getRectangle().y,
					World.ball.getRectangle().width/2 , World.ball.getRectangle().height/2, 
					World.ball.getRectangle().width, World.ball.getRectangle().height,
					1,1, rotation);
		}
		batch.draw(Assets.pad, World.pad.getRectangle().x, World.pad.getRectangle().y,
				World.pad.getRectangle().width, World.pad.getRectangle().height);
		
		batch.draw(Assets.wallIzq, World.paredIzq.x,World.paredIzq.y, World.paredIzq.width, World.paredIzq.height);
		batch.draw(Assets.wallDer, World.paredDer.x,World.paredDer.y, World.paredDer.width, World.paredDer.height);
		batch.draw(Assets.wallBot, World.paredBot.x,World.paredBot.y, World.paredBot.width, (float) (World.paredBot.height));
		
		Assets.writeFont(batch, Assets.fontSmall, MainMenuScreen.player.getName(), MainMenuScreen.camX/30, 6*MainMenuScreen.camY/30);
		Assets.writeFont(batch, Assets.fontSmall, " Vs ", 12*MainMenuScreen.camX/30, 6*MainMenuScreen.camY/30);
		Assets.writeFont(batch, Assets.fontSmall, enemyPlayer.getName(), 18*MainMenuScreen.camX/30, 6*MainMenuScreen.camY/30);
		
		Assets.fontSuperBig.setColor(defineColorPlayer(MainMenuScreen.player.getScore()));
		Assets.fontSuperBig.draw(batch, "" + Integer.toString(MainMenuScreen.player.getScore()),
				2*MainMenuScreen.camX/30, 4*MainMenuScreen.camY/30);
		
		Assets.fontSuperBig.setColor(defineColorEnemy(enemyPlayer.getScore()));
		Assets.fontSuperBig.draw(batch, Integer.toString(enemyPlayer.getScore()),
				20*MainMenuScreen.camX/30, 4*MainMenuScreen.camY/30);
		
		Assets.writeFont(batch, Assets.fontBig1, "Time", 11*MainMenuScreen.camX/30, 4*MainMenuScreen.camY/30);
		if(MainMenuScreen.player.getTimer().segIni==-1)
			Assets.writeFont(batch, Assets.fontBig1, "OFF", 11*MainMenuScreen.camX/30, 2*MainMenuScreen.camY/30);
		else
			Assets.writeFont(batch, Assets.fontBig1, MainMenuScreen.player.getTimeString(), 11*MainMenuScreen.camX/30, 2*MainMenuScreen.camY/30);
		batch.end();
	}
	
	/**
	 * funcion muy basica que segun el valor del score cambia de color (para el score enemigo)
	 * la idea seria que si esta a punto de ganar se vaya poniendo mas rojo, señalando peligro
	 * @param score
	 * @return
	 */
	private Color defineColorEnemy(int score)
	{
		switch(score){
			case 0:
				return Color.valueOf("ffffff"); //blanco
			case 1:
				return Color.valueOf("fef988"); //amarillo light
			case 2:
				return Color.valueOf("ffd943"); //amarillo con naranja 
			case 3:
				return Color.valueOf("ffb243");	//naranja light
			case 4:
				return Color.valueOf("ff3333"); //rojo light
			default:
				return Color.valueOf("ffffff");
		}
	}
	
	/**
	 * funcion muy basica que segun el valor del score cambia de color (para nuestro score)
	 * la idea seria que a medida que vamos haciendo puntos, se vaya poniendo verde, señalando que vamos a ganar
	 * @param score
	 * @return
	 */
	private Color defineColorPlayer(int score){
		switch(score){
		case 0:
			return Color.valueOf("ffffff"); //blanco
		case 1:
			return Color.valueOf("fef988"); //amarillo light
		case 2:
			return Color.valueOf("dafc52"); //amarillo verdon 
		case 3:
			return Color.valueOf("bafc52");	//verdecito claro
		case 4:
			return Color.valueOf("62fc52"); //verde mas fuerte
		default:
			return Color.valueOf("ffffff");
	}
	}

	/**
	 * Funcion que se encarga de mandar la informacion al otro dispositivo
	 * @param x
	 * @param y
	 * @param vel
	 * @param score
	 * @param sendBall
	 */
	public static void sendInformation(float x, float y, Vector2 vel, int score, boolean sendBall){
		try {
			JSONObject data = new JSONObject();
			data.put("sendBall", sendBall);
			if(sendBall==true){
				data.put("x", x);
				data.put("y", y);
				data.put("velX", vel.x);
				data.put("velY", vel.y);
				dibujarPelota= false;	
			}
			else
				data.put("score", score);	
			WarpController.getInstance().sendGameUpdate(data.toString());
		} catch (Exception e) {
			// exception in sendLocation
		}
	}
	
	/**
	 * Funcion que se encarga de pedir el nombre del otro jugador
	 */
	public static void requestName(){
		try{
			JSONObject data= new JSONObject();
			data.put("request", true);
			WarpController.getInstance().sendGameUpdate(data.toString());
		} catch (Exception e) {
			// exception in sendLocation
		}
	}
	
	/**
	 * funcion que manda el nombre del jugador
	 * @param name
	 */
	public static void sendName(String name){
		try{
			JSONObject data= new JSONObject();
			data.put("name", name);
			WarpController.getInstance().sendGameUpdate(data.toString());
		} catch (Exception e) {
			// exception in sendLocation
		}
	}
	
//	public static void requestTime(){
//		try{
//			JSONObject data= new JSONObject();
//			data.put("request", false);
//			WarpController.getInstance().sendGameUpdate(data.toString());
//		} catch (Exception e) {
//			// exception in sendLocation
//		}
//	}
	
	public static void sendTime(int seg){
		try{
			JSONObject data= new JSONObject();
			data.put("time", seg);
			WarpController.getInstance().sendGameUpdate(data.toString());
		} catch (Exception e) {
			// exception in sendLocation
		}
	}
}
