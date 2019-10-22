package org.halloweenalcala.app.ui.poems;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.Place;
import org.halloweenalcala.app.model.PoemCharacter;
import org.halloweenalcala.app.ui.MainActivity;
import org.halloweenalcala.app.ui.image_full.ImageFullActivity;
import org.halloweenalcala.app.util.Util;
import org.halloweenalcala.app.util.WindowUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PoemItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PoemItemFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_POSITION = "arg_position";

    private TextView tvPoemText;
    private AppCompatImageView imgCharacter;
    private AppCompatImageView imgCharacterLocked;
    private View viewLockedPoem;
    private AppCompatEditText editCharacterName;
    private int position;
    private View btnCheck;
    private PoemCharacter poemCharacter;
    private TextView tvPoemTitle;
    private Button btnGoToMap;
    private View viewUnlockedPoem;
    private TextView tvPoemUnlockDescription;
    private TextView tvHiddenPoemName;
    private TextView tvPoemSubtitle;

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    public PoemItemFragment() {
        // Required empty public constructor
    }

    private void findViews(View layout) {

        tvPoemTitle = (TextView)layout.findViewById( R.id.tv_poem_title);
        tvPoemSubtitle = (TextView)layout.findViewById( R.id.tv_poem_subtitle);
        tvPoemText = (TextView)layout.findViewById( R.id.tv_poem_text );
        tvPoemUnlockDescription = (TextView)layout.findViewById( R.id.tv_poem_unlock_description );
        tvHiddenPoemName = (TextView)layout.findViewById( R.id.tv_hidden_poem_name );
        imgCharacter = (AppCompatImageView)layout.findViewById( R.id.img_character );
        imgCharacterLocked = (AppCompatImageView)layout.findViewById( R.id.img_character_locked );
        viewLockedPoem = layout.findViewById(R.id.view_locked_poem);
        viewUnlockedPoem = layout.findViewById(R.id.view_unlocked_poem);
        editCharacterName = (AppCompatEditText)layout.findViewById( R.id.edit_character_name );
        btnCheck = layout.findViewById(R.id.btn_check);
        btnGoToMap = layout.findViewById(R.id.btn_go_to_map);

        btnCheck.setOnClickListener(this);
        btnGoToMap.setOnClickListener(this);
        imgCharacterLocked.setOnClickListener(this);

    }


    public static PoemItemFragment newInstance(int position) {
        PoemItemFragment fragment = new PoemItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt(ARG_POSITION);
            poemCharacter = PoemBook.poemBook.get(position);
        } else {
            throw new IllegalArgumentException("Position argument needed");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_poem_item, container, false);

        findViews(layout);

        setupData();

        return layout;
    }


    private void setupData() {

        boolean isUnlocked = poemCharacter.isOpen() || getPrefs().getBoolean(getUnlockedKey(), false);

        if (isUnlocked) {
            viewLockedPoem.setVisibility(View.GONE);
            tvPoemTitle.setText(getString(poemCharacter.getPoemTitleId()));
            tvPoemSubtitle.setText(getString(poemCharacter.getPoemSubtitleId()));
            tvPoemText.setText(getString(poemCharacter.getPoemTextId()));
//            imgCharacter.setImageResource(poemCharacter.getCharacterDrawableId());
            imgCharacter.setImageResource(poemCharacter.getImageId());
            viewUnlockedPoem.setVisibility(View.VISIBLE);
        } else {

            viewUnlockedPoem.setVisibility(View.GONE);

            switch (poemCharacter.getTypeId()) {

                case PoemCharacter.TYPE_RESPONSE:
                    imgCharacterLocked.setImageResource(poemCharacter.getCharacterDrawableId());
                    btnGoToMap.setVisibility(View.GONE);
                    break;

                case PoemCharacter.TYPE_PLACE:
                    Place place = App.getDB().placeDao().getById(poemCharacter.getIdPlaceServer());
//                    Place place = (Place) Select.from(Place.class).where(Condition.prop("idserver").eq(poemCharacter.getIdPlaceServer())).first();
                    tvPoemUnlockDescription.setText(Html.fromHtml(String.format(getString(R.string.place_unlock_description), place.getName())));
                    String title = getString(poemCharacter.getPoemTitleId());
                    tvHiddenPoemName.setText(title);
                    btnGoToMap.setVisibility(View.VISIBLE);
                    break;

            }
        }


    }



    private String getUnlockedKey() {
        return App.SHARED_POEM_UNLOCKED + "." + poemCharacter.getId();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check:
                checkCharacterName();
                WindowUtils.hideSoftKeyboard(editCharacterName);
                break;

            case R.id.btn_go_to_map:
                Place place = App.getDB().placeDao().getById(poemCharacter.getIdPlaceServer());
                ((MainActivity)getActivity()).onGoToMapButtonClick(place);
                break;

            case R.id.img_character_locked:
                startActivity(ImageFullActivity.newImageFullActivity(getActivity(), poemCharacter.getCharacterDrawableId()));
                break;
        }
    }

    private void checkCharacterName() {
        String name = editCharacterName.getText().toString().trim().toLowerCase();
        name = Util.normalizeText(name);

        if (isValidNameOrCode(name)) {

            toastHalloween(R.string.guessed);
            getPrefs().edit().putBoolean(getUnlockedKey(), true).commit();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setupData();
                    ((PoemsFragment)getParentFragment()).setupPoemIsShareable(position);
                }
            }, 2500);


        } else {
            toastHalloween(R.string.fail);
        }
    }

    private boolean isValidNameOrCode(String nameOrCode) {
        if (PoemCharacter.TYPE_RESPONSE.equals(poemCharacter.getTypeId())) {
            return isValidName(nameOrCode);
        } else {
            return isValidCode(nameOrCode);
        }
    }

    private boolean isValidCode(String code) {
        String correctCode = PoemBook.codesPlaces.get(poemCharacter.getIdPlaceServer());
        boolean success = code.toLowerCase().equals(correctCode.toLowerCase());

        Crashlytics.logException(new Exception("Poem guess: " + getString(poemCharacter.getPoemTitleId()) + ". Success: " + success));
        return success;
    }

    private boolean isValidName(String name) {

        int responsesArrayId = poemCharacter.getResponsesArrayId();
        String[] responsesArray = getResources().getStringArray(responsesArrayId);

        Response:
        for (int i = 0; i < responsesArray.length; i++) {
            String keywords = responsesArray[i];

            String[] wordsParts = keywords.split(" ");
            for (int j = 0; j < wordsParts.length; j++) {
                String wordPart = wordsParts[j];

                if (!name.contains(wordPart)) {
                    continue Response;
                } else if (j == wordsParts.length - 1) {
                    sendPoemGuessResult(name, true);
                    return true;
                }

            }

        }

        sendPoemGuessResult(name, false);
        return false;
    }

    private void sendPoemGuessResult(String name, boolean success) {

        Bundle bundle = new Bundle();
        bundle.putInt(FirebaseAnalytics.Param.ITEM_ID, poemCharacter.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, getString(poemCharacter.getPoemTitleId()));
        bundle.putInt(FirebaseAnalytics.Param.SCORE, success ? 1 : -1);
        bundle.putString(FirebaseAnalytics.Param.ITEM_VARIANT, name);

        FirebaseAnalytics.getInstance(getActivity()).logEvent(FirebaseAnalytics.Event.POST_SCORE, bundle);
    }
}
