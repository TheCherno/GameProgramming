package com.thecherno.rain.graphics.ui;

public class UIButtonListener {

	public void entered(UIButton button) {
		button.setColor(0xcdcdcd);
	}
	
	public void exited(UIButton button) {
		button.setColor(0xaaaaaa);
	}
	
	public void pressed(UIButton button) {
		button.setColor(0xcc2222);
	}
	
	public void released(UIButton button) {
		button.setColor(0xcdcdcd);
	}

}
