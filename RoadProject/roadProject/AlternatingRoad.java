package roadProject;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A subclass of superclass Road inheriting all the attributes of class Road 
 * @author Nnamdi Asouzu and Felix Bamidele
 * @version 1.0
 */
public class AlternatingRoad extends Road {
	
	/**
	 * A constructor for creating objects of the subclass AlternatingRoad
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
	 * @throws ModelModelException
	 * 		| if ((!(isValidIdentification(identification))) || (!(canHaveAsEndPoint(startLocation, endLocation)) ||
	 * 			 (!(isValidSpeedLimit(speedLimit))) || (!(isValidAverageSpeed(averageSpeed)))
	 */
	public AlternatingRoad(String identification, Location startLocation, Location endLocation, int length,
			Float speedLimit, Float averageSpeed, int capacity) throws ModelException {
		super(identification, startLocation, endLocation, length, speedLimit, averageSpeed, capacity);
	}

	/**
	 *   The method returns an array of length 1 or 2, with its elements in the same
	 *   order as the elements of the array returned by getEndPoints.
	 * @param road
	 * @return the start locations of the given road.
	 * 	| Location [] startPoint = {this.getStartLocation()}
	 */
	@Override
	public Location [] getStartingPoint() {
		Location [] startPoint = {this.getStartLocation()};
		return startPoint;
	}
	
	/**
	 *   The method returns an array of length 1 or 2, with its elements in the same
	 *   order as the elements of the array returned by getEndPoints.
	 * @param road
	 * @return the end locations of the given road.
	 * 			| Location [] endPoint = {road.getEndLocation()}
	 */
	@Override
	public  Location [] getEndingPoint () {
		Location [] endPoint = {this.getEndLocation()};
		return endPoint;
	}
	
	/**
	 * Return the startLocation of this road.
	 * 		| this.startLocation
	 */
	@Basic	@Raw  @Override
	public  Location getStartLocation() {
		return this.startLocation;
	}
	
	/**
	 * Return the endLoction of this road.
	 * 		| this.endLocation
	 */
	@Basic 	@Raw  @Override
	public Location getEndLocation() {
		return this.endLocation;
	}
	
	/**
	 * Check whether this road can have the given endPoints as its endPoints.
	 * @param startLocation
	 * 		 The starting endPoint to check.
	 * @param endLocation
	 * 		 The ending endPoint to check
	 * @return  true
	 * 		| result == ((this.startLocation == this.getRoadCoordinates()[0] || 
				this.startLocation == this.getRoadCoordinates()[1]) && (this.endLocation == this.getRoadCoordinates()[0] 
				|| this.endLocation == this.getRoadCoordinates()[1]))
	 */
	@Raw
	@Override
	public boolean canHaveAsEndPoint(Location startLocation, Location endLocation) {
		if (this.startLocation != null || this.endLocation != null)
			return true;
		else if ((this.startLocation == this.getRoadCoordinates()[0] || 
				this.startLocation == this.getRoadCoordinates()[1]) && (this.endLocation == this.getRoadCoordinates()[0] 
				|| this.endLocation == this.getRoadCoordinates()[1]))
			return true;
		else return false;
	}
	
	/**
	 * Return the delay of this road.
	 * 		| this.delayForward
	 * @throws ModelException
	 * 		| if (!forward)
	 */
	@Basic @Raw @Override
	public float getDelay(boolean forward) throws ModelException {
		if (forward) {
			return this.delayForward;
			} else {
			  throw new ModelException();
			}
	}
	
	/**
	 * Set the delay of this road in the forward direction
	 * @param delayForward 
	 *  	The new delay for this road.
	 * @pre The given delay must be a valid delay for any road. 
	 *      | isValidDelay(delayForward,delayBackward)
	 * @post The delay of this road is equal to the given delay. 
	 * 		| new.getDelay() == delayForward
	 * @throws ModelException
	 * 		throws an exception if the direction is not forward.
	 * 		| if(!forward)
	 */
	@Raw
	public void setDelayForward(float delayForward, boolean forward) throws ModelException{
			if (forward == true)
				this.delayForward = delayForward;
			else if (! forward)
				throw new ModelException();				
		}
	
	/**
	 * checks if the road is blocked in the forward direction
	 * @param forward
	 * 		variable showing the direction of the road
	 * @return True
	 * 			| this.delayForward == Float.POSITIVE_INFINITY
	 * @throws ModelException
	 * 		if(!forward)
	 */
	@Override
	public boolean isBlocked ( boolean forward) throws ModelException{
		if (forward)
			return (this.delayForward == Float.POSITIVE_INFINITY);
		else throw new ModelException();
	}
	
	/**
	 * method to set block on the road in the direction of the flag
	 * @param flag 
	 * 		shows direction to which the road should be blocked
	 * @param forward 
	 * 		shows direction of the road
	 * @throws ModelException
	 * 		if(!forward)
	 */
	@Override
	public void changeBlockedState (Road road,boolean flag, boolean forward) throws ModelException {
		if (!forward)
			throw new ModelException();
		if (flag)
			this.block(forward);
		if (!(flag))
			this.unblock(forward);
		}
    
	/**
	 * This method returns the capacity of an alternating road
	 */
	@Override
	public Integer getCapacity(boolean forward) throws ModelException {
		if (forward) 
			return this.capacity;
		else return null;
	}
	
	/**
	 * This method checks if the capacity for an alternating road is valid.
	 */
	@Override
	public boolean isValidCapacity(int capacity, boolean forward) throws ModelException {
		return (this.getCapacity(true) % 3 == 0);
	}
	
}
