package com.jpb.android.jpb24.ui.home

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jpb.android.jpb24.R
import com.jpb.android.jpb24.databinding.FragmentHomeBinding
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.text.DecimalFormat


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        var activityManager: ActivityManager? = null
        var memoryInfo: ActivityManager.MemoryInfo? = null
        activityManager = this.activity?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        memoryInfo = ActivityManager.MemoryInfo()
        activityManager?.getMemoryInfo(memoryInfo)
        val freeMemory = memoryInfo.availMem
        val totalMemory = memoryInfo.totalMem
        val usedMemory = freeMemory.let { totalMemory.minus(it) }

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val oemtext: TextView = binding.textView2
        oemtext.text = Build.MANUFACTURER
        val devicetext: TextView = binding.textView4
        devicetext.text = Build.MODEL
        val cputext: TextView = binding.textView6
        if (Build.VERSION.SDK_INT >= 31) {
            cputext.text = Build.SOC_MODEL
        } else {
            cputext.text = getCPUInfo().toString()
        }
        val memtext: TextView = binding.textView8
        memtext.text = formatSize(totalMemory)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Throws(IOException::class)
    fun getCPUInfo(): Map<String, String> {
        val br = BufferedReader(FileReader("/proc/cpuinfo"))

        var str: String

        val output: MutableMap<String, String> = HashMap()

        while ((br.readLine().also { str = it }) != null) {
            val data = str.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

            if (data.size > 1) {
                var key = data[0].trim { it <= ' ' }.replace(" ", "_")
                if (key == "model_name") key = "cpu_model"

                output[key] = data[1].trim { it <= ' ' }
            }
        }

        br.close()

        return output
    }

    private fun formatSize(size: Long): String {
        if (size <= 0)
            return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
    }
}