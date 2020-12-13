package com.lifeistech.assu.kei.original

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_barcode.*


class BarcodeActivity : AppCompatActivity() {
    var scannedResult: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barcode)

        barcode_imageButton.setOnClickListener {
            run {
                IntentIntegrator(this@BarcodeActivity).initiateScan();
            }
        }
    }

    override fun onActivityResult(requestCode: Int,resultCode: Int,data: Intent?){

        var result:IntentResult? = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)

        if(result !=null){

            if(result.contents != null){
                scannedResult = result.contents
                barcode_textview.text= scannedResult
            } else {
                barcode_textview.text ="scan failed"
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState?.putString("scannedResult", scannedResult)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let{
            scannedResult= it.getString("scannedResult")!!
            barcode_textview.text= scannedResult
        }
    }


}
