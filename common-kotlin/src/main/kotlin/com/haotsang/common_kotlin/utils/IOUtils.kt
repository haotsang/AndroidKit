package com.haotsang.common_kotlin.utils

import android.content.Context
import android.net.Uri
import okio.buffer
import okio.sink
import okio.source
import java.io.File

object IOUtils {

    fun uri2file(context: Context) {
        val fis = context.contentResolver.openInputStream(Uri.EMPTY)

        if (fis != null) {

            val inBuffer = fis.source().buffer()

            val outFile = File(context.getExternalFilesDir("xiaoxiao"), "naixiao5566.jpg")
            outFile.sink().buffer().use {
                it.writeAll(inBuffer)
                inBuffer.close()
            }

        }
    }
}