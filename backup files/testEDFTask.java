package cpre458project;

import java.awt.Color;

public class testEDFTask {
	int computationTime;
	int period;
	int periodConut = 1;
	int currentDeadline = 0;
	int lastPeriodTime = 0;

	Color color;

	// new added
	boolean canDraw;
	int remainComputationTime;
	int currentTime;
	// above is new added

	public testEDFTask(int computationTime, int p, Color c) {
		this.computationTime = computationTime;
		period = p;
		color = c;
		currentDeadline = period;
		canDraw = true;
		remainComputationTime = computationTime;
		currentTime = 0;

	}

	public void printoutTask() {
		System.out.println("(" + computationTime + "," + period + ")  " + "Color:" + color + " current Deadline:"
				+ currentDeadline);
	}

	public int getComputationTime() {
		return computationTime;
	}

	public int getPeriod() {
		return period;
	}

//	public Color updateDraw(int currentTime) { // draw this task
//
//		if (remainComputationTime != 0) { // draw available, return color
//			remainComputationTime--;
//			if (remainComputationTime == 0) { // new period
//				remainComputationTime = computationTime;
//				periodConut++;
//				currentDeadline = period * periodConut;
//			}
//			canDraw = true;
//			return this.color;
//		} else if (remainComputationTime == 0 && currentTime > currentDeadline) { // new period
//
//			remainComputationTime--;
//			return this.color;
//		} else {
//			canDraw = false;
//			return Color.BLACK; // cannot do anything, just draw white
//		}
//
//	}

	public boolean getCanDraw(int timeUnit) {
		if (this.remainComputationTime != 0 && timeUnit < getCurrentDeadline() && timeUnit >= lastPeriodTime()) {
			return true;
		}
		return false;
	}

	public int getCurrentDeadline() {
		return currentDeadline;
	}

	public Color getColor() {
		return color;
	}

	public void setupCurrentTime(int time) {
		currentTime = time;
	}

	public void update() {
		remainComputationTime--;
		if (remainComputationTime == 0) {
			periodConut++;
			currentDeadline = period * periodConut;
			remainComputationTime = computationTime;
		}
	}

	public int lastPeriodTime() {
		lastPeriodTime = currentDeadline - period;
		return lastPeriodTime;
	}
}
