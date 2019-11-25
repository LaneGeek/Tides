package sekhah.lane.tides

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Eventually we will get this data from the XML file
        val date = arrayOf("2019/01/01", "2019/01/01", "2019/01/01", "2019/01/01", "2019/01/02", "2019/01/02", "2019/01/02", "2019/01/02", "2019/01/03", "2019/01/03")
        val day = arrayOf("Tue", "Tue", "Tue", "Tue", "Wed", "Wed", "Wed", "Wed", "Thu", "Thu")
        val time = arrayOf("02:47 AM", "09:03 AM", "04:13 PM", "10:25 PM", "03:44 AM", "09:50 AM", "05:02 PM", "11:24 PM", "04:37 AM", "10:33 AM")
        val predictionInFt = arrayOf("2.11", "7.16", "0.42", "5.13", "2.44", "7.26", "0.03", "5.36", "2.65", "7.32")
        val predictionInCm = arrayOf("64", "218", "13", "156", "74", "221", "1", "163", "81", "223")
        val highLow = arrayOf("L", "H", "L", "H", "L", "H", "L", "H", "L", "H")

        // Lets use Kotlin's functional programming here
        val predictionInInches = predictionInCm.map { x -> (x.toFloat() / 0.254).toInt().toFloat() / 10 }
        val highLowNiceFormat = highLow.map { x -> if (x == "H") "High" else "Low"}
        val output = Array(date.size) {""}
        date.forEachIndexed { i, x -> output[i] = day[i] + " " + x + "\n" + time[i] + " - " + highLowNiceFormat[i]}

        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, output)

        listView.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
            Toast.makeText(this, "Tide Height: ${predictionInInches[position]} Inches", Toast.LENGTH_SHORT).show()
        }
    }
}
