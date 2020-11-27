package cpre458project;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class RMS_Scheduler {

	public static void main(String[] a) {
		ArrayList<Task> taskList = new ArrayList<Task>();
		taskList.add(new Task(1, 8));
		taskList.add(new Task(2, 6));
		taskList.add(new Task(4, 24));
		int LCM = 24;
		Color[] colors = new Color[LCM];

		int currentLowPTaskIndex = 0;
		while (taskList.size() != 0) {
			// generate random color for this task
			Random rand = new Random();
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			Color randomColor = new Color(r, g, b);

			currentLowPTaskIndex = 0;
			// find smallest period which is highest priority
			int period = taskList.get(0).getPeriod();
			for (int i = 0; i < taskList.size(); i++) {
				if (period > taskList.get(i).getPeriod()) {
					period = taskList.get(i).getPeriod();
					currentLowPTaskIndex = i;
				}
			}

			// fill in highest priority task RMS
			int periodTimes = LCM / period;
			for (int i = 0; i < periodTimes; i++) {
				for (int j = i * period; j < taskList.get(currentLowPTaskIndex).getComputationTime()
						+ i * period; j++) {
					if (colors[j] == null) { // if index is not taken by higher priority value
						colors[j] = randomColor;
					} else {
						// check next available index
						int index = checkNextNullIndex(j, colors);
						colors[index] = randomColor;
					}
				}
			}
			// remove current task so iterate to next task in task list, if list size reduce
			// to 0, end while loop
			taskList.remove(currentLowPTaskIndex);
		}

		for (int i = 0; i < colors.length; i++) {
			if (colors[i] == null) {
				colors[i] = Color.WHITE; // cover all idle time slot to color white
			}
			System.out.println(colors[i]);
		}

		// need to return ArrayList<Rectangle> recList = new ArrayList<Rectangle>();

	}

	public static int checkNextNullIndex(int j, Color[] colors) {
		int i = j;
		while (colors[i] != null) {
			i++;
		}
		return i;
	}

}
