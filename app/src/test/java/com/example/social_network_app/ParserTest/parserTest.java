package com.example.social_network_app.ParserTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicParser;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicToken;
import com.example.social_network_app.Tokenizer_Parser.Post.PostParser;
import com.example.social_network_app.Tokenizer_Parser.Post.PostToken;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class parserTest {

    @Test
    public void tokenListTest1(){
        String s = "#abc;#xyz";
        MusicParser parser = new MusicParser(s);
        List<MusicToken> list = new ArrayList<>();
        list.add(new MusicToken("#abc"));
        list.add(new MusicToken("#xyz"));
        assertEquals(parser.getTokenList(),list);
    }

    @Test
    public void tokenListTest2(){
        String s = "@abc;@Mike";
        MusicParser parser = new MusicParser(s);
        List<MusicToken> list = new ArrayList<>();
        list.add(new MusicToken("@abc"));
        list.add(new MusicToken("@Mike"));
        assertEquals(parser.getTokenList(),list);
    }

    @Test
    public void tokenListTest3(){
        String s = "*>4.6";
        MusicParser parser = new MusicParser(s);
        List<MusicToken> list = new ArrayList<>();
        list.add(new MusicToken("*>4.6"));
        assertEquals(parser.getTokenList(),list);
    }

    @Test
    public void tokenListTest4(){
        String s = "abc;xyz";
        MusicParser parser = new MusicParser(s);
        List<MusicToken> list = new ArrayList<>();
        list.add(new MusicToken("abc"));
        list.add(new MusicToken("xyz"));
        assertEquals(parser.getTokenList(),list);
    }

    @Test
    public void tokenListTest5(){
        String s = "#abc;@Mike;*>4.3;asd";
        MusicParser parser = new MusicParser(s);
        List<MusicToken> list = new ArrayList<>();
        list.add(new MusicToken("#abc"));
        list.add(new MusicToken("@Mike"));
        list.add(new MusicToken("*>4.3"));
        list.add(new MusicToken("asd"));
        assertEquals(parser.getTokenList(),list);
    }

    @Test
    public void tokenListTest6(){
        String s = "@abc";
        PostParser parser = new PostParser(s);
        List<PostToken> list = new ArrayList<>();
        list.add(new PostToken("@abc"));
        assertEquals(parser.getTokenList(),list);
    }

    @Test
    public void tokenListTest7(){
        String s = "@abc;*<=500";
        PostParser parser = new PostParser(s);
        List<PostToken> list = new ArrayList<>();
        list.add(new PostToken("@abc"));
        list.add(new PostToken("*<=500"));
        assertEquals(parser.getTokenList(),list);
    }

    @Test
    public void tokenListTest8(){
        String s = "ax";
        PostParser parser = new PostParser(s);
        List<PostToken> list = new ArrayList<>();
        list.add(new PostToken("ax"));
        assertEquals(parser.getTokenList(),list);
    }

}
