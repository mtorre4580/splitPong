package app.badlogicgames.splitpong;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class SplitPongDesktop 
{
	/**
	 * Esta clase me permite correr el juego en el escritorio
	 * @param argv
	 */
	public static void main (String[] argv)
	{
		new LwjglApplication(new SplitPong(), "Split Pong", 320, 480, false);
	}
}
