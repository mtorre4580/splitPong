package app.badlogicgames.splitpong;

import com.badlogic.gdx.Game;

public class SplitPong extends Game {
	boolean firstTimeCreate = true;

	
	/**
	 * metodo create me carga los assets q definimos estos son las imagenes sonidos, todo para q corra lindo el juego
	 * seteamos la pantallla del juego principal , la pantalla del menu(mainmenuscreen)
	 */
	public void create () {
		Assets.load();
		setScreen(new MainMenuScreen(this));
	}
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		getScreen().dispose();
	}
}
