package com.example.newapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.newapplication.ui.theme.NewApplicationTheme
import com.example.newapplication.ui.theme.PrimaryLight

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewApplicationTheme  {
                SplashScreenContent()
            }
        }
    }

}

@Composable
private fun SplashScreenContent() {
    val context  = LocalContext.current
    LaunchedEffect(Unit){
        Handler(Looper.getMainLooper())
            .postDelayed({
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                (context as? ComponentActivity)?.finish()

            },3000)


    }
    SplashContent()
}
@Composable
fun SplashContent(modifier: Modifier = Modifier) {


    Column(modifier = Modifier.fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .navigationBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Spacer(modifier = Modifier.weight(2F))
        Image(
            painter = painterResource(R.drawable.new_log_light),
            contentDescription = stringResource(R.string.logo),
            modifier = Modifier.size(150.dp)

        )
        Spacer(modifier = Modifier.weight(1F))
        Image(
            painter = painterResource(R.drawable.signature_light),
            contentDescription = stringResource(R.string.logo),
            modifier = Modifier.size(200.dp)
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun SplashContentPreview() {
    SplashContent()

}

