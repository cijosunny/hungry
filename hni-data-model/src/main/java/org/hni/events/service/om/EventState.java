package org.hni.events.service.om;

import org.hni.common.om.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "event_state")
public class EventState implements Persistable, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    protected Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "eventname", nullable = false)
    private EventName eventName;

    @Column(name = "phoneno", nullable = false, unique = true)
    private String phoneNumber;

    public EventState() {
    }

    public EventState(Long id, EventName eventName, String phoneNumber) {
        this.id = id;
        this.eventName = eventName;
        this.phoneNumber = phoneNumber;
    }

    public EventState(EventName eventName, String phoneNumber) {
        this.eventName = eventName;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public Long getId() {
        return id;
    }

    public EventName getEventName() {
        return eventName;
    }

    public void setEventName(EventName eventName) {
        this.eventName = eventName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EventState that = (EventState) o;

        if (id == null) {
            if (that.id != null)
                return false;
        } else if (!id.equals(that.id))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
}
