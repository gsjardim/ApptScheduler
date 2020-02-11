
package cst8284.asgmt3.scheduler;
import java.io.Serializable;
/** The Activity Class holds information related to the activity to be performed for each new appointment.
 *The Appointment class constructor stores an instance of the Activity class in the Appointment object.
 *This class implements the Serializable interface.
 *@author Guilherme Jardim
 *@version 1.0
 **/
public class Activity implements Serializable{
	
	//private static final long serialVersionUID = 1L;
	
	/** a brief description informed by the scheduler's user of what is to be done during the appointment;
	 */
	private String descriptionOfWork;
	/** holds the Employee's activity type, that is, a service category.
	 */
	private String category;
	
	/**
	 * The two-args constructor passes the two String parameters "description" and "category"
	 * on to their respective setters, "setDescription" and "setCategory".
	 * @param description String - holds the detailed description of the work to be performed.
	 * @param category String - holds the Employee's subclass activity type. 
	 */
	public Activity (String description, String category) {
		this.setDescription(description);
		this.setCategory(category);
	}
	/**
	 * This method takes no parameter and returns the Sring value of the private field "descriptionOfWork".
	 * @return String descriptionOfWork
	 */
	public String getDescription() {
		return this.descriptionOfWork;
	}
	/**
	 * This method takes one parameter "s" of type String and sets the private field descriptionOfWork with this parameter's value.
	 * @param s String
	 */
	public void setDescription(String s) {
		this.descriptionOfWork = s;
	}
	/**
	 *  This method takes no parameter and returns the Sring value of the private field "category".
	 * @return String category
	 */
	public String getCategory() {
		return this.category;
	}
	/**
	 * This method takes one parameter "s" of type String and sets the private field category with this parameter's value.
	 * @param s String
	 */
	public void setCategory(String s) {
		this.category = s;
	}
	
	/**
	 * This method overrides the toString method from the {@link}Object class.
	 * It takes no parameter and returns a String value.
	 * It calls upon the getCategory and getDescription methods.
	 * @return String, being the concatenation of the fields category and descriptionOfWork, separated by a newline character.
	 */
	public String toString () {
		return (getCategory()+"\n"+getDescription());
	}
	

}
