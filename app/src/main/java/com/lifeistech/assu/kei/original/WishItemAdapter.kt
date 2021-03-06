package com.lifeistech.assu.kei.original

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.RealmRecyclerViewAdapter
import kotlinx.android.synthetic.main.list_item.view.*


class WishItemAdapter(
    private val context: Context,
    private var wishItemList: OrderedRealmCollection<WishItem>?,
    private val autoUpdate: Boolean

) :
    RealmRecyclerViewAdapter<WishItem, WishItemAdapter.WishItemViewHolder>(wishItemList, autoUpdate) {

    override fun getItemCount(): Int = wishItemList?.size ?: 0

    override fun onBindViewHolder(holder: WishItemViewHolder, position: Int) {
        val wishItem: WishItem = wishItemList?.get(position) ?: return

        holder.nameTextView.text ="Name: "+ wishItem.name
        holder.URLTextView.text = "URL: "+ wishItem.url
        holder.barcodeTextView.text="Barcode:"+ wishItem.barcode
        holder.container.setOnLongClickListener{

            val realm: Realm = Realm.getDefaultInstance()
            realm.executeTransaction {
              wishItem.deleteFromRealm()
            }
            return@setOnLongClickListener true

        }

        val byteArrayImageData = wishItem.image
        byteArrayImageData?.let {
            val bitmap =
                BitmapFactory.decodeByteArray(byteArrayImageData, 0, byteArrayImageData.size)
            holder.imageView.setImageBitmap(bitmap)
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): WishItemViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.list_item, viewGroup, false)
        return WishItemViewHolder(v)
    }

    //override fun onSwiped(viewHolder: WishItemAdapter.WishItemViewHolder, direction: Int) {


    //}

    class WishItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.nameTextView
        val URLTextView: TextView = view.URLTextView
        val barcodeTextView: TextView =view.barcodeTextView
        val imageView: ImageView=view.imageView
        val container: LinearLayout=view.container
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(item: WishItem)
    }
}