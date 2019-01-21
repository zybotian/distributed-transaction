# distributed-transaction

This project is a distributed transaction solution. We can use MQ(ActiveMQ in this demo code) and event table to implement this feature.

The overview design is like this:

### service 1(database 1):
```java
public class AccountService {
    @Transaction 
    void newAccount(Account account) {
        // do some insert/update; for example, insert user register records;
        insertAccount(account); 
        // insert an event record into event table, mark event_progress as NEW;
        insertNewAccountEvent(new Event()
                              .setAccountId(account.id)
                              .setPoint(account.point)
                              .setEventProgress(NEW)
                              ); 
    }
}
```

```java
public class ScanNewEventJob {
    @Scheduled(cron="******")
    void task() {
        // select * from event where event_progress = NEW;
        list<Event> events = findNewEvent(); 
        for (Event event : events) {
            sendToMQ(event);
            // update event set event_progress = PUBLISHED;
            updateEventStatus(event.id, PUBLISHED);
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
    
    @Scheduled(cron="******")
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
### 针对该解决方案的思考
   - 数据库本地事务可以保证插入account和插入对应event全部成功或者全部失败
   - service1的定时任务相关问题
      - 如果定时任务挂掉了,只需要重启定时任务即可
      - 如果event状态修改失败,消息发送失败,则等待下次定时任务扫描即可重入
      - 如果event状态修改成功但消息发送失败了,可以数据库上线,保证event可以再次被扫到
      - 如果event状态修改失败但消息发送成功了,定时任务下次扫到了event会再次发送给mq,mq收到了重复消息,问题转化为mq如何对消息去重?
      - mq如何去重消息? mq什么都不需要做,只要消息的消费者保证自己的处理逻辑是幂等操作即可
   - service2的定时任务相关问题
      - 如果定时任务挂了,只需要重启定时任务即可
      - 数据库本地事务保证,修改event状态和增加积分两个操作要么全部成功,要么全部失败
      - 如果同时失败了,下一次定时任务依然会扫描到,可以重入