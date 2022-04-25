package fr.synchrone.glpisupport.presentation.home

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
 * Dialog used in home composable to change user's profile or entity.
 */
@Composable
fun ChangeProfileDialog(
    screenHeight: Dp,
    title: String,
    areProfilesLoading: Boolean,
    areEntitiesLoading: Boolean,
    description: String,
    profilesPickerElements: PickersElementsGrouped,
    profilesNameValue: String?,
    entitiesPickerElements: PickersElementsGrouped,
    entitiesNameValue: String?,
    onProfileIdChange: (Int) -> Unit,
    onEntityIdChange: (Int) -> Unit,
    onTapConfirm: () -> Unit,
    isVisible: Boolean
) {

    val profileNameLabel = "Rôle"
    val entityNameLabel = "Entité"
    val textFieldHeight = 75.dp

    //Interaction

    val interactionSource = remember { MutableInteractionSource() }

    //State

    var isPickerComposableVisible by remember { mutableStateOf(false) }
    var pickerLabelToEdit by remember { mutableStateOf("") }
    var pickerElements by remember { mutableStateOf<PickersElementsGrouped>(mapOf()) }

    //Animations

    val backgroundAlpha by animateFloatAsState(targetValue = if (isVisible) 0.5f else 0f)
    val mainContentAlpha by animateFloatAsState(targetValue = if (isVisible) 1f else 0f, tween(delayMillis = 0, easing = FastOutSlowInEasing))
    val mainContentYOffset by animateDpAsState(targetValue = if (isVisible) 0.dp else 15.dp, tween(delayMillis = 0, easing = FastOutSlowInEasing))

    fun showPickerComposable(label: String) {
        pickerLabelToEdit = label
        pickerElements = when(pickerLabelToEdit) {
            profileNameLabel -> profilesPickerElements
            entityNameLabel -> entitiesPickerElements
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
            minHeight = 400.dp
        ),
        onTapOutside = { onTapConfirm() },
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
                        .alpha(mainContentAlpha)
                        .offset(y = mainContentYOffset)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(title, fontFamily = AppFonts.openSans, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center, fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(description, fontFamily = AppFonts.openSans, fontWeight = FontWeight.Normal, textAlign = TextAlign.Center, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(32.dp))
                    PickerTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(textFieldHeight),
                        value = profilesNameValue ?: "",
                        label = profileNameLabel,
                        enabled = !areProfilesLoading && !areEntitiesLoading,
                        onClick = {
                            showPickerComposable(label = profileNameLabel)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    PickerTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .height(textFieldHeight),
                        value = entitiesNameValue ?: "",
                        label = entityNameLabel,
                        enabled = !areProfilesLoading && !areEntitiesLoading && entitiesPickerElements.isNotEmpty(),
                        onClick = {
                            showPickerComposable(label = entityNameLabel)
                        }
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = { onTapConfirm() },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
                        ) {
                            Text("Quitter", fontFamily = AppFonts.openSans, fontWeight = FontWeight.SemiBold, color = Color.White)
                        }
                    }
                }
            }
        }
    )

    PickerComposable(
        screenHeight = screenHeight,
        isVisible = isPickerComposableVisible,
        elements = pickerElements,
        onElementSelected = {
            if(pickerLabelToEdit == profileNameLabel) {
                onProfileIdChange(it.id)
            } else if (pickerLabelToEdit == entityNameLabel) {
                onEntityIdChange(it.id)
            }
            hidePickerComposable()
        }, onTapCancel = {
            hidePickerComposable()
        })

}