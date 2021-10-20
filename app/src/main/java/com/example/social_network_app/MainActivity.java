package com.example.social_network_app;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.social_network_app.Basic_classes.MusicDao.Music;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDao;
import com.example.social_network_app.Basic_classes.MusicDao.MusicDaoInterface;
import com.example.social_network_app.Basic_classes.UserDao.User;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicParser;
import com.example.social_network_app.Tokenizer_Parser.Music.MusicToken;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;


public class MainActivity extends AppCompatActivity {
    private String TAG = "MainActivity";
    List<Music> MusicList;
    List<Music> resultList = new ArrayList<>();
    List<Map<String,Object>> resultMapList = new ArrayList<>();
    User currentUser;

    ListView resultView;
    ImageButton searchButton;
    TextView tv_userName;
    ImageView iv_userHead;
    TextView tv_location;
    EditText search;
    // Declare the AMapLocationClient class object    public AMapLocationClient mLocationClient = null;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String mLocation = "";

    //Request permission
    private static final String[] mPermissions = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        currentUser = (User) getIntent().getSerializableExtra("CurrentUser");


        initView();

        /*Scott positioning*/
        requestPermissions();
    }

    private void initView() {
        resultView = findViewById(R.id.rv_musiclist);
        searchButton = findViewById(R.id.ib_search);
        tv_userName = findViewById(R.id.tv_username);
        iv_userHead = findViewById(R.id.iv_userhead);
        search = findViewById(R.id.search);
        tv_location = findViewById(R.id.tv_location);

        GlobalVariable global = (GlobalVariable) getApplication();
        MusicList = global.getMusicList();
        resultList = MusicList;
        showMusic(resultList);
        showUser();

        resultView.setOnItemClickListener(CommentViewListener);
        searchButton.setOnClickListener(searchResultListener);
    }

    private final AdapterView.OnItemClickListener CommentViewListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(),CommentsActivity.class);
            Music music = resultList.get(i);
            Bundle bundle = new Bundle();
            bundle.putSerializable("CurrentUser", currentUser);
            bundle.putSerializable("Music",music);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    private final View.OnClickListener searchResultListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String searchText = search.getText().toString();
            if(!searchText.equals("")){
                MusicParser parser = new MusicParser(searchText);
                try{
                    if(!parser.isValid()){
                        List<MusicToken> validList = parser.getValidList();
                        if(validList.size() == 0){
                            Toast.makeText(getApplicationContext(),"Invalid Input!",Toast.LENGTH_LONG).show();
                            return;
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Partial Valid Input! Valid condition: "+validList.toString(),Toast.LENGTH_LONG).show();
                        }
                    }

                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Invalid Input! IllegalTokenException!",Toast.LENGTH_LONG).show();
                }
                resultList = new ArrayList<>();
                for(Music music : MusicList){
                    if(parser.isMatched(music)){
                        resultList.add(music);
                    }
                }
                Log.e("!!!!!!!!!!!!!!",resultList.toString());
                showMusic(resultList);
                Toast toast = Toast.makeText(getApplicationContext(),"Search successfully! "+resultList.size()+" result(s)",Toast.LENGTH_LONG);
                //toast.setGravity(Gravity.TOP,0,0);
                toast.show();
            }
        }
    };



    @SuppressLint("SetTextI18n")
    public void showUser(){
        String name = currentUser.getName();
        String head_img = currentUser.getHead();
        Log.e("!!!!!!!!!!!!!!",String.valueOf(head_img));
        tv_userName.setText(name + " !");
        try {
            Field field = R.drawable.class.getField(head_img);
            int img_id = field.getInt(field.getName());
            iv_userHead.setImageResource(img_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void showMusic(List<Music> list){
        resultMapList.clear();
        for(int i =0;i<list.size();i++){
            Map<String,Object> map = new HashMap<>();
            Music music = list.get(i);
            try {
                Field field = R.drawable.class.getField(music.getPicture());
                int img_id = field.getInt(field.getName());
                map.put("m_img",img_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            map.put("m_name",music.getName());
            map.put("m_artist",music.getArtist());
            map.put("m_date",music.getReleaseDate());
            map.put("m_rate",music.getRate());
//            Log.e("!!!!!!!!!",map.toString());
            resultMapList.add(map);
        }
        SimpleAdapter listAdapter = new SimpleAdapter(
                this,
                resultMapList,
                R.layout.music_item,
                new String[]{"m_img","m_name","m_artist","m_date","m_rate"},
                new int[]{R.id.m_img,R.id.m_name,R.id.m_artist,R.id.m_date,R.id.m_rate}
        );
        resultView.setAdapter(listAdapter);
    }


    @Override
    protected
    void onSaveInstanceState(Bundle outState) {
        Log.d(TAG," -- onSaveInstanceState");
        Bundle bundle = new Bundle();
        bundle.putSerializable("CurrentUser", currentUser);
        outState.putBundle("CurrentUser", bundle);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected
    void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG," -- onRestoreInstanceState");
        if(savedInstanceState != null) {
            Bundle bundle = savedInstanceState.getBundle("CurrentUser");
            currentUser = (User) bundle.getSerializable("CurrentUser");
        }}

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d(TAG," -- onConfigurationChanged");
//        if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
//
//            setContentView(R.layout.activity_main);
//        }else{
//
//            setContentView(R.layout.activity_main);
//        }
        setContentView(R.layout.activity_main);
        initView();
    }
    /**
     * Request permission
     */
    private void requestPermissions() {
        if (PermissionsUtil.hasPermission(this,mPermissions)) {
            //Have access
            getLocalLocation();
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted( String[] permissions) {
                    //The user granted access
                    getLocalLocation();
                }
                @Override
                public void permissionDenied( String[] permissions) {
                    //The user rejected the request for access
                    Toast.makeText(MainActivity.this,"The request for access was denied",Toast.LENGTH_LONG).show();
                }
            }, mPermissions);
        }
    }


    private void getLocalLocation() {
        //Initial positioning
        initLocation();

        startLocation();
    }


    /**
     * code in this function is based on the code from github
     * url:https://github.com/qwd/OpenWeatherPlus-Android
     * Initial positioning
     *
     */
    private void initLocation(){
        //Initialize the client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //Setting Location Parameters
        locationClient.setLocationOption(locationOption);
        // Setting location Listening
        locationClient.setLocationListener(locationListener);
    }

    /**
     * code in this function are beased on the codes from TecentCloud
     * https://www.tabnine.com/web/assistant/code/rs/5c7c32112ef5570001da71d7#L130
     * Default location parameters
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//Optional: Set the positioning mode, including high precision, device only, and network only. The default mode is high precision
        mOption.setGpsFirst(false);//This parameter is optional. This parameter is available only in high-precision mode. Off by default
        mOption.setHttpTimeOut(30000);//Optional: Set the network request timeout period. The default value is 30 seconds. Invalid in device-only mode
        mOption.setInterval(2000);//Optional. Set the location interval. The default value is 2 seconds
        mOption.setNeedAddress(true);//Optional: Specifies whether to return the inverse geographic address information. The default is true
        mOption.setOnceLocation(false);//This parameter is optional. The default is false
        mOption.setOnceLocationLatest(false);//This parameter is optional. The default value is false. If this parameter is set to true, the system automatically changes to single location
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//Optional: Set the network request protocol. The value can be HTTP or HTTPS. The default for HTTP
        mOption.setSensorEnable(false);//Optional: Set whether to use a sensor. The default is false
        mOption.setWifiScan(true); //Optional. Enable or disable wifi scanning.This parameter is optional. The default value is true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//Optional, set the language of the inverse geographic information.
        return mOption;
    }

    /**
     *  code in my OnLocationChanged function is based on some code from CSDN
     *  url:https://blog.csdn.net/dr_abandon/article/details/77946585
     * Positioning to monitor
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                mLocation = location.getLongitude() + "\n" + location.getLatitude() + "\n" + location.getAddress();
                stopLocation();

            } else {
                mLocation = "locate failure, loc is null";
            }
            tv_location.setText(mLocation);

        }
    };

    //code in this function are based on the code from CSDN
    //url:https://blog.csdn.net/xiayiye5/article/details/88695284
    // Resets the positioning parameters based on the selection of the control
    private void resetOption() {
        // Set whether to display the address information
        locationOption.setNeedAddress(true);
        /**
         * Set whether to return the GPS location result preferentially.
         *
         */
        locationOption.setGpsFirst(true);
        // Set whether to enable caching
        locationOption.setLocationCacheEnable(true);
        // Set whether to locate the device once
        locationOption.setOnceLocation(true);
        //Set whether to wait for the wifi refresh of the device.
        locationOption.setOnceLocationLatest(true);
        //Sets whether to use sensors
        locationOption.setSensorEnable(true);
    }

    /**
     * positioning
     *
     */
    private void startLocation(){
        tv_location.setText("begin location");
        //Resets the positioning parameters based on the selection of the control
        resetOption();
        // Setting Location Parameters
        locationClient.setLocationOption(locationOption);
        // positioning start
        locationClient.startLocation();
        tv_location.setText("locating...");
    }

    /**
     * positioning stop
     *
     */
    private void stopLocation(){
        // positioning stop
        locationClient.stopLocation();
    }

    /**
     * destroy positioning
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * If the AMapLocationClient is instantiated in the current Activity,
             * Be sure to execute the AMapLocationClient's onDestroy in the Activity's onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }
}