package dtu.se1.softwarehuset;

import static org.junit.Assert.*;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TestActivity extends SampleDataSetup {

	@Test
	public void testAddStaff() throws Exception {
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		m.logout();
		m.login(d1);
		p.becomeProjectLeader();

		a.addStaff(d2);

		assertEquals(1, a.getStaff().size());
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
			assertEquals("You are not the leader of this project",
					e.getMessage());
		}

		assertEquals(0, a.getStaff().size());
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

		assertEquals(1, a.getStaff().size());
	}

	@Test
	public void testAddStaffNotAvailable() throws AccessDeniedException,
			ActivityStaffException {
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		m.logout();
		m.login(d1);
		p.becomeProjectLeader();

		d2.setAvailable(false);
		try {
			a.addStaff(d2);
			fail("The activity should not be added");
		} catch (ActivityStaffException e) {
			assertEquals("The developer is unavailable", e.getMessage());
		}

		assertEquals(0, a.getStaff().size());

	}
	
	@Test
	public void testRequestAssistanceNotAssigned() throws AccessDeniedException {
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		m.logout();
		m.login(d1);

		try {
			a.requestAssistance(d2);
			fail("Request should have been denied.");
		} catch (Exception e) {
			assertEquals(
					"You need to be assigned to the activity to request assistance.",
					e.getMessage());
		}

	}

	@Test
	public void testRequestAssistanceDeveloperAlreadyOnStaff() throws Exception {
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		Developer d3 = m.createDev();

		m.logout();
		m.login(d1);
		p.becomeProjectLeader();
		a.addStaff(d2);
		a.addStaff(d3);

		m.logout();
		m.login(d2);
		try {
			a.requestAssistance(d3);
			fail("Request should have been denied.");
		} catch (Exception e) {
			assertEquals(
					"The chosen developer is already assigned to this activity.",
					e.getMessage());
		}

	}
	
	@Test
	public void testRequestAssistance() throws Exception{
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		Developer d3 = m.createDev();

		m.logout();
		m.login(d1);
		p.becomeProjectLeader();
		a.addStaff(d2);

		m.logout();
		m.login(d2);
		try {
			a.requestAssistance(d3);
			
			assertEquals(1, d3.getRequests().size());
			assertEquals(d2, d3.getRequests().get(a));
		} catch (Exception e) {
			fail("Request should not have been denied.");
		}
	}
	
	@Test
	public void testRequestAssistanceDeveloperUnavailable() throws Exception{
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		Developer d3 = m.createDev();

		m.logout();
		m.login(d1);
		p.becomeProjectLeader();
		a.addStaff(d2);

		d3.setAvailable(false);
		
		m.logout();
		m.login(d2);
		try {
			a.requestAssistance(d3);
			fail("Request should have been denied.");
		} catch (Exception e) {
			assertEquals("The chosen developer is not available", e.getMessage());
		}
	}
	
	@Test
	public void testRequestAssistanceDuplicateRequest() throws Exception{
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		Developer d3 = m.createDev();

		m.logout();
		m.login(d1);
		p.becomeProjectLeader();
		a.addStaff(d2);
		
		m.logout();
		m.login(d2);
		try {
			a.requestAssistance(d3);
			a.requestAssistance(d3);
			fail("Request should have been denied.");
		} catch (Exception e) {
			assertEquals("The chosen developer has already been requested", e.getMessage());
		}
	}

}
