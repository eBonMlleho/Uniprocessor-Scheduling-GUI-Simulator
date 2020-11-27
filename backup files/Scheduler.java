package cpre458project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class Scheduler {
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
			// draw graph
			drawGraphFunction(taskList);
			System.out.println("\nScheduler graph is showing......");
		} else if (exactAnalysis(taskList)) {

			System.out.println("this task set is schedulable by exact analysis");
			// draw graph
			drawGraphFunction(taskList);
			System.out.println("\nScheduler graph is showing......");
		} else {
			System.out.println("this task set is NOT schedulable !");
		}
	}

	public static void EDFOperation(ArrayList<Task> taskList) {
		if (RMSEDFSchedubilityCheck(taskList, "EDF")) {
			System.out.println("This task set is schedulable!");
			// draw graph
			drawEDFGraphFunction(taskList);
			System.out.println("\nScheduler graph is showing......");
		} else {
			System.out.println("this task set is NOT schedulable !");
		}

	}

	public static void drawGraphFunction(ArrayList<Task> taskList) {
		// creating object of JFrame(Window popup)
		JFrame window = new JFrame();

		// setting closing operation
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setting size of the pop window
		window.setBounds(400, 200, 1000, 700);

		// setting canvas for draw
		MyFinalRMSEDFCanvas canvas = new MyFinalRMSEDFCanvas();
		canvas.setProps(taskList, "RMS");
		window.getContentPane().add(canvas);

		// set visibility
		window.setVisible(true);
	}

	public static void drawEDFGraphFunction(ArrayList<Task> taskList) {
		// creating object of JFrame(Window popup)
		JFrame window = new JFrame();

		// setting closing operation
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setting size of the pop window
		window.setBounds(400, 200, 1000, 700);
//		window.setBackground(Color.WHITE);
		// setting canvas for draw
		MyFinalRMSEDFCanvas canvas = new MyFinalRMSEDFCanvas();
		canvas.setProps(taskList, "EDF");
		window.getContentPane().add(canvas);

		// set visibility
		window.setVisible(true);
	}

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
					algorithm + "Schedubility Check passed! Because " + df.format(value2) + " <= " + df.format(value1));
			return true;
		} else {
			System.out.println("Failed " + algorithm + " Schedubility Check test because " + df.format(value2) + " > "
					+ df.format(value1));
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

class MyFinalRMSEDFCanvas extends JComponent {
	int LCM = 1;
	ArrayList<Task> taskList = new ArrayList<Task>();
	int numberOfTask = 0;
	String algorithm;

	public void setProps(ArrayList<Task> list, String algorithm) {
		for (int i = 0; i < list.size(); i++) {
			taskList.add(list.get(i));
		}
		numberOfTask = taskList.size();
		this.algorithm = algorithm;
		// find LCM from all period
		for (int i = 0; i < taskList.size(); i++) {
			LCM = findLCM(taskList.get(i).getPeriod(), LCM);
		}

		repaint();// mark this component to be repainted
	}

	public void paint(Graphics g) {

		// Get the current size of this component
		Dimension d = this.getSize();
		int width = d.width;
		int height = d.height;

		// draw a centered horizontal line
		Graphics2D g2 = (Graphics2D) g;
		// draw in black
		g.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(3));
		// draw coordinate
		g2.drawLine(50, height / 2, width - 50, height / 2);
		double unitLength = (width - 100) / LCM;
		for (int i = 0; i <= LCM; i++) {
			g2.drawLine((int) (50 + i * unitLength), height / 2, (int) (50 + i * unitLength), height / 2 - 10);
			g2.drawString(new Integer(i).toString(), (int) (50 + i * unitLength) - 4, height / 2 + 15);
		}

		// Figure out rectangle
		Color[] myfinalarray = new Color[LCM];

		if (algorithm == "RMS") {
			myfinalarray = rectIndexWithColor(); // CANNOT PUT THIS INTO ANY LOOP!!!!
		} else if (algorithm == "EDF") {
			myfinalarray = EDFrectIndexWithColor();
		}

		for (int i = 0; i < LCM; i++) {
			g2.setColor(myfinalarray[i]);
			g2.fillRect((int) (50 + i * unitLength), height / 2 - (int) unitLength, (int) unitLength, (int) unitLength);
		}
	}

	public Color[] EDFrectIndexWithColor() {
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
		Color[] colors = new Color[LCM];
		for (int i = 0; i < LCM; i++) { // i is current time

			int index = findSmallestDeadlineAndCanRraw(testEDFtaskList, i);
//			System.out.println("smallest deadline is at index: " + index + " at time " + i + " and deadline is:"
//					+ testEDFtaskList.get(index).getCurrentDeadline() + " remaning times at index is: "
//					+ testEDFtaskList.get(index).remainComputationTime + " last period time is: "
//					+ testEDFtaskList.get(index).lastPeriodTime());

			if (testEDFtaskList.get(index).remainComputationTime != 0
					&& i < testEDFtaskList.get(index).getCurrentDeadline()
					&& i >= testEDFtaskList.get(index).lastPeriodTime()) {
				testEDFtaskList.get(index).update();
				colors[i] = testEDFtaskList.get(index).getColor();

			} else {
//				colors[i] = Color.WHITE;
				colors[i] = UIManager.getColor("Panel.background");
			}

		}
//		for (int i = 0; i < LCM; i++) {
//			System.out.println(colors[i]);
//		}
		return colors;

	}

	public int findSmallestDeadlineAndCanRraw(ArrayList<testEDFTask> EDFtaskList, int timeUnit) {

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

	public Color[] rectIndexWithColor() {
		Color[] colors = new Color[LCM];
		int currentLowPTaskIndex = 0;

		ArrayList<Task> taskListforDraw = new ArrayList<Task>();
		for (int i = 0; i < taskList.size(); i++) {
			taskListforDraw.add(taskList.get(i));
		}

		while (taskListforDraw.size() != 0) {
			// generate random color for this task
			Random rand = new Random();
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			Color randomColor = new Color(r, g, b);

			currentLowPTaskIndex = 0;
			// find smallest period which is highest priority
			int period = taskListforDraw.get(0).getPeriod();
			for (int i = 0; i < taskListforDraw.size(); i++) {
				if (period > taskListforDraw.get(i).getPeriod()) {
					period = taskListforDraw.get(i).getPeriod();
					currentLowPTaskIndex = i;
				}
			}

			// fill in highest priority task RMS
			int periodTimes = LCM / period;
			for (int i = 0; i < periodTimes; i++) {
				for (int j = i * period; j < taskListforDraw.get(currentLowPTaskIndex).getComputationTime()
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
			taskListforDraw.remove(currentLowPTaskIndex);
		}

		for (int i = 0; i < colors.length; i++) {
			if (colors[i] == null) {
				// colors[i] = Color.WHITE; // cover all idle time slot to color white
				colors[i] = UIManager.getColor("Panel.background");
			}
			// System.out.println(colors[i]);
		}

		return colors;
	}

	public static int checkNextNullIndex(int j, Color[] colors) {
		int i = j;
		while (colors[i] != null) {
			i++;
		}
		return i;
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

	public int findLCM(int a, int b) {
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
