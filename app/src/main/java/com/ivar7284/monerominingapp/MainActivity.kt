package com.ivar7284.monerominingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.annotation.RequiresApi
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.eclipse.jgit.api.CloneCommand
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.errors.GitAPIException
import org.eclipse.jgit.api.errors.TransportException
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    val TAG = "my_app"
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //https://github.com/XMRig-for-Android/xmrig-for-android
        //https://github.com/XMRig-for-Android/xmrig-for-android/blob/main/BUILD.md
        //https://www.opensourceagenda.com/projects/android-xmrig-miner
//--------------------------------------------------------------------------------------------------
        //profile-wallet valle loop kr bare mai kuch karna hai
        //hash-rate ko bhi realtime karna hai
        //wallet screen recycler view

//        val commands = arrayOf(
//            "apt update && apt upgrade",
//            "pkg install git build-essential cmake -y",
//            "git clone https://github.com/xmrig/xmrig.git",
//            "apt upgrade",
//            "cd xmrig",
//            "mkdir build",
//            "cd build",
//            "cmake .. -DWITH_HWLOC=OFF",
//            "make -j10"
//        )


        val startBtn : Button = findViewById(R.id.startBtn)
        val setupBtn : Button = findViewById(R.id.setupBtn)
        val payoutBtn : LinearLayout = findViewById(R.id.payoutBtn)
        val profileBtn: LinearLayout = findViewById(R.id.profileBtn)
        val gitUrl = "https://github.com/xmrig/xmrig.git"
        val localPath = File(applicationContext.filesDir, "xmrig_repository")
        val gitCloneTask = GitCloneTask(gitUrl, localPath)


        setupBtn.setOnClickListener {
            GlobalScope.launch {
                val result = gitCloneTask.cloneRepository()
                withContext(Dispatchers.Main) {
                    if (result) {
                        showToast("Repository cloned successfully.")
                    } else {
                        showToast("Error cloning repository.")
                    }
                }
            }
        }

        startBtn.setOnClickListener {
            startActivity(Intent(applicationContext, Details::class.java))
        }

        payoutBtn.setOnClickListener {
            startActivity(Intent(applicationContext, walletScreen::class.java ))
            finish()
        }
        profileBtn.setOnClickListener {
            startActivity(Intent(applicationContext, profileActivity::class.java ))

        }


// -------------------------------------------------------------------------------------------------
        //real-time karna ab-hi bacha hai
        //GRAPH-CODE
        lateinit var lineChart: LineChart
        lateinit var xValues: List<String>

        //gradient for graphs
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(Color.parseColor("#582ECB"), Color.parseColor("#00000000"))
        )
        val gradientDrawable2 = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM,
            intArrayOf(Color.parseColor("#C661E4"), Color.parseColor("#00000000"))
        )

        lineChart = findViewById(R.id.chart)
        lineChart.setBackgroundColor(Color.TRANSPARENT)
        val description = Description()
        description.text = ""
        lineChart.description = description
        lineChart.axisRight.setDrawLabels(false)

        xValues = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xValues)
        xAxis.labelCount = 7
        xAxis.granularity = 1f
        xAxis.axisLineWidth = 0f
        xAxis.axisLineColor = Color.WHITE
        xAxis.removeAllLimitLines()
        xAxis.textColor = Color.WHITE
        xAxis.setDrawGridLines(false)
        xAxis.spaceMin = .2f
        xAxis.setDrawAxisLine(false)


        val yAxis = lineChart.axisLeft
        yAxis.axisMinimum = 0f
        yAxis.axisMaximum = 100f
        yAxis.axisLineWidth = 0f
        yAxis.axisLineColor = Color.WHITE
        yAxis.labelCount = 10
        yAxis.removeAllLimitLines()
        yAxis.textColor = Color.WHITE
        yAxis.setDrawGridLines(false)
        yAxis.setDrawAxisLine(false)
        yAxis.setDrawLabels(false)

        val entries1 = mutableListOf<Entry>()
        entries1.add(Entry(0f, 10f))
        entries1.add(Entry(1f, 10f))
        entries1.add(Entry(2f, 15f))
        entries1.add(Entry(3f, 45f))
        entries1.add(Entry(4f, 50f))
        entries1.add(Entry(5f, 51f))
        entries1.add(Entry(6f, 40f))

        val entries2 = mutableListOf<Entry>()
        entries2.add(Entry(0f, 5f))
        entries2.add(Entry(1f, 15f))
        entries2.add(Entry(2f, 25f))
        entries2.add(Entry(3f, 30f))
        entries2.add(Entry(4f, 35f))
        entries2.add(Entry(5f, 25f))
        entries2.add(Entry(6f, 40f))

        val dataSet1 = LineDataSet(entries1, "ONE")
        dataSet1.color = Color.rgb(88,46, 203)
        dataSet1.valueTextColor = Color.WHITE
        dataSet1.setCircleColor(Color.rgb(88,46, 203))
        dataSet1.setCircleRadius(1.5f)
        dataSet1.setDrawFilled(true)
        dataSet1.fillDrawable = gradientDrawable
        val dataSet2 = LineDataSet(entries2, "TWO")
        dataSet2.color = Color.rgb(198,97,228)
        dataSet2.valueTextColor = Color.WHITE
        dataSet2.setCircleColor(Color.rgb(198,97, 228))
        dataSet2.setCircleRadius(1.5f)
        dataSet2.setDrawFilled(true)
        dataSet2.fillDrawable = gradientDrawable2

        dataSet1.setDrawValues(true)
        dataSet2.setDrawValues(true)

        val legend = lineChart.legend
        legend.isEnabled = false

        lineChart.setDrawGridBackground(false)
        lineChart.setDrawMarkers(false)
        lineChart.setPinchZoom(false)
        lineChart.isDragYEnabled=false

        val lineData = LineData(dataSet1, dataSet2)
        lineChart.data = lineData
        lineChart.invalidate()
        //--------------------------------------------------------------------------------------------------
    }
    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }
    }
    class GitCloneTask(private val url: String, private val localPath: File) {

        suspend fun cloneRepository(): Boolean {
            return try {
                val cloneCommand = CloneCommand()
                    .setURI(url)
                    .setDirectory(localPath)

                // Uncomment and provide credentials if the repository requires authentication
                // .setCredentialsProvider(UsernamePasswordCredentialsProvider("username", "password"))

                val git: Git = cloneCommand.call()
                git.close()
                true
            } catch (e: TransportException) {
                // Handle transport-related exceptions (e.g., network issues)
                e.printStackTrace()
                false
            } catch (e: GitAPIException) {
                // Handle Git API exceptions (e.g., invalid repository)
                e.printStackTrace()
                false
            } catch (e: Exception) {
                // Handle other exceptions
                e.printStackTrace()
                false
            }
        }

    }
}