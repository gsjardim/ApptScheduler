package cst8284.asgmt3.accessories;

import java.util.Comparator;
import cst8284.asgmt3.scheduler.Appointment;
/**
 *The SortAppointmentByCalendar class is an auxiliary class that implements the Comparator interface.
 *Therefore, it contains a compare method, which will be used associated with the Collections.sort method
 *and with the Collections.binarySearch method.
 *@author Guilherme Jardim
 *@version 1.0
 **/
public class SortAppointmentByCalendar implements Comparator<Appointment> {
	
	/**
	 * Returns an integer resulting from the comparison between the calendars properties of two Appointments.
	 * It implements the Calendar class compare method.
	 * @param apt1,apt2 Appointments objects which calendars need to be compared.
	 * @return int If this appointment's calendar is greater than the specified appointment's calendar, it retuns a positive integer.
	 * If this appointment's calendar is lesser than the specified appointment's calendar, it returns a negative integer.
	 * If both appointments' calendars are equal, it returns Zero.
	 */
	public int compare(Appointment apt1, Appointment apt2) {
		return apt1.getAptDate().compareTo(apt2.getAptDate());
	}

}
