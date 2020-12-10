package com.tp.webtp;

import com.tp.webtp.entity.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TagTest {

    @Test
    void cannotBuildWithTagNameEmpty () {
        assertThrows(IllegalArgumentException.class, () -> new Tag(""));
    }

    @Test
    void cannotBuildWithNullTagName () {
        assertThrows(IllegalArgumentException.class, () -> new Tag(null));
    }

    @Test
    void mustBeReturnItsTagName () {
        assertThat(new Tag("toto").getTagName().equals("toto")).isTrue();
    }

    @Test
    void mustBeNewTagName () {
        Tag tag = new Tag("toto");
        tag.setTagName("titi");
        assertThat(tag.getTagName().equals("titi")).isTrue();
    }

}



