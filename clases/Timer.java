package app.badlogicgames.splitpong;

public class Timer {
	public int segIni= 30;
	public int minIni= 0;
	int seg, min;
	
	public Timer() {
		this.seg = segIni;
		this.min = minIni;
	}

	public void decreaseTime(){
		if(segIni!= -1){ //no se activo el tiempo infinito
			seg --;
			if(seg<0){
				min--;
				seg=60;
			}
		}
	}
	
	public void restoreTime(){
		seg = segIni;
		min = minIni;
	}
	
	public void addTime(int seg, int min){
		this.seg+= seg;
		this.min+= min;
		while(this.seg>60)
		{
			this.min ++;
			this.seg-= 60;
		}
	}
	
	public void setTime(int seg, int min){
		this.seg= seg;
		this.min= min;
	}

	public int getSeg(){
		return seg;
	}
	
	public int getMin(){
		return min;
	}
	
}
