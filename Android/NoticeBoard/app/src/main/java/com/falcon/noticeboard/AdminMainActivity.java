package com.falcon.noticeboard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class AdminMainActivity extends Activity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        listView = findViewById(R.id.adminListView);
        populateListView();
    }

    private void populateListView() {
        String noticeTitles[] = Controller.getNoticeTitles(this);
        listView.setAdapter(new AdminNoticeAdapter(this, noticeTitles));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AdminMainActivity.this, NoticeDetailActivity.class);
                intent.putExtra(Controller.EXTRA_NOTICE_ID, position);
                startActivity(intent);
            }
        });
    }

    public void addNoticeClicked(View view) {
        startActivity(new Intent(this, EditableNoticeActivity.class));
    }

    public void editClicked(View view) {
        Intent intent = new Intent(this, EditableNoticeActivity.class);
        View item = (View) view.getParent();
        int id = ((ListView) item.getParent()).indexOfChild(item);
        intent.putExtra(Controller.EXTRA_EDITABLE_ID, id);
        startActivity(intent);
    }

    public void deleteClicked(final View view) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.exit_dialog_title))
                .setMessage(getString(R.string.confirm_message))
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View item = (View) view.getParent();
                        int id = ((ListView) item.getParent()).indexOfChild(item);
                        Controller.deleteNotice(id);
                        populateListView();
                        Toast.makeText(AdminMainActivity.this, getString(R.string.notice_deleted),
                                Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
}
