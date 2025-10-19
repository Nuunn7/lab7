package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class OrganizationTest {
    private Organization org;
    
    @Before
    public void setUp() {
        org = new Organization();
    }
    
    @Test
    public void testConstructor() {
        assertNotNull(org.getEmployees());
        assertNotNull(org.getRooms());
        assertEquals(5, org.getEmployees().size());
        assertEquals(5, org.getRooms().size());
    }
    
    @Test
    public void testGetRoom_exists() {
        try {
            Room room = org.getRoom("2A01");
            assertNotNull(room);
            assertEquals("2A01", room.getID());
        } catch(Exception e) {
            fail("Should find existing room");
        }
    }
    
    @Test
    public void testGetRoom_notExists() {
        try {
            org.getRoom("9Z99");
            fail("Should throw exception for non-existent room");
        } catch(Exception e) {
            assertEquals("Requested room does not exist", e.getMessage());
        }
    }
    
    @Test
    public void testGetEmployee_exists() {
        try {
            Person employee = org.getEmployee("Greg Gay");
            assertNotNull(employee);
            assertEquals("Greg Gay", employee.getName());
        } catch(Exception e) {
            fail("Should find existing employee");
        }
    }
    
    @Test
    public void testGetEmployee_notExists() {
        try {
            org.getEmployee("Non Existent");
            fail("Should throw exception for non-existent employee");
        } catch(Exception e) {
            assertEquals("Requested employee does not exist", e.getMessage());
        }
    }
}
