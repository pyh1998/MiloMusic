package com.example.social_network_app.Basic_classes.PostDao;

import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class PostDao implements PostDaoInterface {

//    @Override
//    public List<Post> findAllPosts(Context context) {
//        String myjson = getJson(context, "post.json");
//        Gson gson = new Gson();
//        return  gson .fromJson(myjson, new TypeToken<List<Post>>(){}.getType());
//    }
//
//    public static String getJson(Context context, String fileName){
//        StringBuilder stringBuilder = new StringBuilder();
//        AssetManager assetManager = context.getAssets();
//        try {
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
//                    assetManager.open(fileName),"utf-8"));
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
//                stringBuilder.append(line);
//            }
//            bufferedReader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return stringBuilder.toString();
//    }

    @Override
    public List<Post> findAllPosts(Context context) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        ArrayList<Post> postList = new ArrayList<>();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(context.getAssets().open("post.xml"));

            NodeList nodeList = document.getElementsByTagName("post");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String music = element.getElementsByTagName("music").item(0).getTextContent();
                    String user = element.getElementsByTagName("user").item(0).getTextContent();
                    String user_name = element.getElementsByTagName("userName").item(0).getTextContent();
                    String datetime = element.getElementsByTagName("datetime").item(0).getTextContent();
                    String userReviews = element.getElementsByTagName("userReviews").item(0).getTextContent();
                    String likeCount = element.getElementsByTagName("likeCount").item(0).getTextContent();
                    Post post = new Post(i + 1, Integer.parseInt(music),Integer.parseInt(user),user_name,userReviews,datetime,Integer.parseInt(likeCount));
                    postList.add(post);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postList;
    }

}
