package thortechasia.android.basekotlin.utils


import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * Tracks the Lifecycle of the whole application thanks to {@link LifecycleObserver}.
 * This is registered via {@link ProcessLifecycleOwner#get()} ()}. The events are designed
 * to be dispatched with delay (by design) so activity rotations don't trigger these calls.
 * See: https://developer.android.com/reference/android/arch/lifecycle/ProcessLifecycleOwner.html
 */
class SampleLifecycleListener : LifecycleObserver {

    companion object{
        val lifecycleListenerObvIsForeground= ObservableVariable(0)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        Log.d("SampleLifecycle", "Returning to foreground…")
        lifecycleListenerObvIsForeground.value = 1
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        Log.d("SampleLifecycle", "Moving to background…")
        lifecycleListenerObvIsForeground.value = 2
    }

}