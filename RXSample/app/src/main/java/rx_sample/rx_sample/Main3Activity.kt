package rx_sample.rx_sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class Main3Activity : AppCompatActivity() {

    val customers : HashMap<Int, String> = hashMapOf(12345 to "Hans", 12346 to "Klaus")
    var customerNumber = 12345


    val subject : PublishSubject<String> = PublishSubject.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        RxView.clicks(findViewById<Button>(R.id.clickme))
                .flatMapSingle { getCustomerId() }
                .flatMapSingle(this::getCustomerName)
                .onErrorResumeNext{ error: Throwable -> Observable.just("Unknown")}
                //.subscribe(RxTextView.text(findViewById<Button>(R.id.label)))
        // Mark2: see end of function


        // Erklärung:
        // Single, Maybe, Completable

        // Erklärung:
        // rxMarbles: http://rxmarbles.com/


        // Erklärung:
        // Seiteneffekte: doOnNext, doOnError, ...

        // Mark3: onErrorResumeNext
        // .onErrorResumeNext { Single.just("Unbekannt")}


        // Mark4: Subject (Advanced: own Observables)
                .doOnNext{subject.onNext(it)}



        // Dispose

        // Mark2
              .subscribe(RxTextView.text(findViewById<Button>(R.id.label)), Consumer { e: Throwable ->
                Log.e("Main3Activity", "An error occured", e)
         })


        // Mark4
        subject.subscribe{Log.i("Main3Activity", "Event: " + it)}
    }



    private fun getCustomerId() : Single<Int> {
        return Single.just(customerNumber++)
                .delay(600, TimeUnit.MILLISECONDS)
        // Mark1
                .observeOn(AndroidSchedulers.mainThread())

    }

    private fun getCustomerName(id : Int) : Single<String> {
        return Single.just(customers[id])
    }

}
