package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;

public class MeetingTest {
    
    @Test
    public void testDefaultConstructor() {
        Meeting meeting = new Meeting();
        assertNotNull("Meeting should be created", meeting);
    }
    
    @Test
    public void testConstructorWithMonthDay() {
        Meeting meeting = new Meeting(6, 15);
        assertEquals(6, meeting.getMonth());
        assertEquals(15, meeting.getDay());
        assertEquals(0, meeting.getStartTime());
        assertEquals(23, meeting.getEndTime());
    }
    
    @Test
    public void testConstructorWithDescription() {
        Meeting meeting = new Meeting(6, 15, "Team Retreat");
        assertEquals("Team Retreat", meeting.getDescription());
    }
    
    @Test
    public void testConstructorWithTimes() {
        Meeting meeting = new Meeting(6, 15, 10, 14);
        assertEquals(10, meeting.getStartTime());
        assertEquals(14, meeting.getEndTime());
    }
    
    @Test
    public void testFullConstructor() {
        ArrayList<Person> attendees = new ArrayList<>();
        attendees.add(new Person("John"));
        Room room = new Room("2A01");
        
        Meeting meeting = new Meeting(6, 15, 10, 12, attendees, room, "Project Review");
        
        assertEquals(6, meeting.getMonth());
        assertEquals(15, meeting.getDay());
        assertEquals(10, meeting.getStartTime());
        assertEquals(12, meeting.getEndTime());
        assertEquals("Project Review", meeting.getDescription());
        assertEquals(room, meeting.getRoom());
        assertEquals(1, meeting.getAttendees().size());
    }
    
    @Test
    public void testAddRemoveAttendee() {
        Meeting meeting = new Meeting(6, 15, 10, 12);
        ArrayList<Person> attendees = new ArrayList<>();
        meeting = new Meeting(6, 15, 10, 12, attendees, new Room(), "Test");
        
        Person person = new Person("Alice");
        meeting.addAttendee(person);
        assertEquals(1, meeting.getAttendees().size());
        
        meeting.removeAttendee(person);
        assertEquals(0, meeting.getAttendees().size());
    }
    
    @Test
    public void testSettersAndGetters() {
        Meeting meeting = new Meeting();
        
        meeting.setMonth(7);
        assertEquals(7, meeting.getMonth());
        
        meeting.setDay(20);
        assertEquals(20, meeting.getDay());
        
        meeting.setStartTime(14);
        assertEquals(14, meeting.getStartTime());
        
        meeting.setEndTime(16);
        assertEquals(16, meeting.getEndTime());
        
        meeting.setDescription("Updated Meeting");
        assertEquals("Updated Meeting", meeting.getDescription());
        
        Room room = new Room("3B02");
        meeting.setRoom(room);
        assertEquals(room, meeting.getRoom());
    }
}
