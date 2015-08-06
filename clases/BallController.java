 package app.badlogicgames.splitpong;

import app.badlogicgames.splitpong.multiplayer.World;
import app.badlogicgames.splitpong.multiplayer.WorldRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class BallController {
	Ball ball;
	Pad pad;
	Rectangle paredIzq, paredDer, paredTop, paredBot;
	double alfa; //el angulo que va a tomar la pelota, medido en grados
	double velocity; //modulo de la velocidad del vector velocity
	float impact; //lugar de impacto dentro del pad
	
	public BallController(World world) {
		paredDer= world.getParedDer();
		paredIzq= world.getParedIzq();
		paredTop= world.getParedTop();
		paredBot= world.getParedBot();
		ball = world.getBall(); //tomo la pelota de la scene
		pad= world.getPad();	//tomo el pad de la scene
	}
	
	/** The main update method **/
	public void update() {
		if(WorldRenderer.dibujarPelota==true) //si la pelota era visible
		{
			//toca paredes de los costados
			if(ball.getRectangle().overlaps(paredIzq) || ball.getRectangle().overlaps(paredDer)){
				Assets.playSound(Assets.bounce);
				ball.getRectangle().x += -ball.getVelocity().x; //arreglo del rebote de la pelota  
				ball.getVelocity().x *=-1;	//cambia el sentido de la pelota 
			}
			
//			si se acaba el tiempo
			if(MainMenuScreen.player.getTimer().getMin()==0 && MainMenuScreen.player.getTimer().getSeg()==0){
				ball.setPositionRandom();
				MainMenuScreen.player.getTimer().setTime(MainMenuScreen.player.getTimer().segIni, 0);
				WorldRenderer.enemyPlayer.addScore(1);
				Assets.playSound(Assets.loose);
				WorldRenderer.sendInformation(0,0, new Vector2(0,0), MainMenuScreen.player.getScore(), false);	
			}
			
			//toca pared de arriba
			if(ball.getRectangle().overlaps(paredTop)){
				Assets.playSound(Assets.transfer);
				ball.getRectangle().y += -ball.getVelocity().y; //arreglo del rebote de la pelota
				ball.getVelocity().y *=-1;
				ball.getVelocity().x *= 1; 
				WorldRenderer.sendInformation(ball.getRectangle().x, ball.getRectangle().y, ball.velocity, MainMenuScreen.player.getScore(), true);
			}
			
//			toca pared de abajo
			if(ball.getRectangle().overlaps(paredBot)){
				ball.setPositionRandom();
				MainMenuScreen.player.getTimer().setTime(MainMenuScreen.player.getTimer().segIni, 0);
				WorldRenderer.enemyPlayer.addScore(1);
				Assets.playSound(Assets.loose);
				WorldRenderer.sendInformation(0,0, new Vector2(0,0), MainMenuScreen.player.getScore(), false);	
			}

			//toca el pad
			alfa=0; 
			if(ball.getRectangle().overlaps(pad.getRectangle())) { //si colisionan
				Assets.playSound(Assets.bounce);
				Gdx.input.vibrate(200);
				velocity= Math.pow(Math.pow(ball.getVelocity().x, 2)+Math.pow(ball.getVelocity().y, 2), 0.5); //modulo de la velocidad
				impact= ball.getPosition().x - pad.getPosition().x + ball.getRectangle().width; //obtengo el lugar de impacto de la pelota en el pad
				alfa=impact/pad.getWidth(); //obtengo el lugar sin depender de la longitud del pad
				alfa*=90+30; //angulo minimo=30, angulo maximo= 150

				if(alfa<30) //parche para que la pelota no vaya muy horizontal
					alfa=30;
				velocity+= 0.5; //aumento la velocidad de la pelota
				ball.getVelocity().x = (float) (-velocity * Math.cos(Math.toRadians(alfa)));
				ball.getVelocity().y = (float) (velocity * Math.sin(Math.toRadians(alfa)));
					
			}
			ball.update();
		}
	}
}
