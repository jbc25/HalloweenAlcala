package org.halloweenalcala.app.ui.zombiselfie;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;

import org.halloweenalcala.app.R;
import org.halloweenalcala.app.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class ZombiSelfieFragment extends BaseFragment implements ZombiSelfieView, View.OnClickListener {


    private ZombiSelfiePresenter presenter;
    private CameraView camera;
    private View btnTakePicture;
    private View btnFrame1;
    private View btnFrame2;
    private View btnFrame3;
    private AppCompatImageView imgFramePicture;

    public ZombiSelfieFragment() {
        // Required empty public constructor
    }

    private void findViews(View layout) {

        camera = layout.findViewById(R.id.cameraview);
        btnTakePicture = layout.findViewById(R.id.btn_take_picture);
        btnFrame1 = layout.findViewById(R.id.btn_frame_1);
        btnFrame2 = layout.findViewById(R.id.btn_frame_2);
        btnFrame3 = layout.findViewById(R.id.btn_frame_3);

        imgFramePicture = layout.findViewById(R.id.img_frame_picture);

        btnTakePicture.setOnClickListener(this);
        btnFrame1.setOnClickListener(this);
        btnFrame2.setOnClickListener(this);
        btnFrame3.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        presenter = ZombiSelfiePresenter.newInstance(this, getActivity());
        setBasePresenter(presenter);

        View layout = inflater.inflate(R.layout.fragment_zombi_selfie, container, false);
        findViews(layout);

        return layout;
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

            toast(result.getSize().toString());

//            // Picture was taken!
//            // If planning to show a Bitmap, we will take care of
//            // EXIF rotation and background threading for you...
            result.toBitmap(600, 600, bitmap -> {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageBitmap(bitmap);

                new AlertDialog.Builder(getActivity())
                        .setView(imageView)
                        .show();
            });
//
//            // If planning to save a file on a background thread,
//            // just use toFile. Ensure you have permissions.
//            File file = new File(getActivity().getFilesDir(), "test_photo.jpg");
//            result.toFile(file, file1 -> {
//                Bitmap bitmap = BitmapFactory.decodeFile(file1.getPath());
//
//                ImageView imageView = new ImageView(getActivity());
//                imageView.setImageBitmap(bitmap);
//
//                new AlertDialog.Builder(getActivity())
//                        .setView(imageView)
//                        .show();
//            });
        }

        @Override
        public void onCameraError(@NonNull CameraException exception) {
            super.onCameraError(exception);
        }

    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_take_picture:
                camera.takePictureSnapshot();
                break;

            case R.id.btn_frame_1:
                imgFramePicture.setImageResource(R.mipmap.marco01);
                break;

            case R.id.btn_frame_2:
                imgFramePicture.setImageResource(R.mipmap.marco02);
                break;

            case R.id.btn_frame_3:
                imgFramePicture.setImageResource(R.mipmap.marco03);
                break;
        }
    }
}
