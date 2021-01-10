package com.lifeistech.assu.kei.original

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.system.Os.remove
import android.widget.AdapterView.OnItemLongClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
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
import java.nio.file.Files.delete
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

        val adapter =
            WishItemAdapter(this, wishList, true)


        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

       // val swipeToDismissTouchHelper = getSwipeToDismissTouchHelper(WishItemAdapter)
        //swipeToDismissTouchHelper.attachToRecyclerView(recyclerView)


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
//
//    private fun getSwipeToDismissTouchHelper(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) =
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        ){
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean {
//                return false
//            }
//
//
//            //override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//
//                //削除対象を取得
//                var wishItem = realm.where(WishItem::class.java)
//
//                wishItem =results.get(1)
//                wishItem.deleteFromRealm()
//            }
//
//            override fun onChildDraw(
//                c: Canvas,
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                dX: Float,
//                dY: Float,
//                actionState: Int,
//                isCurrentlyActive: Boolean
//            ) {
//                super.onChildDraw(
//                    c,
//                    recyclerView,
//                    viewHolder,
//                    dX,
//                    dY,
//                    actionState,
//                    isCurrentlyActive
//                )
//                val itemView = viewHolder.itemView
//                val background = ColorDrawable()
//                background.color = Color.parseColor("#f44336")
//                if (dX < 0)
//                    background.setBounds(
//                        itemView.right + dX.toInt(),
//                        itemView.top,
//                        itemView.right,
//                        itemView.bottom
//                    )
//                else
//                    background.setBounds(
//                        itemView.left,
//                        itemView.top,
//                        itemView.left + dX.toInt(),
//                        itemView.bottom
//                    )
//
//                background.draw(c)
//            }
//        })

        }

