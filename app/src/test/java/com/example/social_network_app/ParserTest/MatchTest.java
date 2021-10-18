package com.example.social_network_app.ParserTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicParser;

import org.junit.Test;

public class MatchTest {
    @Test
    public void matchTest(){
        Music music = new Music(1,"music1","artist1","album1","2021-10-10",4.5,"picture1","1,2");
        MusicParser parser1 = new MusicParser("#blues;@artist1");
        MusicParser parser2 = new MusicParser("#blues;@artist2");
        MusicParser parser3 = new MusicParser("*>4.2");
        MusicParser parser4 = new MusicParser("*>4");
        MusicParser parser5 = new MusicParser("*<4.5");
        assertTrue(parser1.isMatched(music));
        assertFalse(parser2.isMatched(music));
        assertTrue(parser3.isMatched(music));
        assertTrue(parser4.isMatched(music));
        assertFalse(parser5.isMatched(music));
    }

}
