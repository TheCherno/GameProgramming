package com.thecherno.rain.net.player;

import com.thecherno.rain.entity.mob.Mob;
import com.thecherno.rain.graphics.Screen;

public class NetPlayer extends Mob {

	public NetPlayer() {
		x = 22 * 16;
		y = 42 * 16; 
	}
	
	public void update() {
		
	}

	public void render(Screen screen) {
		screen.fillRect(x, y, 32, 32, 0x2030cc, true);
	}
	
}
