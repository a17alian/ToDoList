import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.github.lgooddatepicker.components.DatePicker;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.awt.event.ActionEvent;

// Implements interface Print Text
public class MainFrame implements PrintText {
	private JFrame frame;
	private String taskType;
	private JTextField[] textFields = new JTextField[10];
	private JButton[] taskBtn = new JButton[10];
	private int taskCount = 0;
	private JLabel numOfTasks = new JLabel();
	private JLabel taskAddedLable = new JLabel();
	
    private ArrayList<Task> tasks = new ArrayList<Task>(10);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	public MainFrame() {
		frame = new JFrame();
		frame.setBounds(100, 100, 550, 420);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setLocationRelativeTo(null); // Centers frame
		JPanel btnPanel = new JPanel();
		frame.getContentPane().add(btnPanel, BorderLayout.NORTH);

		
		JLabel lblNewLabel = new JLabel(printLable1());
		btnPanel.add(lblNewLabel);
/* OFFICE TASK BTN */
		JButton officeBtn = new JButton("Office");
		// Adds office task to panel
		btnPanel.add(officeBtn);
		officeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPopUp("Office");
			}
		});
		
/* HOME TASK BTN */
		JButton homeBtn = new JButton("Home");
		// Adds home taskto panel
		btnPanel.add(homeBtn);

		homeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPopUp("Home");
			}
		});
/* ERRANDS TASK BTN */
		JButton errandsBtn = new JButton("Errands");
		// Adds errands task to panel
		errandsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createPopUp("Errands");

			}
		});
		
		btnPanel.add(errandsBtn);
		
/* Panel and layput for tasklist */
		JPanel taskPanel = new JPanel();
		frame.getContentPane().add(taskPanel, BorderLayout.CENTER);
		taskPanel.setLayout(new BorderLayout(0, 0));
		
// Lable
		numOfTasks = new JLabel(printLable2() + taskCount);
		numOfTasks.setForeground(Color.red); // Make label red
		taskPanel.add(numOfTasks, BorderLayout.NORTH);
		

		taskAddedLable = new JLabel(printTaskAdded());
		taskAddedLable.setForeground(Color.red); // Make label red
		taskPanel.add(taskAddedLable, BorderLayout.PAGE_END);
		
		
/****** TASK LIST *******/
		JPanel taskList = new JPanel();
		taskPanel.add(taskList, BorderLayout.CENTER);
		taskList.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 2));


// Generates 10 rows of textfields and buttons
		for(int i = 0; i < 10; i ++) {
				textFields[i] = new JTextField(30);
				taskList.add(textFields[i]);
				taskBtn[i] = new JButton("Clear Task");
				taskList.add(taskBtn[i]);
		}
/*Solution for getting the index of clicked jbutton*/
		for(int i = 0; i < taskBtn.length; i++) {
			taskBtn[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Object source = e.getSource();
					for (int i = 0; i < taskBtn.length; i++) {
						     if (source == taskBtn[i]) {
						    	 deleteTask(i); // Calls deleteTask method and passes 'i' (clicked button index) as parameter
						     }
						}
				}
			});
		}
	}
/* clearTextFields clear all textfields after a task has been deleted in order to be re printed again 
 * Without this method the deleted tasks textfield will be filled with an incorrect task */	
	public void clearTextFields() {
		for(int i = 0; i < textFields.length; i ++) {
// Creates try catch in order to prevent program from crashing when array size is out of bounds
				try {
					textFields[i].setText(null);
					}
				catch(IndexOutOfBoundsException e) { 
							System.out.println("Out of bounds");
					}
			}
	}
		
	
/* Method for printing tasks from array using for loop*/	
	public void printTasks() {
		for(int i = 0; i < tasks.size(); i ++) {
// Creates try catch in order to prevent program from crashing when array size is out of bounds
			try {
				textFields[i].setText(tasks.get(i).toString());
			} catch(IndexOutOfBoundsException e) { 
				System.out.println("Out of bounds");
			}

		}
	}

/*Adding new task besed on 'type' parameter passed by add button
 * Uses switch case for determining witch task should be added  */		
	public void addNewTask(String type, String text, LocalDate localDate) {
		switch (type) {
		case "Home":
			Task newHomeTask = new HomeTask(type, text, localDate);
			tasks.add(newHomeTask);

			countTasks();
			taskAddedLable.setText(printTaskAdded() + newHomeTask);
			break;
		case "Office": 
			Task newOfficeTask = new OfficeTask(type, text, localDate);
			tasks.add(newOfficeTask);
			countTasks();
			taskAddedLable.setText(printTaskAdded() + newOfficeTask);

			break;
		case "Errands":			
			Task newErrandsTask = new ErrandsTask(type, text, localDate);
			tasks.add(newErrandsTask);
			countTasks();
			taskAddedLable.setText(printTaskAdded() + newErrandsTask);
			break;
		default: type = "Invalid task type!";
		}
		
	}

