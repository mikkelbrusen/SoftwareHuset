package dtu.se1.softwarehuset;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Test;

public class TestMaster{

	@Test
	public void testCreateProject() throws Exception{
		Master m = new Master();
		m.login(m.getAdmin());
		Calendar cal = new GregorianCalendar(2015, Calendar.JANUARY, 10);

		Project p = m.createProject("first project", cal);

		assertEquals(1, m.getProjects().size());

		assertEquals("first project", p.getTitle());
		assertEquals(cal, p.getStartDate());

		assertEquals(p.getActivities().size(), 1);
		assertEquals(p.getActivities().get(0).getTitle(),
				"Project Leader");	    
	}
	
	@Test
	public void testCreateProjectNoLogin() {
		Master m = new Master();

		Calendar cal = new GregorianCalendar(2015, Calendar.JANUARY, 10);
		try {

			m.createProject("first project", cal);
			fail("Project should not have been created!");
			
		} catch (Exception e) {
			assertEquals("You are not an administrator", e.getMessage());
		}
	}
	
}
