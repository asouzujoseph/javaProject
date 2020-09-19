package roadProject;

import java.util.*;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import roadProject.ModelException;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * 
 * @invar The joinRoad of each location must be a valid joinRoad for any
 *        location. | isValidjoinRoad(getjoinRoad())
 *
 * @invar Each location can have its coordinates as coordinates. |
 *        canHaveAsCoordinates(this.getCoordinates())
 *  
 * @invar The address of each location must be a valid address for any location.
 *        | isValidAddress(getAddress())
 */

/**
 * A class of locations involving address, coordinates and adjoining roads.
 * @author Nnamdi Asouzu and Felix Bamidele
 * @version 1.0
 */
public class Location implements Facade{
	
	/**
	 * Variable registering the coordinates of this location.
	 */
	private  double[] coordinates = new double[2];

	/**
	 * Variable registering the address of this location.
	 */
	private String address;

	/**
	 * Variable registering whether this location is terminated.
	 */
	private  boolean isTerminated ;
	
	/**
	 * A HashMap collecting different non-terminated locations and their distances from the specified location
	 */
	private HashMap<Location, Double> distances = new HashMap<Location, Double>();
	
	/**
	 * A set collecting all the locations near a specified location
	 */
	private  Set<Location> nearbyLocations = new HashSet<Location>();
	
	/**
	 * A Set collecting all the adjoining roads.
	 * @invar The set of adjoined roads is effective | joinedRoad != null
	 */
	private final Set<Road> joinedRoad = new HashSet<Road>();

	/**
	 * A set collecting all non-terminated locations
	 */
	private final static Set<Location> nonTerminated = new HashSet<Location>();
	
	/**
	 * Constructor method for this class
	 * @param coordinates 
	 * 		The coordinates of the location
	 * @param address 
	 * 		The address of the location
	 * @throws ModelException
	 * 		| if (!(canHaveAsCoordinates(coordinates)) || (!isValidAddress(address)))
	 */
	public Location (double[] coordinates, String address) throws ModelException {
		try {
		assert this.canHaveAsCoordinates(coordinates);
		this.coordinates = coordinates.clone();
		setAddress(address);
		nonTerminated.add(this);
		} catch (Throwable exc) {
			throw new ModelException();
		}
		
	}
	
// Coordinates
	/**
	 * Return the coordinates of this location.
	 * 		| this.coordinates
	 */
	@Basic
	@Raw
	@Immutable
	public double[] getCoordinates() {
		return this.coordinates.clone();
	}

	/**
	 * Return the minimum valid coordinates of this location.
	 * 		| 0.00
	 */
	public static double minCoordinates() {
		return 0.00;
	}

	/**
	 * Return the maximum coordinates of this location.
	 * 		| 70.00
	 */
	public static double maxCoordinates() {
		return 70.00;
	}

	/**
	 * Check whether this location can have the given coordinates as its
	 * coordinates.
	 * 
	 * @param coordinates 
	 * 		The coordinates to check.
	 * @return  true
	 * 		| result == (coordinates.length == 2 && coordinates[0] >= minCoordinates() && coordinates[0] <= maxCoordinates())
					&& (coordinates[1] >= minCoordinates() && coordinates[1] <= maxCoordinates())
	 */
	@Raw
	public boolean canHaveAsCoordinates(double[] coordinates) {
		if (coordinates != null) {
			return (coordinates.length == 2 && coordinates[0] >= minCoordinates() && coordinates[0] <= maxCoordinates())
					&& (coordinates[1] >= minCoordinates() && coordinates[1] <= maxCoordinates());
		} else
			return false;
	}

	/**
	 * Method to get the distance of the location from the specified point
	 * @return the distance
	 * 			| this.distance
	 */
	public double getDistance(Location location1, Location location2) {
		double y1 = location1.coordinates[0];
		double x1 = location1.coordinates[1];
		double y2 = location2.coordinates[0];
		double x2 = location2.coordinates[1];
		double ac = Math.abs(y2 - y1);
	    double cb = Math.abs(x2 - x1);
		return Math.hypot(ac, cb);
	}
	
	/**
	 * A method to assign key and value to the HashMap. 
	 */
	public void findDistances() {
		for (Location location : nonTerminated) {
			distances.put(location, getDistance(this, location));
		}
	}
	
	/**
	 * Method to return a set of all nearby locations to the specified location
	 * @param location
	 * @param distance
	 * @return a new set of the nearby locations
	 * 		| new HashSet<Location>(nearbyLocations)
	 * @throws ModelException 
	 * 			| if (distance.isNaN() || distance<0)
	 */
	public Set<Location> locationsWithinDistance (Location location, Double distance) throws ModelException {
			findDistances();
			for (Double value : distances.values()) {
				if (value<=distance)
					for (Entry<Location, Double> entry : distances.entrySet()) {
						if (entry.getValue().equals(value)) {
							nearbyLocations.add((entry.getKey()));
		            }
				}	
		}
			if (distance.isNaN() || distance<0)
				throw new ModelException();
		return new HashSet<Location>(nearbyLocations);
		
	}
	
//Address
	/**
	 * Return the address of this location.
	 * 		| this.address
	 */
	@Basic
	@Raw
	public  String getAddress() {
		return this.address;
	}

