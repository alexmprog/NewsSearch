package com.renovavision.newssearch.utils.imageprocessing

import android.graphics.Bitmap
import com.bumptech.glide.load.Key
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import timber.log.Timber
import java.security.MessageDigest


class RectangleTransformation(private val targetWidth: Int, private val targetHeight: Int) : BitmapTransformation() {

    override fun transform(pool: BitmapPool, toTransform: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        if (targetHeight <= 0 && targetWidth <= 0) {
            return toTransform
        }

        var drawW = toTransform.width
        var drawH = toTransform.height

        if (drawW > targetWidth) {
            drawH = drawH * targetWidth / drawW
            drawW = targetWidth
        }

        if (drawH > targetHeight) {
            drawW = drawW * targetHeight / drawH
            drawH = targetHeight
        }

        var scaledBitmap: Bitmap? = null
        try {
            scaledBitmap = Bitmap.createScaledBitmap(toTransform, drawW, drawH, false)
        } catch (e: Exception) {
            Timber.e(e, "rectangle transformation failed.")
        }

        return scaledBitmap ?: toTransform
    }

    override fun toString(): String {
        return "RectangleTransformation(targetWidth=$targetWidth,targetHeight=$targetHeight)"
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {
        messageDigest.update(toString().toByteArray(Key.CHARSET))
    }
}