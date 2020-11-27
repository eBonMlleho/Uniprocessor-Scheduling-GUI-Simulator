package cpre458project;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class PureRMS_EDFCheck {
	public static void main(String[] a) {
		Scanner input = new Scanner(System.in);
		System.out.print("How many task? ");
		int numberOftask = input.nextInt();
		ArrayList<Task> taskList = new ArrayList<Task>();

		System.out.println("RMS or EDF?");
		input.nextLine(); // consume new line left-over; only need to do this if nextline() is after
							// nextInt()
		String algorithmString = input.nextLine();
		System.out.println("You picked " + algorithmString + "\n");

		System.out.println("Enter task set computation time and period in format (assume 3 tasks): c1,p1;c2,p2;c3,p3");

		String line = input.nextLine();

		// store all tast parameters into task array list
		for (int i = 0; i < numberOftask; i++) {
			String taskString = line.split(";")[i];

			Task newTask = new Task(Integer.parseInt(taskString.split(",")[0]),
					Integer.parseInt(taskString.split(",")[1]));
			taskList.add(newTask);
		}

		// display all task in task list
		System.out.println("Task set is: ");
		for (int i = 0; i < taskList.size(); i++)
			taskList.get(i).printoutTask();

		// actual operation
		if (algorithmString.equals("RMS")) {
			RMSOperation(taskList);
		} else if (algorithmString.equals("EDF")) {
			EDFOperation(taskList);

		}

	}

	public static void RMSOperation(ArrayList<Task> taskList) {
		if (RMSEDFSchedubilityCheck(taskList, "RMS")) {
			System.out.println("this task set is schedulable by RMS utilization test");
			// TODO: draw graph
		} else if (exactAnalysis(taskList)) {

			System.out.println("this task set is schedulable by exact analysis");
			// TODO: draw graph
		} else {
			System.out.println("this task set is NOT schedulable !");
		}
	}

	public static void EDFOperation(ArrayList<Task> taskList) {
		if (RMSEDFSchedubilityCheck(taskList, "EDF")) {
			System.out.println("This task set is schedulable!");
		}

		// TODO: draw graph
	}

//	public static boolean RMSutilizationTest(ArrayList<Task> taskList) {
//		int numberOftask = taskList.size();
//		double value1 = numberOftask * (Math.pow(2, 1.0 / numberOftask) - 1);
//		DecimalFormat df = new DecimalFormat("#.####");
//		double value2 = 0;
//		for (int i = 0; i < numberOftask; i++) {
//			value2 = value2 + 1.0 * taskList.get(i).getComputationTime() / (1.0 * taskList.get(i).getPeriod());
//		}
//		if (value2 <= value1) {
//
//			System.out
//					.println("RMS utilization test passed! Because " + df.format(value2) + " <= " + df.format(value1));
//			return true;
//		} else {
//			System.out.println("Failed RMS utilization test because " + df.format(value2) + " > " + df.format(value1));
//			return false;
//		}
//
//	}

	public static boolean RMSEDFSchedubilityCheck(ArrayList<Task> taskList, String algorithm) {
		int numberOftask = taskList.size();
		double value1 = 0;
		if (algorithm.equals("EDF")) {
			value1 = 1;
		} else if (algorithm.equals("RMS")) {
			value1 = numberOftask * (Math.pow(2, 1.0 / numberOftask) - 1);
		}

		DecimalFormat df = new DecimalFormat("#.####");
		double value2 = 0;
		for (int i = 0; i < numberOftask; i++) {
			value2 = value2 + 1.0 * taskList.get(i).getComputationTime() / (1.0 * taskList.get(i).getPeriod());
		}
		if (value2 <= value1) {

			System.out.println(
					"EDF Schedubility Check passed! Because " + df.format(value2) + " <= " + df.format(value1));
			return true;
		} else {
			System.out.println("Failed RMS utilization test because " + df.format(value2) + " > " + df.format(value1));
			return false;
		}

	}

	public static boolean exactAnalysis(ArrayList<Task> taskList) {
		// find biggest period
		System.out.println("Try Exact Analysis");
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
			System.out.println("Not schedulable since t_" + counter + " = " + newT + " > " + biggestPeriod);
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
