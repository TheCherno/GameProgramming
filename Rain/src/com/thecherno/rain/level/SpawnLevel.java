package com.thecherno.rain.level;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.thecherno.rain.entity.mob.Dummy;
import com.thecherno.rain.entity.mob.Player;
import com.thecherno.rain.entity.mob.Shooter;

public class SpawnLevel extends Level {

	public SpawnLevel(String path) {
		super(path);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();
			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception! Could not load level file!");
		}
		//add(new Chaser(20, 55));
		//add(new Star(17, 35));
		add(new Shooter(20, 48));
		add(new Shooter(20, 55));
		add(new Dummy(15, 53));
		
		for (int i = 0; i < 5; i++) {
			//add(new Dummy(20, 55));
		}
	}

	protected void generateLevel() {
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
			}
		}
		tile_size = 16;
	}
}
