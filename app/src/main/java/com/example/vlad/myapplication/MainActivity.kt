package com.example.vlad.myapplication

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var status = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun checkClick(view: View) {
        val view:View = if (currentFocus == null) View (this) else currentFocus
        var inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0);

        if (editText.text.isEmpty()) {
            val textString = "Edit text is empty"
            val toastMe: Toast = Toast.makeText(this, textString, Toast.LENGTH_SHORT)
            toastMe.show()
        } else {
            requestDatabase()


            //val toastMe: Toast = Toast.makeText(this, editText.text, Toast.LENGTH_SHORT)
            //toastMe.show()
        }
    }

    fun requestDatabase() {
        var reference = FirebaseDatabase.getInstance().reference
        var myQuery = reference.orderByChild("VehicleNumber").equalTo("Р642НР197")

        var postListener = object: ValueEventListener {
            override fun onDataChange ( dataSnapshot: DataSnapshot?) {
                val valueString : String = dataSnapshot?.value as String

                this@MainActivity.status = dataSnapshot?.children.first().children.elementAt(11) as String
                button3.text = status;
                val toastMe = Toast.makeText(this@MainActivity, valueString, Toast.LENGTH_SHORT)
                toastMe.show()
            }

            override fun onCancelled( dataSnapshot: DatabaseError?) {

            }
        }
        myQuery.addValueEventListener(postListener)
    }
}
