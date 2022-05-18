@file:OptIn(ExperimentalCoilApi::class)

package com.softprodigy.technicaltest.ui.screens

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.softprodigy.technicaltest.MainViewModel
import com.softprodigy.technicaltest.data.entities.User
import com.softprodigy.technicaltest.ui.ui.StaggeredVerticalGrid
import com.softprodigy.technicaltest.util.StoreUserEmail
import kotlinx.coroutines.launch


sealed class Screens(val route: String, val title: String) {

    sealed class DrawerScreens(
        route: String,
        title: String,
        val icon: ImageVector
    ) : Screens(route, title) {
        object Home : DrawerScreens("home", "Home",Icons.Filled.Home)
        object Images : DrawerScreens("images", "Images",Icons.Filled.Info)
        object Profile : DrawerScreens("profile", "Profile",Icons.Filled.Person)
    }

    sealed class LoginScreen(
        route: String,
        title: String
    ) : Screens(route, title) {
        object login : LoginScreen("login", "login")

    }

}

val screensInHomeFromBottomNav = listOf(
    Screens.DrawerScreens.Home,
    Screens.DrawerScreens.Images,
    Screens.DrawerScreens.Profile,
)

val screensFromDrawer = listOf(
    Screens.DrawerScreens.Home,
    Screens.DrawerScreens.Images,
    Screens.DrawerScreens.Profile,
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun Home(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(Screens.DrawerScreens.Home)
    val userList by viewModel.movieList.observeAsState(null)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewModel.getUsers()

        StaggeredVerticalGrid(
            maxColumnWidth = (150..220).random().dp,
            modifier = Modifier.padding(4.dp)
        ) {
            ((userList ?: emptyList()) as List<User>).forEach {
                Card(it.avatar)
            }
        }
      /*

      //uncomment this and comment above StaggeredVerticalGrid for Simple Grid


        LazyVerticalGrid(modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(), cells = GridCells.Fixed(2)) {
                items((userList ?: emptyList()) as List<User>) { user ->
                    val url = user.avatar
                    PhotoItem(
                        modifier = Modifier.padding(4.dp),
                        data = url
                    )

                }
            }*/
        }
    }

@Composable
fun Card(
    id: String,

) {



        Column (modifier = Modifier
            .height((50..200).random().dp)
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {

            PhotoItem(
                modifier = Modifier.padding(4.dp),
                data = id
            )
        }

}
@Composable
fun PhotoItem(modifier: Modifier = Modifier, data: Any) {
    val painter = rememberImagePainter(
        data = data,
        builder = {
            // transition when placeholder -> image
        }
    )

    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier.aspectRatio(1.0f),
        contentScale = ContentScale.Crop
    )
}



@OptIn(ExperimentalPagerApi::class)
@Composable
fun Images(modifier: Modifier = Modifier, viewModel: MainViewModel) {
    viewModel.setCurrentScreen(Screens.DrawerScreens.Images)
    val userList by viewModel.movieList.observeAsState(null)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        viewModel.getUsers()
        val pagerState = rememberPagerState()
        HorizontalPager(
            count = ((userList ?: emptyList()) as List<User>).size,
            state = pagerState
        ) { page ->
            // Our page content

            PhotoItem(
                modifier = Modifier.padding(4.dp),
                data = userList!![page].avatar
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
        )
    }
}
    @Composable
    fun Profile(modifier: Modifier = Modifier, viewModel: MainViewModel) {

        viewModel.setCurrentScreen(Screens.DrawerScreens.Profile)
        val context = LocalContext.current
        val scope = rememberCoroutineScope()
        val dataStore = StoreUserEmail(context)
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    //launch the class in a coroutine scope
                    scope.launch {
                        dataStore.clearEmail()

                    }

                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    (context as Activity).startActivity(intent)
                    (context as Activity).finish()

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(16.dp, 0.dp, 16.dp, 0.dp),
            ) {
                Text(
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White,
                    text = "Logout",

                    )
            }
        }
    }
