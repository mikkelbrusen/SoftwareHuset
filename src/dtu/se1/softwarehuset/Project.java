package dtu.se1.softwarehuset;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class Project {
	private String title;
	private Calendar startDate; 
	private List<Activity> activityList;
	private Master m;
	
	public Project(Master m, String title, Calendar startDate) {
		activityList = new ArrayList<Activity>();
		
		this.setTitle(title);
		this.setStartDate(startDate);		
		this.m = m;
		createActivity("Project Leader", 0, startDate, null);
	}
	
	public Activity createActivity(String title, int expectedWorkHours, Calendar startDate, Calendar endDate) {
		activityList.add(new Activity(title, expectedWorkHours, startDate, endDate));
		return activityList.get(activityList.size()-1);
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

	public void becomeProjectLeader() throws ActivityStaffException  {
		Activity pLeader = getActivities().get(0);
		int i = m.getLoginId();
		if (pLeader.getStaff().size() != 0){
			throw new ActivityStaffException("Project leader is already assigned");
		}
		pLeader.addStaff(m.getDevById(m.getLoginId()));
	}

}
