@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection"
)

package fr.synchrone.glpisupport.presentation.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.synchrone.glpisupport.presentation.items.viewmodel.PickersElementsGrouped
import fr.synchrone.glpisupport.presentation.theme.AppFonts
import fr.synchrone.glpisupport.presentation.utilities.PickerTextField
import fr.synchrone.glpisupport.presentation.utilities.picker.GenericBottomPopup
import fr.synchrone.glpisupport.presentation.utilities.picker.GenericBottomPopupSize
import fr.synchrone.glpisupport.presentation.utilities.picker.PickerComposable

/**
 * Dialog used in home composable to create item.
 */
@Composable
fun AddItemDialog(
    screenHeight: Dp,
    title: String,
    serial: String,
    description: String,
    typesPickerElements: PickersElementsGrouped,
    typeNameValue: String,
    templatesPickerElements: PickersElementsGrouped,
    templateNameValue: String,
    onTypeIdChange: (Int) -> Unit,
    onTemplateIdChange: (Int) -> Unit,
    onTapCancel: () -> Unit,
    onTapConfirm: () -> Unit,
    isVisible: Boolean
) {

    val typeLabel = "Type de l'appareil"
    val templatesNameLabel = "Modèle (facultatif)"
    val textFieldHeight = 75.dp

    val pickerFieldBackgroundColor = MaterialTheme.colors.secondary
    val pickerFieldOnBackgroundColor = MaterialTheme.colors.onSecondary

    val interactionSource = remember { MutableInteractionSource() }

    var isPickerComposableVisible by remember { mutableStateOf(false) }
    var pickerLabelToEdit by remember { mutableStateOf("") }
    var pickerElements by remember { mutableStateOf<PickersElementsGrouped>(mapOf()) }

    val backgroundAlpha by animateFloatAsState(targetValue = if (isVisible) 0.5f else 0f)

    fun showPickerComposable(label: String) {
        pickerLabelToEdit = label
        pickerElements = when(pickerLabelToEdit) {
            typeLabel -> typesPickerElements
            templatesNameLabel -> templatesPickerElements
            else -> mapOf()
        }
        isPickerComposableVisible = true
    }

    fun hidePickerComposable() {
        isPickerComposableVisible = false
        pickerElements = mapOf()
        pickerLabelToEdit = ""
    }

    Box(modifier = Modifier
        .alpha(backgroundAlpha)
        .fillMaxSize()
        .offset(y = if (isVisible) 0.dp else screenHeight)
        .background(Color.Black)
        .clickable(
            interactionSource = interactionSource,
            indication = null
        ) { }
    )

    GenericBottomPopup(
        screenHeight = screenHeight,
        isVisible = isVisible,
        size = GenericBottomPopupSize(
            percentHeight = 0.5f,
            minHeight = 450.dp
        ),
        onTapOutside = { onTapCancel() },
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .clip(RoundedCornerShape(5.dp))
                        .background(MaterialTheme.colors.background)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        title,
                        fontFamily = AppFonts.openSans,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        serial,
                        fontFamily = AppFonts.openSans,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        description,
                        fontFamily = AppFonts.openSans,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    PickerTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(textFieldHeight),
                        value = typeNameValue,
                        label = typeLabel,
                        backgroundColor = pickerFieldBackgroundColor,
                        onBackgroundColor = pickerFieldOnBackgroundColor,
                        enabled = true,
                        onClick = {
                            showPickerComposable(label = typeLabel)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PickerTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(textFieldHeight),
                        value = templateNameValue,
                        label = templatesNameLabel,
                        backgroundColor = pickerFieldBackgroundColor,
                        onBackgroundColor = pickerFieldOnBackgroundColor,
                        enabled = templatesPickerElements.isNotEmpty(),
                        onClick = {
                            showPickerComposable(label = templatesNameLabel)
                        }
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { onTapCancel() },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
                            enabled = isVisible
                        ) {
                            Text(
                                "Annuler",
                                fontFamily = AppFonts.openSans,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = { onTapConfirm() },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                        ) {
                            Text(
                                "Confirmer",
                                fontFamily = AppFonts.openSans,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    )

    PickerComposable(
        screenHeight = screenHeight,
        isVisible = isPickerComposableVisible,
        elements = pickerElements,
        onElementSelected = {
            if(pickerLabelToEdit == typeLabel) {
                onTypeIdChange(it.id)
            } else if (pickerLabelToEdit == templatesNameLabel) {
                onTemplateIdChange(it.id)
            }
            hidePickerComposable()
        }, onTapCancel = {
            hidePickerComposable()
        }
    )

}