package org.hni.order.service;

import org.hni.events.service.EventService;
import org.hni.user.om.User;

public interface OrderProcessor extends EventService {

    String processMessage(User user, String message);

    String processMessage(Long userId, String message);
}
