package thortechasia.android.basekotlin.utils
import io.reactivex.subjects.BehaviorSubject

class ObservableVariable<T>(private val defaultValue: T) {
    var value: T = defaultValue
        set(value) {
            field = value
            observable.onNext(value)
        }
    val observable = BehaviorSubject.createDefault(value)
}