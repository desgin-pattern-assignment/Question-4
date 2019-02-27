package com.falcon.noticeboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditableNoticeActivity extends Activity {

    private EditText titleEditText, detailEditText;
    private int id;
    private boolean isNew = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editable_notice);

        titleEditText = findViewById(R.id.editableNoticeTitle);
        detailEditText = findViewById(R.id.editableNoticeDetail);

        id = getIntent().getIntExtra(Controller.EXTRA_EDITABLE_ID, -1);

        if (id != -1) {
            isNew = false;
            String title = Controller.getNoticeTitle(id);
            String detail = Controller.getNoticeDetail(id);
            titleEditText.setText(title);
            detailEditText.setText(detail);
        }
    }

    public void saveClicked(View view) {
        String title = titleEditText.getText().toString();
        String detail = detailEditText.getText().toString();
        int reply;
        if (isNew) {
            if (title.isEmpty() || detail.isEmpty()) {
                Toast.makeText(this, getString(R.string.specify_all_fields), Toast.LENGTH_SHORT).show();
                return;
            }
            reply = Controller.addNotice(title, detail);
            switch (reply) {
                case 0:
                    startActivity(new Intent(this, AdminMainActivity.class));
                    break;
                case 1:
                    Toast.makeText(this, getString(R.string.title_exists), Toast.LENGTH_SHORT).show();
                    return;
                case 2:
                    Toast.makeText(this, getString(R.string.unable_to_add_notice), Toast.LENGTH_SHORT).show();
                    return;
                default:
                    return;
            }
        } else {
            if (title.isEmpty() || detail.isEmpty()) {
                Toast.makeText(this, getString(R.string.specify_all_fields), Toast.LENGTH_SHORT).show();
                return;
            }
            if (Controller.editNotice(id, title, detail)) {
                Toast.makeText(this, getString(R.string.successfully_edited), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, AdminMainActivity.class));
            } else {
                Toast.makeText(this, getString(R.string.unable_to_edit_notice), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
