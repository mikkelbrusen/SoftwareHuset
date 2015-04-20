package dtu.se1.softwarehuset;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Activity {
	private String title;
	private int expectedWorkHours;
	private Calendar startDate;
	private Calendar endDate;
	private Map<Developer, Integer> registeredHours;

	public Activity(String title, int expectedWorkHours, Calendar startDate,
			Calendar endDate) {

		this.setTitle(title);
		this.setExpectedWorkHours(expectedWorkHours);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		registeredHours = new HashMap<Developer, Integer>();
	}
	
	public void registerHours(Developer d, int hours) {
		if (!registeredHours.containsKey(d)) {
			registeredHours.put(d, hours);
			d.registerActivity(this);
		} else {
			registeredHours.put(d, registeredHours.get(d) + hours);
		}
	}

	public int getRegisteredHours() {
		int hours = 0;
		for (int i: registeredHours.values()) {
			hours += i;
		}
		return hours;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getExpectedWorkHours() {
		return expectedWorkHours;
	}

	public void setExpectedWorkHours(int expectedWorkHours) {
		this.expectedWorkHours = expectedWorkHours;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}

}
