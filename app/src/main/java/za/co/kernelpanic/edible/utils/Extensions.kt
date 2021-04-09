package za.co.kernelpanic.edible.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.text.DecimalFormat

fun Fragment.tagView() {
    Timber.i("x-class ${this::class.java.simpleName}")
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> {
    return this
}

fun Int.toCurrencyFormat(): String {
    return "€${this / 100}"
}

fun Fragment.showSnackBar(message: String, coordinatorLayoutParentView: View) {
    Snackbar.make(coordinatorLayoutParentView, message, Snackbar.LENGTH_LONG).show()
}

fun Int.formatDistanceInKm(): String {
    val calculatedDistance = this * 0.001
    val df = DecimalFormat("#.##")
    return "${df.format(calculatedDistance)} km"
}