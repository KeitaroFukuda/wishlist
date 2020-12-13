package com.lifeistech.assu.kei.original

import android.content.res.Resources
import android.graphics.Bitmap
import android.media.Image
import androidx.core.content.res.ResourcesCompat
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class WishItem(
    @PrimaryKey open var id: String = UUID.randomUUID().toString(),
    open var image: ByteArray? = null,
    open var name: String = "",
    open var url: String = ""
) : RealmObject()
