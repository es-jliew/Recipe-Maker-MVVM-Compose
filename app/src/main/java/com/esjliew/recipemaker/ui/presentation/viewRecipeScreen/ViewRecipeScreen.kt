package com.esjliew.recipemaker.ui.presentation.viewRecipeScreen

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.esjliew.recipemaker.data.RecipeModel
import com.esjliew.recipemaker.ui.presentation.homeScreen.NoteSwappable
import com.esjliew.recipemaker.ui.presentation.homeScreen.ShowNoRecipe
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewRecipeScreen (
    recipeId: Int,
    onFabClicked: () -> Unit,
    navigateBack: () -> Unit,
) {
    val viewModel: ViewRecipeViewModel = koinViewModel()
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Ingredients", "Instructions")

    LaunchedEffect(Unit) {
        viewModel.getRecipeById(recipeId.toLong())
    }

    Scaffold(
        topBar = {  },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(0.dp, 0.dp, 20.dp, 32.dp),
                onClick = { onFabClicked() },
                //containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Icon(
                    Icons.Filled.Edit,
                    contentDescription = "add")
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(padding)
                    .padding(all = 8.dp)
            ) {
                RecipeImageCard(viewModel.recipeModel.imageUri?.toUri())
                Divider(
                    modifier = Modifier.height(8.dp),
                    color = MaterialTheme.colorScheme.background
                )
                Text(text = "${viewModel.recipeModel.name}",
                    modifier = Modifier.fillMaxWidth(),
                )
                Divider(
                    modifier = Modifier.height(8.dp),
                    color = MaterialTheme.colorScheme.background
                )
                TabRow(selectedTabIndex = state) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = state == index,
                            onClick = { state = index },
                            text = { Text(text = title, maxLines = 2, overflow = TextOverflow.Ellipsis) }
                        )
                    }
                }
                when (state) {
                    0 -> IngredientList(viewModel.recipeModel.ingredients)
                    1 -> InstructionsList(viewModel.recipeModel.instructions)
                }
                Divider(
                    modifier = Modifier.height(8.dp),
                    color = MaterialTheme.colorScheme.background
                )
            }
        }
    )
}

@Composable
fun RecipeImageCard(
    photoUri: Uri?
) {

    Card(
        modifier = Modifier
            .border(BorderStroke(1.dp, color = MaterialTheme.colorScheme.outline))
            .heightIn(188.dp, 188.dp)
            .fillMaxWidth(),
        //colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        if (photoUri != null) {
            AsyncImage(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = photoUri)
                    .build(),
                contentDescription = "Recipe Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
fun IngredientList(ingredientList: MutableList<String>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 20.dp, 0.dp, 0.dp)
    ) {
        items(ingredientList) { ingredient ->
            Text(
                text = ingredient,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun InstructionsList(instructionList: MutableList<String>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp, 20.dp, 0.dp, 0.dp)
    ) {
        items(instructionList) { instruction ->
            Text(
                text = instruction,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}