
package app.badlogicgames.splitpong;

import java.util.Random;

import app.badlogicgames.splitpong.multiplayer.StartMultiplayerScreen;
import app.badlogicgames.splitpong.multiplayer.WorldRenderer;
import appwarp.WarpController;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen {
	
	Game game;
	public static Player player= new Player("Player"); //se define el player por primera y unica vez
	OrthographicCamera guiCam;
	SpriteBatch batcher;
	Rectangle soundBounds, titleRect, playRect, configRect, creditsRect;
	public static int camX=320, camY=480;
	Vector3 touchPoint;

	public MainMenuScreen (Game game) {
		this.game = game;
		
		guiCam = new OrthographicCamera(camX, camY);
		guiCam.position.set(camX / 2, camY / 2, 0);
		batcher = new SpriteBatch();
		//disposicion de los botones
		soundBounds = new Rectangle(0, 0, 64, 64);
		creditsRect= new Rectangle(camX-64, 0, 64, 64);
		titleRect= new Rectangle((float) (0.25*camX/8), 8*camY/12,
				15*camX/16, 5*camY/16);
		playRect= new Rectangle(2*camX/8, 5*camY/12,
						4*camX/8, 2*camY/12);
		configRect= new Rectangle(2*camX/8, 2*camY/12,
						4*camX/8, 2*camY/12);
		batcher= new SpriteBatch();
		touchPoint = new Vector3();
		Assets.playMusic(Assets.music);
		Assets.selectBackground(); //esta es la funcion que cambia el fondo
		
	}

	public void update () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (configRect.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.bounce);
				game.setScreen(new ConfigScreen(game));
				return;
			}
			if (creditsRect.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.bounce);
				game.setScreen(new CreditsScreen(game));
				return;
			}
			if (playRect.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.bounce);
				WorldRenderer.dibujarPelota= false;
				WarpController.getInstance().startApp(getRandomHexString(10)); //se conecta al server
				game.setScreen(new StartMultiplayerScreen(game)); //cambia la screen
				return;
			}
			if (soundBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.bounce);
				player.setSoundEnabled(!player.isSoundEnabled());
				Assets.playMusic(Assets.music);
			}
		}
	}
	
	public void draw () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);

		batcher.begin();
		batcher.draw(Assets.background, 0, 0, 320, 480);
		Assets.fontMainScreen.draw(batcher, "V 3.1", (float) (6*camX/8), (float) (0.6*camY/12));
		
		batcher.draw(Assets.title, titleRect.getX(), titleRect.getY(), titleRect.width, titleRect.height);
		batcher.draw(Assets.play, playRect.getX(), playRect.getY(), playRect.width, playRect.height);
		batcher.draw(Assets.config, configRect.getX(), configRect.getY(), configRect.width, configRect.height);
		batcher.draw(player.isSoundEnabled() ? Assets.soundOn : Assets.soundOff, 0, 0, 64, 64);
		batcher.end();
	}

	@Override
	public void render (float delta) {
		update();
		draw();
	}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void show () {
	}

	@Override
	public void hide () 
	{
		
	}

	@Override
	public void pause () 
	{
		 Gdx.app.exit();
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
	
	/**
	 * genera un nombre random para poder entrar al servidor
	 */
	private String getRandomHexString(int numchars){
      Random r = new Random();
      StringBuffer sb = new StringBuffer();
      while(sb.length() < numchars){
          sb.append(Integer.toHexString(r.nextInt()));
      }
      return sb.toString().substring(0, numchars);
  }
}
