package com.tp.webtp.services;

import com.tp.webtp.dao.TagDao;
import com.tp.webtp.service.TagService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    TagDao tagDao;

    @InjectMocks
    TagService tagService;

    @Test
    void cannotGetTagWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagByTagId(null);
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetTagsByUserWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagsByUserId(null);
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetEventsByTagNameAndUserIdWithNullTagName() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getEventsByTagNameAndUserId(null, UUID.randomUUID());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetEventsByTagNameAndUserIdWithEmptyTagName() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getEventsByTagNameAndUserId("", UUID.randomUUID());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetEventsByTagNameAndUserIdWithBlankTagName() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getEventsByTagNameAndUserId("       ", UUID.randomUUID());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetEventsByTagNameAndUserIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getEventsByTagNameAndUserId("tagNameTest", null);
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetTagsByUserIdAndEventIdWithNullEventId() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagsByUserIdAndEventId(null, UUID.randomUUID());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetTagsByUserIdAndEventIdWithNullUserId() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagsByUserIdAndEventId(UUID.randomUUID(), null);
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetEventsDateByTagNameAndUserIdWithNullTagName() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getEventsDateByTagNameAndUserId(null, UUID.randomUUID());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetEventsDateByTagNameAndUserIdWithEmptyTagName() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getEventsDateByTagNameAndUserId("", UUID.randomUUID());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetEventsDateByTagNameAndUserIdWithBlankTagName() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getEventsDateByTagNameAndUserId("       ", UUID.randomUUID());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetEventsDateByTagNameAndUserIdWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getEventsDateByTagNameAndUserId("tagNameTest", null);
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetTagFrequencyBetweenDatesWithNullTagName() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagFrequencyBetweenDates(null, UUID.randomUUID(), new Date(), new Date());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetTagFrequencyBetweenDatesWithBlankTagName() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagFrequencyBetweenDates("      ", UUID.randomUUID(), new Date(), new Date());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetTagFrequencyBetweenDatesWithEmptyTagName() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagFrequencyBetweenDates("", UUID.randomUUID(), new Date(), new Date());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetTagFrequencyBetweenDatesWithNullUserId() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagFrequencyBetweenDates("tagValide", null, new Date(), new Date());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetTagFrequencyBetweenDatesWithNullDateDebut() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagFrequencyBetweenDates("tagValide", UUID.randomUUID(), null, new Date());
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetTagFrequencyBetweenDatesWithNullDateFin() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagFrequencyBetweenDates("tagValide", UUID.randomUUID(), new Date(), null);
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotGetTagFrequencyBetweenDatesWithDateDebutAfterDateFin() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.getTagFrequencyBetweenDates("tagValide", UUID.randomUUID(), new Date(2), new Date(1));
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotSaveTagWithNullTag() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.saveTag(null);
        });
        verify(tagDao, never()).findById(any());
    }

    @Test
    void cannotDeleteTagWithNullTag() {
        assertThrows(IllegalArgumentException.class, () -> {
            tagService.deleteTag(null);
        });
        verify(tagDao, never()).findById(any());
    }
}
