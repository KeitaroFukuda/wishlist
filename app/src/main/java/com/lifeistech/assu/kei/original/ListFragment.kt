package com.lifeistech.assu.kei.original

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_list.*
import java.util.*

class ListFragment : Fragment() {

    private val realm: Realm by lazy {
        Realm.getDefaultInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        val wishList = readAll()

        // タスクリストが空だったときにダミーデータを生成する
//        if (wishList.isEmpty()) {
//            createDummyData()
//        }
        //削除
        // delete()

        val adapter =
            WishItemAdapter(view.context, wishList, true)


        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = adapter
        return view
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

    fun deleteAll() {
        realm.executeTransaction {
            realm.deleteAll()
        }
    }


}

