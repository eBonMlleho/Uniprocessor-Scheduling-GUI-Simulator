package cpre458project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class draft {
	public static void main(String[] a) {

		ArrayList<Task> taskList = new ArrayList<Task>();
		taskList.add(new Task(3, 12));
		taskList.add(new Task(3, 12));
		taskList.add(new Task(8, 16));

		// create new object array to add color and current deadline property to task
		// list
		ArrayList<EDFTask> EDFtaskList = new ArrayList<EDFTask>();

		// random color generator to assign indivisual EDF task
		Random rand = new Random();
		for (int i = 0; i < taskList.size(); i++) {
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			Color randomColor = new Color(r, g, b);
			EDFtaskList
					.add(new EDFTask(taskList.get(i).getComputationTime(), taskList.get(i).getPeriod(), randomColor));
		}
//		EDFtaskList.get(0).updateDeadline();
//		EDFtaskList.get(2).updateDeadline();
//		for (int i = 0; i < EDFtaskList.size(); i++) {
//			EDFtaskList.get(i).printoutTask();
//		}
//		System.out.println(findSmallestDeadline(EDFtaskList));
//
		int LCM = 1;
		// find LCM from all period
		for (int i = 0; i < taskList.size(); i++) {
			LCM = findLCM(taskList.get(i).getPeriod(), LCM);
		}

		Color[] colors = new Color[LCM];
		int fillColorsNumber = 0;
		while (fillColorsNumber < LCM) {
			int index = findSmallestDeadline(EDFtaskList);
			int c = EDFtaskList.get(index).getComputationTime();
			for (int i = fillColorsNumber; i < fillColorsNumber + c; i++) {
				colors[i] = EDFtaskList.get(index).getColor();
			}
			EDFtaskList.get(index).updateDeadline();
			fillColorsNumber = fillColorsNumber + c;

		}
		for (int i = 0; i < LCM; i++) {
			System.out.println(colors[i]);
		}

	}

	public static int findSmallestDeadline(ArrayList<EDFTask> EDFtaskList) {
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

}