	/**
	 * Method to check if the address is valid
	 * @param address 
	 * 		address to check
	 * @return true
	 *    | (address.length() > 2) && (Character.isUpperCase(address.charAt(0))
	 *    && (Character.isLetter(address.charAt(index))) && (Character.isDigit(address.charAt(index)))
					&& (address.charAt(index) = ' ') && (address.charAt(index) = ','))
	 */
	public  boolean isValidAddress(String address) {
		try {
		if (address.length() < 2)
			return false;
		if (!Character.isUpperCase(address.charAt(0)))
			return false;
		for (int index = 1; index < address.length(); index++)
			if ((!Character.isLetter(address.charAt(index))) && (!Character.isDigit(address.charAt(index)))
					&& (address.charAt(index) != ' ') && (address.charAt(index) != ','))
				return false;
		}
		catch (Exception exp) {
			return false;
		}
	return true;
	}

	/**
	 * Set the address of this location to the given address.
	 * @param address 
	 * 			The new address for this location.
	 * @post If the given address is a valid address for any location, the address
	 *       of this new location is equal to the given address. 
	 *       | if (isValidAddress(address)) | then new.getAddress() == address
	 *       | if (!isValidAddress(address)) | then new.getAddress() == "NULL"
	 */
	@Raw
	public void setAddress(String address) throws ModelException {
		if (isValidAddress(address) == true) {
			this.address = address;
		}else this.address = "NULL";
	}
	
//Adjoining roads
	/**
	 * Check whether the given road is a valid Adjoined Road for any location.
	 * @param road 
	 * 		The road to check.
	 * @return  true
	 * 		|  (coordinates == Road.getStartLocation()) || (coordinates == Road.getEndLocation())
	 */
	 boolean canHaveAsJoinedRoad(Road road) {
		if ((this.coordinates != null) && road.isTerminatedRoad() == false)
			return ((this == road.getStartLocation()) || (this == road.getEndLocation()));
		 return false;
	}
	
	/**
	 * Method to add roads as adjoining road to a location
	 * @param road 
	 * 		the road to add as an adjoining road
	 */
	void addAsJoinedRoad(Road road) {
		assert (canHaveAsJoinedRoad(road));
			this.joinedRoad.add(road);
	}
	
	/**
	 * Method to check if a location has a given road as an adjoining road
	 * @param road 
	 * 		the road to check if it is an adjoining road.
	 * @return true
	 * 		| this.joinedRoad.contains(road)
	 */
	public boolean hasAsJoinedRoad(Road road) throws ModelException  {
		if (canHaveAsJoinedRoad(road) && road.isTerminated == false) 
			return this.joinedRoad.contains(road);
		else return false;
	}

	/**
	 * Method to remove an adjoined road
	 * @param road
	 * 		the road to check if it is an adjoined road
	 * @throws ModelException
	 * 		| if (!(hasAsJoinedRoad(road)))
	 */
	 void removeAsJoinedRoad(Road road)  {
		 assert road.isTerminatedRoad();
			this.joinedRoad.remove(road);	
	}

	/**
	 * Method to get all the adjoined roads for a location
	 * @return a new set of all the adjoined roads
	 * 		| new HashSet<Road>(location.joinedRoad)
	 */
	public Set<Road> getAllJoinedRoad() throws ModelException {
			return new HashSet<Road>(this.joinedRoad);	
	}
	
	/**
	 * Method to return a set of all non-terminated locations
	 * @return 
	 * 		| new HashSet<Location>(nonTerminated)
	 * @throws ModelException
	 * 		| if ((this.isTerminated)==true)
	 */
	public static Set<Location> getLocations () throws ModelException {
		return new HashSet<Location>(nonTerminated); 
	}
	
	/**
	 * Method to set the joinRoad of this location to the given joinRoad.
	 * @param road
	 * 		 The new joinRoad for this location.
	 * @post The joinRoad of this new location is equal to the given joinRoad. 
	 *       | new.getjoinRoad() == joinRoad
	 * @throws ModelModelModelException The given joinRoad is not a valid joinRoad for any location. 
	 *       | ! canHaveAsJoinedRoad(getJoinRoad())
	 */
	@Raw
	public void setJoinedRoad(Road road) throws ModelException {
		if (!canHaveAsJoinedRoad(road)) {
			throw new ModelException("road cannot be adjoined");
		} else if(canHaveAsJoinedRoad(road)) {
			this.joinedRoad.add(road);
			}else throw new ModelException();
	}

	/**
	 * this method destroys the specified location
	 * @param coordinates
	 */
	public void terminateLocation() throws ModelException {
		this.isTerminated = true;
		this.joinedRoad.clear();
		for (Road road : this.joinedRoad)
			road.terminateRoad();
		nonTerminated.remove(this);
	}

	/**
	 * this method checks if the specified location is destroyed
	 * @return true
	 * 			| this.isTerminated
	 */
	public  boolean isTerminatedLocation() {
		return this.isTerminated;
	}

	
	
}
