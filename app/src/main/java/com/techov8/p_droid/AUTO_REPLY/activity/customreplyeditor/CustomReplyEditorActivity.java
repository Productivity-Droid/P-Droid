package com.techov8.p_droid.AUTO_REPLY.activity.customreplyeditor;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.techov8.p_droid.R;
import com.techov8.p_droid.AUTO_REPLY.activity.BaseActivity;
import com.techov8.p_droid.AUTO_REPLY.model.CustomRepliesData;
import com.techov8.p_droid.AUTO_REPLY.model.preferences.PreferencesManager;
import com.techov8.p_droid.AUTO_REPLY.viewmodel.SwipeToKillAppDetectViewModel;

public class CustomReplyEditorActivity extends BaseActivity {
    TextInputEditText autoReplyText;
    Button saveAutoReplyTextBtn;
    CustomRepliesData customRepliesData;
    PreferencesManager preferencesManager;
    Button watoMessageLinkBtn;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_reply_editor);

        ActionBar actionBar = getSupportActionBar();

        setTitle(R.string.mainAutoReplyLabel);
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#30D5C8"));

        //Set BackgroundDrawable
        actionBar.setBackgroundDrawable(colorDrawable);

        SwipeToKillAppDetectViewModel viewModel = new ViewModelProvider(this).get(SwipeToKillAppDetectViewModel.class);

        customRepliesData = CustomRepliesData.getInstance(this);
        preferencesManager = PreferencesManager.getPreferencesInstance(this);

        autoReplyText = findViewById(R.id.autoReplyTextInputEditText);
        saveAutoReplyTextBtn = findViewById(R.id.saveCustomReplyBtn);
        watoMessageLinkBtn = findViewById(R.id.tip_wato_message);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        autoReplyText.setText ((data != null)
                ? data.getQueryParameter("message")
                : customRepliesData.get());

        autoReplyText.requestFocus();
        autoReplyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable editable) {
                // Disable save button if text does not satisfy requirements
                saveAutoReplyTextBtn.setEnabled(CustomRepliesData.isValidCustomReply(editable));
            }
        });

        saveAutoReplyTextBtn.setOnClickListener(view -> {
            String setString = customRepliesData.set(autoReplyText.getText());
            if (setString != null) {
                this.onNavigateUp();
            }
        });

        watoMessageLinkBtn.setOnClickListener(view -> {
            String url = getString(R.string.watomatic_wato_message_url);
            startActivity(
                    new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url))
            );
        });

    }
}