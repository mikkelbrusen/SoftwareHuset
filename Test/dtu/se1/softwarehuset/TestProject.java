package dtu.se1.softwarehuset;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestProject {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
		System.setOut(null);
	}

	@Test
	public void testCreateProject() {
		Master master = new Master();
		Calendar cal = new GregorianCalendar(2015, Calendar.JANUARY, 10);

		master.createProject("first", cal);

		assertEquals(1, master.getProjects().size());

		Project project = master.getProjects().get(0);

		assertEquals("first", project.getTitle());
		assertEquals(cal, project.getStartDate());

		assertEquals(project.getActivities().size(), 1);
		assertEquals(project.getActivities().get(0).getTitle(),
				"Project Leader");	    
	    
		assertEquals("The project has been created", outContent.toString());
	}
	
	@Test
	public void testCreateExistingProject() {
		//Step 1, create the first
		Master master = new Master();
		Calendar cal = new GregorianCalendar(2015, Calendar.JANUARY, 10);

		master.createProject("first", cal);
		Project project = master.getProjects().get(0);
		
		assertEquals(1, master.getProjects().size());
		assertEquals("first", project.getTitle());
		assertEquals(cal, project.getStartDate());
		
		//Step 2, create a second project
		master.createProject("first", cal);
		
		assertEquals(1, master.getProjects().get(0));
		assertEquals("The project already exists", outContent.toString());
	}

}
