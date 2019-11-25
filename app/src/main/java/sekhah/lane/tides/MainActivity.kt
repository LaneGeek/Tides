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

        // Lets use the XML parser
        val parser = XmlPullParserHandler()
        val inputStream = assets.open("Florence_2019_tide_predictions.xml")
        val tidesData = parser.parse(inputStream)

        // Lets create our parallel arrays using Kotlin's functional programming
        val date = tidesData.map { x -> x.date }
        val day = tidesData.map { x -> x.day }
        val time = tidesData.map { x -> x.time }
        val predictionInCm = tidesData.map { x -> x.predictionInCm }
        val highLow = tidesData.map { x -> x.highLow }

        // Lets create calculated and formatted arrays again using Kotlin's functional programming
        val predictionInInches = predictionInCm.map { x -> (x!!.toFloat() / 0.254).toInt().toFloat() / 10 }
        val highLowNiceFormat = highLow.map { x -> if (x == "H") "High" else "Low" }
        val output = Array(date.size) { "" }
        date.forEachIndexed { i, x -> output[i] = day[i] + " " + x + "\n" + time[i] + " - " + highLowNiceFormat[i] }

        // Render the ListView
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, output)

        // Pop the toast
        listView.setOnItemClickListener { _: AdapterView<*>?, _: View?, position: Int, _: Long ->
            Toast.makeText(this, "Tide Height: ${predictionInInches[position]} Inches", Toast.LENGTH_SHORT).show()
        }
    }
}
