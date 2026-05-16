package com.weathersnap.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.weathersnap.app.ui.theme.*
import com.weathersnap.app.ui.viewmodel.CreateReportViewModel

@Composable
fun CreateReportScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCamera: () -> Unit,
    onReportSaved: () -> Unit,
    cameraPhotoPath: String?,
    viewModel: CreateReportViewModel = hiltViewModel()
) {
    val notes by viewModel.notes.collectAsState()
    val photoPath by viewModel.photoPath.collectAsState()

    LaunchedEffect(cameraPhotoPath) {
        if (!cameraPhotoPath.isNullOrEmpty()) {
            viewModel.updatePhotoPath(cameraPhotoPath)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
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
            Column(modifier = Modifier.weight(1f)) {
                Text("Create Report", color = TextOnPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Capture, compress, annotate", color = TextOnPrimary.copy(alpha = 0.7f), fontSize = 12.sp)
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

        // Selected Weather Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceDark)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = viewModel.cityName, color = TextPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text(text = viewModel.condition, color = TextSecondary, fontSize = 16.sp)
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(PrimaryLightGreen.copy(alpha = 0.2f))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(text = "${viewModel.temp}°C", color = PrimaryLightGreen, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                WeatherStatCard("Humidity", "${viewModel.humidity}%", WeatherGreen, Modifier.weight(1f))
                WeatherStatCard("Wind", "${viewModel.wind} m/s", WeatherBlue, Modifier.weight(1f))
                WeatherStatCard("Pressure", "${viewModel.pressure}", WeatherOrange, Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Camera Preview Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceDark)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SurfaceVariantDark),
                contentAlignment = Alignment.Center
            ) {
                if (photoPath.isNotEmpty()) {
                    AsyncImage(
                        model = photoPath,
                        contentDescription = "Captured Photo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Text("Photo preview", color = TextPrimary)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateToCamera,
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryLightGreen),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Capture Photo", color = TextOnPrimary)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Notes Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceDark)
                .padding(16.dp)
        ) {
            Text("Field Notes", color = TextPrimary, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = notes,
                onValueChange = { viewModel.updateNotes(it) },
                placeholder = { Text("Notes", color = TextSecondary) },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryLightGreen,
                    unfocusedBorderColor = TextSecondary,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.saveReport(onReportSaved) },
            enabled = photoPath.isNotEmpty() && notes.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryLightGreen,
                disabledContainerColor = SurfaceVariantDark
            ),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(
                "Save Report",
                color = if (photoPath.isNotEmpty() && notes.isNotEmpty()) TextOnPrimary else TextSecondary
            )
        }
    }
}
