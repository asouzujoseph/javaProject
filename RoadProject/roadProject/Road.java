package roadProject;


import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;


/**
 * @invar  The speedLimit of each road must be a valid speedLimit for any
 *         road.
 *       | isValidSpeedLimit(getSpeedLimit())
 *
 * @invar  The averageSpeed of each road must be a valid averageSpeed for any
 *         road.
 *       | isValidaverageSpeed(getaverageSpeed())
 *
 * @invar  The identification of each road must be a valid identification for any
 *         road.
 *       | isValidIdentification(getIdentification())
 *
 * @invar  The length of each road must be a valid length for any
 *         road.
 *       | isValidLength(getLength())
 *
 * @invar  Each road can have its startLocation as startLocation.
 *       | canHaveAsEndPoint(startLocation, endLocation)
 *
 * @invar  Each road can have its endLocation as endLocation.
 *       | canHaveAsEndPoint(startLocation, endLocation)
 *
 * @invar The currentDelay of each road must be a valid currentDelay for any
 *        road.
 *       | isValiddelay(getDelay())
 *
 * @invar The delay of each road must be a valid delay for any road. 
 *       | isValidDelay(getDelay())
 *        
 * @invar  The capacity of each road must be a valid capacity for any
 *         road.
 *       | isValidCapacity(getCapacity())       
 */
 
/**
 * A subclass of superclass Segments with attributes such as identification, length, speed limit, startLocation, endLocation etc.
 * @author Nnamdi Asouzu and Felix Bamidele
 * @version 1.0
 */
public abstract class Road extends Segments {

	/**
	 * Variables registering the identification of this road.
	 */
	protected String identification;

	/**
	 * An ArrayList containing road identification
	 */
	protected  ArrayList<String> uniqueIdentificationNumber = new ArrayList<String>();

	/**
	 * Variable registering the first location of this road.
	 */
	protected  Location startLocation;
	
	/**
	 * Variable registering the second location of this road.
	 */
	protected Location endLocation;

	/**
	 * Variable registering the length of this road.
	 */
	protected int length;

	/**
	 * Variable registering the speedLimit of this road.
	 */
	protected Float speedLimit;

	/**
	 * Variable registering the averageSpeed of this road.
	 */
	protected Float averageSpeed;

	/**
	 * Variable registering the delay of this road in the forward direction.
	 */
	protected float delayForward ;

	/**
	 * Variable registering the delay of this road in the backward direction.
	 */
	protected float delayBackward ;

	/**
	 * Variable showing the direction of this road.
	 */
	protected boolean forward = true;
	
	/**
	 * Shows if a road is blocked in either forward or reverse direction
	 */
	protected boolean[] blocked  = {false, false} ;

	/**
	 * Variable registering whether this road is terminated.
	 */
	protected boolean isTerminated = false;	
	
	/**
	 * Variable showing the total numbers of cars that can be on a road at the same time.
	 */
	protected int capacity;
	
