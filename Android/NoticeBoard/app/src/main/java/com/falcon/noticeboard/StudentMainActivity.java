package com.falcon.noticeboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class StudentMainActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);

        listView = findViewById(R.id.studentListView);
        populateListView(null);
    }

    public void populateListView(View view) {
        String[] noticeTitles = Controller.getNoticeTitles(this);
        listView.setAdapter(new StudentNoticeAdapter(this, noticeTitles));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StudentMainActivity.this, NoticeDetailActivity.class);
                intent.putExtra(Controller.EXTRA_NOTICE_ID, position);
                startActivity(intent);
            }
        });
    }
}
