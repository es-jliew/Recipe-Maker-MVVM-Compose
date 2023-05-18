package com.esjliew.recipemaker.ui.presentation.homeScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.esjliew.recipemaker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar (
    //navigateToAboutScreen:()-> Unit
)
{
    CenterAlignedTopAppBar(
        title = {
            Text(stringResource(R.string.app_name),
                //fontFamily = FontFamily(Font(R.font.playfair_display_regular)),
            )
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = "logo",
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
            )
        },
        /*actions = {
            IconButton(onClick = { navigateToAboutScreen() }) {
                Icon(
                    painterResource(
                        R.drawable.ic_launcher_foreground),
                    contentDescription = "about")
            }
        }*/
    )
}