package com.thecherno.rain.graphics.ui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import com.thecherno.rain.input.Mouse;
import com.thecherno.rain.util.Vector2i;

public class UIButton extends UIComponent {
	
	public UILabel label;
	private UIButtonListener buttonListener;
	private UIActionListener actionListener;
	
	private boolean inside = false;
	private boolean pressed = false;
	
	public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
		super(position, size);
		this.actionListener = actionListener;
		Vector2i lp = new Vector2i(position);
		lp.x += 4;
		lp.y += size.y - 2;
		label = new UILabel(lp, "");
		label.setColor(0x444444);
		label.active = false;
		
		setColor(0xaaaaaa);
		
		buttonListener = new UIButtonListener();
	}
	
	void init(UIPanel panel) {
		super.init(panel);
		panel.addComponent(label);
	}
	
	public void setButtonListener(UIButtonListener buttonListener) {
		this.buttonListener = buttonListener;
	}
	
	public void setText(String text) {
		if (text == "") 
			label.active = false;
		else
			label.text = text;
	}

	public void update() {
		Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);
		if (rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {
			if (!inside)
				buttonListener.entered(this);
			inside = true;
			
			if (!pressed && Mouse.getButton() == MouseEvent.BUTTON1) {
				buttonListener.pressed(this);
				pressed = true;
			} else if (pressed && Mouse.getButton() == MouseEvent.NOBUTTON) {
				buttonListener.released(this);
				actionListener.perform();
				pressed = false;
			}
		} else {
			if (inside) {
				buttonListener.exited(this);
				pressed = false;
			}
			inside = false;
		}

	}
	
	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(position.x + offset.x, position.y + offset.y, size.x, size.y);

		if (label != null)
			label.render(g);
	}
	
}
