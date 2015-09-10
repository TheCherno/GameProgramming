package com.thecherno.rain.entity.mob;

import java.util.List;

import com.thecherno.rain.graphics.AnimatedSprite;
import com.thecherno.rain.graphics.Screen;
import com.thecherno.rain.graphics.Sprite;
import com.thecherno.rain.graphics.SpriteSheet;
import com.thecherno.rain.level.Node;
import com.thecherno.rain.util.Vector2i;

public class Star extends Mob {
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	private int xa = 0;
	private int ya = 0;
	private List<Node> path = null;
	private int time = 0;

	public Star(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		sprite = Sprite.dummy;
	}

	private void move() {
		xa = 0;
		ya = 0;
		int px = level.getPlayerAt(0).getX();
		int py = level.getPlayerAt(0).getY();
		Vector2i start = new Vector2i(getX() >> 4, getY() >> 4);
		Vector2i destination = new Vector2i(px >> 4, py >> 4);
		if (time % 3 == 0) path = level.findPath(start, destination);
		if (path != null) {
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				if (x < vec.getX() << 4) xa++;
				if (x > vec.getX() << 4) xa--;
				if (y < vec.getY() << 4) ya++;
				if (y > vec.getY() << 4) ya--;
			}
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void update() {
		time++;
		move();
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (ya < 0) {
			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;
		} else if (xa > 0) {
			animSprite = right;
			dir = Direction.RIGHT;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob(x - 16, y - 16, this);
	}

}
