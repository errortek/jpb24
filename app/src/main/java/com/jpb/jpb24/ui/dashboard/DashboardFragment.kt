package com.jpb.jpb24.ui.dashboard

import android.app.ActivityManager
import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jpb.jpb24.databinding.FragmentDashboardBinding
import com.jpb.jpb24.utils.CpuInformation
import java.text.DecimalFormat


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var activityManager: ActivityManager? = null
        var memoryInfo: ActivityManager.MemoryInfo? = null
        activityManager = this.activity?.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        memoryInfo = ActivityManager.MemoryInfo()
        activityManager?.getMemoryInfo(memoryInfo)
        val freeMemory = memoryInfo.availMem
        val totalMemory = memoryInfo.totalMem
        val usedMemory = freeMemory.let { totalMemory.minus(it) }
        val cpuInformation = activity?.let { CpuInformation(it) }
        val gpu_vendor = cpuInformation?.gPUVendor


        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val cputext: TextView = binding.CPUDetails
        if (Build.VERSION.SDK_INT >= 31) {
            cputext.text = Build.SOC_MODEL
        } else {
            cputext.text = "CPU model information is unavailable for Android 11 and below, due to the underlying API requiring at least Android 12."
        }
        val memtext: TextView = binding.RAMDetails
        memtext.text = formatSize(totalMemory)
        val gputext: TextView = binding.GPUDetails
        gputext.text = gpu_vendor
        val storagetext: TextView = binding.StorageDetails
        storagetext.text = formatSize(getUsedInternalMemorySize()) + " / " + formatSize(getTotalInternalMemorySize())
        binding.imageView.setTint(com.google.android.material.R.color.material_dynamic_primary50)
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun formatSize(size: Long): String {
        if (size <= 0)
            return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
    }

    /**
     * Get available internal memory size
     * @return long
     */
    private fun getAvailableInternalMemorySize(): Long {
        val path = Environment.getDataDirectory()
        val statFs = StatFs(path.path)
        val blockSize = statFs.blockSizeLong
        val availableBlocks = statFs.availableBlocksLong
        return availableBlocks * blockSize
    }

    /**
     * Get total internal memory size
     * @return long
     */
    private fun getTotalInternalMemorySize(): Long {
        val path = Environment.getDataDirectory()
        val statFs = StatFs(path.path)
        val blockSize = statFs.blockSizeLong
        val totalBlocks = statFs.blockCountLong
        return blockSize * totalBlocks
    }

    /**
     * Get the used internal memory size
     * @return long
     */
    private fun getUsedInternalMemorySize(): Long {
        return if (getTotalInternalMemorySize() > getAvailableInternalMemorySize()) {
            getTotalInternalMemorySize() - getAvailableInternalMemorySize()
        } else {
            -11111111
        }
    }

    fun ImageView.setTint(@ColorRes colorRes: Int) {
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)))
    }
}