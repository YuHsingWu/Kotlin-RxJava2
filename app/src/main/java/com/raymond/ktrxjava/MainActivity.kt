package com.raymond.ktrxjava

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import io.reactivex.schedulers.Schedulers



class MainActivity : AppCompatActivity() {

    var data1 = Observable.just("one", "two", "three", "four", "five")
    var data2 = Observable.just("one", "two", "three", "four", "five")
    var data3 = Observable.just("one", "two", "three", "four", "five")
    var mObList = listOf<Observable<String>>(data1, data2, data3)

    var list = listOf("one", "two", "three", "four", "five")

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                Observable.just("Hello World")
                        .subscribe{ s ->
                            Log.v("zxc", "s = $s")
                            message.text = s
                        }

                Observable.fromArray("hello", "world")
                        .subscribe{ s ->
                            Log.v("zxc", "s = $s")
                            message.text = s
                        }

                Observable.just("Hello, world!")
                        .map<Any> { s ->
                            "$s - map test"
                        }
                        .subscribe { s ->
                            println(s)
                            message.text = s.toString()
                        }

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                var output = ""
                Observable
                    .zip(mObList) { args1 -> args1
                    }
                    .subscribe { arg ->
                        var i = 1
                        for (o in arg) {
                            output += i.toString() + "_" + o.toString() + " : "
                            i++
                        }

                        message.text = output
                    }
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                Observable.merge(Observable.just(1, 2, 3),
                        Observable.just(4, 5, 6),
                        Observable.just(7, 8, 9),
                        Observable.just(10, 11, 12))
                        .subscribe {
                            Log.v("zxc", "it = $it")
                            message.setText(R.string.title_notifications)
                        }

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
