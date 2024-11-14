package com.example.pm_rgbselector

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush.Companion.horizontalGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pm_rgbselector.ui.theme.PM_RGBSelectorTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PM_RGBSelectorTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = "Selector de color RGB", fontWeight = FontWeight.Bold)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = androidx.compose.material3.TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(0xFFD3DDF5)
                            )
                        )
                    }
                ) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)
                    var r by remember { mutableStateOf(255) }
                    var g by remember { mutableStateOf(255) }
                    var b by remember { mutableStateOf(255) }
                    val clipboardManager: ClipboardManager = LocalClipboardManager.current
                    val context = LocalContext.current

                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        // Sliders de Color
                        ColorSlider("Red", r, { r = it }, Color.Red)
                        ColorSlider("Green", g, { g = it }, Color.Green)
                        ColorSlider("Blue", b, { b = it }, Color.Blue)

                        HorizontalDivider(thickness = 1.dp)

                        // Vista previa RGB (clic para copiar RGB)
                        ColorPreview(
                            text = "rgb($r, $g, $b)",
                            color = Color(r, g, b),
                            onClick = {
                                val rgb = "rgb($r, $g, $b)"
                                clipboardManager.setText(AnnotatedString(rgb))
                                Toast.makeText(context, "Color $rgb copiado al portapapeles", Toast.LENGTH_SHORT).show()
                            }
                        )

                        // Vista previa HEX (clic para copiar HEX)
                        ColorPreview(
                            text = "#${r.toString(16).padStart(2, '0')}${g.toString(16).padStart(2, '0')}${b.toString(16).padStart(2, '0')}".uppercase(),
                            color = Color(r, g, b),
                            onClick = {
                                val hexColor = "#${r.toString(16).padStart(2, '0')}${g.toString(16).padStart(2, '0')}${b.toString(16).padStart(2, '0')}".uppercase()
                                clipboardManager.setText(AnnotatedString(hexColor))
                                Toast.makeText(context, "Color $hexColor copiado al portapapeles", Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ColorSlider(label: String, value: Int, onValueChange: (Int) -> Unit, color: Color) {
    val thumbColor = when (label) {
        "Red" -> Color(value, 0, 0)
        "Green" -> Color(0, value, 0)
        "Blue" -> Color(0, 0, value)
        else -> color
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$label: $value",
            fontSize = 18.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Slider(
            value = value.toFloat(),
            onValueChange = { onValueChange(it.toInt()) },
            valueRange = 0f..255f,
            colors = SliderDefaults.colors(
                thumbColor = thumbColor,
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = horizontalGradient(
                        colors = listOf(Color.Black, color)
                    ),
                    shape = RoundedCornerShape(4.dp)
                )
                .height(8.dp)
        )
    }
}

@Composable
fun ColorPreview(text: String, color: Color, onClick: () -> Unit) {
    // Determinar el color del texto dependiendo de la claridad del fondo
    val textColor = if (color.red * 0.299 + color.green * 0.587 + color.blue * 0.114 > 0.5) {
        Color.Black // Texto negro si el fondo es claro
    } else {
        Color.White // Texto blanco si el fondo es oscuro
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(color, RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            fontSize = 24.sp,
            color = textColor
        )
    }
}
