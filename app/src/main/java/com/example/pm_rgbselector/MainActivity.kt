package com.example.pm_rgbselector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pm_rgbselector.ui.theme.PM_RGBSelectorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PM_RGBSelectorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val modifier = Modifier.padding(innerPadding)
                    var r by remember{ mutableStateOf(255)}
                    var g by remember{ mutableStateOf(255)}
                    var b by remember{ mutableStateOf(255)}

                    Column (modifier = modifier.fillMaxSize().padding(top = 15.dp)) {
                        Column (
                            verticalArrangement = Arrangement.spacedBy(5.dp)
                        ){
                            RedLazyRow { nuevoValor -> r = nuevoValor }
                            HorizontalDivider()
                            GreenLazyRow { nuevoValor -> g = nuevoValor }
                            HorizontalDivider()
                            BlueLazyRow { nuevoValor -> b = nuevoValor }
                        }

                        Column (
                            modifier
                                .fillMaxSize()
                                .background(Color(r,g,b)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(
                                text = "rgb($r,$g,$b)\n",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "#${r.toString(16)}${g.toString(16)}${b.toString(16)}",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RedLazyRow(onButtonSelected: (Int) -> Unit) {
    LazyRow () {
        items(255) { indice ->
            Button(
                onClick = {onButtonSelected(255 - indice)},
                colors = ButtonDefaults.buttonColors(Color(255 - indice,0,0)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(2.dp)
                    .height(50.dp)
                    .width(90.dp)
            ) {
                Text(
                    text = "${255-indice}",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun GreenLazyRow(onButtonSelected: (Int) -> Unit) {
    LazyRow () {
        items(255) { indice ->
            Button(
                onClick = {onButtonSelected(255 - indice)},
                colors = ButtonDefaults.buttonColors(Color(0,255 - indice,0)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(2.dp)
                    .height(50.dp)
                    .width(90.dp)
            ) {
                Text(
                    text = "${255-indice}",
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun BlueLazyRow(onButtonSelected: (Int) -> Unit) {
    LazyRow () {
        items(255) { indice ->
            Button(
                onClick = {onButtonSelected(255 - indice)},
                colors = ButtonDefaults.buttonColors(Color(0,0,255 - indice)),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(2.dp)
                    .height(50.dp)
                    .width(90.dp)
            ) {
                Text(
                    text = "${255-indice}",
                    fontSize = 20.sp
                )
            }
        }
    }
}