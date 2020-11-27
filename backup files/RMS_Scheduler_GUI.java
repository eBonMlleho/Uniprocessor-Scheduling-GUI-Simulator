package cpre458project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class RMS_Scheduler_GUI {
	public static void main(String[] a) {
		ArrayList<Task> taskList = new ArrayList<Task>();
		taskList.add(new Task(2, 8));
		taskList.add(new Task(3, 12));
		taskList.add(new Task(4, 16));

		// creating object of JFrame(Window popup)
		JFrame window = new JFrame();

		// setting closing operation
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// setting size of the pop window
		window.setBounds(400, 200, 1000, 700);

		// setting canvas for draw
		MyCanvass canvas = new MyCanvass();
		canvas.setProps(taskList);
		window.getContentPane().add(canvas);

		// set visibility
		window.setVisible(true);
	}
}

class MyCanvass extends JComponent {
	int LCM = 1;
	ArrayList<Task> taskList = new ArrayList<Task>();

	public void setProps(ArrayList<Task> list) {
		for (int i = 0; i < list.size(); i++) {
			taskList.add(list.get(i));
		}

		// find LCM from all period
		for (int i = 0; i < taskList.size(); i++) {
			LCM = findLCM(taskList.get(i).getPeriod(), LCM);
		}
		System.out.println(LCM);
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

		myfinalarray = rectIndexWithColor(); // CANNOT PUT THIS INTO ANY LOOP!!!!
		for (int i = 0; i < LCM; i++) {
			System.out.println(myfinalarray[i]);
		}
		for (int i = 0; i < LCM; i++) {
			g2.setColor(myfinalarray[i]);
			g2.fillRect((int) (50 + i * unitLength), height / 2 - (int) unitLength, (int) unitLength, (int) unitLength);
		}

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
				colors[i] = Color.WHITE; // cover all idle time slot to color white
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