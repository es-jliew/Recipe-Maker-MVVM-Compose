package com.esjliew.recipemaker.ui.presentation.homeScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.esjliew.recipemaker.R
import com.esjliew.recipemaker.data.RecipeModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onFabClicked: () -> Unit,
    navigateToViewRecipeScreen: (recipeId: Int) -> Unit
) {
    val viewModel: HomeViewModel = koinViewModel()
    val recipesModel = viewModel.recipeModel

    LaunchedEffect(Unit) {
        //viewModel.addRecipe()
        //Log.d("Box", "Added recipe.")
        viewModel.getAll()
        Log.d("Box", "Get all recipe.")
    }
    Scaffold(
        topBar = { HomeTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(0.dp, 0.dp, 20.dp, 32.dp),
                onClick = { onFabClicked() },
                //containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = "add")
            }
        },
        content = { padding ->
            Surface(
                modifier = Modifier.padding(padding),
                //shape = RoundedCornerShape(32.dp, 32.dp),
                //color = colorResource(id = R.color.colorBackground)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 20.dp, 0.dp, 0.dp)
                ) {
                    if (recipesModel.isNotEmpty()) {
                        items(recipesModel) { recipeModel ->
                            NoteSwappable(recipeModel, viewModel, navigateToViewRecipeScreen)
                        }
                    } else {
                        item {
                            ShowNoRecipe()
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun NoteSwappable(
    recipeModel: RecipeModel,
    viewModel: HomeViewModel,
    navigateToUpdateRecipeScreen: (noteId: Int) -> Unit,
) {
    RecipeCard(recipeModel, navigateToUpdateRecipeScreen)
}

@Composable
fun ShowNoRecipe() {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(0.dp, 120.dp, 0.dp, 0.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "empty",
            modifier = Modifier.fillMaxWidth(),
            alignment = Alignment.Center
        )
        Text(text = "Your recipes will show here",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun RecipeCard(
    recipeModel: RecipeModel,
    navigateToViewRecipeScreen: (recipeId: Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .heightIn(0.dp, 188.dp)
            .fillMaxWidth()
            .padding(20.dp, 5.dp)
            .clickable {
                navigateToViewRecipeScreen(recipeModel.id.toInt())
                Log.i("HomeScreen", "onCardClicked")
            },
        //colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        //shape = RoundedCornerShape(12.dp)
    ) {
        Log.d("Box", "${recipeModel.imageUri}")
        AsyncImage(
            model = ImageRequest
                .Builder(LocalContext.current)
                .data(data = recipeModel.imageUri?.toUri())
                .build(),
            contentDescription = "Recipe Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
        )
        /*Image(
            modifier = Modifier
                .fillMaxHeight(0.7f),
            painter = painterResource(id = R.drawable.img2),
            contentDescription = "lucy pic",
            contentScale = ContentScale.Crop
        )*/
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp, 6.dp)
        ) {
            Text(
                text = recipeModel.name.toString(),
                fontSize = 24.sp,
                //fontFamily = FontFamily(Font(R.font.playfair_display_regular)),

                )
            /*Text(
                text = recipeModel.instructions.toString(),
                //fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_regular)),
                lineHeight = 17.sp
            )*/
        }
    }
}