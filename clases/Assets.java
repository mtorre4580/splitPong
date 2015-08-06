package app.badlogicgames.splitpong;

import java.util.Random;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets
{
	public static Texture content, Tbackground; // Imagenes de 1024pixels que luego las llamamos con la texture region para dibujarlas
	public static TextureRegion soundOn, soundOff, arrow, title, play, config; //botones
	public static TextureRegion wallIzq, wallDer, wallBot, background, pad, ball; //imagenes juego
	public static BitmapFont fontMainScreen, fontSuperBig, fontBig1, fontBig2, fontSmall, fontMoreBig; //fuente

	public static Music music, musicGame; 
	public static Sound bounce, transfer, win, loose;
	
	public static boolean godMode= false;
	private static int timerAux= 0;
	
	private static String prefix = "data\\"; //por si algun dia llegas a cambiar la carpeta

	public static Texture loadTexture (String file) {
		return new Texture(Gdx.files.internal(file));
	}

	public static void load () {
		//TEXTURE
		Tbackground= new Texture(Gdx.files.internal(prefix + "multibackground.png"));
		content= new Texture(Gdx.files.internal(prefix + "content.png"));
		
		//TEXTURE REGION
    	title= new TextureRegion(content,0, 15, 300, 135);
    	play= new TextureRegion(content, 0,150,160,80);
    	config= new TextureRegion(content, 0,230,160,80);
		soundOn = new TextureRegion(content, 0, 310, 64, 64);
		soundOff = new TextureRegion(content, 0, 374, 64, 64);
		arrow = new TextureRegion(content, 0, 438, 64, 64);
		
		ball= new TextureRegion(content, 64, 370,20,20);
    	pad= new TextureRegion(content, 160,250,80,25);
    	wallIzq= new TextureRegion(content, 0, 502, 15, 384);
    	wallDer= new TextureRegion(content, 0, 502, 15, 384);
    	wallIzq.flip(true, false);
     	wallBot= new TextureRegion(content, 0, 0, 290, 15);
    	
     	//FONT
     	fontMainScreen = new BitmapFont(Gdx.files.internal(prefix + "Visitors.fnt"), Gdx.files.internal(prefix + "Visitors.png"), false);
    	fontMainScreen.setScale(0.9f);
    	
    	fontSuperBig = new BitmapFont(Gdx.files.internal(prefix + "Visitors.fnt"), Gdx.files.internal(prefix + "Visitors.png"), false);
    	fontSuperBig.setScale(2);
    	
    	fontBig1 = new BitmapFont(Gdx.files.internal(prefix + "Visitors.fnt"), Gdx.files.internal(prefix + "Visitors.png"), false);
    	fontBig1.setScale(1);
    	
    	fontBig2 = new BitmapFont(Gdx.files.internal(prefix + "Visitors.fnt"), Gdx.files.internal(prefix + "Visitors.png"), false);
    	fontBig2.setScale(1);
    	
    	fontMoreBig = new BitmapFont(Gdx.files.internal(prefix + "Visitors.fnt"), Gdx.files.internal(prefix + "Visitors.png"), false);
    	fontMoreBig.setScale(1.5f);
    	
    	fontSmall = new BitmapFont(Gdx.files.internal(prefix + "Visitors.fnt"), Gdx.files.internal(prefix + "Visitors.png"), false);
    	fontSmall.setScale(0.8f);
 
		//MUSIC
		music = Gdx.audio.newMusic(Gdx.files.internal(prefix+"Main.mp3"));
		music.setLooping(true);
		musicGame= Gdx.audio.newMusic(Gdx.files.internal(prefix+"Game.mp3"));
		musicGame.setLooping(true);

		//SOUND
		bounce= Gdx.audio.newSound(Gdx.files.internal(prefix+"bounce.mp3"));
		bounce.setVolume(1, (float) 0.5);
		transfer= Gdx.audio.newSound(Gdx.files.internal(prefix+"transfer.mp3"));
		win= Gdx.audio.newSound(Gdx.files.internal(prefix+"win.mp3"));
		loose= Gdx.audio.newSound(Gdx.files.internal(prefix+"loose.mp3"));
	}
	
	/**
	 * Cambia el background aleatoriamente
	 */
	public static void selectBackground(){
		int rand= (int) (Math.random()*6);
		switch(rand){
		case 0:
			background= new TextureRegion(Tbackground,0,0,320,480);
			break;
		case 1:
			background= new TextureRegion(Tbackground,0,480,320,480);
			break;
		case 2:
			background= new TextureRegion(Tbackground,320,0,320,480);
			break;
		case 3:
			background= new TextureRegion(Tbackground,320,480,320,480);
			break;
		case 4:
			background= new TextureRegion(Tbackground,640,0,320,480);
			break;
		case 5:
			background= new TextureRegion(Tbackground,640,480,320,480);
			break;
		default:
			background= new TextureRegion(Tbackground,0,0,320,480);
			break;
		}
	}

	/**
	 * Reproduce un sonido dependiendo si el sonido esta habilitado
	 * @param sound
	 */
	public static void playSound (Sound sound) 
	{
		if (MainMenuScreen.player.isSoundEnabled()) 
			sound.play(1);
	}
	
	/**
	 * Reproduce la musica de fondo dependiendo si el sonido esta habilitado
	 * @param music
	 */
	public static void playMusic (Music music){
		if (MainMenuScreen.player.isSoundEnabled()) 
			music.play();
		else
			music.pause();
	}
	
	public static void writeFont(SpriteBatch batcher, BitmapFont font, String string, float x, float y){
		timerAux++;
		System.out.println(timerAux);
		if(godMode!=false && timerAux>= 30){
			font.setColor(generarColor());
			System.out.println("entro");
			timerAux=0;
		}
		font.draw(batcher, string, x, y);
	}
//	
//	/**
//	 * funcion que cambia el color de una fuente
//	 * @param font
//	 * @param color
//	 */
//	public static void cambiarColor(BitmapFont font, float color){
//		font.setColor(color);
//	}
	
	//genera un color random 
	private static Color generarColor(){
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();
		return new Color(r,g,b,1);
	}
	
}
