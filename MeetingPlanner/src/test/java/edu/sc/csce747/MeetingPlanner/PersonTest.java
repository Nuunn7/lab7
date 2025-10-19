package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class PersonTest {
    private Person person;
    
    @Before
    public void setUp() {
        person = new Person("Testmaa");
    }
    
    @Test
    public void testDefaultConstructor() {
        Person defaultPerson = new Person();
        assertEquals("", defaultPerson.getName());
    }
    
    @Test
    public void testConstructorWithName() {
        assertEquals("Testmaa", person.getName());
    }
    
    @Test
    public void testAddMeeting_success() {
        try {
            Meeting meeting = new Meeting(4, 10, 14, 16);
            person.addMeeting(meeting);
            assertTrue(person.isBusy(4, 10, 14, 16));
        } catch(TimeConflictException e) {
            fail("Should not throw exception");
        }
    }
    
    @Test
    public void testAddMeeting_conflict() {
        try {
            Meeting meeting1 = new Meeting(4, 10, 14, 16);
            person.addMeeting(meeting1);
            
            Meeting meeting2 = new Meeting(4, 10, 15, 17);
            person.addMeeting(meeting2);
            fail("Should throw TimeConflictException");
        } catch(TimeConflictException e) {
            assertTrue(e.getMessage().contains("John Doe"));
        }
    }
    
    @Test
    public void testIsBusy_withMeeting() {
        try {
            Meeting meeting = new Meeting(5, 20, 10, 12);
            person.addMeeting(meeting);
            
            assertTrue(person.isBusy(5, 20, 10, 12));
            assertTrue(person.isBusy(5, 20, 11, 11));
            assertFalse(person.isBusy(5, 20, 13, 14));
        } catch(TimeConflictException e) {
            fail("Should not throw exception");
        }
    }
    
    @Test
    public void testGetMeeting() {
        try {
            Meeting meeting = new Meeting(5, 20, "Team Meeting");
            person.addMeeting(meeting);
            
            Meeting retrieved = person.getMeeting(5, 20, 0);
            assertNotNull(retrieved);
            assertEquals("Team Meeting", retrieved.getDescription());
        } catch(TimeConflictException e) {
            fail("Should not throw exception");
        }
    }
    
    @Test
    public void testRemoveMeeting() {
        try {
            Meeting meeting = new Meeting(5, 20, 10, 12);
            person.addMeeting(meeting);
            assertTrue(person.isBusy(5, 20, 10, 12));
            
            person.removeMeeting(5, 20, 0);
            assertFalse(person.isBusy(5, 20, 10, 12));
        } catch(TimeConflictException e) {
            fail("Should not throw exception");
        }
    }
}
