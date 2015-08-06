package app.badlogicgames.splitpong;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Pad {
	//variables fijas
	final float WIDTH= 80f;		//ancho inicial, esta variable no depende del ancho del pad
	final float HEIGHT = 20f; 	//altura inicial, y puede ser que siempre que no varie
	public static final float MAX_VEL= 5;	//limite de la velocidad maxima
	public static final float MIN_VEL= -5;	//limite de la velocidad minima
	
	//variables que se modifican a medida que avanza el programa
	Rectangle 	rectangle = new Rectangle();	//rectangulo
	float 		velocity=0;					//velocidad
	float 		aceleration= 0.5f;			//aceleration
	float		flowAceleration= -0.1f;		//aceleration del aire
	float 		flowVelocity=0;				//velocidad en el aire
	//CONSTRUCTOR
	public Pad(Vector2 pos) {
		rectangle.x= pos.x;
		rectangle.y= pos.y;
		rectangle.width = WIDTH;
		rectangle.height = HEIGHT;
	}
	
	//UPDATE
	public void update() { //esta funcion es la que hace que se mueva el rectangulo con la posicion
		rectangle.x += velocity;
		rectangle.y += flowVelocity;
	}
	
	public void modifyAltitude(float acel){
		flowVelocity += acel;
		if (flowVelocity>1) 		//limito la velocidad
			flowVelocity= 1;
		if (flowVelocity<-1) 
			flowVelocity= -1;
	}
	
	public void modifyVelocity(float acel){
		if(acel!=0){ //existe una aceleracion 
			velocity+= acel; 	//sumo la aceleracion a la velocidad actual
			if (velocity>MAX_VEL) 		//limito la velocidad
				velocity= MAX_VEL;
			if (velocity<MIN_VEL) 
				velocity= MIN_VEL;
		}
		else
			velocity *= 0.9;	//disminuyo la velocidad
	}
	
	public void move(boolean desp){
		if(desp== true)
			velocity= aceleration;
		if(desp== false)
			velocity= -aceleration;
	}
	
	//GETTERS
	public float getAceleration(){
		return aceleration;
	}
	
	public float getFlowAceleration(){
		return flowAceleration;
	}
	
	public Vector2 getPosition() {
		Vector2 pos= new Vector2(this.rectangle.x, this.rectangle.y); //genero el vector
		return pos;
	}
	
	public float getVelocity(){
		return velocity;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public float getWidth(){
		return rectangle.width;
	}
	
	//SETTERS
	public void setVelocity(float vel){
		velocity= vel;
	}
	
	public void setAceleration(float acel){
		aceleration= acel;
	}
	
	public void setWidth(float width){
		this.rectangle.width= width;
	}

	public void setPosition(Vector2 pos) {
		rectangle.x= pos.x;
		rectangle.y= pos.y;
	}
}
