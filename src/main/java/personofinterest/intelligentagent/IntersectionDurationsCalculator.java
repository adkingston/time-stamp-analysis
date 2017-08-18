package personofinterest.intelligentagent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import personofinterest.intelligentagent.model.IntervalIdentifier;
import personofinterest.intelligentagent.model.TimeInterval;

final class Intersection{
	
	public static ArrayList<IntervalIdentifier> merge(ArrayList<ArrayList<IntervalIdentifier>> lst) {
		// put all elements of the lists into a single list and sort. (shorter code for this?)
		ArrayList<IntervalIdentifier> collective = new ArrayList<IntervalIdentifier>();
		for (ArrayList<IntervalIdentifier> P : lst) {
			collective.addAll(P);
		}
		Collections.sort(collective);
		return collective;
	}
	
	public static Map<Set<String>, List<TimeInterval>> intersectMerged(ArrayList<IntervalIdentifier> lst) {
		// Declare necessary lists: result list, list of names
		// Output looks like: {[name1a, name2a,...] = [common times], [name1b name2b,...] = [common times], ... }
		Map<Set<String>, List<TimeInterval>> result = new HashMap<Set<String>, List<TimeInterval>>();
		Set<String> names = new HashSet<String>();
		
		// iterate through merged person-time-indicator object 
		for (int i=0; i<lst.size(); i++) {
			IntervalIdentifier k = lst.get(i);
			if (k.IDENTIFIER.equals("Start")) {
				names.add(k.PERSON.getFullName());
				
			// otherwise we have reached the end of an interval. Check if any other intervals have started 
				// by looking at the number of names in the list. 
				// Also, if there's only one name in the names list, check if the person belonging to this 
				// interval identifier is not already on the list. If it is, we ignore this particular interval
				// as it does not intersect with anyone else's.
			} else if (names.size() == 0 || (names.size()==1 && names.contains(k.PERSON.getFullName()) ))  {
				names = new HashSet<String>();
				
			// otherwise we have an intersection. 
			} else {
				names.add(k.PERSON.getFullName());
				// declare times and time interval. End time is the time of this interval, and the 
				// start time is the time of the previous interval
				LocalDateTime intersectStart = lst.get(i-1).TIME;
				LocalDateTime intersectEnd = k.TIME;
				TimeInterval intersection = new TimeInterval(intersectStart, intersectEnd);
				
				// check if this collection of people already intersected before 
				// (i.e., the key already appears in the map). 
				if (result.containsKey(names)) {
					result.get(names).add(intersection);
				} else {
					List<TimeInterval> times = new ArrayList<TimeInterval>(Arrays.asList(intersection));
					result.put(names, times);
				}
				
				// reset list of names
				names = new HashSet<String>();
				}
			}
		return result;
	}
	
	public static Map<Set<String>, List<TimeInterval>> getIntersection(ArrayList<ArrayList<IntervalIdentifier>> lst) {
		// One function to perform operations of above two functions 
		// map keys are sets of names which point to the intervals of time where they intersect
		// looks like: {[name 1, name 2,...] = [common times], [name1 name2,...] = [common times], ... }
		ArrayList<IntervalIdentifier> merged = merge(lst);
		return intersectMerged(merged);
	}
	
	public static Map<Set<String>, ArrayList<Duration>> getDurations(Map<Set<String>, List<TimeInterval>> intersection) { 
		// map keys are names which point to durations from above list<TimeInterval>
		Map<Set<String>, ArrayList<Duration>> intersectedDurations = new HashMap<Set<String>, ArrayList<Duration>>();
		for (Set<String> names : intersection.keySet()) {
			ArrayList<Duration> D = new ArrayList<Duration>();
			intersectedDurations.putIfAbsent(names, D); 
			for (TimeInterval t : intersection.get(names)) {
				intersectedDurations.get(names).add(t.length());
			}
		}
		return intersectedDurations;
	}
	
	public static Map<Set<String>, Map<String, String>> doAnalysis(Map<Set<String>, List<TimeInterval>> intersection) {
		// map keys are names which point to maps. These maps connect calculation type with the calculated value
		// looks like {[names...]={average=a, stDev=b, count=c, max=[time interval], min=[time interval]}, ... }
		Map<Set<String>, ArrayList<Duration>> durations = getDurations(intersection);
		Map<Set<String>, Map<String, String>> analysis = new HashMap<Set<String>, Map<String, String>>();
		for (Set<String> names : durations.keySet()) {
			Map<String, String> figures = new HashMap<String, String>();
			double a = Statistics.average(durations.get(names));
			double sD = Statistics.stDev(durations.get(names));
			int count = durations.get(names).size();
			
			figures.put("Average", Statistics.toString(a));
			figures.put("Standard Deviation", Statistics.toString(sD));
			figures.put("Number", Integer.toString(count));
		
			Collections.sort(durations.get(names));
			int maxInd = durations.get(names).size();
			
			figures.put("Longest", Statistics.toString(durations.get(names).get(maxInd-1)));
			figures.put("Shortest", Statistics.toString(durations.get(names).get(0)));
			
			analysis.put(names, figures);
		}
		return analysis;
	}
	// THERES A METHOD TO MY MADNESS, OK??? 
}
		

