package rx_sample.rx_sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class Main3Activity : AppCompatActivity() {

    val customers : HashMap<Int, String> = hashMapOf(12345 to "Hans", 12346 to "Klaus")
    var customerNumber = 12345

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        RxView.clicks(findViewById<Button>(R.id.clickme))
                .flatMapSingle { getCustomerId() }
                .flatMapSingle(this::getCustomerName)
                .subscribe(RxTextView.text(findViewById<Button>(R.id.label)))
        // Mark2
        //      .subscribe(RxTextView.text(findViewById<Button>(R.id.label)), e -> )
    }



    private fun getCustomerId() : Single<Int> {
        return Single.just(customerNumber++)
                .delay(1, TimeUnit.SECONDS)
        // Mark1
                .observeOn(AndroidSchedulers.mainThread())

    }

    private fun getCustomerName(id : Int) : Single<String> {
        return Single.just(customers[id])
    }

}
