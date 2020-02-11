
package cst8284.asgmt3.scheduler;
import java.util.Scanner;
import cst8284.asgmt3.employee.Employee;
import cst8284.asgmt3.exception.BadAppointmentDataException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import cst8284.asgmt3.accessories.SortAppointmentByCalendar;


/**
 *The Scheduler class handles the whole execution of the appointment scheduler. It takes input from the user and processes the appointments.
 *It contains several methods that execute 8 main functions: Save new appointments, delete appointments, change a pre-booked appointment,
 *display a pre-booked appointment, display the whole schedule for one specific date, backup the schedule to a file on the hard-drive, load the
 *schedule from the file on the hard-drive and exit the program.
 *@author Guilherme Jardim
 *@version 1.0
 **/
public class Scheduler {
	/**
	 * of type Scanner, gets user input from keyboard.
	 */
	private static Scanner scan = new Scanner(System.in);
	/**
	 * of type ArrayList, stores the Appointment objects created within the program.
	 */
	private ArrayList<Appointment> appointments;
	/**
	 * of type Employee, holds the information related to the employee that will provide the service. In this case, the actual employee must be a 
	 *subclass of Employee, such as Dentist.
	 */
	private Employee employee;
	/**
	 * of type DateFormat - reference to a SimpleDateFormat object; used to specify the format in which the date is to be entered.
	 */
	private static DateFormat dateFormat= new SimpleDateFormat("ddMMyyyy");
	/**
	 * constant of type int.
	 */
	private static final int SAVE_APPOINTMENT=1;
	/**
	 * constant of type int.
	 */
	private static final int DELETE_APPOINTMENT=2;
	/**
	 * constant of type int.
	 */
	private static final int CHANGE_APPOINTMENT=3;
	/**
	 * constant of type int.
	 */
	private static final int DISPLAY_APPOINTMENT=4;
	/**
	 * constant of type int.
	 */
	private static final int DISPLAY_SCHEDULE=5;
	/**
	 * constant of type int.
	 */
	private static final int SAVE_APPOINTMENT_TO_FILE=6;
	/**
	 * constant of type int.
	 */
	private static final int LOAD_APPOINTMENT_FROM_FILE=7;
	/**
	 * constant of type int.
	 */
	private static final int EXIT=0;
	
	
	/**
	 * The one-arg constructor takes one parameter of type Employee - emp. It displays an opening message using the employee's name,
	 * instantiates a new ArrayList parameterized with the Appointments class, sets the employee field with the parameter's value, 
	 * and set the dateFormat lenient mode to false, in other words, not lenient mode.
	 * @param emp reference value to the new Employee object. Since Employee is an abstract class, it will point to an instance of its subclass object.
	 */
	public Scheduler(Employee emp) {
		
		System.out.println("Scheduling appointments for " + emp.toString());
		appointments = new ArrayList<Appointment>();
		setEmployee(emp);
		dateFormat.setLenient(false);
	}
	/**
	 * This method takes no parameter, and returns nothing. It simply calls the method loadAppointmentsFromFile and then, the executeMenuItem,
	 * passing to this method the returning value from the displayMenu method.
	 */
	public void launch() {
		loadAppointmentsFromFile("CurrentAppointments.apts", getAppointments());
		executeMenuItem(this.displayMenu());
		
	}
	/**
	 * This method takes one parameter of type Employee, emp, and sets the class private field employee with the parameter's value.
	 * @param emp reference value to the new Employee object. Since Employee is an abstract class, it will point to an instance of its subclass object.
	 */
	private void setEmployee(Employee emp) {
		this.employee = emp;
	}
	/**
	 * This method takes no parameter and returns the value of the field employee.
	 * @return a reference value to the new Employee object. Since Employee is an abstract class, it will point to an instance of its subclass object.
	 */
	private Employee getEmployee() {
		return employee;
	}
	/**
	 * Takes no parameter; returns an int value which is input by the user.
	 * Displays a menu with a list of choices, prompting the user to select the desired action.
	 * @return the int value that represents the user's choice from the menu.
	 */
	private int displayMenu() {
		
		System.out.printf("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s", 
		    	 "Enter a selection from the following menu:", 
		  		 SAVE_APPOINTMENT + ". Save appointment",
		  		DELETE_APPOINTMENT + ". Remove appointment",
		  		CHANGE_APPOINTMENT + ". Change appointment",
		         DISPLAY_APPOINTMENT + ". Get appointment",
		         DISPLAY_SCHEDULE + ". Display schedule",
		         SAVE_APPOINTMENT_TO_FILE + ". Backup appointments to file",
		         LOAD_APPOINTMENT_FROM_FILE + ". Load appointments from file",
		         EXIT +". Exit program\n");
		return(scan.nextInt());
		
	}
	/**
	 * Takes one parameter of type int, choice. Using this parameter, the method will branch its execution to the case that matches the parameter's value,
	 * inside the switch block. The switch block is executed inside a while loop, wich only ends when the choice is Zero (exit the program).
	 * This method also has a local variable backupFile of type String, used to store the name of the file where the array of appointments will be saved on
	 * the hard drive. This way, even if the user does not explicitly choose to backup the schedule, this method does that before the program terminates.
	 * Some of the switch cases are equipped with try-catch structures, to catch exceptions generated in lower level methods, such as "makeCalendarFromUserInput".
	 * @param choice the int value taken from the user, which represents the action selected from the menu.
	 */
	private void executeMenuItem(int choice){
		
		String backupFile = "CurrentAppointments.apts";		
		while (choice!=EXIT) {
			scan.nextLine();
			switch (choice){
				case SAVE_APPOINTMENT:
					if(saveAppointment(makeAppointmentFromUserInput()))
						System.out.println("Appointment saved.\n");
					else 
						System.out.println("Cannot save; appointment at that time already exists\n");
					break;
				case DELETE_APPOINTMENT:
					try {
						if(deleteAppointment(makeCalendarFromUserInput(false)))
							System.out.println("Appointment deleted.\n");
						else 
							System.out.println("No appointment found for the specified date and time.\n");
					}
					catch(BadAppointmentDataException ex) {
						System.out.println(ex.getDescription()+"\n"+ex.getMessage());
					}
					catch(RuntimeException ex) {
						System.out.println("Unknown exception thrown.");
					}
					break;
				case CHANGE_APPOINTMENT:
					try {
						if (changeAppointment(makeCalendarFromUserInput(false)))
							System.out.println("Appointment re-booked.\n");
					}
					catch(BadAppointmentDataException ex) {
						System.out.println(ex.getDescription()+"\n"+ex.getMessage());
					}
					catch(RuntimeException ex) {
						System.out.println("Unknown exception thrown.");
					}
					break;
				case DISPLAY_APPOINTMENT:
					try {	
						displayAppointment(makeCalendarFromUserInput(false));
						System.out.println();
					}
					catch(BadAppointmentDataException ex) {
						System.out.println(ex.getDescription()+"\n"+ex.getMessage());
					}
					catch(RuntimeException ex) {
						System.out.println("Unknown exception thrown.");
					}
					break;
				case DISPLAY_SCHEDULE:
					try {
						displayDaySchedule(makeCalendarFromUserInput(true));
						System.out.println();
					}
					catch(BadAppointmentDataException ex) {
						System.out.println(ex.getDescription()+"\n"+ex.getMessage());
					}
					catch(RuntimeException ex) {
						System.out.println("Unknown exception thrown.");
					}
					break;
				case SAVE_APPOINTMENT_TO_FILE:
					if(saveAppointmentsToFile(getAppointments(),backupFile))
						System.out.println("Appointments data saved to "+ backupFile+"\n");
					break;
				case LOAD_APPOINTMENT_FROM_FILE:
					if (loadAppointmentsFromFile(backupFile, getAppointments()))
						System.out.println("Appointments successfully loaded from "+ backupFile+"\n");
					break;
				default:
		        	 System.out.println("Invalid choice; exiting program");
		        	 choice=EXIT;
		        	 break;
				}
			if(choice!=EXIT) choice = displayMenu();
		}
		saveAppointmentsToFile(getAppointments(),backupFile);
		System.out.println("Exiting Scheduler");
	}
	/**
	 * Takes one parameter apt of type Appointment, and returns a boolean value. This method saves an instance of Appointment to the array of appointments,
	 * only if does not find any previous existing appointment with the same date and time. Then, it sorts the array of appointments ordering by their calendar,
	 * using the Collections.sort method, using the compare method from the SortAppointmentByCalendar class.
	 * @param apt a reference value to a new instance of an Appointment object, which is saved into the array of appointments.
	 * @return true if the new appointment is successfully saved and the array is sorted by calendar, or false if it finds that there is already
	 * another appointment with the same date and time.
	 */
	private boolean saveAppointment(Appointment apt) {
		if(findAppointment(apt.getAptDate())==null) {
			getAppointments().add(apt);
			//sorts the array each time a new appointment is saved.
			Collections.sort(getAppointments(), new SortAppointmentByCalendar());
			return true;
		}
		else return false;
	}
	/**
	 * Takes one parameter cal of type calendar, returns a boolean value. This method searches the array of appointments for an existing appointment
	 * with the specified date and time contained in the parameter cal. If it exists, it prompts the user to confirm if the appointment should be
	 * deleted or not. If the user confirms positively, the appointment object is removed from the array. This method uses a local reference variable
	 * apt, which stores the reference value to the appointment object being searched.
	 * @param cal contains the date and time to search the specific appointment in the array.
	 * @return true if the appointment is deleted, or false if the user cancels the operation, or if no appointment with that specific date and time is found.
	 */
	private boolean deleteAppointment(Calendar cal) {
		Appointment apt = findAppointment(cal);
		if (getAppointments().contains(apt)) {
			System.out.println();
			displayAppointment(cal);
			if(getResponseTo("Enter 'Yes' to delete this appointment").equalsIgnoreCase("Yes")) {
				getAppointments().remove(apt);
				return true;
			}
			else return false;
		}
		else return false;
	}
	/**
	 * Takes one parameter, calToChange, of type Calendar.  This method searches the array of appointments for an existing appointment
	 * with the specified date and time contained in the parameter cal. If it exists, it prompts the user to confirm if wants to proceed with the
	 * modification of the appointment's date and/or time. If confirmed, it will prompt the user to enter a new date and time, which will then be
	 * verified for possible existing appoinments. If no appointments exist on that date and time, the desired appointment's calendar is modified,
	 * using the new date and time entered by the user.
	 * It uses a local variable index of type int, to store the index of the appointment to be rescheduled, if it exists in the array.
	 * @param calToChange contains the date and time to search the appointment that is to be rescheduled.
	 * @return true if the appointment is successfully modified, or false if the new date and time already exists, or if the user chooses to cancel
	 * the operation, or if no appointment is found with the calToChange's calendar value.
	 */
	private boolean changeAppointment(Calendar calToChange) {
		int index;
		if (getAppointments().contains(findAppointment(calToChange))) {
			displayAppointment(calToChange);
			if(getResponseTo("Enter 'Yes' to change the date and time of this appointment").equalsIgnoreCase("Yes")) {
				index = getAppointments().indexOf(findAppointment(calToChange));
				System.out.println("Enter new date and time ");
				Calendar newCal = makeCalendarFromUserInput(false);
				if (findAppointment(newCal)==null) {
					getAppointments().get(index).setAptDate(newCal);
					return true;
				}
				else return false;	
			}
			else return false;
		}
		else {
			System.out.println("No appointment found.");
			return false;
		}
		
	}
	/**
	 * Takes one parameter cal, of type Calendar, returns nothing. This method searches the array of appointments for an existing appointment
	 * with the specified date and time contained in the parameter cal. If it does not exists (null), the method prints a message informing that no
	 * appointment is scheduled for that date and time. If the appointment exists in the array, it prints all the information for the appointment, using
	 * the toString method from the Appointment class.
	 * @param cal contains the date and time to search the appointment that is to be displayed.
	 */
	private void displayAppointment(Calendar cal) {
		Appointment apt = findAppointment(cal);
		if(apt==null)
			System.out.printf("No appointment scheduled between %02d:00 and %02d:00\n", 
					cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.HOUR_OF_DAY)+1);
		else
			System.out.println("\n"+apt.toString()+"\n");
		
	}
	/**
	 * Takes one parameter cal, of type Calendar, returns nothing. This method uses a for loop to call the method displayAppointment nine times.
	 * Each iteration, it will pass the cal parameter on the displayAppointment with a different hour of day, being the loop's variable of control
	 * that ranges from 8 to 16.
	 * @param cal contains the date and time to search the appointment that is to be displayed.
	 */
	private void displayDaySchedule(Calendar cal) {
		for(int k=8; k<17;k++) {
			cal.set(Calendar.HOUR_OF_DAY, k);
			this.displayAppointment(cal);
		}
	}
	/**
	 * Takes two parameters - apts of type ArrayList and saveFile of type String, and returns a boolean value.
	 * This method is responsible for saving the array of appointments to a physical binary file on the hard drive. It verifies first if the
	 * file with the give name already exists, if not, it creates a file, using the name in the saveFile parameter. Then, it loops through the whole array
	 * while outputting the objects to the file through an ObjectOutputStream. The methods to create and output the file are wrapped in a try-catch 
	 * structure, so that if the process fails it will output an exception message and return false. If everything goes right, the method returns true. 
	 * @param apts A reference value to the ArrayList object parameterized with the class . This is the object to be saved to the file.
	 * @param saveFile a String value containing the name of the file where the array of appointments is to be saved.
	 * @return true if the array of appointments is succcessfully saved, or false if any exception is caught.
	 */
	private static boolean saveAppointmentsToFile(ArrayList<Appointment> apts, String saveFile) {
		
		File file = new File(saveFile);
		
		//these few lines check if the file already exists, if not, creates a new file.
		//source: https://docs.oracle.com/javase/7/docs/api/java/io/File.html
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to create file.");
		}
		
		try (FileOutputStream objectFileStream =	new FileOutputStream(file);
				ObjectOutputStream oos = new ObjectOutputStream(objectFileStream);)
			{
				for (Appointment thisApt: apts)
						oos.writeObject(thisApt);
				
				return true;
			}
			catch(FileNotFoundException ex){
				System.out.println("File not found.");
				return false;
			}
			catch(IOException ex) {
				ex.printStackTrace();
				return false;
			}
				
	}
	/**
	 * Takes two parameters - sourceFile of type String, and apts of type ArrayList; returns a boolean value.
	 * This method is responsible for loading the array of appointments from a physical binary file on the hard drive. It verifies first if the
	 * file with the give name already exists, if not, it creates an empty file, using the name in the sourceFile parameter. Then, using an ObjectInputStream
	 * object, it reads the content of the file and converts to appointments, adding them one by one into the apts ArrayList.
	 * The methods to create and output the file are wrapped in a try-catch structure, so that if the process fails it will output an exception message
	 * and return false. If everything goes right, the method returns true.
	 * @param sourceFile a String value containing the name of the file where the array of appointments is to be loaded from.
	 * @param apts A reference value to the ArrayList object parameterized with the class Appointments. This is the object where to save the
	 * contents from the file.
	 * @return true if the array of appointments is succcessfully loaded, or false if any exception is caught.
	 */
	private static boolean loadAppointmentsFromFile(String sourceFile, ArrayList<Appointment>apts) {
		
		File sFile = new File(sourceFile);
		try {
			sFile.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try(
			FileInputStream inputStream = new FileInputStream(sourceFile);
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			){
			//Source for the condition in the while loop:
			//https://stackoverflow.com/questions/2626163/java-fileinputstream-objectinputstream-reaches-end-of-file-eof
			while (inputStream.available()>0) 
				apts.add((Appointment) ois.readObject());
			//inputStream.close();
			//ois.close();
			return true;
		}
		catch(EOFException ex) {
			//This exception tells End Of File, when reading from a file.
			return false;
			}
		catch(FileNotFoundException ex){
			System.out.println("File not found.");
			return false;
			} 
		catch (IOException e) {
			
			e.printStackTrace();
			return false;
		} 
		catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			return false;
		}
		
		
	}
	/**
	 * Takes one parameter s of type String and returns a String value. This method displays the message received in the parameter s, and then reads
	 * the input from the keyboard, using the Scanner object.
	 * @param s message to be displayed to the user, containing specific inquiries.
	 * @return a String containing the value read from the user, as a response to the message.
	 */
	private static String getResponseTo(String s) {
		System.out.printf(s);
		return(scan.nextLine());
	}
	/**
	 * Takes no parameter; returns a reference value of type Appointment. This method makes good use of the getResponseTo method,
	 * in order to get input from the user and store them in the corresponding variables. Those variables are used to create a new instance of
	 * the Appointment class, using a four-args constructor. The setting of the variables is contained inside a try-catch block, which is placed inside a
	 * loop. If any exception is caught, the apt local variable is not set and the loop starts over. Only when the local variable isAptSet is set to true,
	 * that it exits the loop and returns the new Appointment object.
	 * @return a reference value to a new instance of an Appointment object, holding the information just entered by the user.
	 */
	private Appointment makeAppointmentFromUserInput() {
		boolean isAptSet = false; Appointment apt=null;
		do {
			try {
				String newName = getResponseTo("Enter Client Name (as FirstName LastName):");
				TelephoneNumber newPhone = new TelephoneNumber(getResponseTo("Phone Number (e.g. 613-555-1212):"));
				Calendar newCal = makeCalendarFromUserInput(false);
				Activity newActivity = new Activity(getResponseTo("Enter Activity: "),getEmployee().getActivityType());
				apt = new Appointment(newCal, newName, newPhone, newActivity);
				isAptSet = true;
			}
			catch(BadAppointmentDataException ex){
				System.out.println(ex.getDescription() +"\n"+ ex.getMessage()+"\nPlease re-enter the information.");
			}
			catch(RuntimeException ex) {
				System.out.println("Unknown exception thrown.");
			}
		}while(!isAptSet);
		return apt;
	}
	/**
	 * Takes one parameter suppressHour, of type boolean; returns a reference value of Calendar type.
	 * This method prompts the user to enter the date and time, in order to create a new Calendar object. The date and time are temporarily
	 * stored in local variables of type String. If the parameter suppressHour is false, the time will not be requested. If it's true, the
	 * user must enter a time value. Before being converted to Calendar, the date value is validated by the isCalendarCorrect method.
	 * Both date and time values are then parsed and inserted into the corresponding fields of the Calendar object, which is returned at the end.
	 * @param suppressHour tells the method whether it should prompt the user for the time. If false, time must be entered, if true, time is not needed.
	 * @return a reference value to the new Calendar object.
	 */
	private static Calendar makeCalendarFromUserInput(boolean suppressHour) {
		/*Some of the codes in this method were based on the information found in:
		 * https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html */
		String date;
		do{date = getResponseTo("Appointment Date (Entered as DDMMYYYY):");}
		while(!isCalendarCorrect(date));
		String time = "0";
		if(suppressHour==false) time = getResponseTo("Appointment Time:");
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(0,2)));
		cal.set(Calendar.MONTH,(Integer.parseInt(date.substring(2,4)))-1);
		cal.set(Calendar.YEAR, Integer.parseInt(date.substring(4,8)));
		cal.set(Calendar.HOUR_OF_DAY, processTimeString(time));
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		return cal;
	}
	/**
	 * Takes one parameter date, of type String; returns a boolean value.
	 * This method verifies whether the date enterd by the user is in the format specified by the dateFormat object. If so, returns true. If not, 
	 * throws a new BadAppointmentDataException with the corresponding message.
	 * @param date holds the String value of the date entered by the user, and used by the dateFormat parser in order to validate the format.
	 * @return true if the parameter's value matches the specified format, or false, along with a BadAppointmentDataException associated with the error.
	 * @throws BadAppointmentDataException if the date's value is empty, or if its format is invalid.
	 */
	private static boolean isCalendarCorrect(String date) {
		
		if(date.equalsIgnoreCase("")) throw new BadAppointmentDataException("Must enter a value","Empty or null value entered");
		try {
			dateFormat.parse(date);
			return true;
		}
		catch(ParseException e) {
			throw new BadAppointmentDataException("Bad calendar date entered; format is DDMMYYYY","Bad Calendar format\n");
		}
		
	}
	/**
	 * Takes one parameter time of type String; returns an int value.
	 * This method parses the parameter's value into an integer, and converts its value into 24 hour format, if necessary, returning the hour value
	 * as an int.
	 * @param time holds the String value of the hour used in the Calendar object.
	 * @return an int value containing the hour for the appointment.
	 */
	private static int processTimeString(String time) {
		//This split method was based on the one from Lab 3
		String [] timeStr = time.split("(?=\\ )|(?=\\:)");
		int hour = Integer.parseInt(timeStr[0]);	
		if (hour <5) hour+=12;
		return hour;
	}
	/**
	 * Takes one parameter cal, of type Calendar; returns one Appointment reference value.
	 * This method sorts and searches the array of appointments to find the one that matches the specified calendar. It instantiates a new Appointment object,
	 * using the calendar passed as a parameter. Then, the Collections.binarySearch method searches for this appointment in the array.
	 * If it exists, the appointment object is returned. If not, the method returns a null object.
	 * @param cal The calendar that is used to search for the desired appointment, matching its date and time.
	 * @return the appointment that matches the parameter's calendar, or null if the appointment does not exist.
	 */
	private Appointment findAppointment(Calendar cal) {
		
		Appointment aptSearch = new Appointment(cal,"x x",new TelephoneNumber("300-000-0000"),new Activity("",""));
		//Collections.sort(getAppointments(), new SortAppointmentByCalendar());
		Collections.sort(getAppointments(), (Appointment apt1, Appointment apt2)->apt1.getAptDate().compareTo(apt2.getAptDate()));
		int i = Collections.binarySearch(getAppointments(), aptSearch, new SortAppointmentByCalendar());
		return (i>=0)? getAppointments().get(i):null;
		
	}
	/**
	 * Takes no parameter; returns an ArrayList reference value.
	 * @return the array of appointments referenced by the private field appointments.
	 */
	private ArrayList<Appointment> getAppointments() {
		return appointments;
	}
	

		
}