	/**
	 * A constructor for creating subclasses of the superclass Road
	 * @param identification
	 * 			The identification of the road
	 * @param startLocation
	 * 			The start end point of the road
	 * @param endLocation
	 * 			The ending end point of the road
	 * @param length
	 * 			The length of the road
	 * @param speedLimit
	 * 			The speed limit on the road
	 * @param averageSpeed
	 * 			The average speed on the road
	 * @param capacity
	 * 			The limit on the total number of cars on each road
	 * @throws ModelModelException
	 * 		| if ((!(isValidIdentification(identification))) || (!(canHaveAsEndPoint(startLocation, endLocation)) ||
	 * 			 (!(isValidSpeedLimit(speedLimit))) || (!(isValidAverageSpeed(averageSpeed))) || 
	 * 			 (!(isValidCapacity(getCapacity())) || (!(isValidLength(getLength()))) 
	 * @pre This  road can have the given location as its startLocation or endLocation. 
	 * 		 | canHaveAsEndPoint(startLocation, endLocation)
	 * @effect The speedLimit of this new road is set to the given speedLimit. 
	 * 		 | this.setSpeedLimit(speedLimit)
	 * @effect The averageSpeed of this new road is set to
	 *         the given averageSpeed.
	 *       | this.setAverageSpeed(averageSpeed)
	 * @effect The identification of this new road is set to
	 *         the given identification.
	 *       | this.setIdentification(identification)
	 * @post The startLocation of this new road is equal to the given startLocation. 
	 * 		 | new.getStartLocation() == startLocation
	 * @post The endLocation of this new road is equal to the given endLocation
	 * 		 | new.getEndLocation() == endLocation
	 * @post   If the given capacity is a valid capacity for any road,
	 *         the capacity of this new road is equal to the given
	 *         capacity. Otherwise, the capacity of this new road is equal
	 *         to null.
	 *       | if (isValidCapacity(capacity))
	 *       |   then new.getCapacity() == capacity
	 *       |   else new.getCapacity() == null
	 * @post If the given length is a valid length for any road, the length of this
	 *       new road is equal to the given length. Otherwise, the length of this
	 *       new road is equal to 0. 
	 *       | if (isValidLength(Length)) 
	 *        then  new.getLength() == length 
	 *         else new.getLength() == 0
	 */
	public Road(String identification, Location startLocation, Location endLocation, int length, Float speedLimit,
			Float averageSpeed, int capacity) throws ModelException {
		setIdentification(identification);
		try {
			if (canHaveAsEndPoint(startLocation, endLocation)==true) {
				this.startLocation = startLocation;
				this.endLocation = endLocation;}
		} catch (Throwable exc) {
			throw new ModelException();
		}
		setLength(length);
		setSpeedLimit(speedLimit);
		setAverageSpeed(averageSpeed);
		startLocation.addAsJoinedRoad(this);
		endLocation.addAsJoinedRoad(this);
		setCapacity(capacity,forward);
	}

	
	// Identification
	/**
	 * Return the identification of this road.
	 * 		| this.identification
	 */
	@Basic
	@Raw
	public String getIdentification() {
		return this.identification;
	}

	/**
	 * Check whether the given identification is a unique identification for any
	 * road.
	 * 
	 * @param identification 
	 * 			The identification to check.
	 * @return True if the identification is unique 
	 * 			| result == !(uniqueIdentificationNumber.contains(identification)
	 */
	public boolean uniqueID(String identification) {
		if ((uniqueIdentificationNumber.contains(this.identification))==false) 
			return true;
		else
		return false;
	}
	
	/**
	 * Check whether the given identification is a valid identification for any road.
	 * @param identification 
	 * 		   The identification to check.
	 * @return True if and only if unique identification is a letter and Upper case
	 *         at the first character and a digit at the second or third character.
	 *         | result ==  (identification.length()==3 || 2) &&
	 *         (Character.isUpperCase(identification.charAt(0))) &&
	 *         (Character.isDigit(identification.charAt(1)) &&
	 *         (Character.isLetter(identification.charAt(0))) &&
	 *         (identification.charAt(0)>='A') && (identification.charAt(0)<='Z' ) &&
	 *         (identification.charAt(1)>='0') && (identification.charAt(1)<='9') &&
	 *         (identification.charAt(2)>='0') && (identification.charAt(2)<='9') 
	 */
	public boolean isValidIdentification(String identification) {
		if ((identification.length() == 3) ) {
			return ((Character.isUpperCase(identification.charAt(0)) && (Character.isDigit(identification.charAt(1)))
					&& (Character.isDigit(identification.charAt(2))) && (Character.isLetter(identification.charAt(0)))
					&& (identification.charAt(0) >= 'A') && (identification.charAt(0) <= 'Z'))
					&& (identification.charAt(1) >= '0') && (identification.charAt(1) <= '9')
					&& (identification.charAt(2) >= '0') && (identification.charAt(2) <= '9'));
		} else if ((identification.length() == 2)) {
			return ((Character.isUpperCase(identification.charAt(0))) && (Character.isDigit(identification.charAt(1))
					&& (Character.isLetter(identification.charAt(0))) && (identification.charAt(0) >= 'A')
					&& (identification.charAt(0) <= 'Z') && (identification.charAt(1) >= '0')
					&& (identification.charAt(1) <= '9'))  && (uniqueID(identification) == true));
		} else
			return false;
	}

