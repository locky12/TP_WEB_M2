package com.tp.webtp.services;

import com.tp.webtp.dao.SerieDao;
import com.tp.webtp.service.SerieService;
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
public class SerieServiceTest {

    @Mock
    SerieDao serieDao;

    @InjectMocks
    SerieService serieService;

    @Test
    void cannotGetSerieWithNullId() {
        assertThrows(IllegalArgumentException.class, () -> {
            serieService.getSerieBySerieId(null);
        });
        verify(serieDao, never()).findById(any());
    }

    @Test
    void cannotSaveSerieWithNullSerie() {
        assertThrows(IllegalArgumentException.class, () -> {
            serieService.saveSerie(null);
        });
        verify(serieDao, never()).findById(any());
    }

    @Test
    void cannotDeleteSerieWithNullSerie() {
        assertThrows(IllegalArgumentException.class, () -> {
            serieService.deleteSerie(null);
        });
        verify(serieDao, never()).findById(any());
    }
}
