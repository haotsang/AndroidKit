package me.haotsang.app.rules_engine.core

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.google.gson.Gson
import me.haotsang.app.rules_engine.core.data.ActionTypeEnum

class ActionHandler(val activity: Context) : Handler(Looper.getMainLooper()) {
    private val TAG = "ActionHandler"

    override fun handleMessage(msg: Message) {
        super.handleMessage(msg)
        val data = msg.data.getString(RuleConstant.ACTIONS)
        val sceneName = msg.data.getString(RuleConstant.SCENE_NAME)
        val actionInfos = data?.let { JsonUtil.actionToJson(it) }
        Log.i(TAG, "actionlist info is :${Gson().toJson(actionInfos)}")

        actionInfos?.let {
            for (action in it) {
                when (action.actionCode) {
                    ActionTypeEnum.SWITCH, ActionTypeEnum.SINGLE_LIGHT -> {
                        when (action.value) {
                            "ON" -> {
                                //TODO
                            }
                            "OFF" -> {
                                //TODO
                            }
                        }
                    }
                    ActionTypeEnum.DUAL_LIGHT -> {
                        when (action.value) {
                            "打开通道1" -> {
                                //TODO
                            }
                            "关闭通道1" -> {
                                //TODO
                            }
                            "打开通道2" -> {
                                //TODO
                            }
                            "关闭通道2" -> {
                                //TODO
                            }
                        }
                    }
                    ActionTypeEnum.FAN -> {
                        when (action.value) {
                            "SLOW" -> {
                                //TODO
                            }
                            "MODERATE" -> {
                                //TODO
                            }
                            "FAST" -> {
                                //TODO
                            }
                            "OFF" -> {
                                //TODO
                            }
                        }
                    }
                    ActionTypeEnum.CURTAIN -> {
                        when (action.value) {
                            "OPEN" -> {
                                //TODO
                            }
                            "CLOSE" -> {
                                //TODO
                            }
                        }
                    }
                }
            }

            Log.d(TAG, "已执行${sceneName}")
        }

    }

}