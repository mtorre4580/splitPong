package app.badlogicgames.splitpong;

public class Player {
//	final static int maxCantPower= 2;
	private String name;
	private int score;
	private int sensibility;
	private Timer timer;
	private boolean soundEnabled;
	
	public Player(String name){
		this.name= name;
		score= 0;
		timer= new Timer();
		sensibility= 10;
		setSoundEnabled(false);
	}
	
	public void addScore(int cant){
		if(score<5){
			score+= cant;
			timer.setTime(timer.segIni, 0);
		}
	}
	
	public String getTimeString(){
		return timer.getMin() + ":" + timer.getSeg();
	}
	
	public Timer getTimer(){
		return timer;
	}
	
	public int getScore(){
		return score;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name= name;
	}
	
	public void setScore(int score){
		this.score= score;
	}
	
	public int getSensibility() {
		return sensibility;
	}

	public void setSensibility(int sensibility) {
		this.sensibility = sensibility;
	}

	public String toString(){
		return this.name;
	}

	public boolean isSoundEnabled() {
		return soundEnabled;
	}

	public void setSoundEnabled(boolean soundEnabled) {
		this.soundEnabled = soundEnabled;
	}
}
