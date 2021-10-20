package com.example.social_network_app.DataGenerate;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class DataGenerate {

    public final static int music_num = 20;
    public final static int user_num = 21;
    public final static int review_num = 50;
    public final static int post_num = 1500;

    public final static String postListPath = "app/src/main/assets/post.xml";
    public final static String reviewPath = "app/src/main/assets/userReviews.csv";
    public final static String musicPath = "app/src/main/assets/music_list.json";
    public final static String userPath = "app/src/main/assets/user.json";

    public static void createData(int num) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = factory.newDocumentBuilder();
        Document document = db.newDocument();
        Random random = new Random();


        document.setXmlStandalone(true);
        Element postList = document.createElement("postList");

        for (int i = 0; i < num; i++) {
            Element post = document.createElement("post");

            Element music = document.createElement("music");
            Element user = document.createElement("user");
            Element userName = document.createElement("userName");
            Element datetime = document.createElement("datetime");
            Element userReviews = document.createElement("userReviews");
            Element likeCount = document.createElement("likeCount");

            int music_id = random.nextInt(music_num) + 1;
            music.setTextContent(String.valueOf(music_id));
            int user_id = random.nextInt(user_num) + 1;
            user.setTextContent(String.valueOf(user_id));
            userName.setTextContent(getUserNameById(user_id));
            String date = getRandomDatetime();
            datetime.setTextContent(date);

            String review = getRandomReview();
            userReviews.setTextContent(review);

            int count = random.nextInt(500);
            likeCount.setTextContent(String.valueOf(count));

            post.appendChild(music);
            post.appendChild(user);
            post.appendChild(userName);
            post.appendChild(datetime);
            post.appendChild(userReviews);
            post.appendChild(likeCount);

            post.setAttribute("id",i+1+"");
            postList.appendChild(post);
        }

        document.appendChild(postList);
        //transform a source tree into a result tree
        //Used to process XML from a variety of sources and write the transformation output to a variety of sinks (see transformer documentation)
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        //set encoding
        transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
        //indent the output document
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        //create document
        DOMSource source = new DOMSource(document); //Acts as a holder for a transformation Source tree in the form of a Document Object Model (DOM) tree.
        StreamResult result = new StreamResult(new File(postListPath));//Acts as a holder for a transformation result, which may be XML,..
        transformer.transform(source, result); //Transform the XML Source to a Result.


    }

    public static String getMusicNameById(int id){
        List<Music> musicList = new ArrayList<>();
        Gson gson = new Gson();
        JsonReader jsonReader = null;

        final Type CUS_LIST_TYPE = new TypeToken<List<Music>>() {}.getType();

        try{
            jsonReader = new JsonReader(new FileReader(musicPath));
        }catch (Exception e) {
            e.printStackTrace();
        }

        musicList = gson.fromJson(jsonReader, CUS_LIST_TYPE);
        for(Music music : musicList){
            if(music.getId()==id) return music.getName();
        }
        return null;
    }

    public static String getUserNameById(int id){
        List<User> userList = new ArrayList<>();
        Gson gson = new Gson();
        JsonReader jsonReader = null;

        final Type CUS_LIST_TYPE = new TypeToken<List<User>>() {}.getType();

        try{
            jsonReader = new JsonReader(new FileReader(userPath));
        }catch (Exception e) {
            e.printStackTrace();
        }

        userList = gson.fromJson(jsonReader, CUS_LIST_TYPE);
        for(User user : userList){
            if(user.getId()==id) return user.getName();
        }
        return null;
    }




    @SuppressLint("DefaultLocale")
    public static String getRandomDatetime(){
        Random random = new Random();
        return "2021-" +
                String.format("%02d", random.nextInt(9) + 1) +
                "-" +
                String.format("%02d", random.nextInt(30) + 1) +
                " " +
                String.format("%02d", random.nextInt(24)) +
                ":" +
                String.format("%02d", random.nextInt(60)) +
                ":" +
                String.format("%02d", random.nextInt(60));
    }

    public static List<String> getReviewList(){
        BufferedReader bufferedReader;
        ArrayList<String> result = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(reviewPath));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                result.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getRandomReview(){
        List<String> reviewList = getReviewList();
        Random random = new Random();
        int index = random.nextInt(review_num);
        return reviewList.get(index);
    }


    public static void main(String[] args) throws TransformerException, ParserConfigurationException {
        createData(post_num);
        System.out.println("Generate data successful!");
    }
}
