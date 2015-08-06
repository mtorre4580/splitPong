package app.badlogicgames.splitpong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class CreditsScreen implements Screen {
	Game game;

	OrthographicCamera guiCam;
	SpriteBatch batcher;
	Rectangle backBounds;
	Vector3 touchPoint;

	public CreditsScreen (Game game) {
		this.game = game;

		guiCam = new OrthographicCamera(MainMenuScreen.camX, MainMenuScreen.camY);
		guiCam.position.set(MainMenuScreen.camX/2, MainMenuScreen.camY/2, 0);
		backBounds = new Rectangle(0, 0, 64, 64);
		touchPoint = new Vector3();
		batcher = new SpriteBatch();
		Assets.godMode= true;
		System.out.println(Assets.godMode);
	}

	public void update () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (backBounds.contains(touchPoint.x, touchPoint.y)) {
				Assets.playSound(Assets.bounce);
				game.setScreen(new MainMenuScreen(game));
				return;
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
		Assets.writeFont(batcher, Assets.fontSuperBig, "Credits", MainMenuScreen.camX/32, 11*MainMenuScreen.camY/12);

		Assets.writeFont(batcher, Assets.fontMoreBig, "Coders: ",MainMenuScreen.camX/16, 9*MainMenuScreen.camY/12);
		Assets.writeFont(batcher, Assets.fontBig1, "- Matias", 3*MainMenuScreen.camX/16, 7*MainMenuScreen.camY/12);
		Assets.writeFont(batcher, Assets.fontBig1, "Torre", 6*MainMenuScreen.camX/16, 6*MainMenuScreen.camY/12);
		Assets.writeFont(batcher, Assets.fontBig2, "- Emanuel", 3*MainMenuScreen.camX/16, 5*MainMenuScreen.camY/12);
		Assets.writeFont(batcher, Assets.fontBig2, "Suriano", 6*MainMenuScreen.camX/16, 4*MainMenuScreen.camY/12);
		
		Assets.writeFont(batcher, Assets.fontBig1, "PLEASE ENJOY!", 2*MainMenuScreen.camX/16, 2*MainMenuScreen.camY/12);

		batcher.draw(Assets.arrow, backBounds.x, backBounds.y, backBounds.width, backBounds.height);
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
	public void hide () {
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
