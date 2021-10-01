package nu.oueslati.flagquizz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)

        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.btnStart)
        val editText = findViewById<EditText>(R.id.et_name)

        button.setOnClickListener {

            if (editText.text.toString().isEmpty()) {

                Toast.makeText(this@MainActivity, "Please enter your name", Toast.LENGTH_SHORT)
                    .show()
            } else {

                val intent = Intent(this@MainActivity, FlagQuestions::class.java)
                // TODO (STEP 2: Pass the name through intent using the constant variable which we have created.)
                // START
                intent.putExtra(Constants.USER_NAME, editText.text.toString())
                // END
                startActivity(intent)
                finish()
            }
        }
    }
}