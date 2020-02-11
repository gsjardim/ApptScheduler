
package cst8284.asgmt3.employee;
import java.util.Scanner;
/**
 *The Dentist class is a concrete subclass of the Employee class.
 *It is used to instantiate a Dentist object used in the Scheduler class.
 *@author Guilherme Jardim
 *@version 1.0
 **/
public class Dentist extends Employee {
	/**
	 * scans the input from the user.
	 */
	private Scanner scan = new Scanner(System.in);
	/**
	 * contains the description of the activity associated with the employee's profession.
	 */
	private String activity;
	
	/**
	 * This constructor redirects the received parameter to the superclass Employee constructor.
	 * @param fullname String parameter that holds the Dentist's full name as one single String.
	 */
	public Dentist(String fullname) {
		
		super(fullname);
	}
	
	/**
	 * Returns the Activity type chosen from the menu in the Scheduler class.
	 * The list of activities are displayed using a switch structure, and are related to the Dentist's profession.
	 * @return String returns the String holding the selected activity.
	 */
	@Override
	public String getActivityType() {
		int choice;
		System.out.printf("%n%s%n%s%n%s%n%s%n%s%n",
				"Please choose the category:",
				"1. Assessment","2. Filling", "3. Crown", "4. Cosmetic Repairs");
		
		do {
			
			choice = scan.nextInt();
			
			switch (choice) {
			case 1: setActivity("Assessment"); break;
			case 2: setActivity("Filling"); break;
			case 3: setActivity("Crown"); break;
			case 4: setActivity("Cosmetic Repairs"); break;
			default: System.out.println("Invalid choice. Please enter the number corresponding to the category.");
					choice = 0;
					break;
			}
		}while (choice<1||choice>4);
		
		return getActivity();
	}
	
	/**
	 * Sets the field activity of type String with the selected activity from the getActivityType method.
	 * @param activity String
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	/**
	 * Returns the field activity of type String holding the selected activity.
	 * @return String activity
	 */
	public String getActivity() {
		return activity;
	}

}
