package org.hni.events.service.om;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RegistrationStep {

    STATE_REGISTER_START(100, EventName.REGISTER),
    STATE_REGISTER_GET_FIRST_NAME(101, EventName.REGISTER),
    STATE_REGISTER_GET_LAST_NAME(102, EventName.REGISTER),
    STATE_REGISTER_GET_EMAIL(103, EventName.REGISTER),
    STATE_REGISTER_CONFIRM_EMAIL(104, EventName.REGISTER),
    STATE_REGISTER_GET_PASSWORD(105, EventName.REGISTER),
    STATE_REGISTER_CONFIRM_PASSWORD(106, EventName.REGISTER),
	STATE_REGISTER_NUMBER_OF_CHILDREN(107, EventName.REGISTER),
	STATE_REGISTER_ACCEPT_POLICY(108, EventName.REGISTER);
	
	

    private static final Map<EventName, RegistrationStep> INITIAL_STATES;
    private static final Map<Integer, RegistrationStep> ALL_STATES;

    static {
        INITIAL_STATES = new HashMap<>();
        INITIAL_STATES.put(EventName.REGISTER, STATE_REGISTER_START);

        ALL_STATES = new HashMap<>();
        Arrays.stream(values()).forEach(s -> ALL_STATES.put(s.getStateCode(), s));
    }

    int stateCode;
    EventName eventName;

    RegistrationStep(int stateCode, EventName eventName) {
        this.stateCode = stateCode;
        this.eventName = eventName;
    }

    public EventName getEventName() {
        return eventName;
    }

    public int getStateCode() {
        return stateCode;
    }

    public static RegistrationStep getInitalState(final EventName eventName) {
        return INITIAL_STATES.get(eventName);
    }

    public static RegistrationStep fromStateCode(final int stateCode) {
        return ALL_STATES.get(stateCode);
    }
}
