package org.hni.events.service.dao;

import org.hni.events.service.om.RegistrationState;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultRegistrationStateDAO implements RegistrationStateDAO {

    //TODO get rid of this with real DAO
    Map<String, RegistrationState> registrationStateMap;

    public DefaultRegistrationStateDAO() {
        registrationStateMap = new HashMap<>();
    }

    public RegistrationState get(String phoneNumber) {
        return registrationStateMap.get(phoneNumber);
    }

    public RegistrationState insert(RegistrationState state) {
        return registrationStateMap.put(state.getPhoneNumber(), state);
    }

    public RegistrationState update(RegistrationState state) {
        return registrationStateMap.put(state.getPhoneNumber(), state);
    }

    public RegistrationState delete(String phoneNumber) {
        return registrationStateMap.remove(phoneNumber);
    }
}
