package com.lifeistech.assu.kei.original

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.Activity_Add.*
import kotlinx.android.synthetic.main.activity_barcode.*


class BarcodeFragment : Fragment() {

    var scannedResult: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnScan.setOnClickListener {
            run {
                IntentIntegrator(this@BarcodeFragment).initiateScan();
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

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

val view = inflater.inflate(R.layout.fragment_barcode, container, false)
    }


}

