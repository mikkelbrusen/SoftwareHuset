package dtu.se1.softwarehuset;

import static org.junit.Assert.*;

import java.nio.file.AccessDeniedException;

import org.junit.Test;

public class TestProject extends SampleDataSetup{
	
	@Test
	public void testCreateActivity() throws AccessDeniedException, ActivityStaffException {
		int activitySize = p.getActivities().size();
		Developer pl = m.createDev();
		m.logout();
		m.login(pl);
		p.becomeProjectLeader();
		
		Activity a = p.createActivity("activity", 10, start, end);
		
		assertEquals(activitySize+1, p.getActivities().size());

		assertEquals("activity", a.getTitle());
		assertEquals(10, a.getExpectedWorkHours());
		assertEquals(start, a.getStartDate());
		assertEquals(end, a.getEndDate());
		
	}
	
	@Test
	public void testCreateActivityNotProjectLeader() throws AccessDeniedException, ActivityStaffException {
		int activitySize = p.getActivities().size();
		Developer pl = m.createDev();
		m.logout();
		m.login(pl);
		try {
			p.createActivity("activity", 10, start, end);
			fail("Activity shoud not be created");
		} catch (AccessDeniedException e) {
			assertEquals("You are not the leader of this project", e.getMessage());
		}
		
		assertEquals(activitySize, p.getActivities().size());
	}
	
	@Test
	public void testCreateExistingActivity() throws AccessDeniedException, ActivityStaffException {
		int activitySize = p.getActivities().size();
		Developer pl = m.createDev();
		m.logout();
		m.login(pl);
		p.becomeProjectLeader();
		try {
			p.createActivity("activity", 10, start, end);
			fail("Activity should not be created");
		} catch (AccessDeniedException e) {
			assertEquals("The activity already exists", e.getMessage());
		}
		
		assertEquals(activitySize, p.getActivities().size());
	}
	
	@Test
	public void testBecomeProjectLeader() throws Exception {
		Activity a = p.createActivity("activity", 10, start, end);
		Developer d = m.createDev();
		m.logout();
		m.login(d);
		p.becomeProjectLeader();
		
		assertEquals(1, p.getActivities().get(0).getStaff().size());
	}
	
	@Test
	public void testBecomeProjectLeaderAlreadyAssigned() throws Exception {
		Activity a = p.createActivity("activity", 10, start, end);
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		m.logout();
		m.login(d1);
		p.becomeProjectLeader();
		
		assertEquals(1, p.getActivities().get(0).getStaff().size());
		
		m.logout();
		m.login(d2);
		try {
			p.becomeProjectLeader();
			fail("A new project leader should not be assigned!");
		} catch (ActivityStaffException e) {
			assertEquals("Project leader is already assigned", e.getMessage());
		}
		
		assertEquals(1, p.getActivities().get(0).getStaff().size());
	}
	
}