	/**
	 * Set the identification of this road to the given identification.
	 * @param identification 
	 * 			The new identification for this road.
	 * @post The identification of this new road is equal to the given identification. 
	 * 			| new.getIdentification() == identification
	 * @throws ModelModelException The given identification is not a valid identification for any road. 
 	 *			| ! isValidIdentification(getIdentification()) 
	 */
	@Raw
	public void setIdentification(String identification) throws ModelException {
		if (isValidIdentification(identification)) {
			this.identification = identification;
			uniqueIdentificationNumber.add(identification);
		}
		else if (!(isValidIdentification(identification))) 
			throw new ModelException();
		else if (this.isTerminatedRoad() == true)
			uniqueIdentificationNumber.remove(this.identification);
		else throw new ModelException();
	}

	/**
	 * This method destroys the specified road
	 */
	public void terminateRoad() {
		this.isTerminated = true;
		uniqueIdentificationNumber.remove(this.identification);
		this.startLocation.removeAsJoinedRoad(this);
		this.endLocation.removeAsJoinedRoad(this);
	}

	/**
	 * This method checks if the specified road is destroyed
	 * @return 
	 *       | this.isTerminated
	 */
	public boolean isTerminatedRoad() {
		return this.isTerminated;
	}
	
	/**
	 * This method returns the startLocation of this road.
	 */
	@Basic
	@Raw
	@Immutable
	@Override
	public  Location getStartLocation() {
		return this.startLocation;
	}

	/**
	 * This method return the endLocation of this road.
	 */
	@Basic
	@Raw
	@Immutable
	@Override
	public Location getEndLocation() {
		return this.endLocation;
	}
	
	/**
	 * This method checks whether this road can have the given endPoints as its endPoints.
	 * 
	 * @param startLocation
	 * 		 The starting endPoint to check.
	 * @param endLocation
	 * 		 The ending endPoint to check
	 * @return  true
	 * 			| result == (this.startLocation == null || this.endLocation == null)
	 * @throws ModelException 
	 */
	@Raw
	public boolean canHaveAsEndPoint(Location startLocation, Location endLocation) throws ModelException {
		double[] coordinates = getLocationCoordinates(startLocation);
		if (coordinates != null)
			return true;
		else return false;
	} 

    /**
     * Method showing both the startLocation and endLocation of a road
     * @return an array of the startLocation and endLocation
     *     |  Location [] endPoints = { Road.getStartLocation(), Road.getEndLocation() };
     */
	public  Location [] getRoadCoordinates() {
		Location [] endPoints = { this.getStartLocation(), this.getEndLocation() };
		return endPoints;
	}
	
	/**
	 * sets the start Location and end Location of the road, if they are valid coordinates
	 * @param startLocation
	 * 			The starting point of the road
	 * 			| this.startLocation = startLocation
	 * @param endLocation
	 * 			The ending point of the road
	 * 			| this.endLocation = endLocation
	 * @throws ModelException
	 * 			Thrown if the coordinates are invalid
	 * 			| !(canHaveAsEndPoint(startLocation, endLocation)
	 */
	public void setEndPoint(Location startLocation, Location endLocation) throws ModelException {
		assert (canHaveAsEndPoint(startLocation, endLocation));// {
			this.startLocation = startLocation;
			this.endLocation = endLocation;
		//}
		 if (!(canHaveAsEndPoint(startLocation, endLocation))) 
			throw new ModelException();
		else throw new ModelException();
		
	}
	
