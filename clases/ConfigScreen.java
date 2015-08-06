package app.badlogicgames.splitpong;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class ConfigScreen implements Screen ,InputProcessor{
	Game game;
	OrthographicCamera guiCam;
	SpriteBatch batcher;
	Rectangle backBounds, playerRect, timeRect, sensibilityRect;
	Vector3 touchPoint;
	ShapeRenderer shaper;
	AddName listener;
	
	public ConfigScreen (Game game) 
	{
		Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
		this.game = game;

		guiCam = new OrthographicCamera(MainMenuScreen.camX, MainMenuScreen.camY);
		guiCam.position.set(MainMenuScreen.camX/2, MainMenuScreen.camY/2, 0);
		//disposicion de las zonas de interaccion
		backBounds = new Rectangle(0, 0, 64, 64);
		playerRect= new Rectangle(6*MainMenuScreen.camX/16 - 5, 7.5f*MainMenuScreen.camY/12, 175, 30);
		timeRect= new Rectangle(8*MainMenuScreen.camX/16 - 5, 5.5f*MainMenuScreen.camY/12, 100, 30);
		sensibilityRect= new Rectangle(6*MainMenuScreen.camX/16 - 5, 2.5f*MainMenuScreen.camY/12, 150, 30);
		
		touchPoint = new Vector3();
		listener= new AddName();
		batcher = new SpriteBatch();
		shaper= new ShapeRenderer();
		Assets.selectBackground();
	}
	public void update ()
	{
		if (Gdx.input.justTouched()) 
		{
			
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (backBounds.contains(touchPoint.x, touchPoint.y))
			{
				Assets.playSound(Assets.bounce);
				game.setScreen(new MainMenuScreen(game));
				return;
			}
			
			if(playerRect.contains(touchPoint.x, touchPoint.y)) {
				Gdx.input.getTextInput(listener, "Name: ", MainMenuScreen.player.getName()); //tira ventana de agregar nick
			}
			
			if(timeRect.contains(touchPoint.x, touchPoint.y)) {
				if(MainMenuScreen.player.getTimer().segIni== -1)
					MainMenuScreen.player.getTimer().segIni= 30;
				else
					MainMenuScreen.player.getTimer().segIni= -1;
			}
			
			if(sensibilityRect.contains(touchPoint.x, touchPoint.y)) {
				switch(MainMenuScreen.player.getSensibility()){
					case 5:{
						MainMenuScreen.player.setSensibility(20);
						break;
					}
					case 10:{
						MainMenuScreen.player.setSensibility(5);
						break;
					}
					case 20:{
						MainMenuScreen.player.setSensibility(10);
						break;
					}
				}
			}
		}
	}

	public void draw () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		guiCam.update();
		
		batcher.setProjectionMatrix(guiCam.combined);
		
		shaper.setProjectionMatrix(guiCam.combined);
		
		
		batcher.begin();
		batcher.draw(Assets.background, 0, 0, 320, 480);
		
		Assets.writeFont(batcher, Assets.fontSuperBig, "Config", MainMenuScreen.camX/32, 11*MainMenuScreen.camY/12);
		
		Assets.fontBig1.draw(batcher, "Name", MainMenuScreen.camX/16, 9*MainMenuScreen.camY/12);
		Assets.fontBig1.draw(batcher, MainMenuScreen.player.getName(), 6*MainMenuScreen.camX/16, 8*MainMenuScreen.camY/12);
		
		Assets.fontBig1.draw(batcher,"Time", MainMenuScreen.camX/16, 6*MainMenuScreen.camY/12);
		if(MainMenuScreen.player.getTimer().segIni== -1 )
			Assets.fontBig1.draw(batcher,"OFF", 8*MainMenuScreen.camX/16, 6*MainMenuScreen.camY/12);
		else
			Assets.fontBig1.draw(batcher,"ON", 8*MainMenuScreen.camX/16, 6*MainMenuScreen.camY/12);
		
		Assets.fontBig1.draw(batcher,"Sensibility", MainMenuScreen.camX/16, 4*MainMenuScreen.camY/12);
		switch(MainMenuScreen.player.getSensibility()){
			case 5:{
				Assets.fontBig1.draw(batcher,"High", 6*MainMenuScreen.camX/16, 3*MainMenuScreen.camY/12);
				break;
			}
			case 10:{
				Assets.fontBig1.draw(batcher,"Medium", 6*MainMenuScreen.camX/16, 3*MainMenuScreen.camY/12);
				break;
			}
			case 20:{
				Assets.fontBig1.draw(batcher,"Low", 6*MainMenuScreen.camX/16, 3*MainMenuScreen.camY/12);
				break;
			}
		}

		batcher.draw(Assets.arrow, backBounds.x, backBounds.y, backBounds.width, backBounds.height);
		batcher.end();
		
		shaper.begin(ShapeType.Line);
		if(Assets.godMode== true)
			shaper.setColor(Color.BLACK);
		else
			shaper.setColor(Color.WHITE);
		shaper.rect(playerRect.x, playerRect.y, playerRect.width, playerRect.height);
		shaper.rect(timeRect.x, timeRect.y, timeRect.width, timeRect.height);
		shaper.rect(sensibilityRect.x, sensibilityRect.y, sensibilityRect.width, sensibilityRect.height);
		shaper.end();
		
	}

	@Override
	public void render (float delta) {
		cambiarNick();
		update();
		draw();
	}
	
	public void cambiarNick(){
		if(listener.getName()!=null){
			if(listener.getName().length()<8)
				MainMenuScreen.player.setName(listener.getName());
			listener.setName(null);
		}
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
	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
}
