package dtu.se1.softwarehuset;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Project {
	private String title;
	private Calendar startDate; 
	private List<Activity> activityList;
	
	
	public Project(String title, Calendar startDate) {
		activityList = new ArrayList<Activity>();
		
		this.setTitle(title);
		this.setStartDate(startDate);		
		
		createActivity("Project Leader", 0, startDate, null);
	}
	
	public void createActivity(String title, int expectedWorkHours, Calendar startDate, Calendar endDate) {
		activityList.add(new Activity(title, expectedWorkHours, startDate, endDate));
	}
	
	public List<Activity> getActivities() {
		return activityList;
	}

	public Calendar getStartDate() {
		return startDate;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
