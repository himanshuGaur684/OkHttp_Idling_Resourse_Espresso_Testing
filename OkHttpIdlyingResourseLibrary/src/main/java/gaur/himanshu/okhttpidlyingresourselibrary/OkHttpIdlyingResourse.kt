package gaur.himanshu.okhttpidlyingresourselibrary


import okhttp3.Dispatcher
import okhttp3.OkHttpClient

class OkHttpIdling(name: String, dispatcher: Dispatcher) : androidx.test.espresso.IdlingResource {

    @Volatile
    var callback: androidx.test.espresso.IdlingResource.ResourceCallback? = null
    private var name: String? = null
    private var dispatcher: Dispatcher? = dispatcher

    companion object {
        fun create(name: String, client: OkHttpClient) = OkHttpIdling(name, client.dispatcher)
    }

    init {
        this.name = name
        dispatcher.idleCallback = Runnable {
            val callback = this@OkHttpIdling.callback
            callback?.onTransitionToIdle()
        }
    }

    override fun getName(): String? {
        return name
    }

    override fun registerIdleTransitionCallback(callback: androidx.test.espresso.IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    override fun isIdleNow(): Boolean {
        return dispatcher?.runningCallsCount() == 0
    }


}