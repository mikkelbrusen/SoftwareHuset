package dtu.se1.softwarehuset;

import static org.junit.Assert.*;

import java.nio.file.AccessDeniedException;

import org.junit.Test;

public class TestDeveloper  extends SampleDataSetup{
	
	@Test
	public void testCreateDeveloper() throws Exception {
		
		m.createDev();
		assertEquals(1, m.getDevs().size());
	}
	
	@Test
	public void testRegisterHours() throws Exception {
		Developer d = m.createDev();
		Developer pl = m.createDev();
		m.logout();
		m.login(pl);
		p.becomeProjectLeader();
		a.addStaff(d);
		
		a.registerHours(d, 10);
		assertEquals(10, d.getRegisteredHours());
		assertEquals(10, a.getRegisteredHours());
		
	}
	
	@Test
	public void testRegisterHoursMultiple() throws Exception {		
		Developer d = m.createDev();
		Developer pl = m.createDev();
		m.logout();
		m.login(pl);
		p.becomeProjectLeader();
		a.addStaff(d);

		a.registerHours(d, 10);
		a.registerHours(d, 5);
		
		assertEquals(15, d.getRegisteredHours());
		assertEquals(15, a.getRegisteredHours());
	}
	
	@Test
	public void testRegisterHoursNotAssigned() throws AccessDeniedException, ActivityStaffException {
		Developer d = m.createDev();
		Developer pl = m.createDev();
		m.logout();
		m.login(pl);
		p.becomeProjectLeader();
		try {
			a.registerHours(d, 10);
			fail("The hours should not be registered");
		} catch (AccessDeniedException e) {
			assertEquals("The developer is not assigned to the activity", e.getMessage());
		}
		
		assertEquals(0, d.getRegisteredHours());
		assertEquals(0, a.getRegisteredHours());
	}
	
	@Test
	public void testRegisterHoursMultipleDevs() throws Exception {
		Developer pl = m.createDev();
		Developer d1 = m.createDev();
		Developer d2 = m.createDev();
		m.logout();
		m.login(pl);
		p.becomeProjectLeader();
		a.addStaff(d1);
		a.addStaff(d2);
		
		a.registerHours(d1, 10);
		a.registerHours(d2, 10);
		a.registerHours(d1, 10);
		
		assertEquals(30, a.getRegisteredHours());
		
	}
	
	@Test
	public void testAcceptRequest() throws Exception{
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
		} catch (Exception e) {
			fail("Request should have been created");
		}
		
		d3.acceptRequest(a, true);
		assertEquals(true, a.getStaff().contains(d3));
		assertEquals(0, d3.getRequests().size());
	}
	
	@Test
	public void testDeclineRequest() throws Exception{
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
		} catch (Exception e) {
			fail("Request should have been created");
		}
		
		d3.acceptRequest(a, false);
		assertEquals(false, a.getStaff().contains(d3));
		assertEquals(0, d3.getRequests().size());
	}

	@Test
	public void testChangeAvailableStatus() throws AccessDeniedException {
		Developer d = m.createDev();
		
		assertEquals(true, d.isAvailable());
		
		m.logout();
		m.login(d);
		d.setAvailable(false);
		
		assertEquals(false, d.isAvailable());
	}
	
	@Test
	public void testChangeAvailableStatusNoLogin() throws AccessDeniedException {
		Developer d = m.createDev();
		
		assertEquals(true, d.isAvailable());
		
		m.logout();
		try {
			d.setAvailable(false);
			fail("Available status should not have been changed");
		} catch (AccessDeniedException e) {
			assertEquals("Must be logged in to change available status", e.getMessage());
		}
		
		assertEquals(true, d.isAvailable());
	}

}
