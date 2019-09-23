package org.halloweenalcala.app.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.halloweenalcala.app.R;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;

public class TestPhotoEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_photo_editor);

        PhotoEditorView mPhotoEditorView = findViewById(R.id.photoEditorView);
        PhotoEditor mPhotoEditor = new PhotoEditor.Builder(this, mPhotoEditorView)
                .setPinchTextScalable(true)
//                .setDefaultTextTypeface(mTextRobotoTf)
//                .setDefaultEmojiTypeface(mEmojiTypeFace)
                .build();

        mPhotoEditor.setFilterEffect(PhotoFilter.GRAIN);
    }
}
