package app.badlogicgames.splitpong;

import app.badlogicgames.splitpong.multiplayer.World;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Ball {

	final Vector2 velocityDefault= new Vector2(4,3);
	final float	SIZE_BALL = 20f; 					//tamaño por defecto de la pelota
	Vector2 	velocity= new Vector2(4,3);		//velocidad
	Rectangle 	rectangle = new Rectangle();		//rectangulo= guarda posicion, ancho y largo
	
	//CONSTRUCTOR
	public Ball(Vector2 pos) {
		this.rectangle.x= pos.x;
		this.rectangle.y= pos.y;
		this.rectangle.width = SIZE_BALL;
		this.rectangle.height = SIZE_BALL;
	}
	
	public Ball(Vector2 pos, int width, int height) { //largo y ancho especial
		this.rectangle.x= pos.x;
		this.rectangle.y= pos.y;
		this.rectangle.width = width;
		this.rectangle.height = height;
	}
	
	//UPDATE
	public void update() { 
		this.rectangle.x += velocity.x;
		this.rectangle.y += velocity.y;
	}

	//GETTER
	public Vector2 getPosition() {
		Vector2 pos= new Vector2(this.rectangle.x, this.rectangle.y); //genero el vector
		return pos;
	}

	public Rectangle getRectangle() {
		return rectangle;
	}
	
	public float getSize() {
		return this.rectangle.x;
	}
	
	public Vector2 getVelocity(){
		return this.velocity;
	}
	
	//SETTER
	public void setSize(float size) {
		this.rectangle.x= size;
		this.rectangle.y= size;
	}
	
	public void setVelocity(Vector2 vel){
		this.velocity= vel;
	}
	
	public void setPosition(Vector2 pos){
		this.rectangle.x= pos.x;
		this.rectangle.y= pos.y;
	}
	
	public void setPositionRandom(){
		velocity.set(velocityDefault);
		rectangle.x= (float) Math.random()*(MainMenuScreen.camX-3*World.grosorPared)+ World.grosorPared; 
		rectangle.y= 3*MainMenuScreen.camY/4 ;
	}
}
