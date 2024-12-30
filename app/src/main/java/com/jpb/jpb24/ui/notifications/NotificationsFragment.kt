package com.jpb.jpb24.ui.notifications

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.system.Os
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jpb.jpb24.R
import com.jpb.jpb24.databinding.FragmentNotificationsBinding
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern


class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val androidVerTextView: TextView = binding.OSDetails
        androidVerTextView.text = Build.VERSION.RELEASE + " (" + Build.VERSION.CODENAME + ", API " + Build.VERSION.SDK_INT + ")"
        val securityPatchTextView: TextView = binding.SecurityPatchDetails
        securityPatchTextView.text = Build.VERSION.SECURITY_PATCH
        val kernelTextView: TextView = binding.KernelDetails
        kernelTextView.text = Os.uname().release
        var androidVersionImage: Drawable
        if (Build.VERSION.SDK_INT == 27) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_o_mr1)
            binding.OSImageView.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 28) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_p)
            binding.OSImageView.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 29) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_q)
            binding.OSImageView.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 30) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_r)
            binding.OSImageView.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 31 || Build.VERSION.SDK_INT == 32) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_s)
            binding.OSImageView.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 33) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_t)
            binding.OSImageView.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 34) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_u)
            binding.OSImageView.setImageDrawable(androidVersionImage)
        } else if (Build.VERSION.SDK_INT == 35) {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android_v)
            binding.OSImageView.setImageDrawable(androidVersionImage)
        } else {
            androidVersionImage = resources.getDrawable(R.drawable.ic_android)
            binding.OSImageView.setImageDrawable(androidVersionImage)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}