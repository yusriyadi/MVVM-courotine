package tellabs.android.basekotlin.presentation.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.github.ajalt.timberkt.d
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast
import tellabs.android.basekotlin.utils.SampleLifecycleListener.Companion.lifecycleListenerObvIsForeground

class MyService : Service() {
    val disposables = CompositeDisposable()

    override fun onBind(intent: Intent): IBinder? {
       return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        toast("Started")

            GlobalScope.launch {
                delay(100)
            }

        lifecycleAppListener()

    }

    private fun lifecycleAppListener() {
        lifecycleListenerObvIsForeground.observable.subscribe {
            if (lifecycleListenerObvIsForeground.value == 1) {
                runOnUiThread {
                    toast("foreground")
                    d { "foreground" }
                }
            } else if (lifecycleListenerObvIsForeground.value == 2) {
                runOnUiThread {
                    toast("background")
                    d { "background" }
                }
            }
        }.addTo(disposables)
    }


    override fun onDestroy() {
        super.onDestroy()
        d{"destroy"}
    }

}
