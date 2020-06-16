package com.example.myapplication.ui.photo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoFragment extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    public PhotoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_photo, container, false);
        ImageView imageView = v.findViewById(R.id.imageView1);
        Button photoButton = v.findViewById(R.id.button1);
//        photoButton.setOnClickListener(new View.OnClickListener()
//        {
//            @SuppressLint("WrongConstant")
//            @Override
//            public void onClick(View v)
//            {
//                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
//                {
//                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
//                }
//                else
//                {
//                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
//                }
//            }
//
//        });
//    }
        return v;
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
//    {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_CAMERA_PERMISSION_CODE)
//        {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//            }
//            else
//            {
//                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
//        {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageView.setImageBitmap(photo);
//        }
//    }
}