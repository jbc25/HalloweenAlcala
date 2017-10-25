package org.halloweenalcala.app.ui.static_info;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseActivity;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.util.Util;

import java.io.IOException;

public class TextHtmlActivity extends BaseActivity {

    public static final int TYPE_ALCALA_HALLOWEEN = 0;
    public static final int TYPE_7MZ = 1;

    private final String FILENAME_ALCALA_HALLOWEEN_HTML = "info_mock.html";
    private final String FILENAME_MARCHA_ZOMBIE_HTML = "info_marcha_zombie.html";

    private TextView tvHtml;
    private ImageView imgHtmlInfo;

    public static void start(Context context, int typeInfo) {
        Intent intent = new Intent(context, TextHtmlActivity.class);
        intent.putExtra(Util.EXTRA_INT, typeInfo);
        context.startActivity(intent);
    }

    private void findViews() {
        tvHtml = (TextView) findViewById(R.id.tv_html);
        imgHtmlInfo = (ImageView) findViewById(R.id.img_html_info);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_text_html);

        findViews();

        configureSecondLevelActivity();

        int typeScreen = getIntent().getIntExtra(Util.EXTRA_INT, -1);

        switch (typeScreen) {

            case TYPE_ALCALA_HALLOWEEN:
                setToolbarTitle(R.string.alcala_halloween);
                loadHtml(FILENAME_ALCALA_HALLOWEEN_HTML);
                imgHtmlInfo.setImageResource(R.mipmap.img_skyline_logo_ayto);

                break;

            case TYPE_7MZ:
                setToolbarTitle(R.string.marcha_zombie);
                loadHtml(FILENAME_MARCHA_ZOMBIE_HTML);
                imgHtmlInfo.setImageResource(R.mipmap.img_cover_info_mz);
                break;

            default:
                throw new IllegalArgumentException(
                        "Not passed parameter typeScreen");

        }

    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    private void loadHtml(String htmlFile) {

        tvHtml.setMovementMethod(LinkMovementMethod.getInstance());

        try {
            String htmlText = Util.getStringFromAssets(this, "html/" + htmlFile);
            tvHtml.setText(Html.fromHtml(htmlText));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
