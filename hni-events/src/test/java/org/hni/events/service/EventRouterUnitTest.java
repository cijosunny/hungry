package org.hni.events.service;

import org.hni.events.service.dao.EventStateDao;
import org.hni.events.service.om.Event;
import org.hni.events.service.om.EventName;
import org.hni.events.service.om.EventState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class EventRouterUnitTest {

    private static final Long EVENT_STATE_ID = 1L;
    private static final String PHONE_NUMBER = "8188461238";
    private static final String RETURN_MESSAGE = "returnmessage";

    @InjectMocks
    private EventRouter eventRouter;

    @Mock
    private EventStateDao eventStateDao;

    @Mock
    private RegisterService registerService;

    private Event event;

    private EventState eventState = null;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        eventRouter.init();
        event = Event.createEvent("text/plain", PHONE_NUMBER, "message");
        when(eventStateDao.insert(any(EventState.class))).thenReturn(new EventState());
        when(registerService.handleEvent(eq(event))).thenReturn(RETURN_MESSAGE);
    }

    // @Test
    public void testInvalidEventAndNoActiveWorkFlow() {
        when(eventStateDao.byPhoneNumber(eq(PHONE_NUMBER))).thenReturn(eventState);
        Assert.assertEquals("Unknown keyword " + event.getTextMessage(), eventRouter.handleEvent(event));
        verify(eventStateDao, times(0)).insert(any(EventState.class));
    }

    // @Test
    public void testInvalidEventWithActiveWorkFlow() {
        eventState = new EventState(EVENT_STATE_ID, EventName.REGISTER, PHONE_NUMBER);
        when(eventStateDao.byPhoneNumber(eq(PHONE_NUMBER))).thenReturn(eventState);
        Assert.assertEquals(RETURN_MESSAGE, eventRouter.handleEvent(event));
        verify(eventStateDao, times(0)).insert(any(EventState.class));
    }

    // @Test
    public void testStartRegisterWorkFlow() {
        when(eventStateDao.byPhoneNumber(eq(PHONE_NUMBER))).thenReturn(eventState);
        event.setTextMessage("SIGNUP");
        Assert.assertEquals(RETURN_MESSAGE, eventRouter.handleEvent(event));
        verify(eventStateDao, never()).delete(eq(eventState));
        verify(eventStateDao, times(1)).insert(any(EventState.class));
    }

    // @Test
    public void testInterruptExistingWorkFlow() {
        eventState = new EventState(EVENT_STATE_ID, EventName.MEAL, PHONE_NUMBER);
        when(eventStateDao.byPhoneNumber(eq(PHONE_NUMBER))).thenReturn(eventState);
        event.setTextMessage("SIGNUP");
        Assert.assertEquals(RETURN_MESSAGE, eventRouter.handleEvent(event));
        verify(eventStateDao, times(1)).update(eq(eventState));
        verify(eventStateDao, times(0)).insert(any(EventState.class));
    }

    // @Test
    public void testContinueRegisterWorkFlow() {
        eventState = new EventState(EVENT_STATE_ID, EventName.REGISTER, PHONE_NUMBER);
        when(eventStateDao.byPhoneNumber(eq(PHONE_NUMBER))).thenReturn(eventState);
        Assert.assertEquals(RETURN_MESSAGE, eventRouter.handleEvent(event));
        verify(eventStateDao, never()).insert(any(EventState.class));
    }
}
