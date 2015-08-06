package app.badlogicgames.splitpong.multiplayer;

import org.json.JSONException;
import org.json.JSONObject;

import app.badlogicgames.splitpong.Assets;
import app.badlogicgames.splitpong.BallController;
import app.badlogicgames.splitpong.MainMenuScreen;
import app.badlogicgames.splitpong.PadController;
import appwarp.WarpController;
import appwarp.WarpListener;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class MultiplayerGameScreen implements Screen, WarpListener {
	static final int GAME_READY = 0;
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_LEVEL_END = 3;
	static final int GAME_OVER = 4;
	final int MAXSCORE = 5;

	Game game;

	int state;
	OrthographicCamera guiCam;
	Vector3 touchPoint;
	SpriteBatch batcher;
	World world;
	WorldRenderer renderer;
	Rectangle quitBounds;
	private StartMultiplayerScreen prevScreen;
	
	PadController 	controllerp;
	BallController 	controllerb;
	public static Timer time;
	
	public MultiplayerGameScreen (Game game, StartMultiplayerScreen prevScreen) {
		this.game = game;
		this.prevScreen = prevScreen;

		MainMenuScreen.player.setScore(0);
		state = GAME_RUNNING;
		guiCam = new OrthographicCamera(MainMenuScreen.camX, MainMenuScreen.camY);
		guiCam.position.set(MainMenuScreen.camX/2, MainMenuScreen.camY/2, 0);
		touchPoint = new Vector3();
		batcher = new SpriteBatch();
		world = new World();
		renderer = new WorldRenderer(batcher, world);
		quitBounds = new Rectangle(MainMenuScreen.camX-64, 0, 64, 64);
		controllerp = new PadController(world);			//define el control del pad
		controllerb= new BallController(world);
		
		MainMenuScreen.player.getTimer().restoreTime();
		time= new Timer();
		Timer.schedule(new Task(){
		    @Override
		    public void run() {
		    	MainMenuScreen.player.getTimer().decreaseTime();
		    }
		},1,1);
		WarpController.getInstance().setListener(this);
		
		if (MainMenuScreen.player.isSoundEnabled()){
			Assets.musicGame.play();
			Assets.music.pause();
		}
	}

	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;

		switch (state) {
		case GAME_READY:
			updateReady();
			break;
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_LEVEL_END:
			updateLevelEnd();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		}
	}

	private void updateReady () {
		if (Gdx.input.justTouched()) {
			state = GAME_RUNNING;
		}
	}

	private void updateRunning (float deltaTime) {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (quitBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.bounce);
				if (MainMenuScreen.player.isSoundEnabled()){
					Assets.musicGame.pause();
					Assets.music.play();
				}
				game.setScreen(new MainMenuScreen(game));
				handleLeaveGame();
				return;
			}
		}

//		if (world.state == World.WORLD_STATE_NEXT_LEVEL) {
//			state = GAME_LEVEL_END;
//		}
//		if (world.state == World.WORLD_STATE_GAME_OVER) {
//			state = GAME_OVER;
//		}
	}



	private void updateLevelEnd () {
		if (Gdx.input.justTouched()) {
//			world = new World(worldListener);
//			renderer = new WorldRenderer(batcher, world);
//			world.score = lastScore;
//			state = GAME_READY;
			WarpController.getInstance().handleLeave();
			game.setScreen(new MainMenuScreen(game));
		}
	}

	private void updateGameOver () {
		if (Gdx.input.justTouched()) {
			WarpController.getInstance().handleLeave();
			game.setScreen(new MainMenuScreen(game));
		}
	}

	public void draw () {
		GLCommon gl = Gdx.gl;
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderer.render();
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		batcher.enableBlending();
		batcher.begin();
		batcher.draw(Assets.arrow, quitBounds.x, quitBounds.y, quitBounds.width, quitBounds.height);
		batcher.end();
	}

	/** 
	 * Funcion que checkea los scores de los jugadores
	 * @author Ema
	 */
	private void checkGame(){
		if(MainMenuScreen.player.getScore()==MAXSCORE){
			handleLeaveGame();
			WarpController.getInstance().onUserLose();
			time.clear();
			return;
		}
		if(WorldRenderer.enemyPlayer.getScore()==MAXSCORE){
			handleLeaveGame();		
			WarpController.getInstance().onUserWin();
			time.clear();
			return;
		}
	}
	
	@Override
	public void render (float delta) {
		update(delta);
		controllerp.update();
		controllerb.update();
		checkGame(); 
		draw();
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void show () {
	}

	@Override
	public void hide () {
	}

	@Override
	public void pause () {
//		if (state == GAME_RUNNING) state = GAME_PAUSED;
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
		
	}
	private void handleLeaveGame(){
		time.clear();
		WarpController.getInstance().handleLeave();
	}

	@Override
	public void onWaitingStarted (String message) {
		
	}

	@Override
	public void onError (String message) {
		
	}

	@Override
	public void onGameStarted (String message) {
		
	}

	//CUANDO TERMINA EL JUEGO!!
	@Override
	public void onGameFinished (int code, boolean isRemote) {
		if(isRemote){
			prevScreen.onGameFinished(code, true);
		}else{
			if(code==WarpController.GAME_WIN){
//				world.state = World.WORLD_STATE_NEXT_LEVEL;
				System.out.println("1");
			}else if(code==WarpController.GAME_LOOSE){
//				world.state = World.WORLD_STATE_GAME_OVER;
				System.out.println("2");
			}
		}
		WarpController.getInstance().handleLeave();
	}

	//CUANDO RECIBE LA INTERACCION!!!!!!!!!!!!!!!!!
	@Override
	public void onGameUpdateReceived (String message) {
		try {
			JSONObject data = new JSONObject(message);
			try{
				if(data.getBoolean("request")==true) //verifica si se recibio una señal de pedido de nombre
					WorldRenderer.sendName(MainMenuScreen.player.getName()); //se envia el nombre del jugador local	
//				else //se recibio una señal de pedido de estado del tiempo
//					WorldRenderer.sendTime(MainMenuScreen.player.getTimer().segIni);
			}
			catch (JSONException e1){ //si la clave request no se encontraba
				try{
					WorldRenderer.enemyPlayer.setName(data.getString("name"));
				}
				catch (JSONException e2){ //si la clave name no se encontraba
					try{
						MainMenuScreen.player.getTimer().segIni= data.getInt("time");
//						MainMenuScreen.player.getTimer().restoreTime();
					}
					catch(JSONException e3){ //si la clave time no se encontraba
						if(data.getBoolean("sendBall")==true){ //verifica si se envio las propiedades de la pelota
							world.getBall().getRectangle().x = (float)data.getDouble("x");
							world.getBall().getRectangle().y= (float)data.getDouble("y");
							world.getBall().setVelocity(new Vector2((float)data.getDouble("velX"), (float)data.getDouble("velY")));
							if(WorldRenderer.dibujarPelota==true) //invierte la visibilidad de la pelota
								WorldRenderer.dibujarPelota=false;
							else
								WorldRenderer.dibujarPelota=true;
							Assets.playSound(Assets.transfer);
						}
						else{ //sino se envio la pelota, entonces se mando el score, por lo tanto se actualiza
							WorldRenderer.enemyPlayer.setScore(data.getInt("score"));
							MainMenuScreen.player.addScore(1);
							Assets.playSound(Assets.win);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
