package dtu.se1.softwarehuset;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestProject extends SampleDataSetup{
	
	@Test
	public void testCreateActivity() {
	
		int activitySize = p.getActivities().size();
		Activity a = p.createActivity("activity", 10, start, end);
		
		assertEquals(activitySize+1, p.getActivities().size());

		assertEquals("activity", a.getTitle());
		assertEquals(10, a.getExpectedWorkHours());
		assertEquals(start, a.getStartDate());
		assertEquals(end, a.getEndDate());
		
	}
	
}
