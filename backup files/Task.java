package cpre458project;

public class Task {
	int computationTime;
	int period;

	public Task(int computationTime, int p) {
		this.computationTime = computationTime;
		period = p;
	}

	public void printoutTask() {
		System.out.print("(" + computationTime + "," + period + ")  ");
	}

	public int getComputationTime() {
		return computationTime;
	}

	public int getPeriod() {
		return period;
	}

}
