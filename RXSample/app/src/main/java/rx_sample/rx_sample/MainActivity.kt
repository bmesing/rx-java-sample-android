package rx_sample.rx_sample

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable

class MainActivity : AppCompatActivity() {

    private lateinit var rxClickButton: Button
    private lateinit var label: TextView


    companion object {
        val TAG = MainActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rxClickButton = findViewById<View>(R.id.rxClickButton) as Button
        label = findViewById<View>(R.id.label) as TextView
    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()
        Observable.just(1,2,3,4,5,6)
                .filter { it % 2 == 0 }
                .map { it * 2 }
                .doOnNext{
                    Log.d(TAG, "Value: $it");
                }
                // mark 1 (keine Auswertung)
                .subscribe{
                    label.text = it.toString()
                }

        // mark 2 (UI Cool)
        RxView.clicks(rxClickButton).subscribe{
            Log.d(TAG, "Clicked")
        }

        // mark 3 (WTF)
        RxView.clicks(rxClickButton).subscribe{
            Log.d(TAG, "Clicked2")
        }

        // mark 4 (Lösung: Hot vs. Cold)
        val observable = RxView.clicks(rxClickButton).share();

        observable.subscribe{
            Log.d(TAG, "Clicked3")
        }
        observable.subscribe{
            Log.d(TAG, "Clicked4")
        }

        /// Prinzip: UI-Änderungen sind Nebenwirkung von Event-Streams


        RxView.clicks(findViewById<View>(R.id.go_to_step_2)).subscribe{
            val launchIntent = Intent(this, Main2Activity::class.java)
            startActivity(launchIntent)
        }


    }
}
