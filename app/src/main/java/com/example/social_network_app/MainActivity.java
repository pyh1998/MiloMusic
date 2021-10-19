package com.example.social_network_app;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
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
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String mLocation = "";
    //申请的权限
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

        /*高德定位*/
        requestPermissions();
    }

    private void initView() {
        resultView = findViewById(R.id.rv_musiclist);
        searchButton = findViewById(R.id.ib_search);
        tv_userName = findViewById(R.id.tv_username);
        iv_userHead = findViewById(R.id.iv_userhead);
        search = findViewById(R.id.search);
        tv_location = findViewById(R.id.tv_location);

        MusicList = getMusicList();
        resultList = MusicList;  //TODO search function(parse & token)
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
            MusicParser parser = new MusicParser(searchText);
            try{
                if(!parser.isValid()){
                    Toast.makeText(getApplicationContext(),"Invalid Input!",Toast.LENGTH_LONG).show();
                    return;
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

    public List<Music> getMusicList(){
        MusicDaoInterface music = new MusicDao();
        return music.findAllMusics(this);
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
     * 请求权限
     */
    private void requestPermissions() {
        if (PermissionsUtil.hasPermission(this,mPermissions)) {
            //有访问权限
            getLocalLocation();
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted( String[] permissions) {
                    //用户授予了访问权限
                    getLocalLocation();
                }
                @Override
                public void permissionDenied( String[] permissions) {
                    //用户拒绝了访问的申请
                    Toast.makeText(MainActivity.this,"拒绝了访问的申请",Toast.LENGTH_LONG).show();
                }
            }, mPermissions);
        }
    }


    private void getLocalLocation() {
        //初始化定位
        initLocation();

        startLocation();
    }


    /**
     * 初始化定位
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//可选，设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                mLocation = location.getLongitude() + "\n" + location.getLatitude() + "\n" + location.getAddress();
                stopLocation();

            } else {
                mLocation = "定位失败，loc is null";
            }
            tv_location.setText(mLocation);

        }
    };


    // 根据控件的选择，重新设置定位参数
    private void resetOption() {
        // 设置是否需要显示地址信息
        locationOption.setNeedAddress(true);
        /**
         * 设置是否优先返回GPS定位结果，如果30秒内GPS没有返回定位结果则进行网络定位
         * 注意：只有在高精度模式下的单次定位有效，其他方式无效
         */
        locationOption.setGpsFirst(true);
        // 设置是否开启缓存
        locationOption.setLocationCacheEnable(true);
        // 设置是否单次定位
        locationOption.setOnceLocation(true);
        //设置是否等待设备wifi刷新，如果设置为true,会自动变为单次定位，持续定位时不要使用
        locationOption.setOnceLocationLatest(true);
        //设置是否使用传感器
        locationOption.setSensorEnable(true);
    }

    /**
     * 开始定位
     *
     */
    private void startLocation(){
        tv_location.setText("begin location");
        //根据控件的选择，重新设置定位参数
        resetOption();
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
        tv_location.setText("locating...");
    }

    /**
     * 停止定位
     *
     */
    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
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