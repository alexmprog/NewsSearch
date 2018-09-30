package com.renovavision.newssearch.utils.imageprocessing

import android.graphics.Bitmap
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import timber.log.Timber
import java.security.MessageDigest

class WidthTransformation(private val targetWidth: Int) : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val inWidth = toTransform.width
        val inHeight = toTransform.height

        val drawW = targetWidth
        val drawH = inHeight * targetWidth / inWidth

        var result: Bitmap? = null
        try {
            result = Bitmap.createScaledBitmap(toTransform, drawW, drawH, false)
        } catch (e: Exception) {
            Timber.e(e, "letterbox transformation failed.")
        }

        return result ?: toTransform

    }

    override fun toString(): String {
        return "WidthTransformation(targetWidth=$targetWidth)"
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(toString().toByteArray(Key.CHARSET))
    }
}