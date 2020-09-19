package roadProject;

import java.util.*;


import roadProject.ModelException;
import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;
import roadProject.Location;

/**
* @invar Each route can have its startLocation as startLocation. 
*        | canHaveAsRouteStartLocation(this.getStartLocation())
*        
* @invar The list of segments is effective 
* 		  | listRoads != null
*/

/**
 * A subclass of superclass Segments having start location, end location and segments of roads and / or segments of routes.
 * @author Nnamdi Asouzu and Felix Bamidele
 * @version 1.1
 */
public class Route extends Segments {
	
	/**
	 * Variable registering the startLocation of this Route.
	 */
	private Location startLocation ;
	
	/**
	 * Variable registering the endLocation of this route
	 */
	private Location endLocation;
	
	/**
	 * Variable showing the direction of the route
	 */
	private boolean forward = true;
	
	/**
	 * List collecting segments (either roads and/or route) that constitute this route.
	 */
	ArrayList<Object> listRoads = new ArrayList<Object>();
	
	/**
	 * Constructor for creating the objects of this class
	 * @param startLocation
	 * 		 The starting point of the route
	 * @param roads
	 * 		 The roads that make up a route
	 * @throws ModelException 
	 * 		 Exception is thrown if the variables are invalid
	 * 		 | if (!(canHaveAsRouteStartLocation(startLocation)) || (!(canHaveAsSegment(segments))) 
	 * 			|| (!(canHaveAsSegmentOneWay(segments))) || (!(canHaveAsSegmentOneWay(segments)))
	 */
	public Route(Location startLocation, Object... segments) throws ModelException{
		try {
		if (canHaveAsRouteStartLocation(startLocation));
			this.startLocation = startLocation;
			setSegment(segments);
		} catch (Throwable exc) {
			throw new ModelException();
		}		
	}
		
//Start Location
	
	@Override
	public Location getStartLocation() {
		return this.startLocation;
	}

	/**
	 * This method checks whether this route can have the given startLocation as its startLocation.
	 * @param startLocation 
	 * 		The Location to check.
	 * @return True if the coordinates are not null and between 0 and 70 degrees
	 *        | (coordinates.length == 2 && coordinates[0] >= Location.minCoordinates() 
	 *           && coordinates[0] <= Location.maxCoordinates())
					&& (coordinates[1] >= Location.minCoordinates()
					 && coordinates[1] <= Location.maxCoordinates()); 
	 * @throws ModelException if the coordinates of the start location are invalid
	 * 		| if (coordinates == null)
	 */
	@Raw
	public boolean canHaveAsRouteStartLocation(Location startLocation) throws ModelException {
		double[] coordinates = getLocationCoordinates(startLocation);
		if (coordinates != null) {
			return (coordinates.length == 2 && coordinates[0] >= Location.minCoordinates() && coordinates[0] <= Location.maxCoordinates())
					&& (coordinates[1] >= Location.minCoordinates() && coordinates[1] <= Location.maxCoordinates());
		} else
			return false; 
	}

	//Segments
	/**
	 * Method to get the end location of a route
	 * @return
	 * 		| endLocation = (lastRoad.getRoadCoordinates()[0] || endLocation = lastRoad.getRoadCoordinates()[1])
	 * 		| endLocation = ((Road) lastRoad).getStartLocation()) || endLocation = ((Road) lastRoad).getEndLocation())
	 */
	@Override
	public Location getEndLocation() {
		if (this.listRoads.size()>1) {
			int last = this.listRoads.size()-1;
			Object lastRoad = listRoads.get(last);
			Object secondLastRoad = listRoads.get(last-1);
			if (((Road) lastRoad).getRoadCoordinates()[0] == ((Road) secondLastRoad).getRoadCoordinates()[0] ||
					((Road) lastRoad).getRoadCoordinates()[0] == ((Road) secondLastRoad).getRoadCoordinates()[1])
				endLocation = ((Road) lastRoad).getRoadCoordinates()[1];
			else endLocation = ((Road) lastRoad).getRoadCoordinates()[0];
				return endLocation;
			}
			else if (this.listRoads.size()==1) {
				Object lastRoad = (Road) listRoads.get(0);
				if (this.startLocation == ((Road) lastRoad).getEndLocation())
					return ((Road) lastRoad).getStartLocation();
				else return ((Road) lastRoad).getEndLocation();
			}
			else	
				return this.startLocation;
	}

