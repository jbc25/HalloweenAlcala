package org.halloweenalcala.app.ui.poems;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.PoemCharacter;
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
    private View viewUnlockedPoem;

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    public PoemItemFragment() {
        // Required empty public constructor
    }

    private void findViews(View layout) {

        tvPoemTitle = (TextView)layout.findViewById( R.id.tv_poem_title);
        tvPoemText = (TextView)layout.findViewById( R.id.tv_poem_text );
        imgCharacter = (AppCompatImageView)layout.findViewById( R.id.img_character );
        imgCharacterLocked = (AppCompatImageView)layout.findViewById( R.id.img_character_locked );
        viewLockedPoem = layout.findViewById(R.id.view_locked_poem);
        viewUnlockedPoem = layout.findViewById(R.id.view_unlocked_poem);
        editCharacterName = (AppCompatEditText)layout.findViewById( R.id.edit_character_name );
        btnCheck = layout.findViewById(R.id.btn_check);

        btnCheck.setOnClickListener(this);
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
            poemCharacter = PoemCharacter.poemsCharacters.get(position);
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
            tvPoemText.setText(getString(poemCharacter.getPoemTextId()));
            imgCharacter.setImageResource(poemCharacter.getCharacterDrawableId());
            viewUnlockedPoem.setVisibility(View.VISIBLE);
        } else {
            imgCharacterLocked.setImageResource(poemCharacter.getCharacterDrawableId());
            viewUnlockedPoem.setVisibility(View.GONE);
        }

    }

    private String getUnlockedKey() {
        return App.SHARED_POEM_UNLOCKED + "." + position;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_check:
                checkCharacterName();
                WindowUtils.hideSoftKeyboard(editCharacterName);
                break;
        }
    }

    private void checkCharacterName() {
        String name = editCharacterName.getText().toString().trim().toLowerCase();
        String nameReal = getString(poemCharacter.getCharacterNameId()).toLowerCase();

        if (name.equals(nameReal)) {

            toastHalloween(R.string.guessed);
            getPrefs().edit().putBoolean(getUnlockedKey(), true).commit();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setupData();
                }
            }, 2500);

        } else {
            toastHalloween(R.string.fail);
        }
    }
}
