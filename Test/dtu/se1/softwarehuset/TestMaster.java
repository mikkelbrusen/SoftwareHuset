package dtu.se1.softwarehuset;

import static org.junit.Assert.*;

import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;
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
		assertEquals(p, m.getProjectById(p.getId()));
		assertEquals(null, m.getProjectById(p.getId()+1));
		assertEquals(p.getActivities().size(), 1);
		int activityId = p.getActivities().get(0).getId();
		assertEquals("Project Leader", p.getActivityById(activityId).getTitle());
		assertEquals(null, p.getActivityById(activityId+1));
		
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
	
	@Test
	public void testCreateProjectAlreadyExcist() throws Exception{
		Master m = new Master();
		m.login(m.getAdmin());
		Calendar cal = new GregorianCalendar(2015, Calendar.JANUARY, 10);

		m.createProject("first project", cal);
		try {
			m.createProject("first project", cal);
			fail("Project should not have been created");
			
		} catch(AlreadyExistingException e){
			assertEquals("The project already exists",e.getMessage());
		}
	}
	
	@Test
	public void testCreateProjectEmptyName() throws Exception {
		Master m = new Master();
		m.login(m.getAdmin());
		Calendar cal = new GregorianCalendar(2015, Calendar.JANUARY, 10);
		try {
			m.createProject("", cal);
			fail("Project should not have been created");
		} catch (InvalidParameterException e) {
			assertEquals("Project must have a non-empty name", e.getMessage());
		}
	}
	
	@Test
	public void testCreateDeveloper() throws AccessDeniedException {
		Master m = new Master();
		m.login(m.getAdmin());
		m.createDev();
		
		assertEquals(1, m.getDevs().size());
		assertEquals(m.getDevById(23), null);
	}
	
	@Test
	public void testCreateDeveloperNoLogin() throws AccessDeniedException {
		Master m = new Master();
		try {
			m.createDev();
			fail("Developer should not have been created!");
		} catch (AccessDeniedException e) {
			assertEquals("You are not an administrator", e.getMessage());
		}
	}
}
