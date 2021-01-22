package tellabs.android.basekotlin

import android.app.Application
import com.github.ajalt.timberkt.Timber
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tellabs.android.basekotlin.di.myAppModule
import tellabs.android.basekotlin.utils.SampleLifecycleListener
import androidx.lifecycle.ProcessLifecycleOwner

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupLifecycleListener()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@App)
            modules(myAppModule)
        }
    }

    private val lifeCycleListener: SampleLifecycleListener by lazy {
        SampleLifecycleListener()
    }

    private fun setupLifecycleListener(){
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifeCycleListener)
    }

}