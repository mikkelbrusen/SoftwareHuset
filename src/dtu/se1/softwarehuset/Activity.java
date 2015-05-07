package dtu.se1.softwarehuset;

import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity {
	private static int index = 1;
	private int id;
	
	private String title;
	private int expectedWorkHours;
	private Calendar startDate;
	private Calendar endDate;
	private Map<Developer, Integer> registeredHours;
	private List<Developer> staff;
	private Project p;
	private Master m;

	public Activity(Master m, Project p, String title, int expectedWorkHours, Calendar startDate,
			Calendar endDate) {
		
		if (expectedWorkHours < 0) {
			throw new InvalidParameterException("Expexted work hours must be non-negative");
		}

		this.id += Activity.index++;

		this.setTitle(title);
		this.setExpectedWorkHours(expectedWorkHours);
		this.setStartDate(startDate);
		this.setEndDate(endDate);
		this.m = m;
		this.p = p;
		registeredHours = new HashMap<Developer, Integer>();
		staff = new ArrayList<Developer>();
	}
	
	public void registerHours(Developer d, int hours) throws AccessDeniedException {
		if (!staff.contains(d)){
			throw new AccessDeniedException("The developer is not assigned to the activity");
		}
		
		if (!registeredHours.containsKey(d)) {
			registeredHours.put(d, hours);
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
	
	public int getDevRegisteredHours(Developer d) {
		return registeredHours.get(d);
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

	public void addStaff(Developer d) throws ActivityStaffException {
		if (!d.isAvailable()){
			throw new ActivityStaffException("The developer is unavailable");
		} else if (alreadyAssigned(d)){
			throw new ActivityStaffException("Developer is already assinged");
		} else if (projectLeaderIsEmpty()) {
			staff.add(d);
		} else if (!isProjectLeader()) {
			throw new ActivityStaffException("You are not the leader of this project");
		} else {
			staff.add(d);
			d.registerActivity(this);
		}
	}
	
	protected void addStaffRequest(Developer d) {
		staff.add(d);
	}

	private boolean alreadyAssigned(Developer d) {
		return staff.contains(d);
	}

	private boolean isProjectLeader() {
		return m.getDevById(m.getLoginId()).equals(p.getProjectLeader());
	}

	private boolean projectLeaderIsEmpty() {
		return title.equals("Project Leader") && staff.size() == 0;
	}

	public List<Developer> getStaff() {
		return staff;
	}
	
	public Project getProject() {
		return p;
	}

	public int getId() {
		return id;
	}

	public void requestAssistance(Developer reqD) throws ActivityStaffException {
		Developer d = m.getLogin();
		
		if (!isOnStaff(d))
			throw new ActivityStaffException("You need to be assigned to the activity to request assistance.");
			
		if (isOnStaff(reqD))
			throw new ActivityStaffException("The chosen developer is already assigned to this activity.");
		
		if (!reqD.isAvailable()) 
			throw new ActivityStaffException("The chosen developer is not available");
		
		if (alreadyRequested(reqD)) 
			throw new ActivityStaffException("The chosen developer has already been requested");
		
		reqD.addRequest(this, d);
		
	}

	private boolean alreadyRequested(Developer reqD) {
		return reqD.getRequests().containsKey(this);
	}
	
	private boolean isOnStaff(Developer d) {
		return staff.contains(d);
	}

}
