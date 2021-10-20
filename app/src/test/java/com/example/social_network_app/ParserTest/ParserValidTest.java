package com.example.social_network_app.ParserTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicParser;

import org.junit.Test;

public class ParserValidTest {

    @Test
    public void validTest(){
        Music music = new Music(1,"music1","artist1","album1","2021-10-10",4.5,"picture1","1,2");
        MusicParser parser1 = new MusicParser("#blues;@artist1");
        MusicParser parser2 = new MusicParser("#blues;@artist2");
        MusicParser parser3 = new MusicParser("#blues;@artist2;*>4.5");
        MusicParser parser4 = new MusicParser("*>4.9;xyz");
        MusicParser parser5 = new MusicParser("abcd");
        assertTrue(parser1.isValid());
        assertTrue(parser2.isValid());
        assertTrue(parser3.isValid());
        assertTrue(parser4.isValid());
        assertTrue(parser5.isValid());
    }

    @Test
    public void invalidTest(){
        Music music = new Music(1,"music1","artist1","album1","2021-10-10",4.5,"picture1","1,2");
        MusicParser parser1 = new MusicParser("#");
        MusicParser parser2 = new MusicParser("#;*");
        MusicParser parser3 = new MusicParser("#rap;*");
        MusicParser parser4 = new MusicParser(";;");
        MusicParser parser5 = new MusicParser("#rap;#pop;#blues;#R&B");
        MusicParser parser6 = new MusicParser("@abc;@xyz");
        MusicParser parser7 = new MusicParser("abc;xyz");
        assertFalse(parser1.isValid());
        assertFalse(parser2.isValid());
        assertFalse(parser3.isValid());
        assertFalse(parser4.isValid());
        assertFalse(parser5.isValid());
        assertFalse(parser6.isValid());
        assertFalse(parser7.isValid());


    }
}
