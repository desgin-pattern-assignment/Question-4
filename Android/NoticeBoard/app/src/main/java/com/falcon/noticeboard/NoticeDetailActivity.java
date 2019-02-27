package com.falcon.noticeboard;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NoticeDetailActivity extends Activity {

    private TextView noticeTitleTextView, noticeContentTextView;
    private String noticeTitle, noticeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        int noticeId = getIntent().getIntExtra(Controller.EXTRA_NOTICE_ID, -1);

        noticeTitleTextView = findViewById(R.id.noticeDetailTitle);
        noticeContentTextView = findViewById(R.id.noticeDetailContent);
        setContent(noticeId);
    }

    private void setContent(int noticeId) {
        noticeTitle = Controller.getNoticeTitle(noticeId);
        noticeContent = Controller.getNoticeDetail(noticeId);
        noticeTitleTextView.setText(noticeTitle);
        noticeContentTextView.setText(noticeContent);
    }
}
