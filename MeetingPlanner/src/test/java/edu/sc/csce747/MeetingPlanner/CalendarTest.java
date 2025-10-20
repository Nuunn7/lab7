package edu.sc.csce747.MeetingPlanner;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class CalendarTest {
    private Calendar calendar;
    
    @Before
    public void setUp() {
        calendar = new Calendar();
    }
    
    //Энгийн кейсүүд
    @Test
    public void testAddMeeting_normalCase() {
        try {
            Meeting meeting = new Meeting(3, 15, 10, 11);
            meeting.setDescription("Team Meeting");
            calendar.addMeeting(meeting);
            assertTrue("Meeting should be added", calendar.isBusy(3, 15, 10, 11));
        } catch(TimeConflictException e) {
            fail("Should not throw exception for valid meeting");
        }
    }
    
    @Test
    public void testAddMeeting_holiday() {
        try {
            Meeting midsommar = new Meeting(6, 26, "Midsommar");
            calendar.addMeeting(midsommar);
            Boolean added = calendar.isBusy(6, 26, 0, 23);
            assertTrue("Midsommar should be marked as busy on the calendar", added);
        } catch(TimeConflictException e) {
            fail("Should not throw exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testIsBusy_noMeetings() {
        try {
            assertFalse("Should not be busy without meetings", 
                       calendar.isBusy(5, 15, 10, 11));
        } catch(TimeConflictException e) {
            fail("Should not throw exception");
        }
    }
    
    //Edge кейсүүд 
    @Test
    public void testAddMeeting_firstDayOfMonth() {
        try {
            Meeting meeting = new Meeting(1, 1, "New Year Meeting");
            calendar.addMeeting(meeting);
            assertTrue(calendar.isBusy(1, 1, 0, 23));
        } catch(TimeConflictException e) {
            fail("Should handle first day of month");
        }
    }
    
    @Test
    public void testAddMeeting_lastDayOfJanuary() {
        try {
            Meeting meeting = new Meeting(1, 31, "Month End Meeting");
            calendar.addMeeting(meeting);
            assertTrue(calendar.isBusy(1, 31, 0, 23));
        } catch(TimeConflictException e) {
            fail("Should handle last day of January");
        }
    }
    
    @Test
    public void testAddMeeting_february28() {
        try {
            Meeting meeting = new Meeting(2, 28, "February Meeting");
            calendar.addMeeting(meeting);
            assertTrue(calendar.isBusy(2, 28, 0, 23));
        } catch(TimeConflictException e) {
            fail("February 28 should be valid");
        }
    }
    
    //Алдаатай кейсүүд  
    @Test
    public void testAddMeeting_february29() {
        try {
            Meeting meeting = new Meeting(2, 29, "Invalid February Meeting");
            calendar.addMeeting(meeting);
            fail("Should throw exception for February 29");
        } catch(TimeConflictException e) {
            // Expected exception
        }
    }
    
    @Test
    public void testAddMeeting_february35() {
        assertThrows(TimeConflictException.class, () -> {
            Meeting meeting = new Meeting(2, 35, "Invalid Meeting");
            calendar.addMeeting(meeting);
        });
    }
    
    @Test
    public void testAddMeeting_month0() {
        assertThrows(TimeConflictException.class, () -> {
            Meeting meeting = new Meeting(0, 15, "Invalid Month 0");
            calendar.addMeeting(meeting);
        });
    }
    
    @Test
    public void testAddMeeting_month13() {
        assertThrows(TimeConflictException.class, () -> {
            Meeting meeting = new Meeting(13, 15, "Month 13");
            calendar.addMeeting(meeting);
        });
    }
    
    @Test
    public void testAddMeeting_negativeMonth() {
        assertThrows(TimeConflictException.class, () -> {
            Meeting meeting = new Meeting(-1, 15, "Negative Month");
            calendar.addMeeting(meeting);
        });
    }
    
    @Test
    public void testAddMeeting_day0() {
        assertThrows(TimeConflictException.class, () -> {
            Meeting meeting = new Meeting(5, 0, "Day 0");
            calendar.addMeeting(meeting);
        });
    }
    
    @Test
    public void testAddMeeting_day32() {
        assertThrows(TimeConflictException.class, () -> {
            Meeting meeting = new Meeting(5, 32, "Day 32");
            calendar.addMeeting(meeting);
        });
    }
    
    //BUG олох тестүүд
    @Test
    public void testGetMeeting_invalidIndices() {
        //BUG: getMeeting doesn't check bounds
        try {
            Meeting result = calendar.getMeeting(99, 99, 99);
            fail("BUG DETECTED: getMeeting should check bounds but doesn't");
        } catch(IndexOutOfBoundsException e) {
            // This should happen
        }
    }
    
    @Test
    public void testRemoveMeeting_invalidIndices() {
        //BUG: removeMeeting doesn't check bounds
        try {
            calendar.removeMeeting(99, 99, 99);
            fail("BUG DETECTED: removeMeeting should check bounds but doesn't");
        } catch(IndexOutOfBoundsException e) {
            // This should happen
        }
    }
    
    // TIME VALIDATION тестүүд   
    @Test
    public void testAddMeeting_negativeStartTime() {
        assertThrows(TimeConflictException.class, () -> {
            Meeting meeting = new Meeting(5, 15, -1, 10);
            calendar.addMeeting(meeting);
        });
    }
    
    @Test
    public void testAddMeeting_startTime24() {
        assertThrows(TimeConflictException.class, () -> {
            Meeting meeting = new Meeting(5, 15, 24, 25);
            calendar.addMeeting(meeting);
        });
    }
    
    @Test
    public void testAddMeeting_endTimeBeforeStartTime() {
        assertThrows(TimeConflictException.class, () -> {
            Meeting meeting = new Meeting(5, 15, 15, 10);
            calendar.addMeeting(meeting);
        });
    }
    
    //CONFLICT олох тестүүд
    @Test
    public void testAddMeeting_conflictExactOverlap() {
        try {
            Meeting meeting1 = new Meeting(5, 15, 10, 12);
            meeting1.setDescription("First Meeting");
            calendar.addMeeting(meeting1);
            
            Meeting meeting2 = new Meeting(5, 15, 10, 12);
            meeting2.setDescription("Second Meeting");
            calendar.addMeeting(meeting2);
            fail("Should throw TimeConflictException for overlapping meetings");
        } catch(TimeConflictException e) {
            assertTrue(e.getMessage().contains("Overlap"));
        }
    }
    
    @Test
    public void testAddMeeting_conflictPartialOverlap() {
        try {
            Meeting meeting1 = new Meeting(5, 15, 10, 12);
            meeting1.setDescription("First Meeting");
            calendar.addMeeting(meeting1);
            
            Meeting meeting2 = new Meeting(5, 15, 11, 13);
            meeting2.setDescription("Second Meeting");
            calendar.addMeeting(meeting2);
            fail("Should throw TimeConflictException for partial overlap");
        } catch(TimeConflictException e) {
            assertTrue(e.getMessage().contains("Overlap"));
        }
    }
    
    @Test
    public void testClearSchedule() {
        try {
            Meeting meeting = new Meeting(5, 15, 10, 12);
            meeting.setDescription("Team Meeting");
            calendar.addMeeting(meeting);
            assertTrue(calendar.isBusy(5, 15, 10, 12));
            
            calendar.clearSchedule(5, 15);
            assertFalse(calendar.isBusy(5, 15, 10, 12));
        } catch(TimeConflictException e) {
            fail("Should not throw exception");
        }
    }
}