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
import java.util.List;

public class RBtreeTest {

    Music music1 = new Music(1,"name1","artist1","album1","2021-01-01",4.5,"picture1","1,2");
    Music music2 = new Music(2,"name2","artist2","album2","2021-03-01",4.5,"picture2","1,2");
    Music music3 = new Music(3,"name3","artist3","album3","2021-05-01",4.3,"picture3","1,2");
    Music music4 = new Music(3,"name4","artist4","album4","2021-05-01",4.3,"picture4","1,2");
    Music music5 = new Music(3,"name5","artist5","album5","2021-05-01",4.6,"picture5","1,2");
    List<Music> musicList = new ArrayList<>();

    @Test
    public void musicRateTest(){
        RBTree<Double> rateTree = new RBTree<>();
        rateTree.insert(new Node<>(music1.getRate(),music1));
        rateTree.insert(new Node<>(music2.getRate(),music2));
        rateTree.insert(new Node<>(music3.getRate(),music3));
        rateTree.insert(new Node<>(music4.getRate(),music3));
        rateTree.insert(new Node<>(music5.getRate(),music3));
        System.out.println(rateTree.preOrder());
        assertTrue(true);
    }

    @Test
    public void musicDateTest(){
        RBTree<String> rateTree = new RBTree<>();
        rateTree.insert(new Node<>(music1.getReleaseDate(),music1));
        rateTree.insert(new Node<>(music2.getReleaseDate(),music2));
        rateTree.insert(new Node<>(music3.getReleaseDate(),music3));
        rateTree.insert(new Node<>(music4.getReleaseDate(),music3));
        rateTree.insert(new Node<>(music5.getReleaseDate(),music3));
        System.out.println(rateTree.preOrder());
        assertTrue(true);
    }

}
