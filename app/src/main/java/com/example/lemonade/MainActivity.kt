package com.example.lemonade

import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.provider.CalendarContract.Colors
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonadeApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun LemonadeApp() {
    LemonadeTheme {
        Surface {
            Column {
                HeaderNav()
                ImageAndDescription(
                    Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center))
            }
        }
    }
}

data class ScreenContent(val imageResource: Int, val imageContentDescription: Int, val description: Int)

val lemonTreeScreen = ScreenContent(imageResource = R.drawable.lemon_tree, imageContentDescription = R.string.content_description_lemon_tree, description = R.string.tap_the_lemon_tree)
val lemonSqueezeScreen = ScreenContent(imageResource = R.drawable.lemon_squeeze, imageContentDescription = R.string.content_description_lemon, description = R.string.keep_tapping_the_lemon)
val lemonadeScreen = ScreenContent(imageResource = R.drawable.lemon_drink, imageContentDescription = R.string.content_description_glass_of_lemonade, description = R.string.tap_the_lemonade_to)
val emptyGlassScreen = ScreenContent(imageResource = R.drawable.lemon_restart, imageContentDescription = R.string.content_description_empty_glass, description = R.string.tap_the_empty_glass)

@Composable
fun HeaderNav() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(Color.Yellow)
            .height(48.dp)
            .fillMaxWidth()
    ) { Text(stringResource(id = R.string.app_name), fontWeight = FontWeight.Bold, fontSize = 20.sp)
    }
}


@Composable
fun ImageAndDescription(modifier: Modifier = Modifier) {
    var currentStep by remember {
        mutableIntStateOf(1)
    }

    var lemonTapCount by remember {
        mutableIntStateOf(0)
    }

    val screen = when(currentStep) {
        1 -> lemonTreeScreen
        2 -> lemonSqueezeScreen
        3 -> lemonadeScreen
        else -> emptyGlassScreen
    }

    fun restart() {
        currentStep = 1
        lemonTapCount = 0
    }

    fun goToNextStep() {
        if (currentStep == 4) {
            restart()
            return
        }

        if (currentStep == 2 && lemonTapCount < 3) {
            lemonTapCount += 1
            return
        }

        currentStep += 1
    }

    Column(horizontalAlignment =  Alignment.CenterHorizontally, modifier = modifier) {
        Box {
            Button(onClick = { goToNextStep() }, colors = ButtonDefaults.buttonColors(containerColor = Color.Green), shape = RoundedCornerShape(48.dp)) {
                Image(
                    painter = painterResource(id = screen.imageResource),
                    contentDescription = stringResource(id = screen.imageContentDescription)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(stringResource(id = screen.description))
    }
}