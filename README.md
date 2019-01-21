# distributed-transaction

This project is a distributed transaction solution. We can use MQ(ActiveMQ in this demo code) and event table to implement this feature.

The overview design is like this:

### service 1(database 1):
```java
public AccountService {
    @Transaction 
    void newAccount(Account account) {
        // do some insert/update; for example, insert user register records;
        insertAccount(account); 
        // insert an event record into event table, mark event_progress as NEW;
        insertNewAccountEvent(new Event()
                              .setAccountId(account.id)
                              .setPoint(account.point)
                              .setEventProgress("NEW")
                              ); 
    }
}
```

```java
public class ScanNewEventJob {
    @Scheduled(cron=******)
    void task() {
        // select * from event where event_progress = NEW;
        list<Event> events = findNewEvent(); 
        for (Event event : events) {
            sendToMQ(event);
            // update event set event_progress = PUBLISHED;
            updateEventStatus(event.id, "PUBLISHED");
        }
    }
}
```
### service 2(database 2):
```java
public class MsgListener {
    @Autowired
    EventServic eventService;
    
    void receiveMsg(Msg msg) {
        // parse message content
        Event event = parseMessage(msg);
        // insert an event record into event table,mark event_progress as PUBLISHED;
        eventService.insertEvent(event);
    }
}
```

```java
public class ScanPublishedEventJob {
    @Autowired
    EventService eventService;
    
    @Scheduled(cron=******)
    void task() {
        // select * from event where event_progress = PUBLISHED;
        list<Event> events = findPublished(); 
        for (Event event : events) {
            eventService.processPublished(event);
        }
    }
}
```
```java
public class EventService {
    @Autowired
    AccountService accountService;
    
    @Transaction
    void processPublished(Event event) {
        // do some insert/update; for example, increase user point
        accountService.increaseAccountPoint(event.getAccountId(),event.getPoint()); 
        // update event set event_progress = PROCESSED where id = event.id;
        this.updateEventStatus(eventId, PROCESSED); 
    }
}
```
