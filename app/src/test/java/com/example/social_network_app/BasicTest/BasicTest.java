package com.example.social_network_app.BasicTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDao;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDaoInterface;
import com.example.social_network_app.Basic_classes.MusicDao.MusicTag;
import com.example.social_network_app.Basic_classes.PostDao.Post;
import com.example.social_network_app.Basic_classes.PostDao.PostDao;
import com.example.social_network_app.Basic_classes.PostDao.PostDaoInterface;
import com.example.social_network_app.Basic_classes.UserDao.CurrentUser;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.Basic_classes.UserDao.UserDao;
import com.example.social_network_app.Basic_classes.UserDao.UserDaoInterface;

import org.junit.Test;

public class BasicTest {

    @Test
    public void MusicTest(){
        Music music = new Music(1,"music1","artist1","album1","2021-10-10",4.5,"picture1","1,2");
        assertEquals(1,music.getId());
        assertEquals("music1",music.getName());
        assertEquals("artist1",music.getArtist());
        assertEquals("album1",music.getAlbum());
        assertEquals("2021-10-10",music.getReleaseDate());
        assertEquals(4.5,music.getRate(),0);
        assertEquals("picture1",music.getPicture());
        assertEquals("alternative",music.getTag()[0].toString());
        assertEquals("blues",music.getTag()[1].toString());
        music.setId(2);
        music.setName("music2");
        music.setArtist("artist2");
        music.setAlbum("album2");
        music.setReleaseDate("2021-10-11");
        music.setRate(4.6);
        music.setPicture("picture2");
        music.setTag("1");
        assertEquals(2,music.getId());
        assertEquals("music2",music.getName());
        assertEquals("artist2",music.getArtist());
        assertEquals("album2",music.getAlbum());
        assertEquals("2021-10-11",music.getReleaseDate());
        assertEquals(4.6,music.getRate(),0);
        assertEquals("picture2",music.getPicture());
        assertEquals("alternative",music.getTag()[0].toString());

        assertEquals(1,music.getSortOrder());
        assertEquals(1,music.getSortBy());
        music.setSortOrder(-1,-1);
        assertEquals(-1,music.getSortOrder());
        assertEquals(-1,music.getSortBy());
        MusicDaoInterface musicDaoInterface = new MusicDao();
    }

    @Test
    public void MusicTagTest(){
        assertEquals("alternative",MusicTag.getTagById(1).toString());
        assertEquals("blues",MusicTag.getTagById(2).toString());
        assertEquals("country",MusicTag.getTagById(3).toString());
        assertEquals("dance",MusicTag.getTagById(4).toString());
        assertEquals("electronica",MusicTag.getTagById(5).toString());
        assertEquals("hiphop",MusicTag.getTagById(6).toString());
        assertEquals("pop",MusicTag.getTagById(7).toString());
        assertEquals("rap",MusicTag.getTagById(8).toString());
        assertEquals("rock",MusicTag.getTagById(9).toString());
        assertEquals("R&B",MusicTag.getTagById(10).toString());
        assertNull(MusicTag.getTagById(11));



    }

    @Test
    public void UserTest(){
        User user = new User(1,"name1","email1",15,"male","head1",50);
        assertEquals(1,user.getId());
        assertEquals("name1",user.getName());
        assertEquals("email1",user.getEmail());
        assertEquals(15,user.getAge());
        assertEquals("male",user.getSex());
        assertEquals("head1",user.getHead());
        assertEquals(50,user.getFans());
        user.setId(2);
        user.setName("name2");
        user.setEmail("email2");
        user.setAge(16);
        user.setSex("female");
        user.setHeed("head2");
        user.setFans(51);
        assertEquals(2,user.getId());
        assertEquals("name2",user.getName());
        assertEquals("email2",user.getEmail());
        assertEquals(16,user.getAge());
        assertEquals("female",user.getSex());
        assertEquals("head2",user.getHead());
        assertEquals(51,user.getFans());
        UserDaoInterface userDaoInterface = new UserDao();
    }

    @Test
    public void CurrentUserTest(){
        User user = new User(1,"name1","email1",15,"male","head1",50);
        User user2 = new User(2,"name1","email1",15,"male","head1",50);
        CurrentUser currentUser = CurrentUser.getInstance(user);
        assertEquals(1,currentUser.getId());
        currentUser = CurrentUser.getInstance(user2);
        assertEquals(1,currentUser.getId());
        CurrentUser.delUser();
        currentUser = CurrentUser.getInstance(user2);
        assertEquals(2,currentUser.getId());
    }

    @Test
    public void PostTest(){
        Post post = new Post(1,1,1,"name1","a","2021-10-10 12:12:15",50);
        assertEquals(1,post.getId());
        assertEquals(1,post.getMusic_id());
        assertEquals("name1",post.getUser_name());
        assertEquals("a",post.getUserReviews());
        assertEquals("2021-10-10 12:12:15",post.getDatetime());
        assertEquals(50,post.getLikeCount());

        post.setId(2);
        post.setLikeCount(51);
        assertEquals(2,post.getId());
        assertEquals(51,post.getLikeCount());
        assertEquals("Post{id=2, music_id=1, user_id=1, userReviews='a', datetime='2021-10-10 12:12:15'}",post.toString());

        Post post2 = new Post(2,1,1,"name1","a","2021-10-11 12:12:15",50);
        assertTrue(post2.compareTo(post)>0);
        PostDaoInterface postDaoInterface = new PostDao();
    }


}
