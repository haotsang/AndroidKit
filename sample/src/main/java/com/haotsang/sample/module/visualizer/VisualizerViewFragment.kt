package com.haotsang.sample.module.visualizer

import android.Manifest
import android.media.audiofx.Visualizer
import android.os.Build.VERSION_CODES.P
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.fragment.app.Fragment
import com.haotsang.common.utils.PermissionsUtil

class VisualizerViewFragment : Fragment() {

    private var mVisualizer: Visualizer? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun setupVisualizerView(audioSession: Int, visualizerView: VisualizerView3) {
        if (mVisualizer == null) {
            try {
                mVisualizer = Visualizer(audioSession)
                mVisualizer?.scalingMode = Visualizer.SCALING_MODE_NORMALIZED
                mVisualizer?.captureSize = Visualizer.getCaptureSizeRange()[0]  //0为128；1为1024
                mVisualizer?.setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
                    override fun onWaveFormDataCapture(visualizer: Visualizer?, waveform: ByteArray?, samplingRate: Int) {
                    }

                    override fun onFftDataCapture(visualizer: Visualizer?, fft: ByteArray?, samplingRate: Int) {
                        try {
                            visualizerView.updateVisualizer(fft)
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                    }
                }, Visualizer.getMaxCaptureRate() / 2, false, true)
                mVisualizer?.enabled = true //这个设置必须在参数设置之后，表示开始采样
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun releaseVisualizerView() {
        if (mVisualizer != null) {
            mVisualizer?.release()
            mVisualizer = null
        }
    }

    override fun onDestroy() {
        releaseVisualizerView()
        super.onDestroy()
    }
}