package com.zhuinden.simplestackdemoexamplefragments.application

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import androidx.fragment.app.Fragment
import com.zhuinden.simplestackdemoexamplefragments.data.manager.DatabaseManager
import com.zhuinden.simplestackdemoexamplefragments.util.SchedulerHolder
import com.zhuinden.simplestackextensions.servicesktx.get
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by Zhuinden on 2018. 08. 20.
 */

class MainScopeListener : Fragment() {
    lateinit var handlerThread: HandlerThread

    init {
        retainInstance = true
    }

    private val looperScheduler by lazy {
        (requireContext().applicationContext as CustomApplication).globalServices.get<SchedulerHolder>("LOOPER_SCHEDULER") // workaround
    }

    private val databaseManager by lazy {
        (requireContext().applicationContext as CustomApplication).globalServices.get<DatabaseManager>() // workaround
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handlerThread = HandlerThread("LOOPER_SCHEDULER")
        handlerThread.start()
        synchronized(handlerThread) {
            looperScheduler.scheduler = AndroidSchedulers.from(handlerThread.looper)
        }
        databaseManager.openDatabase()
    }

    override fun onDestroy() {
        databaseManager.closeDatabase()
        Handler().postDelayed({ handlerThread.quit() }, 300)
        super.onDestroy()
    }
}
