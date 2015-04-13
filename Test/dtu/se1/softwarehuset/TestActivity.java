package dtu.se1.softwarehuset;


import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

public class TestActivity {
	Project project;

	@Before
	public void setUp() {
		Master master = new Master();
		Calendar cal = new GregorianCalendar(2015, Calendar.JANUARY, 10);

		master.createProject("Project", cal);
		project = master.getProjects().get(0);
	}
	
	@Test
	public void testCreateActivity() {

		Calendar startDate = new GregorianCalendar(2015, Calendar.JANUARY, 10);
		Calendar endDate = new GregorianCalendar(2015, Calendar.JANUARY, 20);
	
		project.createActivity("activity", 10, startDate, endDate);
		
		assertEquals(2, project.getActivities().size());
		Activity activity = project.getActivities().get(1);
		
		assertEquals(startDate, activity.getStartDate());
		assertEquals(10, activity.getExpectedWorkHours());
		assertEquals(endDate, activity.getEndDate());
		assertEquals("activity", activity.getTitle());
		
	}
	
}
