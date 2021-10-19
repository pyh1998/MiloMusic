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
    //����AMapLocationClient�����
    public AMapLocationClient mLocationClient = null;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private String mLocation = "";
    //�����Ȩ��
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

        /*�ߵ¶�λ*/
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
     * ����Ȩ��
     */
    private void requestPermissions() {
        if (PermissionsUtil.hasPermission(this,mPermissions)) {
            //�з���Ȩ��
            getLocalLocation();
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted( String[] permissions) {
                    //�û������˷���Ȩ��
                    getLocalLocation();
                }
                @Override
                public void permissionDenied( String[] permissions) {
                    //�û��ܾ��˷��ʵ�����
                    Toast.makeText(MainActivity.this,"�ܾ��˷��ʵ�����",Toast.LENGTH_LONG).show();
                }
            }, mPermissions);
        }
    }


    private void getLocalLocation() {
        //��ʼ����λ
        initLocation();

        startLocation();
    }


    /**
     * ��ʼ����λ
     *
     */
    private void initLocation(){
        //��ʼ��client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        locationOption = getDefaultOption();
        //���ö�λ����
        locationClient.setLocationOption(locationOption);
        // ���ö�λ����
        locationClient.setLocationListener(locationListener);
    }

    /**
     * Ĭ�ϵĶ�λ����
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//��ѡ�����ö�λģʽ����ѡ��ģʽ�и߾��ȡ����豸�������硣Ĭ��Ϊ�߾���ģʽ
        mOption.setGpsFirst(false);//��ѡ�������Ƿ�gps���ȣ�ֻ�ڸ߾���ģʽ����Ч��Ĭ�Ϲر�
        mOption.setHttpTimeOut(30000);//��ѡ��������������ʱʱ�䡣Ĭ��Ϊ30�롣�ڽ��豸ģʽ����Ч
        mOption.setInterval(2000);//��ѡ�����ö�λ�����Ĭ��Ϊ2��
        mOption.setNeedAddress(true);//��ѡ�������Ƿ񷵻�������ַ��Ϣ��Ĭ����true
        mOption.setOnceLocation(false);//��ѡ�������Ƿ񵥴ζ�λ��Ĭ����false
        mOption.setOnceLocationLatest(false);//��ѡ�������Ƿ�ȴ�wifiˢ�£�Ĭ��Ϊfalse.�������Ϊtrue,���Զ���Ϊ���ζ�λ��������λʱ��Ҫʹ��
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//��ѡ�� �������������Э�顣��ѡHTTP����HTTPS��Ĭ��ΪHTTP
        mOption.setSensorEnable(false);//��ѡ�������Ƿ�ʹ�ô�������Ĭ����false
        mOption.setWifiScan(true); //��ѡ�������Ƿ���wifiɨ�衣Ĭ��Ϊtrue���������Ϊfalse��ͬʱֹͣ����ˢ�£�ֹͣ�Ժ���ȫ������ϵͳˢ�£���λλ�ÿ��ܴ������
        mOption.setLocationCacheEnable(true); //��ѡ�������Ƿ�ʹ�û��涨λ��Ĭ��Ϊtrue
        mOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.DEFAULT);//��ѡ�������������Ϣ�����ԣ�Ĭ��ֵΪĬ�����ԣ��������ڵ���ѡ�����ԣ�
        return mOption;
    }

    /**
     * ��λ����
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                mLocation = location.getLongitude() + "\n" + location.getLatitude() + "\n" + location.getAddress();
                stopLocation();

            } else {
                mLocation = "��λʧ�ܣ�loc is null";
            }
            tv_location.setText(mLocation);

        }
    };


    // ���ݿؼ���ѡ���������ö�λ����
    private void resetOption() {
        // �����Ƿ���Ҫ��ʾ��ַ��Ϣ
        locationOption.setNeedAddress(true);
        /**
         * �����Ƿ����ȷ���GPS��λ��������30����GPSû�з��ض�λ�����������綨λ
         * ע�⣺ֻ���ڸ߾���ģʽ�µĵ��ζ�λ��Ч��������ʽ��Ч
         */
        locationOption.setGpsFirst(true);
        // �����Ƿ�������
        locationOption.setLocationCacheEnable(true);
        // �����Ƿ񵥴ζ�λ
        locationOption.setOnceLocation(true);
        //�����Ƿ�ȴ��豸wifiˢ�£��������Ϊtrue,���Զ���Ϊ���ζ�λ��������λʱ��Ҫʹ��
        locationOption.setOnceLocationLatest(true);
        //�����Ƿ�ʹ�ô�����
        locationOption.setSensorEnable(true);
    }

    /**
     * ��ʼ��λ
     *
     */
    private void startLocation(){
        tv_location.setText("begin location");
        //���ݿؼ���ѡ���������ö�λ����
        resetOption();
        // ���ö�λ����
        locationClient.setLocationOption(locationOption);
        // ������λ
        locationClient.startLocation();
        tv_location.setText("locating...");
    }

    /**
     * ֹͣ��λ
     *
     */
    private void stopLocation(){
        // ֹͣ��λ
        locationClient.stopLocation();
    }

    /**
     * ���ٶ�λ
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * ���AMapLocationClient���ڵ�ǰActivityʵ�����ģ�
             * ��Activity��onDestroy��һ��Ҫִ��AMapLocationClient��onDestroy
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