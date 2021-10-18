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
        String piece1 = "#tag1";

        assertTrue(MusicToken.isValid(piece1));
    }
}
