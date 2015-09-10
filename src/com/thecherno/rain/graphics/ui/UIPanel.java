package com.thecherno.rain.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.thecherno.rain.util.Vector2i;

public class UIPanel extends UIComponent {

	private List<UIComponent> components = new ArrayList<UIComponent>();
	private Vector2i size;
	
	public UIPanel(Vector2i position, Vector2i size) {
		super(position);
		this.position = position;
		this.size = size;
		color = new Color(0xcacaca);
	}
	
	public void addComponent(UIComponent component) {
		component.init(this);
		components.add(component);
	}
	
	public void update() {
		for (UIComponent component : components) {
			component.setOffset(position);
			component.update();
		}
	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.x, position.y, size.x, size.y);
		for (UIComponent component : components) {
			component.render(g);
		}
	}
}

