package org.halloweenalcala.app.ui.poems;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;
import org.halloweenalcala.app.base.BasePresenter;
import org.halloweenalcala.app.model.PoemCharacter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoemsFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    private PoemPagerAdapter adapter;

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    private ViewPager viewpagerPoems;

    public PoemsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PoemBook.initialize(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout = inflater.inflate(R.layout.fragment_poems, container, false);

        viewpagerPoems = (ViewPager) layout.findViewById(R.id.viewpager_poems);

        adapter = new PoemPagerAdapter(getChildFragmentManager());
        viewpagerPoems.setAdapter(adapter);

        viewpagerPoems.setPageTransformer(true, new BookPageTransformer2());
        viewpagerPoems.addOnPageChangeListener(this);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();

        int lastPosition = getPrefs().getInt(App.SHARED_LAST_PAGE_POEMS, 0);
        viewpagerPoems.setCurrentItem(lastPosition, true);
        setupPoemIsShareable(lastPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        setHasOptionsMenu(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.share_icon, menu);
    }


    public void setupPoemIsShareable(int position) {

        PoemCharacter poemCharacter = PoemBook.poemBook.get(position);
        String keyUnlocked = App.SHARED_POEM_UNLOCKED + "." + poemCharacter.getId();
        boolean isUnlocked = poemCharacter.isOpen() || getPrefs().getBoolean(keyUnlocked, false);
        setHasOptionsMenu(isUnlocked);
    }

    // INTERACTIONS

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_share:
                onSharePoemClick();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSharePoemClick() {

        PoemCharacter poemCharacter = PoemBook.poemBook.get(viewpagerPoems.getCurrentItem());
        String poemShareMessage = String.format(getString(R.string.poem_share_message),
                getString(poemCharacter.getPoemTitleId()), getString(poemCharacter.getPoemTextId()), App.URL_GOOGLE_PLAY_APP);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, poemShareMessage);
        startActivity(intent);
    }

    // VIEWPAGER CALLBACKS
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getPrefs().edit().putInt(App.SHARED_LAST_PAGE_POEMS, position).commit();

        setupPoemIsShareable(position);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
