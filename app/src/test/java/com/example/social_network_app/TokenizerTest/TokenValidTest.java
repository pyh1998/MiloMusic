package com.example.social_network_app.TokenizerTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicParser;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicToken;

import org.junit.Test;

public class TokenValidTest {
    @Test
    public void validTest(){
        /*
        "#" followed by music tag,
        "@" followed by name,
        "*>" or "*>=" or "*<" or "*<=" or "*=" followed by music rate,
        "XXX" type directly means search music name.
         */
        String piece1 = "#blue";
        String piece2 = "@justin bieber";
        String piece3 = "*>4.3";
        String piece4 = "*4.3";
        String piece5 = "*>>4.3";
        String piece6 = "*==4.3";
        String piece7 = "*4..3";
        String piece8 = "*4.3.";
        String piece9 = "*country";
        String piece10 = "#";
        String piece11 = "@";
        String piece12 = "*";

        assertTrue(MusicToken.isValid(piece1));
        assertTrue(MusicToken.isValid(piece2));
        assertTrue(MusicToken.isValid(piece3));
        assertFalse(MusicToken.isValid(piece4));
        assertFalse(MusicToken.isValid(piece5));
        assertFalse(MusicToken.isValid(piece6));
        assertFalse(MusicToken.isValid(piece7));
        assertFalse(MusicToken.isValid(piece8));
        assertFalse(MusicToken.isValid(piece9));
        assertFalse(MusicToken.isValid(piece10));
        assertFalse(MusicToken.isValid(piece11));
        assertFalse(MusicToken.isValid(piece12));

    }
}