	/**
	 * This method appends the specified road to the end of this list of roads.
	 * @param road 
	 * 			The type of object in the segment of a route
	 */
	public void addSegments(Object... segment) throws ModelException{		
		for (Object road : segment) {
				if (road instanceof TwoWayRoad && (((TwoWayRoad) road).getRoadCoordinates()[0] == this.getEndLocation()
						|| 	((TwoWayRoad) road).getRoadCoordinates()[1] == this.getEndLocation()))
						this.listRoads.add(road);
				else if (road instanceof OneWayRoad && ((OneWayRoad)road).getRoadCoordinates()[0] == this.getEndLocation())
						this.listRoads.add(road);
				else if (road instanceof Route &&((Route) road).getEndLocation() == this.getEndLocation()) {
						this.listRoads.add(road);
						Location[] locationList1 = this.getAllVisitedLocations();
						Location[] locationList2 = ((Route) road).getAllVisitedLocations();	
						if (locationList1 == locationList2)
							throw new IllegalStateException();
						}		
		else throw new ModelException();	
		}
	}
	
	
	/**
	 * This method replaces the element at the specified position in this list with the specified element.
	 * @param index 
	 * 			the position of the road in the list
	 * @param listRoads
	 * 			 the list of roads and routes comprising segment of a route
	 * @throws IndexOutOfBoundsModelException if the index is out of range 
	 * 		| (index < 0 || index >= routeSegments.size())
	 */
	public void setAsSegment(int index, Road road) throws ModelException {
		if (index>0 || index <= listRoads.size())  
			listRoads.set(index, road);
		if (index < 0 || index >= listRoads.size());
			throw new ModelException();
	}

	/**
	 * This method removes the element at the specified position in this list. Shifts any
	 * subsequent elements to the left (subtracts one from their indices).
	 * @param index
	 * 		 the index of the element to be removed
	 * @throws IndexOutOfBoundsModelException if the index is out of range 
	 * 			| (index < 0 || index >= routeSegments.size())
	 */
	public void removeSegments(int index) throws ModelException {
		if (index < 0 || index >= listRoads.size()) {
		throw new ModelException();
		} if (index>0 || index <= listRoads.size()) {
			try {
				listRoads.remove(index);
				assert (this.isValidRoute());
			} catch (Throwable exc) {
				throw new IllegalStateException();
			}
		}
	}
	
	/**
	 * Method that checks if segments of the route are valid
	 * @return true
	 * 		| result == (startLocation = ((Road) road).getRoadCoordinates()[1] || 
	 * 			startLocation = ((Road) road).getRoadCoordinates()[0])		
	 */
	public boolean isValidRoute() {
		Location startLocation = this.getStartLocation();
		if (this.listRoads.size() > 0){
			for (Object road : listRoads) {
				if (((Road) road).isTerminatedRoad() == false)
					if (startLocation == ((Road) road).getRoadCoordinates()[0])
						startLocation = ((Road) road).getRoadCoordinates()[1];
					else if (startLocation == ((Road) road).getRoadCoordinates()[1] )
						startLocation = ((Road) road).getRoadCoordinates()[0];
				else return false;
			} return true;
		} 
		if (this.listRoads.size() == 0)
			return true;
		return false;	
	}
	
	/**
	 * This method returns all the segments of this Route.
	 * 		| new ArrayList<Object>(listRoads)
	 */
	@Basic
	@Raw
	public ArrayList<Object> getAllSegment() throws ModelException {
			return new ArrayList<Object>(listRoads);
	}
	
