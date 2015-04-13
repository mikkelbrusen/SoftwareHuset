package dtu.se1.softwarehuset;

import java.util.Calendar;

public class Activity {
	private String title;
	private int expectedWorkHours;
	private Calendar startDate;
	private Calendar endDate;
	

	public Activity(String title, int expectedWorkHours, Calendar startDate,
			Calendar endDate) {

		this.setTitle(title);
		this.setExpectedWorkHours(expectedWorkHours);
		this.setStartDate(startDate);
		this.setEndDate(endDate);		
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
