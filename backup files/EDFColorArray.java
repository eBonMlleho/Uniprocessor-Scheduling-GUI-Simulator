package cpre458project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class EDFColorArray {
	public static void main(String[] a) {

		ArrayList<Task> taskList = new ArrayList<Task>();
		taskList.add(new Task(1, 3));
		taskList.add(new Task(1, 3));

		// create new object array to add color and current deadline property to task
		// list
		ArrayList<testEDFTask> testEDFtaskList = new ArrayList<testEDFTask>();

		// random color generator to assign indivisual EDF task
		Random rand = new Random();
		for (int i = 0; i < taskList.size(); i++) {
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			Color randomColor = new Color(r, g, b);
			testEDFtaskList.add(
					new testEDFTask(taskList.get(i).getComputationTime(), taskList.get(i).getPeriod(), randomColor));
		}

		int LCM = 1;
		// find LCM from all period
		for (int i = 0; i < taskList.size(); i++) {
			LCM = findLCM(taskList.get(i).getPeriod(), LCM);
		}

		Color[] colors = new Color[LCM];
		for (int i = 0; i < LCM; i++) { // i is current time

			int index = findSmallestDeadlineAndCanRraw(testEDFtaskList, i);
			System.out.println("smallest deadline is at index: " + index + " at time " + i + " and deadline is:"
					+ testEDFtaskList.get(index).getCurrentDeadline() + " remaning times at index is: "
					+ testEDFtaskList.get(index).remainComputationTime + " last period time is: "
					+ testEDFtaskList.get(index).lastPeriodTime());

			if (testEDFtaskList.get(index).remainComputationTime != 0
					&& i < testEDFtaskList.get(index).getCurrentDeadline()
					&& i >= testEDFtaskList.get(index).lastPeriodTime()) {
				testEDFtaskList.get(index).update();
				colors[i] = testEDFtaskList.get(index).getColor();

			} else {
				colors[i] = Color.BLACK;
			}

		}

//		Color[] colors = new Color[LCM];
//		for (int i = 0; i < LCM; i++) { // i is current time
//
//			int index = findSmallestDeadlineAndCanRraw(testEDFtaskList);
//			System.out.println(index);
//			colors[i] = testEDFtaskList.get(index).updateDraw(i);
//
//			System.out.println(testEDFtaskList.get(0).remainComputationTime);
//
//		}
//
		for (Color color : colors) {
			System.out.println(color);
		}
	}

	public static int findLCM(int a, int b) {
		int max, step, lcm = 0;
		if (a > b) {
			max = step = a;
		} else {
			max = step = b;
		}

		while (a != 0) {
			if (max % a == 0 && max % b == 0) {
				lcm = max;
				break;
			}
			max += step;
		}
		return lcm;
	}

	public static int findSmallestDeadline(ArrayList<testEDFTask> EDFtaskList) {
		int index = 0;
		int deadline = EDFtaskList.get(0).getCurrentDeadline();
		for (int i = 0; i < EDFtaskList.size(); i++) {
			if (deadline > EDFtaskList.get(i).getCurrentDeadline()) {
				deadline = EDFtaskList.get(i).getCurrentDeadline();
				index = i;
			}
		}
		return index;
	}

	public static int findSmallestDeadlineAndCanRraw(ArrayList<testEDFTask> EDFtaskList, int timeUnit) {

		int index = 0;
		int deadline = 999;
		for (int i = 0; i < EDFtaskList.size(); i++) { // find among all tasks

			if (EDFtaskList.get(i).getCanDraw(timeUnit)) {
				if (deadline > EDFtaskList.get(i).getCurrentDeadline()) {
					deadline = EDFtaskList.get(i).getCurrentDeadline();
					index = i;
				}
			}
		}
		return index;
	}
}
