package com.haotsang.common.utils

import com.google.android.exoplayer2.Timeline

object ExoUtils {

    private fun isLiveStream(timeline: Timeline): Boolean {
        return !timeline.isEmpty && timeline.getWindow(0, Timeline.Window()).isLive()
                && timeline.periodCount == 0
    }
}