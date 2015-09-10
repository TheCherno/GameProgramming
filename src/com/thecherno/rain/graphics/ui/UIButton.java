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
	private boolean ignorePressed = false;
	private boolean ignoreAction = false;
	
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
	
	public void performAction() {
		actionListener.perform();
	}
	
	public void ignoreNextPress() {
		ignoreAction = true;
	}

	public void update() {
		Rectangle rect = new Rectangle(getAbsolutePosition().x, getAbsolutePosition().y, size.x, size.y);
		boolean leftMouseButtonDown = Mouse.getButton() == MouseEvent.BUTTON1;
		if (rect.contains(new Point(Mouse.getX(), Mouse.getY()))) {
			if (!inside) {
				if (leftMouseButtonDown) 
					ignorePressed = true;
				else
					ignorePressed = false;
				buttonListener.entered(this);
			}
			inside = true;
			
			if (!pressed && !ignorePressed && leftMouseButtonDown) {
				buttonListener.pressed(this);
				pressed = true;
			} else if (Mouse.getButton() == MouseEvent.NOBUTTON) {
				if (pressed) {
					buttonListener.released(this);
					if (!ignoreAction)
						actionListener.perform();
					else
						ignoreAction = false;
					pressed = false;
				}
				ignorePressed = false;
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
