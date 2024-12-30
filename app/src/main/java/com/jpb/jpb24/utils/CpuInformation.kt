package com.jpb.jpb24.utils

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Build
import java.io.IOException
import java.io.InputStream
import java.util.Scanner
import java.util.regex.MatchResult
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CpuInformation(private val context: Context) : GLSurfaceView.Renderer {
    private val hashMap = HashMap<String, String>()


    val numberOfCores: Int
        /**
         * It will get the total number of cores of Android CPU
         * @return number of cores in int
         */
        get() = Runtime.getRuntime().availableProcessors()

    val gPURenderer: String?
        /**
         * Get GPU Renderer
         * @return String
         */
        get() {
            gPUInformation
            return hashMap["RENDERER"]
        }

    val gPUVendor: String?
        /**
         * Get GPU Vendor
         * @return String
         */
        get() {
            gPUInformation
            return hashMap["VENDOR"]
        }

    val gPUExtensions: String?
        /**
         * To get the GPU Extensions
         * @return String
         */
        get() {
            gPUInformation
            return hashMap["EXTENSIONS"]
        }

    val gPUVersion: String?
        /**
         * To get GPU Version
         * @return String
         */
        get() {
            gPUInformation
            return hashMap["VERSION"]
        }

    val isGPUSupported: Boolean
        /**
         * To check if GPU is supported or not
         * @return boolean6
         */
        get() {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val configurationInfo = activityManager
                .deviceConfigurationInfo
            return configurationInfo.reqGlEsVersion >= 0x20000
        }
    val cpuInformation: String
        get() {
            val processBuilder: ProcessBuilder
            val process: Process
            val DATA =
                arrayOf("/system/bin/cat", "/proc/cpuinfo")
            val inputStream: InputStream
            val Holder = StringBuilder()

            processBuilder = ProcessBuilder(*DATA)
            val byteArray = ByteArray(1024)
            try {
                process = processBuilder.start()
                inputStream = process.inputStream
                while (inputStream.read(byteArray) != -1) {
                    Holder.append(String(byteArray))
                }
                inputStream.close()
                return Holder.toString()
            } catch (e: IOException) {
                e.printStackTrace()
                return "Exception Occurred"
            }
        }


    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        hashMap.clear()
        hashMap["RENDERER"] = gl.glGetString(GL10.GL_RENDERER)
        hashMap["VENDOR"] = gl.glGetString(GL10.GL_VENDOR)
        hashMap["VERSION"] = gl.glGetString(GL10.GL_VERSION)
        hashMap["EXTENSIONS"] = gl.glGetString(GL10.GL_EXTENSIONS)
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
    }

    override fun onDrawFrame(gl: GL10) {
    }

    private val gPUInformation: Unit
        get() {
            val activityManager =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val configurationInfo = activityManager
                .deviceConfigurationInfo
            val supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000
            //
            val glSurfaceView = GLSurfaceView(this.context)
            glSurfaceView.setRenderer(this)
        }

    companion object {
        val supportedABIs: Array<String>
            /**
             * Get all the supported ABIs (Application Binary Interfaces)
             * @return in String Arrays
             */
            get() = Build.SUPPORTED_ABIS

        val minimumFrequency: Int
            /**
             * To get the minimum cpu frequency
             * @return in kiloHertz.
             */
            get() = readSystemFileAsInt("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq")

        val maximumFrequency: Int
            /**
             * To get the maximum cpu frequency
             * @return in kiloHertz.
             */
            get() = readSystemFileAsInt("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq")

        @get:Throws(Exception::class)
        val bogoMips: Float
            /**
             * BogoMips (from "bogus" and MIPS) is a crude measurement of CPU speed made by the Linux kernel
             * when it boots to calibrate an internal busy-loop.
             * @return float
             * @throws Exception
             */
            get() {
                val matchResult =
                    matchSystemFile(
                        "/proc/cpuinfo",
                        "BogoMIPS[\\s]*:[\\s]*(\\d+\\.\\d+)[\\s]*\n",
                        1000
                    )

                try {
                    if (matchResult.groupCount() > 0) {
                        return matchResult.group(1).toFloat()
                    } else {
                        throw Exception()
                    }
                } catch (e: NumberFormatException) {
                    throw Exception(e)
                }
            }

        val clockSpeed: Int
            /**
             * To get the clock speed of the CPU
             * @return in int
             */
            get() = readSystemFileAsInt("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq")

        /**
         * To check whether your device is 34-bit or 64-bit
         * @return boolean
         */
        fun is64Bit(): Boolean {
            return Build.SUPPORTED_64_BIT_ABIS.size > 0
        }

        val minScalingFrequency: Int
            /**
             * Get minimum scaling frequency of the CPU
             * @return kiloHertz(in int)
             */
            get() = readSystemFileAsInt("/sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq")

        val maxScalingFrequency: Int
            /**
             * Get maximum scaling frequency of the cpu
             * @return kiloHertz(in int)
             */
            get() = readSystemFileAsInt("/sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq")

        private fun readSystemFileAsInt(systemFile: String): Int {
            var inputStream: InputStream? = null
            try {
                val process = ProcessBuilder(*arrayOf("/system/bin/cat", systemFile)).start()
                inputStream = process.inputStream
                val content = readFully(inputStream)
                return content.toInt()
            } catch (e: IOException) {
                e.printStackTrace()
                return 0
            }
        }

        @Throws(IOException::class)
        fun readFully(pInputStream: InputStream?): String {
            val sb = StringBuilder()
            val sc = Scanner(pInputStream)
            while (sc.hasNextLine()) {
                sb.append(sc.nextLine())
            }
            return sb.toString()
        }

        @Throws(Exception::class)
        private fun matchSystemFile(
            systemFile: String,
            pattern: String,
            horizon: Int
        ): MatchResult {
            var `in`: InputStream? = null
            try {
                val process = ProcessBuilder(*arrayOf("/system/bin/cat", systemFile)).start()

                `in` = process.inputStream
                val scanner = Scanner(`in`)

                val matchFound = scanner.findWithinHorizon(pattern, horizon) != null
                if (matchFound) {
                    return scanner.match()
                } else {
                    throw Exception()
                }
            } catch (e: IOException) {
                throw Exception(e)
            }
        }
    }
}
