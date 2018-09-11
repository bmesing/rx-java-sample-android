package rx_sample.rx_sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Observable.just(1,2,3,4,5,6)
                .filter { it % 2 == 0 }
                .doOnNext{
                    Log.d(TAG, "Clicked: $it");
                }
                .subscribe{
                }

        //RxView.clicks(findViewById<View>(R.id.rxClickButton)).subscribe{
        //    Log.d(TAG, "Clicked");
        //}
    }
}
