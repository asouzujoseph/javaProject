package roadProject;

/**
 * A class having start location and end location.
 * @author Nnamdi Asouzu and Felix Bamidele
 * @version 1.0
 */
public abstract class Segments implements Facade {
	
	/**
	 * Variable showing the start location of either route or road in segment
	 */
	protected Location startLocation;
	
	/**
	 * Variable showing the end location of either route or road in segment
	 */
	protected Location endLocation;
	
	/**
	 * an abstract method to get the start location
	 */
	public abstract Location getStartLocation();
	
	/**
	 * an abstract method to get the end location
	 */
	public abstract Location getEndLocation();
	
}
	
