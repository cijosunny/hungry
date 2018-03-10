package org.hni.events.service;

import org.hni.events.service.dao.EventStateDao;
import org.hni.events.service.om.Event;
import org.hni.events.service.om.EventName;
import org.hni.events.service.om.EventState;
import org.hni.user.service.UserOnboardingService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import java.util.HashMap;
import java.util.Map;

@Component
public class EventRouter {

    @Inject
    private EventStateDao eventStateDao;

    @Inject
    private RegisterService registerService;
    
    @Inject
    private UserOnboardingService userOnboardService;

    private Map<EventName, EventService> eventServiceMap;

    @PostConstruct
    void init() {
        eventServiceMap = new HashMap<>();
        eventServiceMap.put(EventName.REGISTER, registerService);
    }

    public void registerService(EventName eventName, EventService service) {
        eventServiceMap.put(eventName, service);
    }

    public EventService getRegistered(EventName eventName) {
        return eventServiceMap.get(eventName);
    }

    @Transactional(rollbackFor = Exception.class)
    public String handleEvent(final Event event) {

        final String phoneNumber = event.getPhoneNumber();

        // Lookup routing state by phone number
        EventState state = eventStateDao.byPhoneNumber(phoneNumber);

        EventName eventName = parseKeyWordToEventName(event.getTextMessage());
        
        if(eventName == EventName.REGISTER){
        	String message = userOnboardService.isValidInvitation(phoneNumber);
        	if(message != null)
        		return message;
        }
        if (eventName == null) {
            if (state == null) {
                // not a keyword, nor having a in progress workflow
                return "Unknown keyword " + event.getTextMessage();
            } else {
                eventName = state.getEventName();
            }
        } else {
            if (state != null) {
                if (eventName != state.getEventName()) {
                    // update the state to have the new eventName
                    state.setEventName(eventName);
                    eventStateDao.update(state);
                }
            } else {
                // Insert the new event
                eventStateDao.insert(new EventState(eventName, phoneNumber));
            }
        }

        // Route the event to the appropriate service
        return eventServiceMap.get(eventName).handleEvent(event);
    }

    private EventName parseKeyWordToEventName(final String keyWord) {
        switch (keyWord.toUpperCase()) {
            // Suport multiple keywords for enrollment
            case "SIGNUP":
            case "ENROLL":
            case "REGISTER":
                return EventName.REGISTER;
            // Support multiple Keywords for ordering
            case "HUNGRY":
            case "MEAL":
            case "ORDER":
                return EventName.MEAL;
            case "GIVE":
            case "DONATE":
                return EventName.DONATE;
            default: // No valid keyword entered
                return null;
        }
    }
}
