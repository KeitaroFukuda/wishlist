package com.lifeistech.assu.kei.original

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.Activity_Add.*
import kotlinx.android.synthetic.main.activity_add2.*
import kotlinx.android.synthetic.main.activity_add2.view.*
import kotlinx.android.synthetic.main.activity_add2.view.barcode_imageButton
import kotlinx.android.synthetic.main.activity_main2.*
import kotlinx.android.synthetic.main.fragment_add2.view.*
import java.io.ByteArrayOutputStream
import java.util.*


class Add2Fragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        view.camera_imageButton.setImageResource(R.drawable.camera)
        view. barcode_imageButton.setImageResource(R.drawable.barcode)

        view.camera_imageButton.setOnClickListener {
            // カメラ機能を実装したアプリが存在するかチェックActivity
            val intent = Intent(activity, CameraActivity::class.java)
            ContextCompat.startActivity(intent)

            Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(packageManager)?.let {
                if (checkCameraPermission()) {
                    takePicture()
                } else {
                    grantCameraPermission()
                }
            } ?: Toast.makeText(this, "カメラを扱うアプリがありません", Toast.LENGTH_LONG).show()
        }


    override fun onResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, rltCode, data)
        if (requestCode == AddActivity.CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.extras?.get("data")?.let {
                view.setimage_imageview.setImageBitmap(it as Bitmap)
                bitmapImageData = it
            }
        }
    }
        // Inflate the layout for this fragment



}

    val view = inflater.inflate(R.layout.fragment_add2, container, false)}