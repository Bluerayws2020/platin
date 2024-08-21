import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.FragmentActivity

class KeyboardVisibilityListener(
    private val rootView: View,
    private val callback: (Boolean) -> Unit
) : ViewTreeObserver.OnGlobalLayoutListener {
    private var isKeyboardVisible = false

    override fun onGlobalLayout() {
        val rect = Rect()
        rootView.getWindowVisibleDisplayFrame(rect)
        val screenHeight = rootView.height
        val keypadHeight = screenHeight - rect.bottom
        val isVisible = keypadHeight > screenHeight * 0.15 // assuming keyboard is visible if it takes more than 15% of screen height

        if (isKeyboardVisible != isVisible) {
            isKeyboardVisible = isVisible
            callback(isKeyboardVisible)
        }
    }

    fun register() {
        rootView.viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    fun unregister() {
        rootView.viewTreeObserver.removeOnGlobalLayoutListener(this)
    }
}
