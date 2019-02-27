package com.falcon.noticeboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class StudentNoticeAdapter extends ArrayAdapter<String> {

    public StudentNoticeAdapter(Context context, String[] noticeTitles) {
        super(context, 0, noticeTitles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String noticeTitle = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_notice_list_item, parent, false);
        }

        ((TextView) convertView.findViewById(R.id.adminNewsTitle)).setText(noticeTitle);
        return convertView;
    }
}
