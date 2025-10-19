package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

public class RoomTest {
    private Room room;
    
    @Before
    public void setUp() {
        room = new Room("2A01");
    }
    
    @Test
    public void testDefaultConstructor() {
        Room defaultRoom = new Room();
        assertEquals("", defaultRoom.getID());
    }
    
    @Test
    public void testConstructorWithID() {
        assertEquals("2A01", room.getID());
    }
    
    @Test
    public void testAddMeeting_success() {
        try {
            Meeting meeting = new Meeting(3, 15, 9, 11);
            room.addMeeting(meeting);
            assertTrue(room.isBusy(3, 15, 9, 11));
        } catch(TimeConflictException e) {
            fail("Should not throw exception");
        }
    }
    
    @Test
    public void testAddMeeting_conflict() {
        try {
            Meeting meeting1 = new Meeting(3, 15, 9, 11);
            room.addMeeting(meeting1);
            
            Meeting meeting2 = new Meeting(3, 15, 10, 12);
            room.addMeeting(meeting2);
            fail("Should throw TimeConflictException");
        } catch(TimeConflictException e) {
            assertTrue(e.getMessage().contains("2A01"));
        }
    }
    
    @Test
    public void testIsBusy_noMeetings() {
        try {
            assertFalse(room.isBusy(3, 15, 9, 11));
        } catch(TimeConflictException e) {
            fail("Should not throw exception");
        }
    }
    
    @Test
    public void testPrintAgenda() {
        try {
            ArrayList<Person> attendees = new ArrayList<>();
            attendees.add(new Person("Alice"));
            Meeting meeting = new Meeting(3, 15, 9, 11, attendees, room, "Planning");
            room.addMeeting(meeting);
            
            String agenda = room.printAgenda(3, 15);
            assertTrue(agenda.contains("3/15"));
            assertTrue(agenda.contains("Planning"));
        } catch(TimeConflictException e) {
            fail("Should not throw exception");
        }
    }
}
