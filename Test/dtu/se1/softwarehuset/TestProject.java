package dtu.se1.softwarehuset;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class TestProject {

	@Test
	public void testCreateProject() {
		Master master = new Master();
		Calendar cal = new GregorianCalendar(2015, Calendar.JANUARY, 10);

		Project project = master.createProject("first", cal);

		assertEquals(1, master.getProjects().size());

		assertEquals("first", project.getTitle());
		assertEquals(cal, project.getStartDate());

		assertEquals(project.getActivities().size(), 1);
		assertEquals(project.getActivities().get(0).getTitle(),
				"Project Leader");	    
	    
	}

}
