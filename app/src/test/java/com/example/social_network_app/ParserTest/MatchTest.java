package com.example.social_network_app.ParserTest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicParser;
import com.example.social_network_app.Tokenizer_Parser.Post.PostParser;

import org.junit.Test;

public class MatchTest {
    @Test
    public void MusicMatchTest(){
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

    @Test
    public void PostMatchTest(){
        Post post = new Post(1,1,1,"Jack","abcd","2021-09-12 12:12:36",500);
        PostParser parser1 = new PostParser("@Jack");
        PostParser parser2 = new PostParser("*<200");
        PostParser parser3 = new PostParser("*>300");
        PostParser parser4 = new PostParser("@j");
        PostParser parser5 = new PostParser("XY");
        assertTrue(parser1.isMatched(post));
        assertFalse(parser2.isMatched(post));
        assertTrue(parser3.isMatched(post));
        assertTrue(parser4.isMatched(post));
        assertFalse(parser5.isMatched(post));
    }

}
