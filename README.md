# distributed-transaction

This project is a distributed transaction solution. We can use MQ(ActiveMQ in this demo code) and event table to
implement this feature.

The overview design is like this:</br></br>

<h3>service 1(database 1):</h3></br>
@Transaction process1() {</br>
    do some insert/update; // for example, insert user register records;</br>
    insert an event record into event table, mark event_progress as NEW;</br>
}</br></br>

scheduled task1() {</br>
    while (true) {</br>
        list<Event> events = select * from event where event_progress = NEW;</br>
        for (Event event : events) {</br>
            sendToMQ(event);</br>
            update event set event_progress = PUBLISHED;</br>
        }</br>
    }</br>
}</br></br>

<h3>service 2(database 2):</h3></br>
eventListener() {</br>
    while (true) {</br>
        event = receiveEventMessage;</br>
        insert an event record into event table,mark event_progress as PUBLISHED;</br>
    }</br>
}</br></br>

scheduled task2() {</br>
    while (true) {</br>
        list<Event> events = select * from event where event_progress = PUBLISHED;</br>
        for (Event event : events) {</br>
            process2(event);</br>
        }</br>
    }</br>
}</br></br>

@Transaction process2(Event event) {</br>
    do some insert/update; // for example, increase user point</br>
    update event set event_progress = PROCESSED where id = event.id;</br>
}