	/**
	 *   This method returns an array of length 1 or 2, with its elements in the same
	 *   order as the elements of the array returned by getEndPoints.
	 * @param road
	 * @return the start locations of the given road.
	 */
	public abstract Location [] getStartingPoint();
	
	/**
	 *   This method returns an array of length 1 or 2, with its elements in the same
	 *   order as the elements of the array returned by getEndPoints.
	 * @param road
	 * @return the end locations of the given road.
	 */
	public abstract Location [] getEndingPoint ();		
	
	/**
	 * This method reverse the direction in which the given road can be traversed
	 * @param road
	 * 			The object to consider, in this case it must be an alternating road
	 * @throws ModelException
	 * 			if the road is not an instance of the class AlternatingRoad
	 */
	public void reverseDirection() throws ModelException {
		if ((this instanceof AlternatingRoad) && (forward)) {
			this.forward = false;
		} else if ((this instanceof AlternatingRoad) && (!forward)) {
			this.forward = true;
		} else throw new ModelException();
	}
			
	
	// Length of the road
	
	/**
	 * Return the length of this road.
	 * 		| this.length
	 */
	@Basic
	@Raw
	public  int getLength() {
		return this.length;
	}

	/**
	 * Method showing the valid minimum length of the road
	 * @return
	 * 		| result == 0
	 */
	public static int minLength() {
		return 0;
	}

	/**
	 * Method showing the valid maximum length of the road
	 * @return
	 * 		| result == Integer.MAX_VALUE
	 */
	public static int maxLength() {
		return Integer.MAX_VALUE;
	}
	
	/**
	 * Check whether the given length is a valid length for any road.
	 * @param Length 
	 * 			The length to check.
	 * @return  true
	 * 			| result == (length >= minLength() && (length < maxLength()))
	 */
	public  boolean isValidLength(int length) {
		return (length >= minLength() && (length < maxLength()));
	}

	/**
	 * Set the length of this road to the given length.
	 * @param Length 
	 * 			The new length for this road.
	 * @post If the given length is a valid length for any road, the length of this
	 *       new road is equal to the given length. 
	 *       | if (isValidLength(Length)) 
	 *       then new.getLength() == Length
	 */
	@Raw
	public void setLength(int length) {
		if (isValidLength(length)) {
			this.length = length;
		} else if (length < 0) {
			this.length = Math.abs(length);
		}
	}

	// Speed
	
	/**
	 * Return the speedLimit of this road.
	 * 		| this.speedLimit
	 */
	@Basic
	@Raw
	public Float getSpeedLimit() {
		return this.speedLimit;
	}

	/**
	 * Method showing the default speedLimit if no speedLimit is specified for the road
	 * @return 
	 * 		| result == 19.5f
	 */
	public  Float fixSpeedLimit() {
		return 19.5f;
	}

	/**
	 * Check whether the given speedLimit is a valid speedLimit for any road.
	 * @param speedlimit 
	 * 			The speedLimit to check.
	 * @return 	true
	 * 			| result == (((speedLimit > minSpeedLimit())  
						&& (speedLimit < maxSpeedLimit()) && speedLimit >= this.getAverageSpeed()))
	 */
	public boolean isValidSpeedLimit(Float speedLimit) {
		if (this.getSpeedLimit()==null) {
			return ((speedLimit > minSpeedLimit())  && (speedLimit < maxSpeedLimit()) || (speedLimit == null)) ;
			}
			else if (this.getSpeedLimit()!=null) {
				return (((speedLimit > minSpeedLimit())  
						&& (speedLimit < maxSpeedLimit()) && (speedLimit >= this.getAverageSpeed())));
			}
		return false;
	}

