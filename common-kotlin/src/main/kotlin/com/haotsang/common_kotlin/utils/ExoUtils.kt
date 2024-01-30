package com.haotsang.common_kotlin.utils

import com.google.android.exoplayer2.Timeline

object ExoUtils {

    private fun isLiveStream(timeline: Timeline): Boolean {
        return !timeline.isEmpty && timeline.getWindow(0, Timeline.Window()).isLive()
                && timeline.periodCount == 0
    }
}