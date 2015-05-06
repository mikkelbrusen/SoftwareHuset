package dtu.se1.softwarehuset;


import static org.junit.Assert.*;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestActivity extends SampleDataSetup{
	
	@Test
	public void testAddStaff() throws Exception {
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		m.logout();
		m.login(d1);
		p.becomeProjectLeader();
		
		a.addStaff(d2);
		
		assertEquals(1,a.getStaff().size());
	}
	
	@Test
	public void testAddStaffNotProjectLeader() throws Exception {
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		m.logout();
		m.login(d1);
		try {
			a.addStaff(d2);
			fail("The developer should not have been added to the project");
		} catch (ActivityStaffException e) {
			assertEquals("You are not the leader of this project", e.getMessage());
		}
		
		assertEquals(0,a.getStaff().size());
	}
	
	@Test
	public void testAddAlreadyAssignedStaff() throws Exception {
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		m.logout();
		m.login(d1);
		p.becomeProjectLeader();
		a.addStaff(d2);
		try {
			a.addStaff(d2);
			fail("The developer is already assigned to the project");
		} catch (ActivityStaffException e) {
			assertEquals("Developer is already assinged", e.getMessage());
		}
		
		assertEquals(1,a.getStaff().size());
	}
	
	@Test
	public void testAddStaffNotAvaible() throws AccessDeniedException, ActivityStaffException {
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		m.logout();
		m.login(d1);
		p.becomeProjectLeader();
		
		List<Activity> activities = new ArrayList<Activity>();
		for (int i = 1; i <= 10; i++){
			activities.add(p.createActivity("activity"+i, 10, start, end));
		}
		
		for (int i = 0; i < activities.size()-1; i++){
			activities.get(i).addStaff(d2);
		}
		
		// NOT DONE BITCH
	}
	
}
