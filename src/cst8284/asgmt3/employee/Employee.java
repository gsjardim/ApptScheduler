
package cst8284.asgmt3.employee;
/**
 *The Employee class is an abstract class that holds an employee name, and also specifies the getActivityType method,
 *which must be overriden in any of its subclasses.
 *@author Guilherme Jardim
 *@version 1.0
 **/
public abstract class Employee {
	/**
	 * Contains the employee's full name as a String.
	 */
	private String fullName;
	
	/**
	 * passes to the one-arg constructor the default String "unknown".
	 */
	protected Employee() {this("unknown");}
	/**
	 * Sets the private field fullName with the received parameter of type String, using the setName method.
	 * @param fullName String
	 */
	protected Employee(String fullName) {setName(fullName);}
	/**
	 * Sets the private field fullName with the received parameter of type String.	
	 * @param fullName String
	 */
	public void setName(String fullName) {this.fullName = fullName;}
	/**
	 * Returns the value of fullName of type String.
	 * @return String fullName
	 */
	public String getName() {return fullName;}
	/**
	 * this abstract method has to be overriden in any of the Employee class' subclasses.
	 * The concrete method has to return the activity type related to the its corresponding class.
	 * @return nothing the method is abstract
	 */
	public abstract String getActivityType();
	/**
	 * This method overrides the Object class toString method.
	 * @return String fullName - calling upon the getName method.
	 */
	@Override
	public String toString() {return getName();}
	
}