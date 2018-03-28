package com.example.kanj.realmsample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where //Need to import this otherwise java where(Class clazz) will be used

class MainActivity : AppCompatActivity() {
    companion object {
        const val NAME: String = "Dumbass"
    }

    private lateinit var messageText: TextView
    private lateinit var realm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        messageText = findViewById(R.id.message)

        //Init realm
        Realm.init(this)
        val realmConfig = RealmConfiguration.Builder()
                .name("db.realm")
                .schemaVersion(1)
                //.deleteRealmIfMigrationNeeded()
                .build()

        Realm.getInstanceAsync(realmConfig, object : Realm.Callback() {
            override fun onSuccess(r: Realm) {
                realm = r
                doStuff()
            }
        })
    }

    fun doStuff() {
        val queryResult = realm.where<ChangingPerson>()
                .equalTo("name", NAME)
                ?.findAll()
        queryResult?.let {
            when (it.size) {
                0 -> {
                    messageText.text = "Creating $NAME"
                    val person = ChangingPerson()
                    person.name = NAME
                    realm.beginTransaction()
                    realm.copyToRealm(person)
                    realm.commitTransaction()
                }
                1 -> messageText.text = "Found $NAME"
                else -> messageText.text = "Unexpected Error"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
