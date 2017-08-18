package personofinterest.intelligentagent;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import personofinterest.intelligentagent.model.*;

/**
 * 
 * @author AKingston
 *
 */

public class MainProgram {
	
	public static TravelHistory individualAnalysis(Person individual) {
		String border = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
		
		System.out.println(individual.toString());
		System.out.println(border);
		TravelHistory TH = individual.getTravelHistory();
		ArrayList<TimeInterval> timesAway = TH.getDurationsAway();
		ArrayList<Duration> durAway = Statistics.getDurations(timesAway);
		ArrayList<TimeInterval> timesHere = TH.getDurationsHere();
		ArrayList<Duration> durHere = Statistics.getDurations(timesHere);
		ArrayList<TimeInterval> timesUnknown = TH.getDurationsUnknown();
		ArrayList<Duration> durUnknown = Statistics.getDurations(timesUnknown);
		
		double avgAway = Statistics.average(durAway);
		double avgHere = Statistics.average(durHere);
		double avgUnknown = Statistics.average(durUnknown);
		
		double stDevAway = Statistics.stDev(durAway);
		double stDevHere = Statistics.stDev(durHere);
		double stDevUnknown = Statistics.stDev(durUnknown);
		
		System.out.println("     Away: " + timesAway.toString() );
		System.out.println("          Average time away: " + Statistics.toString(avgAway));
		System.out.println("          Standard Deviation: " + Statistics.toString(stDevAway));
		System.out.println("          Number of Entries: " + durAway.size());
		System.out.println("          Longest time away: " + Statistics.toString(Statistics.getLongestDuration(durAway)));
		System.out.println("          Shortest time away: " + Statistics.toString(Statistics.getshortestDuration(durAway)));		
		System.out.println(border);
		System.out.println("     Here: " + timesHere.toString() );
		System.out.println("          Average time Here: " + Statistics.toString(avgHere));
		System.out.println("          Standard Deviation: " + Statistics.toString(stDevHere));
		System.out.println("          Number of Entries: " + durHere.size());
		System.out.println("          Longest time away: " + Statistics.toString(Statistics.getLongestDuration(durHere)));
		System.out.println("          Shortest time away: " + Statistics.toString(Statistics.getshortestDuration(durHere)));	
		System.out.println(border);
		System.out.println("     Unknown: " + timesUnknown.toString() );
		System.out.println("          Average time Unknown: " + Statistics.toString(avgUnknown));
		System.out.println("          Standard Deviation: " + Statistics.toString(stDevUnknown));
		System.out.println("          Number of Entries: " + durUnknown.size());
		System.out.println("          Longest time away: " + Statistics.toString(Statistics.getLongestDuration(durUnknown)));
		System.out.println("          Shortest time away: " + Statistics.toString(Statistics.getshortestDuration(durUnknown)));	
		System.out.println(border);
		return TH;
	}
	public static void printData(Map<String, String> stats) {
		System.out.println("                    Average time away: " + stats.get("Average"));
		System.out.println("                    Standard Deviation: " + stats.get("Standard Deviation"));
		System.out.println("                    Number of Entries: " + stats.get("Number"));
		System.out.println("                    Longest Duration: " + stats.get("Longest"));
		System.out.println("                    Shorted Duration: " + stats.get("Shortest"));
		System.out.println("");
	}
	
	public static void collectiveAnalysis(ArrayList<TravelHistory> histories) {
		System.out.println("Collective Analysis");
		String border = "------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------";
		
		ArrayList<ArrayList<IntervalIdentifier>> collectiveAway = new ArrayList<ArrayList<IntervalIdentifier>>();
		ArrayList<ArrayList<IntervalIdentifier>> collectiveHere = new ArrayList<ArrayList<IntervalIdentifier>>();
		ArrayList<ArrayList<IntervalIdentifier>> collectiveUnknown = new ArrayList<ArrayList<IntervalIdentifier>>();
		
		for (TravelHistory H : histories) {
			collectiveAway.add(H.getIntervalDataAway());
			collectiveHere.add(H.getIntervalDataHere());
			collectiveUnknown.add(H.getIntervalDataUnknown());
		}
		
		Map<Set<String>, List<TimeInterval>> intersectionAway = Intersection.getIntersection(collectiveAway);
		Map<Set<String>, List<TimeInterval>> intersectionHere = Intersection.getIntersection(collectiveHere);
		Map<Set<String>, List<TimeInterval>> intersectionUnknown = Intersection.getIntersection(collectiveUnknown);
		
		Map<Set<String>, Map<String, String>> analysisAway = Intersection.doAnalysis(intersectionAway);
		Map<Set<String>, Map<String, String>> analysisHere = Intersection.doAnalysis(intersectionHere);
		Map<Set<String>, Map<String, String>> analysisUnknown = Intersection.doAnalysis(intersectionUnknown);
		
		System.out.println(border);
		System.out.println("     Away:");
		for (Set<String> names : intersectionAway.keySet()) {
			System.out.println("          Individuals " + names + ": " + intersectionAway.get(names).toString());
			Map<String, String> stats = analysisAway.get(names);
			printData(stats);
			}
		System.out.println(border);
		System.out.println("     Here:");
		for (Set<String> names : intersectionHere.keySet()) {
			System.out.println("          Individuals " + names + ": " + intersectionHere.get(names).toString());
			Map<String, String> stats = analysisHere.get(names);
			printData(stats);
		}
		System.out.println(border);
		System.out.println("     Unknown:");
		for (Set<String> names : intersectionUnknown.keySet()) {
			System.out.println("          Individuals " + names + ": " + intersectionUnknown.get(names).toString());
			Map<String, String> stats = analysisUnknown.get(names);
			printData(stats);
		}
		System.out.println(border);
	}
	
	public static void main(String[] args) {
		/**
		 * Program designed to perform a statistical analysis on the entry and departure time data of 
		 * individuals. Inputs are to be strings of ID's, whether they be UUIDs, first names, last names, 
		 * or passport numbers. They do not all have to be the same type. 
		 * 
		 * Analysis includes: average time away, average time in country, average time where location is unknown.
		 * Standard deviations of all of these are also included, along with a count of data points
		 * 
		 * Program also calculates mutual time away, mutual time in country, and mutual time unknown, along with 
		 * mutual average times and mutual standard deviations.
		 * 
		 * Any further statistical calculations should be added to the DurationStatisticsCalculator file. 
		 */
		
		PersonDAO dao = new PersonDAOImpl();
		ArrayList<Person> individuals = null;
		ArrayList<TravelHistory> histories = new ArrayList<TravelHistory>();
		
		if (args.length == 0) {
			System.out.println("Please provide ID's of individuals you wish to analyse, or type ALL");
		} else if (args.length > 0 && !args[0].toLowerCase().equals("all")) {
			ArrayList<String> ALargs = new ArrayList<String>(Arrays.asList(args));
			individuals = dao.getSomePersons(ALargs);
		} else if (args[0].toLowerCase().equals("all")){
			individuals = dao.getAllPersons();
		}
		
		if (individuals.size() < args.length) {
			int num = args.length - individuals.size();
			String word = " individual";
			if (num > 1) {
				word += "s";
			}
			System.out.println(num + word + " could not be found.");
			System.out.println("");
		}
		
		for (Person p : individuals) {
			histories.add(individualAnalysis(p));
		}
		collectiveAnalysis(histories);
		
	}
	
}