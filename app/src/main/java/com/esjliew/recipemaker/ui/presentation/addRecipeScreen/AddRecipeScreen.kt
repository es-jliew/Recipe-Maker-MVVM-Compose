package com.esjliew.recipemaker.ui.presentation.addRecipeScreen

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    onNextClicked: () -> Unit,
    navigateBack: () -> Unit,
) {
    val viewModel: AddRecipeViewModel = koinViewModel()
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var ingredient by remember { mutableStateOf("") }
    var instruction by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    //For dropdown menu
    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded)
        Icons.Filled.ArrowDropUp
    else
        Icons.Filled.ArrowDropDown
    val typeList = arrayOf("Appetizer", "Main Course", "Dessert")
    var selectedText by remember { mutableStateOf(typeList[0]) }


    //Test photo picker
    var photoUri: Uri? by remember { mutableStateOf(null) }

    //The launcher we will use for the PickVisualMedia contract.
    //When .launch()ed, this will display the photo picker.
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        //When the user has selected a photo, its URI is returned here
        photoUri = uri
    }

    Scaffold(
        topBar = { AddRecipeTopBar(viewModel, navigateBack, name, photoUri.toString(), ingredient, instruction) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(padding)
                    .padding(all = 8.dp)
            ) {
                RecipeImageCard(launcher, photoUri)
                Divider(
                    modifier = Modifier.height(8.dp),
                    color = MaterialTheme.colorScheme.background
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        value = selectedText,
                        onValueChange = { type = it },
                        readOnly = true,
                        trailingIcon = {
                            Icon(icon,"contentDescription",
                                Modifier.clickable { expanded = !expanded })
                        }
                        //trailingIcon = TrailingIcon(expanded = expanded),
                        //trailingIcon = TrailingIcon(expanded.also { expanded = it }),
                    )
                    ExposedDropdownMenu(
                        modifier = Modifier.fillMaxWidth(),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        typeList.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    selectedText = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }
                Divider(
                    modifier = Modifier.height(8.dp),
                    color = MaterialTheme.colorScheme.background
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    //textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface,
                    //fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_regular)),
                    singleLine = true,
                    /*colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.LightGray,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),*/
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                    ),
                )
                Divider(
                    modifier = Modifier.height(8.dp),
                    color = MaterialTheme.colorScheme.background
                )
                OutlinedTextField(
                    value = ingredient,
                    onValueChange = { ingredient = it },
                    label = { Text(text = "Ingredient") },
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        //fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_regular)),
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                    /*colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),*/
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                    ),
                )
                Divider(
                    modifier = Modifier.height(8.dp),
                    color = MaterialTheme.colorScheme.background
                )
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = instruction,
                    onValueChange = { instruction = it },
                    label = { Text(text = "Instruction") },
                    //placeholder = { Text("Instruction", color = MaterialTheme.colorScheme.onSurface) },
                    textStyle = TextStyle(
                        color = MaterialTheme.colorScheme.onSurface,
                        //fontFamily = FontFamily(Font(R.font.plus_jakarta_sans_regular)),
                    ),
                    /*colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),*/
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                    ),
                )
            }
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                onClick = { onNextClicked() }
            ) {
                Text(
                    text = "Next"
                )
                Icon(
                    Icons.Outlined.ArrowForward,
                    contentDescription = "Next"
                )
            }
        }
    }
}

@Composable
fun RecipeImageCard(
    launcher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    photoUri: Uri?,
) {

    Box(
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

            /*Image(
                modifier = Modifier,
                painter = painter,
                //painter = painterResource(id = R.drawable.img2),
                contentDescription = "lucy pic",
                contentScale = ContentScale.Crop
            )*/
        }

        FilledTonalButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(all = 2.dp),
            onClick = { launcher.launch(PickVisualMediaRequest(
                //Here we request only photos. Change this to .ImageAndVideo if you want videos too.
                //Or use .VideoOnly if you only want videos.
                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
            ))},
        ) {
            Icon(Icons.Filled.Add,
                contentDescription = "add")
        }
    }
}

/*fun saveImageByUri(uri: Uri){
    val inputStream = contentResolver.openInputStream(uri)
    val output = FileOutputStream(File("${LocalContext.current.filesDir.absolutePath}/$fileName"))
    inputStream?.copyTo(output, 4 * 1024)
}*/
