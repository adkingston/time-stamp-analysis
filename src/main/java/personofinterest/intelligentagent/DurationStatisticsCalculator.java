package personofinterest.intelligentagent;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;

import personofinterest.intelligentagent.model.TimeInterval;

final class Statistics {
	
	public static ArrayList<Duration> getDurations(ArrayList<TimeInterval> time) {
		ArrayList<Duration> durations = new ArrayList<Duration>();
		for (TimeInterval T : time) {
			durations.add(T.length());
		}
		return durations;
	}

	public static Duration getLongestDuration(ArrayList<Duration> times) {
		return Collections.max(times);
	}
	
	public static Duration getshortestDuration(ArrayList<Duration> times) {
		return Collections.min(times);
	}
	
	public static double average(ArrayList<Duration> sequence) {
		if (sequence.size() <= 1) {
			return sequence.get(0).toMillis();
		} else {
			double avg = 0;
			for (Duration x : sequence) {
				avg += x.toMillis();
			}
			avg /= sequence.size();
			return avg;
		}
	}
	
	public static double stDev(ArrayList<Duration> sequence) {	
		if (sequence.size() <= 1) {
			return 0;
		} else {
			double sum = 0;
			double avg = average(sequence);
			for (Duration x : sequence) {
				long y = x.toMillis();
				sum += Math.pow((y - avg), 2);
			}
		sum /= sequence.size();
		return Math.sqrt(sum);
		}
	}
	
	public static String formatting(LocalDate date) {
		int years = date.getYear() - 1970;
		int months = date.getMonth().getValue();
		String monthWord;
		if (months == 1) {
			monthWord = "Month";
		} else {
			monthWord = "Months";
		}
		int days = date.getDayOfMonth();
		
		if (years == 0) {
			return (months + " " + monthWord + " and " + days + " days.");
		} else if (years < 0) {
			return "0 days.";
		} else {
			return (years + " years, " + months + " " + monthWord + " and " + days + " days");
		}
	}
	
	public static String toString(Number num) {
		long numl = num.longValue();
		LocalDate timeDuration = Instant.ofEpochMilli(numl).atZone(ZoneId.systemDefault()).toLocalDate();
		return formatting(timeDuration);
	}
	
	public static String toString(Duration dur) {
		long numl = dur.toMillis();
		LocalDate timeDuration = Instant.ofEpochMilli(numl).atZone(ZoneId.systemDefault()).toLocalDate();
		return formatting(timeDuration);
	}
}