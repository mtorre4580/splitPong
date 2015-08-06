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

import app.badlogicgames.splitpong.Ball;
import app.badlogicgames.splitpong.MainMenuScreen;
import app.badlogicgames.splitpong.Pad;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class World {

	public static final float WORLD_WIDTH = 10;
	public static final float WORLD_HEIGHT = 15 * 20;
	public static final int WORLD_STATE_RUNNING = 0;
	public static final int WORLD_STATE_NEXT_LEVEL = 1;
	public static final int WORLD_STATE_GAME_OVER = 2;
	public static final Vector2 gravity = new Vector2(0, -12);

	
	public static int grosorPared= 15;
	public static Rectangle paredIzq, paredDer, paredBot, paredTop;
	public static Pad pad;
	public static Ball ball;

	public World(){
		pad = new Pad(new Vector2(MainMenuScreen.camX/2, (int) MainMenuScreen.camY/4 + 4));
		ball= new Ball(new Vector2(MainMenuScreen.camX/2, MainMenuScreen.camY/2));
		
		paredIzq= new Rectangle(0								, MainMenuScreen.camY/5, 
								grosorPared						, 4*MainMenuScreen.camY/5);
		paredDer= new Rectangle(MainMenuScreen.camX-grosorPared	, MainMenuScreen.camY/5, 
								grosorPared						, 4*MainMenuScreen.camY/5);
		paredBot= new Rectangle(grosorPared						, MainMenuScreen.camY/5,
								MainMenuScreen.camX-2*grosorPared	, grosorPared);
		paredTop= new Rectangle(grosorPared						, MainMenuScreen.camY-grosorPared,
								MainMenuScreen.camX-2*grosorPared	, grosorPared);
	}
	
	public Rectangle getParedIzq() {
		return paredIzq;
	}
	
	public Rectangle getParedBot() {
		return paredBot;
	}
	
	public Rectangle getParedDer() {
		return paredDer;
	}
	
	public Rectangle getParedTop() {
		return paredTop;
	}
	
	public Ball getBall(){
		return  ball;
	}
	
	public Pad getPad() {
		return pad;
	}
}
