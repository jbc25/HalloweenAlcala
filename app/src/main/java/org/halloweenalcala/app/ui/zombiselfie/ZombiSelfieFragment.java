package org.halloweenalcala.app.ui.zombiselfie;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Flash;

import org.halloweenalcala.app.App;
import org.halloweenalcala.app.BuildConfig;
import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZombiSelfieFragment extends BaseFragment implements ZombiSelfieView, View.OnClickListener, FramesAdapter.OnItemClickListener {


    private ZombiSelfiePresenter presenter;
    private CameraView camera;
    private View btnTakePicture;
    private AppCompatImageView imgFramePicture;
    private RecyclerView recyclerFrames;
    private FramesAdapter adapter;
    List<Integer> framesImagesId = new ArrayList<>();
    private View btnCameraFlash;
    private View btnCameraFlip;

    public ZombiSelfieFragment() {
        // Required empty public constructor
    }

    private void findViews(View layout) {

        camera = layout.findViewById(R.id.cameraview);
        btnTakePicture = layout.findViewById(R.id.btn_take_picture);
        recyclerFrames = layout.findViewById(R.id.recycler_frames);

        btnCameraFlash = layout.findViewById(R.id.btn_camera_flash);
        btnCameraFlip = layout.findViewById(R.id.btn_camera_flip);

        imgFramePicture = layout.findViewById(R.id.img_frame_picture);

        btnTakePicture.setOnClickListener(this);
        btnCameraFlash.setOnClickListener(this);
        btnCameraFlip.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = ZombiSelfiePresenter.newInstance(this, getActivity());
        setBasePresenter(presenter);

        View layout = inflater.inflate(R.layout.fragment_zombi_selfie, container, false);
        findViews(layout);

        if (getPrefs().getBoolean(App.SHARED_FIRST_TIME_SCREEN_ZOMBICUADRO, true)) {
            showZombiSelfieInfo();
            getPrefs().edit().putBoolean(App.SHARED_FIRST_TIME_SCREEN_ZOMBICUADRO, false).commit();
        }

        setHasOptionsMenu(true);

        configureFramesList();

        return layout;
    }

    private void configureFramesList() {

        framesImagesId.clear();

        // Individual
        framesImagesId.add(R.mipmap.marco01);
        framesImagesId.add(R.mipmap.marco02);
        framesImagesId.add(R.mipmap.marco03);
        framesImagesId.add(R.mipmap.marco18);
        framesImagesId.add(R.mipmap.marco19);
        framesImagesId.add(R.mipmap.marco05);

        framesImagesId.add(R.mipmap.marco08);
        framesImagesId.add(R.mipmap.marco07);

        framesImagesId.add(R.mipmap.marco14);
        framesImagesId.add(R.mipmap.marco15);
        framesImagesId.add(R.mipmap.marco16);
        framesImagesId.add(R.mipmap.marco17);
        framesImagesId.add(R.mipmap.marco06);



        adapter = new FramesAdapter(getActivity(), framesImagesId);
        adapter.setOnItemClickListener(this);
        recyclerFrames.setAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.zombieselfie, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        camera.setLifecycleOwner(this);
        camera.addCameraListener(cameraListener);

    }

    CameraListener cameraListener = new CameraListener() {

        @Override
        public void onPictureTaken(@NonNull PictureResult result) {
            super.onPictureTaken(result);

            camera.setFlash(Flash.OFF);

//            toast(result.getSize().toString());
//            DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
//            float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
//
//            toast("width: " + screenWidthDp);

//            // Picture was taken!
//            // If planning to show a Bitmap, we will take care of
//            // EXIF rotation and background threading for you...
//            result.toBitmap(600, 600, bitmap -> {
//                ImageView imageView = new ImageView(getActivity());
//                imageView.setImageBitmap(bitmap);
//
//                new AlertDialog.Builder(getActivity())
//                        .setView(imageView)
//                        .show();
//            });
//
//            // If planning to save a file on a background thread,
//            // just use toFile. Ensure you have permissions.
            File file = new File(getActivity().getCacheDir(), "9mz-picture.jpg");
            result.toFile(file, file1 -> {
                Bitmap bitmap = BitmapFactory.decodeFile(file1.getPath());

                View dialogLayout = View.inflate(getActivity(), R.layout.view_dialog_zombie_selfie, null);
                ImageView imgZombieSelfie = dialogLayout.findViewById(R.id.img_zombie_selfie);
                imgZombieSelfie.setImageBitmap(bitmap);

                new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme)
                        .setView(dialogLayout)
                        .setPositiveButton(R.string.send, (dialog, which) -> sendPicture(file1))
                        .setNeutralButton(R.string.cancel, null)
                        .show();
            });
        }

        @Override
        public void onCameraError(@NonNull CameraException exception) {
            super.onCameraError(exception);
        }

    };


    private void sendPicture(File file) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri uri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID, file);

        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        PackageManager pm = getActivity().getPackageManager();
        if (intent.resolveActivity(pm) != null) {
            startActivity(intent);
        } else {
            toast(R.string.cannot_share);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_take_picture:
                camera.takePictureSnapshot();
                break;

            case R.id.btn_camera_flash:
                if (camera.getFlash() == Flash.TORCH) {
                    camera.setFlash(Flash.OFF);
                } else {
                    if (camera.getFacing() == Facing.BACK) {
                        camera.setFlash(Flash.TORCH);
                    } else {
                        toastHalloween(R.string.flash_available_back_camera);
                    }
                }
                break;

            case R.id.btn_camera_flip:
                if (camera.getFacing() == Facing.BACK) {
                    camera.setFacing(Facing.FRONT);
                } else {
                    camera.setFacing(Facing.BACK);
                }
                break;

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_info:
                showZombiSelfieInfo();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showZombiSelfieInfo() {
        new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme)
                .setTitle(R.string.zombiecuadro_contest)
                .setMessage(R.string.zombiecuadro_contest_message)
                .setPositiveButton(R.string.see_bases, (dialog, which) -> {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(App.URL_BASES_ZOMBICUADRO)));})
                .setNeutralButton(R.string.back, null)
                .show();
    }

    @Override
    public void onItemClick(View view, int position) {
        imgFramePicture.setImageResource(framesImagesId.get(position));
    }
}
