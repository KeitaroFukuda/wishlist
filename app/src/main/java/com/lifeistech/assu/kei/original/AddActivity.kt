package com.lifeistech.assu.kei.original

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_add.*
import android.Manifest
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.util.*
class Add : AppCompatActivity() {

    var bitmapImageData: Bitmap? = null
    var scannedResult: String = ""

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    //カメラ機能
    companion object {
        const val CAMERA_REQUEST_CODE = 1
        const val CAMERA_PERMISSION_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        barcode_imageButton.setOnClickListener {
            val intent = Intent(application, BarcodeActivity::class.java)
            startActivity(intent)
        }

        add_Button.setOnClickListener {
            create(name_editText.text.toString(), barcode_editText.text.toString())
            Toast.makeText(applicationContext, "Added to Wishlist!", Toast.LENGTH_SHORT).show()
            name_editText.setText("")
            barcode_editText.setText("")
            camera_imageButton.setImageResource(R.drawable.camera)
            barcode_imageButton.setImageResource(R.drawable.barcode)

        }
    }

    override fun onResume() {
        super.onResume()


        camera_imageButton.setOnClickListener {
            // カメラ機能を実装したアプリが存在するかチェック
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).resolveActivity(packageManager)?.let {
                if (checkCameraPermission()) {
                    takePicture()
                } else {
                    grantCameraPermission()
                }
            } ?: Toast.makeText(this, "カメラを扱うアプリがありません", Toast.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val image = data?.extras?.get("data")?.let {
                camera_imageButton.setImageBitmap(it as Bitmap)
                bitmapImageData = it
            }
        }
    }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
        }

        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun checkCameraPermission() = PackageManager.PERMISSION_GRANTED ==
            ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)


    private fun grantCameraPermission() =
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                takePicture()
            }
        }
    }

    fun create(name:String = "item", url:String = "URL"){
        val byteArrayImageData = createImageData(bitmapImageData)
        realm.executeTransaction {
            var wishItem = realm.createObject(WishItem::class.java , UUID.randomUUID().toString())
            wishItem.image = byteArrayImageData
            wishItem.name = name
            wishItem.url = url
            realm.copyToRealm(wishItem)
        }
    }

    fun createImageData(bitmap:Bitmap?) : ByteArray? {
        bitmap?.let {
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val imageByteArray = baos.toByteArray()
            return imageByteArray
        }
        return null
    }


    }