/*Method for mathing the index of pressed button with corresponding task in array */		
	public void deleteTask(int buttonPressed) {		
		// Create try catch in order to prevent program from crashing when array size is out of bounds
			try {
				switch (buttonPressed) {
				case 0: 		
					taskAddedLable.setText(printTaskDeleted() + tasks.get(0).toString());
					tasks.remove(0);
					countTasks();
					break;
				case 1: 
					taskAddedLable.setText(printTaskDeleted() + tasks.get(1).toString());
					tasks.remove(1);
					countTasks();
					break;
				case 2: 		
					taskAddedLable.setText(printTaskDeleted() + tasks.get(2).toString());
					tasks.remove(2);
					countTasks();
					break;
				case 3: 
					taskAddedLable.setText(printTaskDeleted() + tasks.get(3).toString());
					tasks.remove(3);
					countTasks();
					break;
				case 4: 
					taskAddedLable.setText(printTaskDeleted() + tasks.get(4).toString());
					tasks.remove(4);
					countTasks();
					break;
					
				case 5: 
					taskAddedLable.setText(printTaskDeleted() + tasks.get(5).toString());
					tasks.remove(5);
					countTasks();
					break;
				case 6: 
					taskAddedLable.setText(printTaskDeleted() + tasks.get(6).toString());
					tasks.remove(6);
					countTasks();
					break;
				case 7: 
					taskAddedLable.setText(printTaskDeleted() + tasks.get(7).toString());
					tasks.remove(7);
					countTasks();
					break;
				case 8: 
					taskAddedLable.setText(printTaskDeleted() + tasks.get(8).toString());
					tasks.remove(8);
					countTasks();
					break;
				case 9: 
					taskAddedLable.setText(printTaskDeleted() + tasks.get(9).toString());
					tasks.remove(9);
					countTasks();
					break;
					
				}

			} catch(IndexOutOfBoundsException e) {
				System.out.println("Number is out of bounds");
			}
	}
	
/*Sorts task array by date using comparator */	
	public void sortArray() {
		// Try catch prevent program from crashing when date is null
		try {
			Collections.sort(tasks, new Comparator<Task>(){
				@Override
				public int compare(Task date1, Task date2) {
					
					// TODO Auto-generated method stub
					return date1.getTaskDate().compareTo(date2.getTaskDate());
				}
				
			});		
		} catch(NullPointerException e) { 
			System.out.println("Date is null");
			}


	}

/*Methods for couting number of tasks using .size to get array size
 * Calls sortArray() for re sorting 
 * Clear all textfields with clearTextFields
 * Prints out the Array again with printTasks*/	
	public void countTasks() { 
		taskCount = tasks.size(); 
		numOfTasks.setText((printLable2() + taskCount));
		sortArray();
		clearTextFields();
		printTasks();
	}

/* Method that creates the popup window */	
	public void createPopUp(String taskType) {
		JFrame pop = new JFrame();
		pop.setSize(300, 200);
		pop.setVisible(true);
		pop.setLayout(new BorderLayout());
		pop.setLocationRelativeTo(null); // Centers frame
		
	// Set layout manager		
		JPanel popUpPanel = new JPanel();
		pop.getContentPane().add(popUpPanel, BorderLayout.CENTER);
		// Set popUp layput
		popUpPanel.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 1));
		
	// Add  components to popUp panel
		popUpPanel.setBorder(BorderFactory.createTitledBorder("Add " + taskType + " task: " ));

		
		JLabel taskText = new JLabel("Task text"); 
		popUpPanel.add(taskText);
		
		final JTextField taskTextField = new JTextField(20);
		popUpPanel.add(taskTextField);
		
		JLabel taskDate = new JLabel("Task date"); 
		popUpPanel.add(taskDate);
		
/*Using the lgood datepicker
 * https://github.com/LGoodDatePicker/LGoodDatePicker */
		DatePicker datePicker = new DatePicker();	
		popUpPanel.add(datePicker);
		
		JButton addBtn = new JButton("Add");
		popUpPanel.add(addBtn);
		

		addBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String taskGetText = taskTextField.getText();		
				addNewTask(taskType, taskGetText, datePicker.getDate());
				
				//launchTaskEvent(new TaskEvent(this, taskGetText));
				//System.out.println(type + taskGetText +  datePicker.getDate());
			}
		});
		
		JButton cancelBtn = new JButton("Cancel");
		popUpPanel.add(cancelBtn);
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pop.setVisible(false);
			}
		});
			
	}
/*Methods from interface */	
	@Override
	public String printLable1() {
		return "Add New Task: ";


	}
	@Override
	public String printLable2() {
		return "The number of tasks is: ";

	}
	@Override
	public String printTaskAdded() {
		return "A new ToDo is added: ";

	}
	@Override
	public String printTaskDeleted() {
		return "A new ToDo is deleted: ";

	}
}
