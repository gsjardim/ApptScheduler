
package cst8284.asgmt3.scheduler;
import cst8284.asgmt3.employee.*;
/**
 *The SchedulerLauncher class contains the main method. It simply instantiates a new Scheduler object,
 *passing as a parameter a new Dentist object.
 *Then it uses the new reference variable to call the launch method in the Scheduler class.
 *@author Guilherme Jardim
 *@version 1.0
 **/
public class SchedulerLauncher {

	public static void main(String[] args) {
		
		Scheduler schedule = new Scheduler(new Dentist("Dr. Andrews"));
		schedule.launch();
		
	}

}
