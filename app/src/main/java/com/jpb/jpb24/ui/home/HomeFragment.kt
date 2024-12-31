package com.jpb.jpb24.ui.home

import android.app.ActivityManager
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jpb.jpb24.R
import com.jpb.jpb24.databinding.FragmentHomeBinding
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
            binding.imageButton.visibility = View.VISIBLE
            cputext.text = "Unavailable"
            cputext.setTextColor(resources.getColor(com.google.android.material.R.color.design_default_color_error));
        }
        val memtext: TextView = binding.textView8
        memtext.text = formatSize(totalMemory)
        val androidVerTextView: TextView = binding.OSDetails
        androidVerTextView.text = Build.VERSION.RELEASE + " (" + Build.VERSION.CODENAME + ")"
        val securityPatchTextView: TextView = binding.SecurityPatchDetails
        securityPatchTextView.text = Build.VERSION.SECURITY_PATCH
        val displayMetrics = DisplayMetrics()
        var width = displayMetrics.widthPixels / displayMetrics.density
        var deviceImage: Drawable
        if (Build.VERSION.SDK_INT >= 31) {
            binding.imageView.setTint(com.google.android.material.R.color.material_dynamic_primary50)
        }
        if (width < 600) {
            deviceImage = resources.getDrawable(R.drawable.tablet)
            binding.imageView.setImageDrawable(deviceImage)
        } else {
            deviceImage = resources.getDrawable(R.drawable.cellphone)
            binding.imageView.setImageDrawable(deviceImage)
        }
        var androidVersionImage: Drawable
        if (Build.VERSION.SDK_INT == 27) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_o_mr1)
            binding.imageView2.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 28) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_p)
            binding.imageView2.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 29) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_q)
            binding.imageView2.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 30) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_r)
            binding.imageView2.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 31 || Build.VERSION.SDK_INT == 32) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_s)
            binding.imageView2.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 33) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_t)
            binding.imageView2.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 34) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_u)
            binding.imageView2.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 35) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_v)
            binding.imageView2.setImageDrawable(androidVersionImage)
        } else {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android)
            binding.imageView2.setImageDrawable(androidVersionImage)
        }
        binding.imageButton.setOnClickListener {
            if (context != null) {
                MaterialAlertDialogBuilder(requireContext())
                    .setIcon(R.drawable.alert)
                    .setTitle("CPU model information unavailable")
                    .setMessage("Fetching CPU model information is only possible on Android 12 (API 31) and newer.")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
        return root
    }

    private fun ImageView.setTint(@ColorRes colorRes: Int) {
        ImageViewCompat.setImageTintList(this, ColorStateList.valueOf(ContextCompat.getColor(context, colorRes)))
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
}