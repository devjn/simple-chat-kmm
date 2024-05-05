import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.jfayz.testchat.App
import java.awt.Dimension

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "KotlinProject") {
        window.minimumSize = Dimension(400, 400)
        App()
    }
}
