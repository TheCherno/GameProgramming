package com.thecherno.rain.graphics.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class UIManager {
	
	private List<UIPanel> panels = new ArrayList<UIPanel>();	

	public UIManager() {
		
	}
	
	public void addPanel(UIPanel panel) {
		panels.add(panel);
	}
	
	public void update() {
		for (UIPanel panel : panels) {
			panel.update();
		}
	}
	
	public void render(Graphics g) {
		for (UIPanel panel : panels) {
			panel.render(g);
		}
	}

}
