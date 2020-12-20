package com.tp.webtp.services;

import com.tp.webtp.dao.ShareDao;
import com.tp.webtp.entity.Role;
import com.tp.webtp.service.ShareService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ShareServiceTest {

    @Mock
    ShareDao shareDao;

    @InjectMocks
    ShareService shareService;

    @Test
    void cannotGetShareWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getShareByShareId(null);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetShareByUserWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getSharesByUserId(null);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetSeriesByUserWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getSeriesByUserId(null);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetSeriesByUserAndRoleWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getSeriesByUserIdAndRole(null, Role.OWNER);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetSeriesByUserAndRoleWithNullRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getSeriesByUserIdAndRole(UUID.randomUUID(), null);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetSeriesByUserAndNotRoleWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getSeriesByUserIdAndNotRole(null, Role.OWNER);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetSeriesByUserAndNotRoleWithNullRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getSeriesByUserIdAndRole(UUID.randomUUID(), null);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetFromUserIdAndSerieIdWithNullUserId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getFromUserIdAndSerieId(null, UUID.randomUUID());
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetFromUserIdAndSerieIdWithNullSerieId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getSeriesByUserIdAndRole(UUID.randomUUID(), null);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetFromUserIdAndSerieIdAndNotRoleWithNullUserId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getFromUserIdAndSerieIdAndNotRole(null, UUID.randomUUID(), Role.OWNER);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetFromUserIdAndSerieIdAndNotRoleWithNullSerieId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getFromUserIdAndSerieIdAndNotRole(UUID.randomUUID(), null, Role.OWNER);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetFromUserIdAndSerieIdAndNotRoleWithNullRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getFromUserIdAndSerieIdAndNotRole(UUID.randomUUID(), UUID.randomUUID(), null);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetFromUserIdAndSerieIdAndRoleWithNullUserId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getFromUserIdAndSerieIdAndRole(null, UUID.randomUUID(), Role.OWNER);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetFromUserIdAndSerieIdAndRoleWithNullSerieId() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getFromUserIdAndSerieIdAndRole(UUID.randomUUID(), null, Role.OWNER);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotGetFromUserIdAndSerieIdAndRoleWithNullRole() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.getFromUserIdAndSerieIdAndRole(UUID.randomUUID(), UUID.randomUUID(), null);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotSaveShareWithNullShare() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.saveShare(null);
        });
        verify(shareDao, never()).findById(any());
    }

    @Test
    void cannotDeleteShareWithNullShare() {
        assertThrows(IllegalArgumentException.class, () -> {
            shareService.deleteShare(null);
        });
        verify(shareDao, never()).findById(any());
    }
}
