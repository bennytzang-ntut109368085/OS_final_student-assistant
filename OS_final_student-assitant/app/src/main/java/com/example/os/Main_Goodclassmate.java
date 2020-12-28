package com.example.os;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

public class Main_Goodclassmate extends ListActivity {
    public static final int REQUESTCODE = 123;
    private static final String BUNDLEID = "commit";
    protected Button insertbtnobj, clsbtnobj;
    private Context context;
    private CommentsDataSource datasource;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__goodclassmate);
        findViews();
        setReqOrientationFunc();
        MyHandler myHandler= new MyHandler(datasource);
        Thread t = new Thread(myHandler);
        t.start();
    }
    private View.OnClickListener ClsBtn = new View.OnClickListener(){
        public void onClick(View v){
            Main_Goodclassmate.this.onDestory();
        }
    };
    private View.OnClickListener InsertBtn = new View.OnClickListener(){
        public void onClick(View v){
            Intent IntentObj = new Intent();
            IntentObj.setClass(Main_Goodclassmate.this, Main_Goodclassmate_submit.class);
            Main_Goodclassmate.this.startActivityForResult(IntentObj,REQUESTCODE);

        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE){
            adapter.list = datasource.getAllComments();
            adapter.notifyDataSetChanged();
        }
    }
    protected void setReqOrientationFunc() {
        /* 設定螢幕不隨手機旋轉 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        /* 設定螢幕直向顯示 */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    protected void findViews() {
        this.context = this;
        insertbtnobj = findViewById(R.id.insertbtn);
        clsbtnobj = findViewById(R.id.clsbtn);
        clsbtnobj.setOnClickListener(ClsBtn);
        insertbtnobj.setOnClickListener(InsertBtn);
        datasource = new CommentsDataSource(this);
        datasource.open();
        List<com.example.os.Comment> values = datasource.getAllComments();
        if(values != null) {
            /* use the SimpleCursorAdapter to show the elements in a ListView */
            adapter = new MyAdapter(context,values);

            setListAdapter(adapter);
        }

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void onDestory() {
        super.onDestroy();
        System.exit(0);
    }

    class MyHandler extends Thread {
        Handler handler = new Handler();
        CommentsDataSource datasource;
        Runnable runnable =new Runnable() {
            @Override
            public void run() {
                datasource.open();
                List<com.example.os.Comment> values = datasource.getAllComments();
                if(values != null) {
                    System.out.println(values);
                    for(int i = 0; i < values.size(); i++){
                        int DataEndNum = Integer.parseInt(values.get(i).getDataend());
                        int WedtNum = Integer.parseInt(values.get(i).getWedt());
                        int limitday = DataEndNum - WedtNum;
                        long DateStartNum = Long.parseLong(values.get(i).getDatestart());
                        SimpleDateFormat sdf = new SimpleDateFormat("dd");
                        Date limitdate = null;
                        try {
                            limitdate = sdf.parse(limitday+"");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date date = new Date();
                        date.setTime(DateStartNum);
                        date.after(limitdate);
                        Calendar c = Calendar.getInstance();
                        if(c.getTime().getTime()>date.getTime()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context,"有筆金額還款時間已到",Toast.LENGTH_LONG).show();
                                }
                            });
                        };

                    }
                }
                handler.postDelayed(this,10000);
            }
        };

        public MyHandler(CommentsDataSource datasource) {
            this.datasource = datasource;
        }

        @Override
        public void run() {

            runOnUiThread(runnable);
        }

    }

}
