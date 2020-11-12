package com.lifeistech.assu.kei.original

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.system.Os.remove
import android.widget.AdapterView.OnItemLongClickListener
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import io.realm.RealmResults
import io.realm.log.RealmLog.remove
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.list_item.*
import java.util.*


class ListActivity : AppCompatActivity() {


private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val wishList = readAll()

        // タスクリストが空だったときにダミーデータを生成する
//        if (wishList.isEmpty()) {
//            createDummyData()
//        }
        //削除
        // delete()

        val adapter = WishItemAdapter(this, wishList, true)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter


    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun createDummyData() {
        for (i in 0..10) {
            create("欲しい物の名前 $i", "url")
        }
    }

    fun delete(){
        realm.executeTransaction {
            var wishItem = realm.where(WishItem::class.java).findAll()
//            wishItem.deleteFromRealm(0)
            wishItem.deleteAllFromRealm()

        }
    }


    fun create(name:String , url: String) {
        realm.executeTransaction {
            val wishItem = it.createObject(WishItem::class.java, UUID.randomUUID().toString())
            wishItem.name = name
            wishItem.url = url
        }
    }

    fun readAll(): RealmResults<WishItem> {
        return realm.where(WishItem::class.java).findAll()
    }
 }

