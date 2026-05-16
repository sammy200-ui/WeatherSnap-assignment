package com.weathersnap.app.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weathersnap.app.data.local.ReportDao
import com.weathersnap.app.data.local.ReportEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class CreateReportViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val reportDao: ReportDao,
    @ApplicationContext private val context: Context
) : ViewModel() {

    val cityName = savedStateHandle.get<String>("cityName") ?: ""
    val temp = savedStateHandle.get<Float>("temp") ?: 0f
    val condition = savedStateHandle.get<String>("condition") ?: ""
    val humidity = savedStateHandle.get<Int>("humidity") ?: 0
    val wind = savedStateHandle.get<Float>("wind") ?: 0f
    val pressure = savedStateHandle.get<Float>("pressure") ?: 0f

    // These are persisted across configuration changes and process death by SavedStateHandle
    val notes = savedStateHandle.getStateFlow("notes", "")
    val photoPath = savedStateHandle.getStateFlow("photoPath", "")

    fun updateNotes(newNotes: String) {
        savedStateHandle["notes"] = newNotes
    }

    fun updatePhotoPath(path: String) {
        savedStateHandle["photoPath"] = path
    }

    fun saveReport(onSuccess: () -> Unit) {
        val currentPhotoPath = photoPath.value
        if (currentPhotoPath.isEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            val originalFile = File(currentPhotoPath)
            if (!originalFile.exists()) return@launch

            val originalSize = originalFile.length()
            
            // Compress the image
            val bitmap = BitmapFactory.decodeFile(originalFile.absolutePath)
            val compressedFile = File(context.filesDir, "report_\${System.currentTimeMillis()}.jpg")
            val out = FileOutputStream(compressedFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, out)
            out.flush()
            out.close()
            
            val compressedSize = compressedFile.length()

            val report = ReportEntity(
                cityName = cityName,
                temperature = temp.toDouble(),
                condition = condition,
                humidity = humidity,
                windSpeed = wind.toDouble(),
                pressure = pressure.toDouble(),
                notes = notes.value,
                imagePath = compressedFile.absolutePath,
                originalImageSizeBytes = originalSize,
                compressedImageSizeBytes = compressedSize
            )

            reportDao.insertReport(report)

            // Cleanup the temporary uncompressed image from cache
            originalFile.delete()

            withContext(Dispatchers.Main) {
                onSuccess()
            }
        }
    }
}
