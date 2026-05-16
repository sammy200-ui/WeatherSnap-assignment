package com.weathersnap.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.weathersnap.app.data.local.ReportEntity
import com.weathersnap.app.ui.theme.*
import com.weathersnap.app.ui.viewmodel.SavedReportsViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SavedReportsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SavedReportsViewModel = hiltViewModel()
) {
    val reports by viewModel.reports.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Top Banner
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(PrimaryLightGreen)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Saved Reports", color = TextOnPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                val subtitle = if (reports.size == 1) "1 report stored locally" else "${reports.size} reports stored locally"
                Text(subtitle, color = TextOnPrimary.copy(alpha = 0.7f), fontSize = 12.sp)
            }
            Button(
                onClick = onNavigateBack,
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceVariantDark),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Back", color = PrimaryLightGreen, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (reports.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No saved reports yet.", color = TextSecondary)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(reports) { report ->
                    SavedReportCard(report)
                }
            }
        }
    }
}

@Composable
fun SavedReportCard(report: ReportEntity) {
    val dateFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
    val dateString = dateFormat.format(Date(report.timestamp))
    
    val originalKb = report.originalImageSizeBytes / 1024
    val compressedKb = report.compressedImageSizeBytes / 1024

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceDark)
            .padding(16.dp)
    ) {
        // Photo
        AsyncImage(
            model = report.imagePath,
            contentDescription = "Report Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceVariantDark)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weather Details
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = report.cityName, color = TextPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = report.condition, color = TextSecondary, fontSize = 14.sp)
                Text(text = dateString, color = TextSecondary, fontSize = 12.sp)
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(PrimaryLightGreen.copy(alpha = 0.2f))
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "${report.temperature.toInt()}°C",
                    color = PrimaryLightGreen,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sizes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceVariantDark)
                    .padding(12.dp)
            ) {
                Text("Original", color = TextSecondary, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text("${originalKb} KB", color = WeatherOrange, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
            
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceVariantDark)
                    .padding(12.dp)
            ) {
                Text("Compressed", color = TextSecondary, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text("${compressedKb} KB", color = WeatherGreen, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Notes
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(SurfaceVariantDark)
                .padding(12.dp)
        ) {
            Text(text = report.notes, color = TextPrimary, fontSize = 14.sp)
        }
    }
}
