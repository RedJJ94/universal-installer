package app.pwhs.universalinstaller.presentation.manage.components

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.DriveFileRenameOutline
import androidx.compose.material.icons.rounded.Folder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import app.pwhs.universalinstaller.R
import app.pwhs.universalinstaller.presentation.composable.SettingsSection
import app.pwhs.universalinstaller.presentation.manage.BackupsUiState
import app.pwhs.universalinstaller.presentation.manage.DirectoryPickerDialog

fun formatExtractorOutputPath(context: Context, raw: String): String {
    if (raw.isBlank()) return context.getString(R.string.backup_output_path_default)
    if (raw.startsWith("/")) {
        val root = android.os.Environment.getExternalStorageDirectory().path
        return if (raw.startsWith(root)) {
            "${context.getString(R.string.backup_output_path_internal_storage)}${raw.removePrefix(root)}"
        } else raw
    }
    val uri = android.net.Uri.parse(raw)
    val docId = android.provider.DocumentsContract.getTreeDocumentId(uri)
    return when {
        docId == null -> context.getString(R.string.backup_output_path_selected_folder)
        docId.startsWith("primary:") ->
            "${context.getString(R.string.backup_output_path_internal_storage)}/${docId.removePrefix("primary:")}"
        else -> docId
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackupsSettingsSheet(
    uiState: BackupsUiState,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onSetOutputPath: (String) -> Unit,
    onSetFilenameTemplate: (String) -> Unit,
    onSetSplitFormat: (String) -> Unit,
    onSetIncludeObb: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    var showDirPicker by remember { mutableStateOf(false) }

    if (showDirPicker) {
        DirectoryPickerDialog(
            onPick = { path ->
                onSetOutputPath(path)
                showDirPicker = false
            },
            onDismiss = { showDirPicker = false },
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Extraction Settings",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )

            SettingsSection(
                title = "APK Extractor",
                icon = Icons.Rounded.Folder,
            ) {
                val folderPickerLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.OpenDocumentTree()
                ) { uri ->
                    uri?.let { onSetOutputPath(it.toString()) }
                }

                val safeLaunchFolderPicker = {
                    val launched = runCatching {
                        folderPickerLauncher.launch(null)
                    }.isSuccess
                    if (!launched) {
                        android.widget.Toast.makeText(
                            context,
                            context.getString(R.string.error_no_file_picker),
                            android.widget.Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                val currentPath = uiState.extractorOutputPath
                val displayPath = formatExtractorOutputPath(context, currentPath)

                OutlinedTextField(
                    value = displayPath,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text(stringResource(R.string.backup_output_path)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { safeLaunchFolderPicker() },
                    leadingIcon = { Icon(Icons.Rounded.Folder, null) },
                    trailingIcon = {
                        IconButton(onClick = { safeLaunchFolderPicker() }) {
                            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, null)
                        }
                    },
                    enabled = true,
                    interactionSource = remember { MutableInteractionSource() }
                        .also { interactionSource ->
                            LaunchedEffect(interactionSource) {
                                interactionSource.interactions.collect {
                                    if (it is PressInteraction.Release) {
                                        safeLaunchFolderPicker()
                                    }
                                }
                            }
                        }
                )

                TextButton(
                    onClick = { showDirPicker = true },
                    modifier = Modifier.padding(horizontal = 16.dp),
                ) {
                    Icon(
                        Icons.Rounded.Folder,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                    )
                    Spacer(Modifier.size(8.dp))
                    Text(stringResource(R.string.dir_picker_browse_action))
                }

                var templateField by remember {
                    mutableStateOf(TextFieldValue(uiState.extractorFilenameTemplate))
                }
                OutlinedTextField(
                    value = templateField,
                    onValueChange = {
                        templateField = it
                        onSetFilenameTemplate(it.text)
                    },
                    label = { Text(stringResource(R.string.backup_filename_template)) },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    leadingIcon = { Icon(Icons.Rounded.DriveFileRenameOutline, null) },
                    placeholder = { Text("{name}-{version}") },
                    supportingText = { Text(stringResource(R.string.backup_tags_hint)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
                )

                Text(
                    text = stringResource(R.string.backup_split_format_label),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    listOf("apks" to "APKS", "xapk" to "XAPK").forEach { (value, label) ->
                        FilterChip(
                            selected = uiState.extractorSplitFormat == value,
                            onClick = { onSetSplitFormat(value) },
                            label = { Text(label) },
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                ListItem(
                    headlineContent = { Text(stringResource(R.string.backup_include_obb_title)) },
                    supportingContent = { Text(stringResource(R.string.backup_include_obb_desc)) },
                    trailingContent = {
                        Switch(
                            checked = uiState.extractorIncludeObb,
                            onCheckedChange = onSetIncludeObb,
                        )
                    },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                )
            }

            TextButton(
                onClick = onDismiss,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.dialog_success_done))
            }
        }
    }
}
