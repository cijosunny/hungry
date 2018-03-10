package org.hni.events.service.dao;

import org.hni.events.service.om.RegistrationState;

public interface RegistrationStateDAO {

    RegistrationState get(String phoneNumber);

    RegistrationState insert(RegistrationState state);

    RegistrationState update(RegistrationState state);

    RegistrationState delete(String phoneNumber);

}
