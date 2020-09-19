package roadProject;

import java.util.Arrays;

import roadProject.Location;
import roadProject.ModelException;
import roadProject.Route;

/**
 * A class containing the main method.
 * @author Nnamdi Asouzu and Felix Bamidele
 * @version 1.0
 */
public class MainProgramPart3 {

	public static void main(String[] args) {
		try {
			Location one = new Location (new double [] {49.97,60.01}, "Russia");
			Location two = new Location (new double [] {49.99,59.94}, "Ayteke Bi District Kazakhstan");
			Location three = new Location (new double [] {50.00,59.86}, "Mangystau Region Kazakhstan");
			Location four = new Location (new double [] {50.01,59.82}, "Komi Republic Russia ");
			Road road1 = new OneWayRoad ("L63", one, two, 10000, 20.5f, 18.6f, 0);
			Road road3  = new AlternatingRoad ("G87", two, three, 12000, 21.7f, 20.5f, 0);
			Road countryRoad = new TwoWayRoad ("E7", three, four,7500, 10.8f, 8.0f, 0 );
			Road motorWay = new TwoWayRoad("N40", three, four, 15000, 30.5f, 30.0f, 0);
			Route route = new Route (one, road1, road3, countryRoad, motorWay);
			
			//PROPERTIES OF THE COUNTRY ROAD//
			double [] startLocationCountryRoad = new double [] {49.99,59.94};
			double [] endLocationCountryRoad = new double [] {50.00,59.86};
			System.out.println("  ");
			System.out.println("PROPERTIES OF THE COUNTRY ROAD");
			System.out.println("the identification is " +countryRoad.getIdentification());
			System.out.println("the start Location is "+Arrays.toString(startLocationCountryRoad));
			System.out.println("the end location is "+Arrays.toString(endLocationCountryRoad));
			System.out.println("the length is "+countryRoad.getLength()+" m");
			System.out.println("the speedlimit is "+ countryRoad.getSpeedLimit()+" m/s");
			System.out.println("the average speed is "+countryRoad.getAverageSpeed()+" m/s");
			System.out.println("the current delay in the forward direction  is "+countryRoad.getDelay(true)+" seconds");
			System.out.println("the current delay in the backward direction  is "+countryRoad.getDelay(false)+" seconds");
			System.out.println("Is the country road blocked in the forward direction? "+countryRoad.isBlocked(true));
			System.out.println("Is the country road blocked in the backward direction? "+countryRoad.isBlocked(false));	
			System.out.println("The time to travel the country road in the forward direction is "+countryRoad.cummulativeTime()+" seconds");
			System.out.println(" ");
			System.out.println(" ");
			
			//PROPERTIES OF THE MOTORWAY
			double [] startLocationMotorway = new double [] {49.99,59.94};
			double [] endLocationMotorway = new double [] {50.00,59.86};
			System.out.println("PROPERTIES OF THE MOTORWAY");
			System.out.println("the identification is " +motorWay.getIdentification());
			System.out.println("the start Location is "+Arrays.toString(startLocationMotorway));
			System.out.println("the end location is "+Arrays.toString(endLocationMotorway));
			System.out.println("the length  is "+motorWay.getLength()+" m");
			System.out.println("the speedlimit  is "+ motorWay.getSpeedLimit()+" m/s");
			System.out.println("the average speed  is "+motorWay.getAverageSpeed()+" m/s");
			System.out.println("the current delay in the forward direction  is "+motorWay.getDelay(true)+" seconds");
			System.out.println("the current delay in the backward direction  is "+motorWay.getDelay(false)+" seconds");
			System.out.println("Is the motor way blocked in the forward direction? "+motorWay.isBlocked(true));
			System.out.println("Is the motor way blocked in the backward direction? "+motorWay.isBlocked(false));	
			System.out.println("The time to travel the motor way in the forward direction is "+motorWay.cummulativeTime()+" seconds");
			System.out.println(" ");
			System.out.println(" ");
			
			// Route
			motorWay.setDelayForward( Float.POSITIVE_INFINITY, true);
			System.out.println("ROUTE");
			System.out.println("Length of route "+(route.routeLength(road1,motorWay,countryRoad,road3))+" m");
			System.out.println("All the visited locations are "+Arrays.toString(route.getAllVisitedLocations()));
			System.out.println("Is the route traversable?  "+route.routeBlocked(route));
			System.out.println("The time of travel on the Route after blocking the Motorway in the forward direction is "+(road1.cummulativeTime()+countryRoad.cummulativeTime()+road3.cummulativeTime()+motorWay.cummulativeTime())+" seconds");
			System.out.println(" ");
	
		} catch (ModelException e) {
			e.printStackTrace();
			System.out.println("oops");
		
		}
		
		}

}
