package app.badlogicgames.splitpong;

import app.badlogicgames.splitpong.multiplayer.World;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class PadController {

	Pad pad;
	Rectangle paredIzq, paredDer;
	OrthographicCamera cam;
	Vector3 		touchPoint;
	float aceleration= 0;
	float flowAceleration;

	//CONSTRUCTOR
	public PadController(World world) {
		this.pad = world.getPad();
		flowAceleration= pad.getFlowAceleration();
		this.paredDer= world.getParedDer();
		this.paredIzq= world.getParedIzq();
		this.cam = new OrthographicCamera(MainMenuScreen.camX, MainMenuScreen.camY); //Definis el largo y ancho de la camara
		this.cam.position.set(MainMenuScreen.camX/2, MainMenuScreen.camY/2, 0); //Definis a donde va a mirar, lo comun es que es mire al centro
		touchPoint = new Vector3();
	}
	
	/** The main update method **/
	public void update() {
		//lee el acelerometro
		aceleration= -Gdx.input.getAccelerometerX()/MainMenuScreen.player.getSensibility()  * pad.getAceleration();
		//lee el tactil
		if (Gdx.input.isTouched())
			if (Gdx.input.getX()< MainMenuScreen.camX/2)
				aceleration= -pad.getAceleration()/MainMenuScreen.player.getSensibility() * 10;
			else
				aceleration= pad.getAceleration()/MainMenuScreen.player.getSensibility() * 10;
		
		pad.modifyVelocity(aceleration); 	//acelero el pad
		aceleration=0;
		
		if(pad.getRectangle().overlaps(paredIzq)) //colisionan
			pad.move(true);
		if(pad.getRectangle().overlaps(paredDer)) //colisionan
			pad.move(false);
		
		if(pad.getRectangle().y< MainMenuScreen.camY/4- 4) //si el pad ya esta muy abajo 
			flowAceleration= -pad.getFlowAceleration();
		if(pad.getRectangle().y > MainMenuScreen.camY/4+ 4) //si el pad esta muy arriba
			flowAceleration= pad.getFlowAceleration();
		pad.modifyAltitude(flowAceleration);

		pad.update();
	}
}