	/**
	 * Method to get all the locations in a route
	 * @return
	 * 		| for (Object road: this.listRoads)
	 * 		| if (road instanceof Route); locationsList.addAll(Arrays.asList(((Route) road).getAllVisitedLocations()));
	 * 		| locationsList.add(((Road) road).getStartLocation());
	 * 		| locationsList.add(((Road) road).getEndLocation());
	 * 		| locationsList.toArray(locationsArray);
	 */
	public Location[] getAllVisitedLocations() {
		Location startLocation = this.getStartLocation();
		int size = this.listRoads.size();
		Location[] locationsArray = new Location[size];
		ArrayList<Location> locationsList = new ArrayList<Location>();
		locationsList.add(this.getStartLocation());
		for (Object road: this.listRoads) {
			if (road instanceof Route) {
				locationsList.addAll(Arrays.asList(((Route) road).getAllVisitedLocations()));
				locationsList.remove(1);
			}
			else if (startLocation == ((Road) road).getEndLocation()) {
				locationsList.add(((Road) road).getStartLocation());
				startLocation = ((Road) road).getStartLocation();
			} else if (startLocation == ((Road) road).getStartLocation()) {
				locationsList.add(((Road) road).getEndLocation());
				startLocation = ((Road) road).getEndLocation();
			}	
		}
		return locationsList.toArray(locationsArray);
	} 
	
	
	/**
	 * Check whether the given roads are valid segments for any Route.
	 * 
	 * @param segments 
	 * 			The segments of route.
	 * @return true
	 * 		| for (Object road : segments)
	 * 		| if ((road instanceof OneWayRoad)) startLocation = ((OneWayRoad) road).getRoadCoordinates()[1]
	 * 		| if ((road instanceof TwoWayRoad)) startLocation = ((TwoWayRoad) road).getRoadCoordinates()[1] || startLocation = ((TwoWayRoad) road).getRoadCoordinates()[0]
	 * 		| if ((road instanceof AlternatingRoad)) startLocation = ((AlternatingRoad) road).getRoadCoordinates()[1] || startLocation = ((AlternatingRoad) road).getRoadCoordinates()[0]
	 * 		| if (road instanceof Route) startLocation = ((Route)road).getEndLocation()
	 * @throws ModelException 
	 * 		| if (!canHaveAsSegmentOneWay(Object...segments))
	 */
	public boolean canHaveAsSeg(Object...segments) throws ModelException {
		Location startLocation = this.getStartLocation(); 
		for (Object road : segments) {
			if ((road instanceof OneWayRoad)) {
				if (startLocation == ((OneWayRoad) road).getRoadCoordinates()[0]) 
					startLocation = ((OneWayRoad) road).getRoadCoordinates()[1];
				else return false;
			}		
			else if ((road instanceof TwoWayRoad)) {
				if (startLocation == ((TwoWayRoad) road).getRoadCoordinates()[0]) 
					startLocation = ((TwoWayRoad) road).getRoadCoordinates()[1];
				else if (startLocation == ((TwoWayRoad) road).getRoadCoordinates()[1])
					startLocation = ((TwoWayRoad) road).getRoadCoordinates()[0];
				else return false;
			} 
			else if (road instanceof Route) {
				if (startLocation == ((Route)road).getStartLocation())
					startLocation = ((Route)road).getEndLocation();
				else return false;
			}
			else if ((road instanceof AlternatingRoad)) {
				if ( ((AlternatingRoad) road).forward && startLocation == ((AlternatingRoad) road).getRoadCoordinates()[0])
					startLocation = ((AlternatingRoad) road).getRoadCoordinates()[1];
				else if (!((AlternatingRoad) road).forward  && startLocation == ((AlternatingRoad) road).getRoadCoordinates()[1])
					startLocation = ((AlternatingRoad) road).getRoadCoordinates()[0];
			}
		} return true;
	}
	
	
	/**
	 * Set the segments of this Route to the given segments.
	 * @param segments
	 * 		 The new segments for this Route.
	 * @post The segments of this new Route is equal to the given segments. 
	 *       | new.getSegment() == segments
	 * @throws ModelModelException 
	 * 		 The given segments is not a valid segments for any Route.
	 * 		 | (! canHaveAsSeg(segments))
	 */
	@Raw
	public void setSegment(Object...segments) throws ModelException {
		if (segments.length>0 && segments != null ) 
			for (Object road: segments )
					if ((canHaveAsSeg(segments)))
							listRoads.add(road);
					else throw new ModelException();
	}
							
	/**
	 * Method to get the total length of the route
	 * @param road 
	 * 		The road in the route
	 * @return the total length of the road
	 * 			| road.getLength();
	 */
	public int routeLength(Object... segments) {
		int total = 0;
		for (Object road: listRoads) 
			total += ((Road) road).getLength();
		return total;
	}

	/**
	 * Method to check if the route is blocked
	 * @param segments  
	 * 		The roads in the route
	 * @return true
	 * 		| road.isBlocked(forward)
	 * @throws ModelException 
	 */
	public boolean routeBlocked(Object...segments) throws ModelException {
		Location startLocation = this.getStartLocation();
		for (Object road: listRoads) {
			if (road instanceof TwoWayRoad) {
				if (startLocation == ((TwoWayRoad)road).getRoadCoordinates()[0]) {
					if (((TwoWayRoad)road).blocked[0])
						return true;
				} else {
					if (((TwoWayRoad)road).blocked[1])
						return true;
				}
			}
			else if (road instanceof OneWayRoad) {
				if (startLocation == ((OneWayRoad)road).getRoadCoordinates()[0]) {
					if (((OneWayRoad)road).blocked[0])
						return true;
				}
			} else if (road instanceof AlternatingRoad) {
				if (forward) {
					if ( ((AlternatingRoad)road).blocked[0])
						return true;
				} else {
					if ( ((AlternatingRoad)road).blocked[1])
						return true;
				}
			}
			else if (road instanceof Route) {
				if (routeBlocked(((Route) road).listRoads))
					return true;
			}
		}		
		return false;
		}		
	}
	


