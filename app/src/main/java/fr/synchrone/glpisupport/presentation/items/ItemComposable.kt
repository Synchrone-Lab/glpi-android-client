package fr.synchrone.glpisupport.presentation.items

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.core.ViewState
import fr.synchrone.glpisupport.presentation.items.viewmodel.ItemViewModel
import fr.synchrone.glpisupport.presentation.theme.AppFonts
import fr.synchrone.glpisupport.presentation.utilities.*
import fr.synchrone.glpisupport.presentation.utilities.picker.PickerComposable
import fr.synchrone.glpisupport.presentation.utilities.picker.PickerElement
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * The item creation/edition screen.
 *
 * @see ItemViewModel
 */
@ExperimentalAnimationApi
@Composable
fun ItemComposable(navController: NavController, screenHeight: Dp, itemViewModel: ItemViewModel) {

    val itemFieldHeightDp = 50.dp
    val commentFieldHeightDp = 100.dp
    val itemFieldWidthPercentScreen = 0.8f
    val offsetsDuration = 1000

    //State

    var isVisible by remember { mutableStateOf(true) }
    var isSaving by remember { mutableStateOf(false) }
    var isViewAppeared by remember { mutableStateOf(false) }
    var isDeviceLogoAppeared by remember { mutableStateOf(false) }
    var isDeviceTypeNameAppeared by remember { mutableStateOf(false) }
    var isDeviceNameAppeared by remember { mutableStateOf(false) }
    var isDeviceInfosTitleAppeared by remember { mutableStateOf(false) }
    var isLoaderAppeared by remember { mutableStateOf(false) }
    var isPickerComposableVisible by remember { mutableStateOf(false) }
    var pickerElements by remember { mutableStateOf(mapOf<PickerElement, List<PickerElement>>()) }
    var pickerLabelToEdit by remember { mutableStateOf("") }
    var isDatePickerVisible by remember { mutableStateOf(false) }
    var dateLabelToEdit by remember { mutableStateOf("") }

    //Interaction

    val interactionSource = remember { MutableInteractionSource() }
    val scrollState = rememberScrollState()
    val scrollCoroutine = rememberCoroutineScope()
    val saveCoroutine = rememberCoroutineScope()

    //View model binding

    val selectedTab by itemViewModel.selectedTab.observeAsState(initial = ItemViewModel.Tab.ITEM_INFOS)
    val itemName by itemViewModel.currentItemName.observeAsState(initial = "")
    val locations by itemViewModel.locationsByEntity.observeAsState(initial = mapOf())
    val groups by itemViewModel.groupsByEntities.observeAsState(initial = mapOf())
    val suppliers by itemViewModel.suppliersByEntity.observeAsState(initial = mapOf())
    val users by itemViewModel.usersByEntity.observeAsState(initial = mapOf())
    val superAdminUsers by itemViewModel.superAdminUsersByEntity.observeAsState(initial = mapOf())
    val states by itemViewModel.statesByEntity.observeAsState(initial = mapOf())
    val types by itemViewModel.typesByEntity.observeAsState(initial = mapOf())
    val manufacturers by itemViewModel.manufacturersByEntity.observeAsState(initial = mapOf())
    val models by itemViewModel.modelsByEntity.observeAsState(initial = mapOf())
    val softwares by itemViewModel.softwaresByEntities.observeAsState(initial = mapOf())
    val softwaresLicenses by itemViewModel.softwaresLicensesByEntities.observeAsState(initial = mapOf())
    val businessCriticities by itemViewModel.businessCriticitiesByEntities.observeAsState(initial = mapOf())
    val budgets by itemViewModel.budgetsByEntities.observeAsState(initial = mapOf())
    val dcrooms by itemViewModel.dcrooms.observeAsState(initial = mapOf())
    val staticsPossiblesValuesByLabel by itemViewModel.staticsPossiblesValuesByLabel.observeAsState(initial = mapOf())
    val fields by itemViewModel.displayedFields.observeAsState(initial = listOf())
    val fieldsValues by itemViewModel.displayedFieldsValue.observeAsState(initial = mapOf())
    val isFetchingErrorDialogVisible by itemViewModel.isFetchingErrorDialogVisible.observeAsState(initial = false)
    val fetchingErrorMessage by itemViewModel.fetchingErrorMessage.observeAsState(initial = null)
    val savingErrorMessage by itemViewModel.savingErrorMessage.observeAsState(initial = null)
    val isSavingErrorMessageVisible by itemViewModel.isSavingErrorMessageVisible.observeAsState(initial = false)

    //Animations

    val wavesXOffset by animateDpAsState(targetValue = if (isViewAppeared) (-500).dp else 0.dp, tween(offsetsDuration, easing = FastOutSlowInEasing))
    val wavesYOffset by animateDpAsState(targetValue = if (isViewAppeared) 0.dp else -screenHeight/3 - 100.dp, tween(offsetsDuration, easing = FastOutSlowInEasing))
    val deviceLogoAlpha by animateFloatAsState(targetValue = if (isDeviceLogoAppeared) 1f else 0f, tween(easing = FastOutSlowInEasing))
    val deviceTypeNameLogoAlpha by animateFloatAsState(targetValue = if (isDeviceTypeNameAppeared) 1f else 0f, tween(easing = FastOutSlowInEasing))
    val deviceTypeNameLogoScale by animateFloatAsState(targetValue = if (isDeviceTypeNameAppeared) 1f else 0.8f, tween(easing = FastOutSlowInEasing))
    val deviceNameLogoAlpha by animateFloatAsState(targetValue = if (isDeviceTypeNameAppeared) 1f else 0f, tween(easing = FastOutSlowInEasing))
    val deviceNameLogoScale by animateFloatAsState(targetValue = if (isDeviceNameAppeared) 1f else 0.8f, tween(easing = FastOutSlowInEasing))
    val deviceInfoTitleAlpha by animateFloatAsState(targetValue = if (isDeviceInfosTitleAppeared) 1f else 0f, tween(easing = FastOutSlowInEasing))
    val deviceInfoTitleYOffset by animateDpAsState(targetValue = if (isDeviceInfosTitleAppeared) 0.dp else (-15).dp, tween(easing = FastOutSlowInEasing))
    val loaderAlpha by animateFloatAsState(targetValue = if (isLoaderAppeared) 1f else 0f, tween(easing = FastOutSlowInEasing))
    val loaderYOffset by animateDpAsState(targetValue = if (isLoaderAppeared) 0.dp else (-15).dp, tween(easing = FastOutSlowInEasing))

    val textfieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = MaterialTheme.colors.onBackground,
        cursorColor = MaterialTheme.colors.onBackground,
        focusedLabelColor = MaterialTheme.colors.onBackground,
        backgroundColor = MaterialTheme.colors.background,
        textColor = MaterialTheme.colors.onBackground,
        unfocusedLabelColor = MaterialTheme.colors.onBackground
    )

    fun showPickerComposable(elements: Map<PickerElement, List<PickerElement>>, label: String) {
        pickerElements = elements
        pickerLabelToEdit = label
        isPickerComposableVisible = true
    }

    fun hidePickerComposable() {
        isPickerComposableVisible = false
        pickerElements = mapOf()
        pickerLabelToEdit = ""
    }

    fun showDatePicker(initialDate: Date, label: String) {

        dateLabelToEdit = label
        isDatePickerVisible = true
    }

    fun hideDatePicker() {
        isDatePickerVisible = false
        dateLabelToEdit = ""
    }

    fun onTapSave() {
        saveCoroutine.launch {
            itemViewModel.save().collect {
                when(it) {
                    is ViewState.Loading -> {
                        isSaving = true
                    }
                    is ViewState.Success -> {
                        isSaving = false
                        isVisible = false
                    }
                    is ViewState.Exception -> {
                        isSaving = false
                    }
                }
            }
        }
    }

    fun onPickerElementSelected(element: PickerElement) {
        itemViewModel.setValueForField(fieldLabel = pickerLabelToEdit, fieldValue = element.id.toString())
        hidePickerComposable()
    }

    fun onDateSelected(date: Date) {
        itemViewModel.setValueForField(fieldLabel = dateLabelToEdit, fieldValue = date)
    }

    fun onDateDelete(labelToDeleteDate: String) {
        itemViewModel.deleteDateFieldValue(label = labelToDeleteDate)
    }

    fun getValueForItemFieldAndLabel(itemField: ItemField, id: Int): String {
        return when(itemField) {
            is LocationPickerField -> itemViewModel.getLocationById(id = id).name
            is GroupPickerField -> itemViewModel.getGroupById(id = id).name
            is SupplierPickerField -> itemViewModel.getSupplierById(id = id).name
            is UserPickerField -> itemViewModel.getUserNameById(id = id)
            is SuperAdminUserPickerField -> itemViewModel.getUserNameById(id = id)
            is StatePickerField -> itemViewModel.getStateById(id = id).name
            is TypePickerField -> itemViewModel.getTypeById(id = id).name
            is ManufacturerPickerField -> itemViewModel.getManufacturerById(id = id).name
            is ModelPickerField -> itemViewModel.getModelById(id = id).name
            is SoftwarePickerField -> itemViewModel.getSoftwareById(id = id).name
            is SoftwareLicensePickerField -> itemViewModel.getSoftwareLicenseById(id = id).name
            is BusinessCriticityPickerField -> itemViewModel.getBusinessCriticityById(id = id).name
            is BudgetPickerField -> itemViewModel.getBudgetById(id = id).name
            is DCRoomPickerField -> itemViewModel.getDCRoomById(id = id).name
            is StaticPickerField -> itemViewModel.getStaticFieldsNameWithLabelAndId(label = itemField.label, id = id)
            else -> return ""
        }
    }

    fun getPickersElementsForItemField(itemField: ItemField): Map<PickerElement, List<PickerElement>> {
        return when(itemField) {
            is LocationPickerField -> locations
            is GroupPickerField -> groups
            is SupplierPickerField -> suppliers
            is UserPickerField -> users
            is SuperAdminUserPickerField -> superAdminUsers
            is StatePickerField -> states
            is TypePickerField -> types
            is ManufacturerPickerField -> manufacturers
            is ModelPickerField -> models
            is SoftwarePickerField -> softwares
            is SoftwareLicensePickerField -> softwaresLicenses
            is BusinessCriticityPickerField -> businessCriticities
            is BudgetPickerField -> budgets
            is DCRoomPickerField -> dcrooms
            is StaticPickerField -> staticsPossiblesValuesByLabel[itemField.label] ?: mapOf()
            else -> return mapOf()
        }
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(100L)
            isViewAppeared = true
            delay(offsetsDuration.toLong() * 1/2)
            isDeviceLogoAppeared = true
            delay(150L)
            isDeviceTypeNameAppeared = true
            delay(150L)
            isDeviceNameAppeared = true
            delay(150L)
            isDeviceInfosTitleAppeared = true
            delay(150L)
            isLoaderAppeared = true
        } else {
            isLoaderAppeared = false
            isDeviceInfosTitleAppeared = false
            isDeviceNameAppeared = false
            isDeviceTypeNameAppeared = false
            isDeviceLogoAppeared = false
            isViewAppeared = false
            delay(offsetsDuration.toLong())
            navController.navigateUp()
        }
    }

    LaunchedEffect(isSavingErrorMessageVisible) {
        if (isSavingErrorMessageVisible) {
            scrollCoroutine.launch {
                delay(100L)
                scrollState.animateScrollTo(scrollState.maxValue)
            }
        }
    }
    
    @Composable
    fun ComposableForItemField(itemField: ItemField) {
        val singleLineModifier = Modifier
            .fillMaxWidth(itemFieldWidthPercentScreen)
            .alpha(loaderAlpha)
        val booleanFieldModifier = Modifier
            .fillMaxWidth(itemFieldWidthPercentScreen)
            .defaultMinSize(minHeight = itemFieldHeightDp)
            .alpha(loaderAlpha)
        val commentModifier = Modifier
            .fillMaxWidth(itemFieldWidthPercentScreen)
            .height(commentFieldHeightDp)
            .alpha(loaderAlpha)
        when(itemField) {
            is TextViewField -> {
                TextField(
                    modifier = singleLineModifier,
                    value = fieldsValues[itemField.label] ?: "",
                    label = { Text(itemField.label) },
                    onValueChange = { itemViewModel.setValueForField(fieldLabel = itemField.label, fieldValue = it) },
                    singleLine = true,
                    colors = textfieldColors
                )
            }
            is CommentTextField -> {
                TextField(
                    modifier = commentModifier,
                    value = fieldsValues[itemField.label] ?: "",
                    label = { Text(itemField.label) },
                    onValueChange = { itemViewModel.setValueForField(fieldLabel = itemField.label, fieldValue = it) },
                    colors = textfieldColors
                )
            }
            is DatePickerField -> {
                PickerTextField(
                    modifier = singleLineModifier,
                    value = fieldsValues[itemField.label] ?: "",
                    label = itemField.label,
                    isDeleteButtonVisible = (fieldsValues[itemField.label]?.trim() ?: "") != "",
                    onClick = {
                        showDatePicker(label = itemField.label, initialDate = Date())
                    },
                    onDelete = {
                        onDateDelete(labelToDeleteDate = itemField.label)
                    }
                )
            }
            is BooleanField -> {
                BooleanCheckField(
                    modifier = booleanFieldModifier,
                    isChecked = itemViewModel.getBooleanForField(fieldValue = fieldsValues[itemField.label] ?: ""),
                    label = itemField.label,
                    onCheckedChanged = { checked ->
                        itemViewModel.setValueForField(fieldLabel = itemField.label, fieldValue = checked)
                    }
                )
            }
            else -> {
                PickerTextField(
                    modifier = singleLineModifier,
                    value = getValueForItemFieldAndLabel(itemField = itemField, id = fieldsValues[itemField.label]?.toIntOrNull() ?: 0),
                    label = itemField.label,
                    onClick = {
                        showPickerComposable(elements = getPickersElementsForItemField(itemField), label = itemField.label)
                    }
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colors.primary),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .height(screenHeight / 3 + 100.dp)
                .fillMaxWidth()
        ) {
            WavesComposable(
                reversed = true,
                wavesXOffset = wavesXOffset,
                wavesYOffset = wavesYOffset,
                color = MaterialTheme.colors.background,
                background = MaterialTheme.colors.primary
            )
            Row(
                modifier = Modifier
                    .height(screenHeight / 3)
                    .fillMaxWidth()
            ) {
                val centerWeight = 0.6f
                val sidesWeights = (1f - 0.6f) / 2f
                Column(
                    modifier = Modifier
                        .weight(sidesWeights)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    Image(
                        painterResource(id = R.drawable.times),
                        "back icon",
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground),
                        modifier = Modifier
                            .alpha(deviceLogoAlpha)
                            .fillMaxWidth(0.35f)
                            .clickable(
                                interactionSource = interactionSource,
                                indication = null
                            ) {
                                isVisible = false
                            },
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier
                        .weight(1f)
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(centerWeight)
                        .fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .matchParentSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            itemViewModel.getDisplayableDeviceTypeNameForItem(),
                            modifier = Modifier
                                .alpha(deviceTypeNameLogoAlpha)
                                .scale(deviceTypeNameLogoScale),
                            color = MaterialTheme.colors.onBackground,
                            fontFamily = AppFonts.openSans,
                            fontWeight = FontWeight.ExtraBold,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Image(
                            painterResource(id = itemViewModel.getIconDrawableIdForItem()),
                            "device icon",
                            contentScale = ContentScale.Fit,
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground),
                            modifier = Modifier
                                .fillMaxSize(0.5f)
                                .alpha(deviceLogoAlpha)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            itemName,
                            modifier = Modifier
                                .alpha(deviceNameLogoAlpha)
                                .scale(deviceNameLogoScale),
                            color = MaterialTheme.colors.onBackground,
                            fontFamily = AppFonts.openSans,
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    }
                }
                Spacer(
                    modifier = Modifier
                        .weight(sidesWeights)
                        .fillMaxHeight()
                )
            }
        }
        TabSelector(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .alpha(deviceInfoTitleAlpha)
                .offset(y = deviceInfoTitleYOffset),
            tabs = itemViewModel.allTabs.toList(),
            selectedTab = selectedTab,
            onTapTab = {
                itemViewModel.setSelectedTab(tab = it)
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        if (fields.isNotEmpty()) {
            fields.forEach {
                ComposableForItemField(itemField = it)
                Spacer(modifier = Modifier.height(16.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onTapSave() },
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary),
                modifier = Modifier
                    .height(40.dp)
                    .alpha(loaderAlpha)
                    .animateContentSize(animationSpec = tween(easing = FastOutSlowInEasing))
            ) {
                if (isSaving) {
                    CircularProgressIndicator(color = MaterialTheme.colors.onSecondary, modifier = Modifier.size(20.dp))
                } else {
                    Text(
                        itemViewModel.saveButtonString,
                        fontFamily = AppFonts.openSans,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colors.onSecondary
                    )
                }
            }

            AnimatedVisibility(visible = isSavingErrorMessageVisible) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(savingErrorMessage ?: "", fontFamily = AppFonts.openSans, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colors.error)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .alpha(loaderAlpha)
                    .offset(y = loaderYOffset),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }

    }

    PickerComposable(
        screenHeight = screenHeight,
        isVisible = isPickerComposableVisible,
        elements = pickerElements,
        onElementSelected = {
            onPickerElementSelected(element = it)
    }, onTapCancel = {
        hidePickerComposable()
    })

    ErrorAlertDialog(
        screenHeight = screenHeight,
        title = "Erreur",
        description = fetchingErrorMessage ?: "",
        onTapConfirm = {
           isVisible = false
        },
        isVisible = isFetchingErrorDialogVisible && isVisible
    )

    val dateLabelValue = fieldsValues[dateLabelToEdit]
    DatePicker(
        screenHeight = screenHeight,
        title = "Sélectionnez une date",
        initialDate = if (dateLabelValue != null) itemViewModel.getDateFromFieldValue(dateLabelValue) else null,
        onTapCancel = {
            hideDatePicker()
        },
        onTapConfirm = {
            onDateSelected(it)
            hideDatePicker()
        },
        isVisible = isDatePickerVisible
    )

}