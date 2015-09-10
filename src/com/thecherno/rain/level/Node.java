package com.thecherno.rain.level;

import com.thecherno.rain.util.Vector2i;

public class Node {

	public Vector2i tile;
	public Node parent;
	public double fCost, gCost, hCost;

	public Node(Vector2i tile, Node parent, double gCost, double hCost) {
		this.tile = tile;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
		this.fCost = this.gCost + this.hCost;
	}
}