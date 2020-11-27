package cpre458project;

import java.awt.Color;

public class EDFTask {
	int computationTime;
	int period;
	int periodConut = 1;
	int currentDeadline = 0;
	Color color;

	// new added
	boolean availableForThisPeriod;

	// above is new added

	public EDFTask(int computationTime, int p, Color c) {
		this.computationTime = computationTime;
		period = p;
		color = c;
		currentDeadline = period;
		availableForThisPeriod = true;
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

	public void updateDeadline() {
		periodConut++;
		currentDeadline = period * periodConut;
	}

	public int getCurrentDeadline() {
		return currentDeadline;
	}

	public Color getColor() {
		return color;
	}
}
