package com.tp.webtp.services;

import com.tp.webtp.dao.EventDao;
import com.tp.webtp.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    EventDao eventDao;

    @InjectMocks
    EventService eventService;

    @Test
    void cannotGetSerieWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventService.getBySerieId(null);
        });
        verify(eventDao, never()).findById(any());
    }

    @Test
    void cannotGetEventWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventService.getEventByEventId(null);
        });
        verify(eventDao, never()).findById(any());
    }

    @Test
    void cannotSaveEventWithNullEvent() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventService.saveEvent(null);
        });
        verify(eventDao, never()).findById(any());
    }

    @Test
    void cannotDeleteEventWithNullEvent() {
        assertThrows(IllegalArgumentException.class, () -> {
            eventService.deleteEvent(null);
        });
        verify(eventDao, never()).findById(any());
    }
}