	/**
	 * Set the speedLimit of this road to the given speedLimit.
	 * @param speedLimit 
	 * 		The new speedLimit for this road.
	 * @post The speedLimit of this new road is equal to the given speedLimit. 
	 * 			|new.getSpeedLimit() == speedLimit
	 * @throws ModelModelException 
	 * 			The given speedLimit is not a valid speedLimit for any road. 
	 *          | ! isValidSpeedLimit(getSpeedLimit())
	 */
	@Raw
	public void setSpeedLimit(Float speedLimit)throws ModelException {
		if (! isValidSpeedLimit(speedLimit))
			throw new ModelException("the speedLimit is invalid");
		else if (speedLimit == null) {
			this.speedLimit = fixSpeedLimit();
		}
		else this.speedLimit = speedLimit;
	}

	/**
	 * Method to get the maximum speedLimit
	 * @return
	 * 		| result == 299792458
	 */
	public static long maxSpeedLimit() {
		return 299792458;
	}

	/**
	 * Method to get the minimum speedLimit 
	 * @return 
	 * 		| 0.0f
	 */
	public static Float minSpeedLimit() {
		return 0.0f;
	}

	/**
	 * Return the averageSpeed of this road.
	 * 		| this.averageSpeed
	 */
	@Basic
	@Raw
	public Float getAverageSpeed() {
		return this.averageSpeed;
	}

	/**
	 * Check whether the given averageSpeed is a valid averageSpeed for any road.
	 * @param averageSpeed 
	 * 		The averageSpeed to check.
	 * @return   true
	 *       |  result == (averageSpeed <= speedLimit && averageSpeed >= 0)
	 */
	public  boolean isValidAverageSpeed(Float averageSpeed) {
		return (averageSpeed <= speedLimit && averageSpeed >= 0);
	}

	/**
	 * Set the averageSpeed of this road to the given averageSpeed.
	 * @param averageSpeed 
	 * 		The new averageSpeed for this road.
	 * @post The averageSpeed of this new road is equal to the given averageSpeed. 
	 *       | new.getAverageSpeed() == averageSpeed
	 * @throws ModelModelException The given averageSpeed is not a valid averageSpeed for any road.
	 *       | ! isValidAverageSpeed(getaverageSpeed())
	 */
	@Raw
	public void setAverageSpeed( Float averageSpeed) throws ModelException {
		if (isValidAverageSpeed(averageSpeed) == false) {
			throw new ModelException();
		} else if (isValidAverageSpeed(averageSpeed) == true) {
		this.averageSpeed = averageSpeed;
		}
	}

	
// Delay on the road
	/**
	 * Initialize this new road with given delay.
	 * @param delay 
	 * 		The delay for this new road.
	 * @pre The given delay must be a valid delay for any road. 
	 *      |isValidDelay(delay)
	 * @post The delay of this new road is equal to the given delay. 
	 *      | new.getDelay() == delay
	 */

	/**
	 * Return the delay of this road.
	 * 		| (this.delayForward || this.delayBackward)
	 */
	@Basic @Raw
	public float getDelay(boolean forward) throws ModelException{
		if (forward) {
		return this.delayForward;
		} else {
		  return this.delayBackward;
		}
	}

	/**
	 * Check whether the given delay is a valid delay for
	 * any road.
	 * @param  delayForward
	 *         The delay in the forward direction.
	 * @param  delayBackward
	 * 		   The delay in the backward direction
	 * @return 	true
	 *       | result == ((delayForward >= 0.0f && delayForward < Float.POSITIVE_INFINITY) || 
				(delayBackward >= 0.0f && delayBackward < Float.POSITIVE_INFINITY))
	*/
	public  boolean isValidDelay(float delayForward, float delayBackward) {
		return ((delayForward >= 0.0f && delayForward < Float.POSITIVE_INFINITY) || 
				(delayBackward >= 0.0f && delayBackward < Float.POSITIVE_INFINITY));
		}

	/**
	 * Set the delay of this road to the given delay in the forward direction. 
	 * @param delayForward 
	 * 		 The new delay for this road.
	 * @pre The given delay must be a valid delay for any road. 
	 *      | isValidDelay(delayForward, delayBackward)
	 * @post The delay of this road is equal to the given delay. 
	 *      | new.getDelay() == delayForward
	 */
	@Raw
	public void setDelayForward( float delayForward, boolean forward) throws ModelException {
		assert (isValidDelay(delayForward, delayBackward));
		if (forward)
		this.delayForward = delayForward;
	}

