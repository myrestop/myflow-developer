import top.myrest.myflow.AppInfo
import top.myrest.myflow.baseimpl.App
import top.myrest.myflow.baseimpl.FlowApp
import top.myrest.myflow.baseimpl.enableDevEnv
import top.myrest.myflow.dev.DevProps

fun main() {
    enableDevEnv()
    DevProps.disableNativeListener = false
    FlowApp().configApp()
    App(AppInfo.APP_NAME + "Developer")
}