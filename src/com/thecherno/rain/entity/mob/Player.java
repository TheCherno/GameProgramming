package com.thecherno.rain.entity.mob;

import java.awt.Font;

import com.thecherno.rain.Game;
import com.thecherno.rain.entity.projectile.Projectile;
import com.thecherno.rain.entity.projectile.WizardProjectile;
import com.thecherno.rain.graphics.AnimatedSprite;
import com.thecherno.rain.graphics.Screen;
import com.thecherno.rain.graphics.Sprite;
import com.thecherno.rain.graphics.SpriteSheet;
import com.thecherno.rain.graphics.ui.UIActionListener;
import com.thecherno.rain.graphics.ui.UIButton;
import com.thecherno.rain.graphics.ui.UIButtonListener;
import com.thecherno.rain.graphics.ui.UILabel;
import com.thecherno.rain.graphics.ui.UIManager;
import com.thecherno.rain.graphics.ui.UIPanel;
import com.thecherno.rain.graphics.ui.UIProgressBar;
import com.thecherno.rain.input.Keyboard;
import com.thecherno.rain.input.Mouse;
import com.thecherno.rain.util.Vector2i;

public class Player extends Mob {

	private String name;
	private Keyboard input;
	private Sprite sprite;
	private boolean walking = false;
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	private int fireRate = 0;
	
	private UIManager ui;
	private UIProgressBar uiHealthBar;
	private UIButton button;

	@Deprecated
	public Player(String name, Keyboard input) {
		this.name = name;
		this.input = input;
		sprite = Sprite.player_forward;
	}

	public Player(String name, int x, int y, Keyboard input) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.input = input;
		sprite = Sprite.player_forward;
		fireRate = WizardProjectile.FIRE_RATE;
		
		ui = Game.getUIManager();
		UIPanel panel = (UIPanel) new UIPanel(new Vector2i((300 - 80) * 3, 0), new Vector2i(80 * 3, 168 * 3)).setColor(0x4f4f4f);
		ui.addPanel(panel);
		UILabel nameLabel = new UILabel(new Vector2i(40, 200), name);
		nameLabel.setColor(0xbbbbbb);
		nameLabel.setFont(new Font("Verdana", Font.PLAIN, 24));
		nameLabel.dropShadow = true;
		panel.addComponent(nameLabel);
		
		uiHealthBar = new UIProgressBar(new Vector2i(10, 215), new Vector2i(80 * 3 - 20, 20));
		uiHealthBar.setColor(0x6a6a6a);
		uiHealthBar.setForegroundColor(0xee3030);
		panel.addComponent(uiHealthBar);
		
		UILabel hpLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2, 16)), "HP");
		hpLabel.setColor(0xffffff);
		hpLabel.setFont(new Font("Verdana", Font.PLAIN, 18));
		panel.addComponent(hpLabel);
		// Player default attributes
		health = 100;
		
		button = new UIButton(new Vector2i(10, 260), new Vector2i(100, 30), new UIActionListener() {
			public void perform() {
				System.out.println("Action Performed!");
			}
		});
		button.setText("Hello");
		panel.addComponent(button);
	}
	
	public String getName() {
		return name;
	}

	public void update() {
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (fireRate > 0) fireRate--;
		int xa = 0, ya = 0;
		if (input.up) {
			animSprite = up;
			ya -= 2;
		} else if (input.down) {
			animSprite = down;
			ya += 2;
		}
		if (input.left) {
			animSprite = left;
			xa -= 2;
		} else if (input.right) {
			animSprite = right;
			xa += 2;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		clear();
		updateShooting();
		
		uiHealthBar.setProgress(health / 100.0);
	}

	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}

	private void updateShooting() {
		if (Mouse.getButton() == 1 && fireRate <= 0) {
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			shoot(x, y, dir);
			fireRate = WizardProjectile.FIRE_RATE;
		}
	}

	public void render(Screen screen) {
		int flip = 0;
		sprite = animSprite.getSprite();
		screen.renderMob(x - 16, y - 16, sprite, flip);
	}

}