	/**
	 * Set the delay of this road to the given delay in the backward direction.
	 * @param delayBackward 
	 * 		The new delay for this road.
	 * @pre The given delay must be a valid delay for any road. 
	 *      | isValidDelay(delayForward, delayBackward)
	 * @post The delay of this road is equal to the given delay. 
	 *      | new.getDelay() == delayBackward
	 */
	@Raw
	public void setDelayBackward( float delayBackward, boolean forward) {
		assert (isValidDelay(delayForward, delayBackward));
		if (! forward)
		this.delayBackward = delayBackward;
	}
	
	/**
	 * Method to check if a road is blocked 
	 * @param forward
	 * @return 
	 * 		| this.isBlocked
	 * @throws ModelException
	 * 			if (((OneWayRoad)road.forward)==false)
	 */
	public boolean isBlocked(boolean forward)throws ModelException{
		if (forward)
			return this.blocked[0];
		else return this.blocked[1];
	}
	
	/**
	 * Method to either block or unblock based on the output of flag
	 * @param flag
	 */
	public void setBlocked (boolean flag, boolean forward) {
		if (forward)
			this.blocked[0] = flag;
		else this.blocked[1] = flag;
	}

	/**
	* Method to block the road
	*/
	public void block (boolean forward) {
		setBlocked(true, forward);
	}
	
	/**
	* Method to unblock the road
	*/
	public void unblock (boolean forward) {
		setBlocked(false, forward);
	}
  
	/**
	 * Method to set block on the road in the direction of the flag
	 * @param flag 
	 * 		shows direction to which the road should be blocked
	 * @param forward 
	 * 		shows direction of the road
	 */
	public void changeBlockedState (Road road, boolean flag, boolean forward) throws ModelException {
		this.setBlocked(flag, forward);
		}
	
	//Capacity
	
	/**
	 * This method returns the capacity of this road.
	 */
	@Basic @Raw
	public Integer getCapacity(boolean forward) throws ModelException{
		return this.capacity;
	}
	
	/**
	 * This method checks whether the given capacity is a valid capacity for any road.
	 * @param  capacity
	 *         The capacity of cars allowed on each road.     
	*/
	public abstract boolean isValidCapacity(int capacity, boolean forward) throws ModelException;
	
	/**
	 * Set the capacity of this road to the given capacity.
	 * @param  capacity
	 *         The new capacity for this road.
	 * @throws ModelException 
	 * @post   If the given capacity is a valid capacity for any road,
	 *         the capacity of this new road is equal to the given
	 *         capacity.
	 *       | if (isValidCapacity(capacity))
	 *       |   then new.getCapacity() == capacity
	 */
	@SuppressWarnings("null")
	@Raw
	public void setCapacity(int capacity, boolean forward) throws ModelException  {
		if (! isValidCapacity(capacity, forward))
			capacity = 0;    //(Integer)null;
		else if (isValidCapacity(capacity, forward))
			this.capacity = capacity;
	}
	
	
	/**
	 * Method showing the cumulative time to travel a set of interconnected roads
	 * @param averageSpeed 
	 * 		the average speed of the road
	 * @param length  
	 * 		the length of the road
	 * @param delayForward 
	 * 		the delay on the road in the forward direction
	 * @return time
	 * 		| ((length / averageSpeed) - delayForward)
	 * @throws ModelException 
	 * 		| if ((!isValidAverageSpeed) && (!isValidDelay(delayForward,delayBackward) &&
	 * 				(!isValidLength))
	 */
	public Float cummulativeTime() throws ModelException {
		this.getAverageSpeed();
		this.getLength();
		this.getDelay(true);
		Float time = ((this.getLength() / this.getAverageSpeed()) -this.getDelay(true));
		return time;
	}


}
