package roadProject;

/**
 * A subclass of superclass Road inheriting all the attributes of class Road 
 * @author Nnamdi Asouzu and Felix Bamidele
 * @version 1.0
 */
public class TwoWayRoad extends Road {
	
	/**
	 * A constructor for creating objects of the subclass TwoWayRoad
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
	public TwoWayRoad(String identification, Location endPoint1, Location endPoint2, int length, Float speedLimit,
			Float averageSpeed, int capacity) throws ModelException {
		super(identification,endPoint1 , endPoint2, length, speedLimit, averageSpeed, capacity);
		
	}
	
	/**
	 *   The method returns an array of length 1 or 2, with its elements in the same
	 *   order as the elements of the array returned by getEndPoints.
	 * @param road
	 * @return the start locations of the given road.
	 */
	@Override
	public Location [] getStartingPoint() {
		Location [] startPoint = {this.getStartLocation(), this.getEndLocation()};
		return startPoint;
	}
	
	/**
	 *   The method returns an array of length 1 or 2, with its elements in the same
	 *   order as the elements of the array returned by getEndPoints.
	 * @param road
	 * @return the end locations of the given road.
	 */
	@Override
	public  Location [] getEndingPoint () {
		Location [] endPoint = {this.getStartLocation(), this.getEndLocation()};
		return endPoint;
	}

    /**
     * This method checks if the capacity is valid for this type of road
     */
	@Override
	public boolean isValidCapacity(int capacity, boolean forward) throws ModelException {
		return (this.getCapacity(forward) % 100 == 0);
			
	}

}
