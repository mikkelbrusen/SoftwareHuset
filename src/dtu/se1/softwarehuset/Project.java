package dtu.se1.softwarehuset;

import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;

public class Project {
	private static int index = 1;
	private int id;
	private String title;
	private Calendar startDate; 
	private List<Activity> activityList;
	private Master m;
	
	public Project(Master m, String title, Calendar startDate) {
		this.id += Project.index++;
		activityList = new ArrayList<Activity>();
		
		this.setTitle(title);
		this.setStartDate(startDate);		
		this.m = m;
		createActivity("Project Leader", 0, startDate, null);
	}
	
	public Activity createActivity(String title, int expectedWorkHours, Calendar startDate, Calendar endDate) {
		activityList.add(new Activity(m, this, title, expectedWorkHours, startDate, endDate));
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
	
	public Developer getProjectLeader() {
		if (activityList.get(0).getStaff().size() == 0){
			return null;
		}
		return activityList.get(0).getStaff().get(0);
	}
	
	public void becomeProjectLeader() throws ActivityStaffException  {
		Activity pLeader = getActivities().get(0);
		if (pLeader.getStaff().size() != 0){
			throw new ActivityStaffException("Project leader is already assigned");
		}
		pLeader.addStaff(m.getDevById(m.getLoginId()));
	}

	public int getId() {
		return id;
	}

	public Activity getActivityById(int activityId) {
		for (Activity a: activityList){
			if (a.getId() == activityId){
				return a;
			}
		}
		return null;
	}

}
