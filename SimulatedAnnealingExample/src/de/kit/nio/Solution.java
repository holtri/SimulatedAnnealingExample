package de.kit.nio;

public class Solution {

	private double x;
	private double y;
	
	public Solution getRandomNeighbor(double distance){
		return new Solution(
				x - distance + Math.random()*distance*2, 
				y - distance + Math.random()*distance*2);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Solution(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
}
