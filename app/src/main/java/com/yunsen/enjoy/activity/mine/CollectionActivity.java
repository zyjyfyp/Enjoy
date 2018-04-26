package com.yunsen.enjoy.activity.mine;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.yunsen.enjoy.R;
import com.yunsen.enjoy.activity.mine.adapter.MyCollectWareAdapter;
import com.yunsen.enjoy.http.AsyncHttp;
import com.yunsen.enjoy.http.URLConstants;
import com.yunsen.enjoy.model.CollectWareData;
import com.yunsen.enjoy.widget.DialogProgress;
import com.yunsen.enjoy.widget.SwipeListView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 收藏页面
 */
public class CollectionActivity extends AppCompatActivity {

    private ArrayAdapter<String> mAdapter;
    private List<String> mDatas;
    private SharedPreferences spPreferences;
    private String strUrl;
    private DialogProgress progress;
    private ArrayList<CollectWareData> list;
    CollectWareData data;
    private SwipeListView my_list;
    MyCollectWareAdapter madapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.collect_ware);
        progress = new DialogProgress(CollectionActivity.this);

        spPreferences = getSharedPreferences("longuserset", MODE_PRIVATE);
        my_list = (SwipeListView) findViewById(R.id.my_list);
        loadWeather();

        // 不要直接Arrays.asList
        //		mDatas = new ArrayList<String>(Arrays.asList("HelloWorld", "Welcome", "Java", "Android", "Servlet", "Struts",
        //				"Hibernate", "Spring", "HTML5", "Javascript", "Lucene"));
        //		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
        //		mListView.setAdapter(mAdapter);

        //		mListView.setDelButtonClickListener(new DelButtonClickListener()
        //		{
        //			@Override
        //			public void clickHappend(final int position)
        //			{
        //				Toast.makeText(CollectionActivity.this, position + " : " + list.get(position), 1).show();
        ////				mAdapter.remove(mAdapter.getItem(position));
        //			}
        //		});
        //
        //		mListView.setOnItemClickListener(new OnItemClickListener()
        //		{
        //			@Override
        //			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        //			{
        //				Toast.makeText(CollectionActivity.this, position + " : " + list.get(position), 1).show();
        //			}
        //		});

        //		my_list.setOnItemClickListener(new OnItemClickListener() {
        //
        //			@Override
        //			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
        //					long arg3) {
        //
        //				String wareId = list.get(arg2).article_id;
        //				Intent intent = new Intent(CollectionActivity.this,WareInformationActivity.class);
        //				intent.putExtra("id", wareId);
        //				startActivity(intent);
        //			}
        //		});

        ImageView iv_fanhui = (ImageView) findViewById(R.id.iv_fanhui);
        iv_fanhui.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                finish();
            }
        });
    }


    public void onDestroy() {
        super.onDestroy();
            if (list!=null&&list.size() > 0) {
                list.clear();
                list = null;
            }
    }

    Handler handler = new Handler() {
        public void dispatchMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    try {

                        int id = (Integer) msg.obj;
                        System.out.println("id====================" + id);

                        String user_id = spPreferences.getString("user_id", "");
                        String str = URLConstants.REALM_URL + "/user_favorite_delete?user_id=" + user_id + "&id=" + id + "";
                        //				progress.CreateProgress();
                        System.out.println("1111====================" + str);

                        AsyncHttp.get(str, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int arg0, String arg1) {

                                System.out.println("====================" + arg1);
                                //						progress.CloseProgress();
                                super.onSuccess(arg0, arg1);
                                loadWeather();
                            }

                            @Override
                            public void onFailure(Throwable arg0, String arg1) {

                                super.onFailure(arg0, arg1);
                                System.out.println("1====================" + arg0);
                                System.out.println("2====================" + arg1);
                            }
                        }, null);
                        //				list = (ArrayList<CollectWareData>) msg.obj;
                        //				MyCollectWareAdapter adapter = new MyCollectWareAdapter(list,CollectionActivity.this);
                        //				mListView.setAdapter(adapter);
                        //				progress.CloseProgress();

                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    break;
                case 1:
                    try {
                        String article_id = (String) msg.obj;
                        System.out.println("article_id====================" + article_id);
						Intent intent = new Intent(CollectionActivity.this,WareInformationActivity.class);
						intent.putExtra("id", article_id);
						startActivity(intent);
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }

        ;
    };


    private void loadWeather() {
        progress.CreateProgress();
        String id = spPreferences.getString("user_id", "");
        strUrl = URLConstants.REALM_URL + "/get_user_favorite_list?user_id=" + id + "&page_size=10&page_index=1" +
                "&strwhere=&orderby=";

        System.out.println("收藏" + strUrl);
        AsyncHttp.get(strUrl, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int arg0, String arg1) {

                System.out.println("====================" + arg1);
                super.onSuccess(arg0, arg1);
                parse(arg1);
            }

            @Override
            public void onFailure(Throwable throwable, String s) {
                super.onFailure(throwable, s);
                progress.CloseProgress();
            }
        }, null);
    }

    private void parse(String st) {
        try {
            JSONObject jsonObject = new JSONObject(st);
            String status = jsonObject.getString("status");
            if (status.equals("y")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                list = new ArrayList<CollectWareData>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    data = new CollectWareData();
                    JSONObject object = jsonArray.getJSONObject(i);
                    data.id = object.getInt("id");
                    data.title = object.getString("title");
                    data.img_url = object.getString("img_url");
                    data.summary = object.getString("summary");
                    data.article_id = object.getString("article_id");
                    data.price = object.getString("price");
                    data.add_time = object.getString("add_time");
                    data.datatype = object.getString("datatype");
                    list.add(data);
                }

                //			MyCollectWareAdapter adapter = new MyCollectWareAdapter(list,CollectionActivity.this);
                //			mListView.setAdapter(adapter);
                madapter = new MyCollectWareAdapter(CollectionActivity.this, list, my_list.getRightViewWidth(), handler);
                my_list.setAdapter(madapter);
                if (list.size() > 0) {
                    MyCollectWareAdapter.aQuery.clear();//清除内存
                }
                data = null;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        progress.CloseProgress();
    }
}
