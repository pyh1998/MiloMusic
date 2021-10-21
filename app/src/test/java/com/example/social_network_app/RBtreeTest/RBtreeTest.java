package com.example.social_network_app.RBtreeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.DataStructure.Node;
import com.example.social_network_app.DataStructure.RBTree;
import com.example.social_network_app.GlobalVariable;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicParser;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RBtreeTest {
    Music music1 = new Music(1,"name1","artist1","album1","2021-01-01",4.5,"picture1","1,2");
    Music music2 = new Music(2,"name2","artist2","album2","2021-03-01",4.5,"picture2","1,2");
    Music music3 = new Music(3,"name3","artist3","album3","2021-05-01",4.3,"picture3","1,2");
    Music music4 = new Music(4,"name4","artist4","album4","2021-05-01",4.3,"picture4","1,2");
    Music music5 = new Music(5,"name5","artist5","album5","2021-06-01",4.6,"picture5","1,2");
    List<Music> musicList = new ArrayList<>();

    /**
     * Test the RBtree to store the rate of music and the music
     */
    @Test
    public void musicRateTest(){
        RBTree<Double> rateTree = new RBTree<>();
        rateTree.insert(new Node<>(music1.getRate(),music1));
        rateTree.insert(new Node<>(music2.getRate(),music2));
        rateTree.insert(new Node<>(music3.getRate(),music3));
        rateTree.insert(new Node<>(music4.getRate(),music4));
        rateTree.insert(new Node<>(music5.getRate(),music5));
        assertEquals("value:4.5value:4.3value:4.6",rateTree.preOrder());

        Node<Double> node = rateTree.search(4.5);
        LinkedList<Music> music = new LinkedList<>();
        music.add(music1);
        music.add(music2);
        assertEquals(music,node.getObjects());
        assertEquals(0,node.getValue().compareTo(4.5));
        assertEquals(0,node.getLeft().getValue().compareTo(4.3));
        assertEquals(0,node.getRight().getValue().compareTo(4.6));

        Node<Double> node2 = rateTree.search(4.3);
        assertEquals(0,node2.getParent().getValue().compareTo(4.5));
    }

    /**
     * Test the RBtree to store the date of music and the music
     */
    @Test
    public void musicDateTest(){
        RBTree<String> dateTree = new RBTree<>();
        dateTree.insert(new Node<>(music1.getReleaseDate(),music1));
        dateTree.insert(new Node<>(music2.getReleaseDate(),music2));
        dateTree.insert(new Node<>(music3.getReleaseDate(),music3));
        dateTree.insert(new Node<>(music4.getReleaseDate(),music4));
        dateTree.insert(new Node<>(music5.getReleaseDate(),music5));
        System.out.println(dateTree.preOrder());
        assertEquals("value:2021-03-01value:2021-01-01value:2021-05-01value:2021-06-01",dateTree.preOrder());

        Node<String> node = dateTree.search("2021-05-01");
        LinkedList<Music> music = new LinkedList<>();
        music.add(music3);
        music.add(music4);
        assertEquals(music,node.getObjects());
        assertEquals(0,node.getValue().compareTo("2021-05-01"));
        assertEquals(0,node.getRight().getValue().compareTo("2021-06-01"));
        assertEquals(0,node.getParent().getValue().compareTo("2021-03-01"));
    }

}
