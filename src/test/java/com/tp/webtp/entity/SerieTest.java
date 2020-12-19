package com.tp.webtp.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;



import static org.junit.jupiter.api.Assertions.*;

class SerieTest {



    @Test
    void getTitle() {
        assertThat(new Serie("title","description").getTitle()).isEqualTo("title");
    }

    @Test
    void setTitle() {
        Serie serie = new Serie("title","description");
        serie.setTitle("t");
        assertThat(serie.getTitle()).isEqualTo("t");
    }

    @Test
    void getDescription() {
        assertThat(new Serie("title","description").getDescription()).isEqualTo("description");
    }

    @Test
    void setDescription() {
        Serie serie = new Serie("title","description");
        serie.setDescription("d");
        assertThat(serie.getDescription()).isEqualTo("d");
    }
}