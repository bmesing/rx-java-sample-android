package rx_sample.rx_sample

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import java.util.*

class Main2Activity : AppCompatActivity() {

    private lateinit var passwordInput: EditText
    private lateinit var passwordConfirmationInput: EditText
    private lateinit var nextButton: Button

    private lateinit var password: Observable<String>
    private lateinit var passwordConfirmation: Observable<String>
    private lateinit var passwordValid: Observable<Boolean>
    private lateinit var passwordsMatch: Observable<Boolean>
    private lateinit var allInputValid: Observable<Boolean>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        passwordInput = findViewById(R.id.passwordInput)
        passwordConfirmationInput = findViewById(R.id.passwordConfirmationInput)
        nextButton  = findViewById(R.id.nextButton)

        setupObservables()
        registerActions()
    }


    private fun setupObservables() {
        password = RxTextView.textChanges(passwordInput).map { it.toString() }.share()
        passwordConfirmation = RxTextView.textChanges(passwordConfirmationInput).map { it.toString() }.share()
        passwordValid = password.map { it.contains("1") &&  it.contains("a")}
        passwordsMatch = Observable.combineLatest(password, passwordConfirmation, BiFunction{ pw: String, pwc : String ->
            pw == pwc
        })
        allInputValid = Observable.combineLatest(passwordValid, passwordsMatch, BiFunction { i1: Boolean, i2: Boolean ->
            i1 && i2
        }).startWith(false)
    }

    @SuppressLint("CheckResult")
    private fun registerActions() {
        allInputValid.subscribe(RxView.enabled(nextButton))
        RxView.clicks(nextButton).subscribe{
            startActivity(Intent(this, Main3Activity::class.java))
        }
    }

}
