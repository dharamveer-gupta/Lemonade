package com.dharamveer.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.dharamveer.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    var currentStep by remember { mutableIntStateOf(1) }

    var squeezeCount by remember { mutableIntStateOf(0) }

    val content = when (currentStep) {
        1 -> lemonadeContent(R.drawable.lemon_tree, R.string.pick_lemon, R.string.lemon_tree)
        2 -> lemonadeContent(R.drawable.lemon_squeeze, R.string.squeeze_lemon, R.string.lemon)
        3 -> lemonadeContent(R.drawable.lemon_drink, R.string.drink_lemonade, R.string.glass_of_lemonade)
        4 -> lemonadeContent(R.drawable.lemon_restart, R.string.start_again, R.string.empty_glass)
        else -> lemonadeContent(R.drawable.lemon_tree, R.string.pick_lemon, R.string.lemon_tree)
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(text = "Lemonade", fontWeight = FontWeight.Bold)
            }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            )
        )
    }) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            LemonTextAndImage(
                content = content,
                onImageClick = {
                    when (currentStep) {
                        1 -> {
                            currentStep = 2
                            squeezeCount = (2..4).random()
                        }
                        2 -> {
                            squeezeCount--
                            if (squeezeCount == 0) {
                                currentStep = 3
                            }
                        }
                        3 -> {
                            currentStep = 4
                        }
                        4 -> {
                            currentStep = 1
                        }
                    }
                }
            )

        }
    }
}

@Composable
fun LemonTextAndImage(content: lemonadeContent, onImageClick: () -> Unit, modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = onImageClick, shape = RoundedCornerShape(dimensionResource(id = R.dimen.button_corner_radius)),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer)
            ) {
                Image(
                    painter = painterResource(id = content.first),
                    contentDescription = stringResource(id = content.third),
                    modifier = Modifier
                        .width(dimensionResource(R.dimen.button_image_width))
                        .height(dimensionResource(R.dimen.button_image_height))
                        .padding(dimensionResource(id = R.dimen.button_interior_padding))
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_vertical)))
            Text(text = stringResource(id = content.second), style = MaterialTheme.typography.bodyLarge)
        }

    }

}

typealias lemonadeContent = Triple<Int, Int, Int>