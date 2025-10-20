**Хичээл:** Программ хангамжийн чанарын баталгаа ба туршилт
**Лабораторийн ажил:** 7  
**Огноо:** 2025 оны 10-р сарын 20

## Төслийн тухай
Meeting Planner програмын алдаа илрүүлэх тестүүд бичих

## Тест статистик
 Нийт тестүүд:               46  
 Амжилтай (passed):          42  
 Бүтэлгүйтсэн (failed):      4  
 Амжилтын хувь:              91.3%  

| Модуль | Амжилтай | Бүтэлгүйтсэн | Нийт |
|--------|----------|-------------|------|
| CalendarTest | 25 | 1 | 26 |
| PersonTest | 7 | 1 | 8 |
| RoomTest | 5 | 0 | 5 |
| OrganizationTest | 3 | 0 | 3 |
| MeetingTest | 2 | 2 | 4 |
| **Нийт** | **42** | **4** | **46** |
## Бүтэлгүйтсэн Тестүүд
**1. CalendarTest: testAddMeeting_february29()**

Алдаа: java.lang.AssertionError: Should throw exception for February 29

Бүтэлгүйтсэн шалтгаан: 
2 сарын 29 өдрийг нэмэх ёсгүй
Calendar классын конструктор дээр 2-сарын 29, 30, 31-ийг блокхлох оролдлога байгаа ч асуудалтай ажилладаг
addMeeting() методын конфликт шалгах кодонд "Day does not exist" гэж тэмдэглэгдсэн уулзалтуудыг алгасдаг

Шийдэл: 
// Calendar класс-д шинэ метод нэмэх
private boolean isValidDate(int month, int day) {
    if (month == 2 && day > 28) return false;
    if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) return false;
    return true;
}

// addMeeting() методод дараах кодыг нэмэх
if (!isValidDate(mMonth, mDay)) {
    throw new TimeConflictException("Invalid date: " + mMonth + "/" + mDay);
}

**2. PersonTest: testAddMeeting_conflict()**

Алдаа: java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because the return value of "...Meeting.getDescription()" is null at Calendar.addMeeting(Calendar.java:123)

Бүтэлгүйтсэн шалтгаан: 
Calendar.addMeeting() дээр конфликт шалгахдаа meeting.getDescription().equals("Day does not exist") гэж дууддаг
getDescription() null буцаадаг тул NullPointerException гарч байна

Шийдэл: 
// Calendar класс-д шинэ метод нэмэх
private boolean isValidDate(int month, int day) {
    if (month == 2 && day > 28) return false;
    if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) return false;
    return true;
}

// addMeeting() методод дараах кодыг нэмэх
if (!isValidDate(mMonth, mDay)) {
    throw new TimeConflictException("Invalid date: " + mMonth + "/" + mDay);
}

**3. RoomTest: testAddMeeting_conflict()**

Алдаа: java.lang.NullPointerException: Cannot invoke "String.equals(Object)" because the return value of "...Meeting.getDescription()" is null at Calendar.addMeeting(Calendar.java:123)

Бүтэлгүйтсэн шалтгаан: 
Уулзалтыг үүсгэхдээ тайлбар нь бичээгүй уулзалтууд null буцаадаг
Calendar.addMeeting() дээр getDescription().equals(...) дууддаг тул NullPointerException гарч байна

Шийдэл: 
@Test
public void testAddMeeting_conflict() {
    try {
        Meeting meeting1 = new Meeting(3, 15, 9, 11);
        meeting1.setDescription("First Meeting");  // Нэмсэн
        room.addMeeting(meeting1);
        
        Meeting meeting2 = new Meeting(3, 15, 10, 12);
        meeting2.setDescription("Second Meeting");  // Нэмсэн
        room.addMeeting(meeting2);
        fail("Should throw TimeConflictException");
    } catch(TimeConflictException e) {
        assertTrue(e.getMessage().contains("2A01"));
    }
}

**4. PersonTest: testIsBusy_withMeeting()**

Алдаа: java.lang.AssertionError: Should not throw exception

Бүтэлгүйтсэн шалтгаан: 
Энэ тест нь уулзалтыг нэмэхдээ NullPointerException гаргадаг
Meeting объектыг үүсгэхдээ description бичээгүй байна

Шийдэл: 
@Test
public void testIsBusy_withMeeting() {
    try {
        Meeting meeting = new Meeting(5, 20, 10, 12);
        meeting.setDescription("Team Meeting");  // Нэмсэн
        person.addMeeting(meeting);
        
        assertTrue(person.isBusy(5, 20, 10, 12));
        assertTrue(person.isBusy(5, 20, 11, 11));
        assertFalse(person.isBusy(5, 20, 13, 14));
    } catch(TimeConflictException e) {
        fail("Should not throw exception");
    }
}

## Дүгнэлт

Meeting Planner программын туршилт 91.3% амжилттай гүйцэтгэсэн ба системийн хувьд энгийн болон edge кейсүүдийг гаргаж, хэрэв алдаа заах ёстой бол зөв алдаа зааж байгаа эсэхийг шалган тестийг хийсэн. Бүтэлгүйтсэн 4 тестийн хувьд системийн тестийн код болон системийн дизайнтай холбоотой асуудлууд илэрсэн. Үүнд:

-Meeting-д заавал тайлбар байх
-Хүчингүй өдрүүдийн баталгаажуулалт сайжруулах
-Month validation нь 12 сарыг шалгах 

зэрэг асуудлууд илэрсэн тул эдгээрийг шийдвэрлэвэл программын тест 100% амжилттай ажиллах боломжтой.

## Build хийх

### Ant ашиглан:
```bash
ant compile          # Compile хийх
ant javadoc          # Documentation үүсгэх
ant all              # Бүгдийг хийх
ant clean            # Цэвэрлэх
```

### IDE дээр:
- Eclipse/IntelliJ IDEA дээр "Run as JUnit Test"

## Project бүтэц
```
MEETINGPLANNER/
├── src/
│   ├── main/java/edu/sc/csce747/MeetingPlanner/
│   │   ├── Calendar.java
│   │   ├── Meeting.java
│   │   ├── Person.java
│   │   ├── Room.java
│   │   ├── Organization.java
│   │   ├── PlannerInterface.java
│   │   └── TimeConflictException.java
│   └── test/java/edu/sc/csce747/MeetingPlanner/
│       ├── CalendarTest.java
│       ├── MeetingTest.java
│       ├── PersonTest.java
│       ├── RoomTest.java
│       └── OrganizationTest.java
├── bin/              # Compiled файлууд
├── doc/              # Javadoc
├── build.xml         # Ant build файл
└── README.md         # Тайлан

```

## Хөгжүүлэгч
[Х. Өнөгэрэл]  
Улаанбаатар, Монгол Улс