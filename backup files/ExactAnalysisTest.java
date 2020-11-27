package cpre458project;

import java.util.ArrayList;

public class ExactAnalysisTest {
	public static void main(String[] a) {
		// System.out.println(Math.ceil(7.0 / 8));
		ArrayList<Task> taskList = new ArrayList<Task>();
		taskList.add(new Task(20, 100));
		taskList.add(new Task(30, 145));
		taskList.add(new Task(68, 150));

		exactAnalysis(taskList);

	}

	public static boolean exactAnalysis(ArrayList<Task> taskList) {
		// find biggest period
		int biggestPeriod = 0;
		for (int i = 0; i < taskList.size(); i++) {
			if (biggestPeriod < taskList.get(i).getPeriod()) {
				biggestPeriod = taskList.get(i).getPeriod();
			}
		}
		System.out.println("The biggest task period is:" + biggestPeriod);

		// exact analysis step by step
		int newT = 0;
		int lastT = 0;
		int counter = -1;
		while (newT <= biggestPeriod) {
			lastT = newT;
			newT = exactAnalysisEquation(taskList, lastT);
			counter++;
			System.out.println("t_" + counter + " is:" + newT);
			if (newT == lastT) {
				System.out.println("Schedulable because " + "t_" + counter + " = " + "t_" + --counter + " = " + newT
						+ " < " + biggestPeriod);
				return true;
			}
		}
		if (newT > biggestPeriod) {
			System.out.println("Not schedulable since t_" + counter + " > " + biggestPeriod);
			return false;
		}
		return false;
	}

	public static int exactAnalysisEquation(ArrayList<Task> taskList, int time) {
		int numberOftask = taskList.size();
		int newTime = 0;

		if (time == 0) {
			for (int i = 1; i <= numberOftask; i++) {
				newTime = newTime + taskList.get(i - 1).getComputationTime();
			}
		} else {
			for (int i = 1; i <= numberOftask; i++) {
				newTime = (int) (newTime + taskList.get(i - 1).getComputationTime()
						* Math.ceil(1.0 * time / taskList.get(i - 1).getPeriod()));
			}

		}

		return newTime;

	}
}
