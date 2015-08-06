package app.badlogicgames.splitpong;

import com.badlogic.gdx.Input.TextInputListener;

public class AddName implements TextInputListener{
	String name;

	@Override
	public void input(String text) {
		this.name= text;
	}

	@Override
	public void canceled() {
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
