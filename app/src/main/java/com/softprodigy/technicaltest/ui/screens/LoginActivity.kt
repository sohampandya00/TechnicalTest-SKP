package com.softprodigy.technicaltest.ui.screens
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.softprodigy.technicaltest.ui.theme.NavigationDrawerTheme
import com.softprodigy.technicaltest.util.StoreUserEmail
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDrawerTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    LoginSCreen()
                }
            }
        }
    }

}

@Composable
fun LoginSCreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreUserEmail(context)

    val userEmail = dataStore.getEmail.collectAsState(initial = null)

    if(userEmail.value != null)
    {
        context.startActivity(Intent(context, MainActivity::class.java))
    }


    Column(modifier = Modifier.fillMaxSize(),Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        var email by rememberSaveable { mutableStateOf("") }
        var password by rememberSaveable { mutableStateOf("") }

        Text(
            style = MaterialTheme.typography.h5,
            color = Color.Gray,
            text = "Login Screen",
            textAlign = TextAlign.Center
            )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            placeholder = { Text("Enter Email") },
            value = email,
            onValueChange = { email = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType
                = KeyboardType.Email
            ),
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            placeholder = { Text("Enter Password") },
            value = password,
            onValueChange = { password = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType
                = KeyboardType.Password
            ),
            modifier = Modifier
                .padding(16.dp, 0.dp, 16.dp, 0.dp)
                .fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                scope.launch {
                    dataStore.saveEmail(email)
                }
                    context.startActivity(Intent(context, MainActivity::class.java))


            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
        ) {

            Text(
                style = MaterialTheme.typography.subtitle1,
                color = Color.White,
                text = "Login",

                )
        }

        Spacer(modifier = Modifier.height(32.dp))

    }
}
