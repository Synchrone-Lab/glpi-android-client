package fr.synchrone.glpisupport.presentation.home

import android.Manifest
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.core.ViewState
import fr.synchrone.glpisupport.presentation.utilities.TextViewAlertDialog
import kotlinx.coroutines.launch
import fr.synchrone.glpisupport.data.model.local.devices.DeviceHistoryDb
import fr.synchrone.glpisupport.presentation.scan.ScanComposable
import fr.synchrone.glpisupport.presentation.theme.AppFonts
import fr.synchrone.glpisupport.presentation.utilities.functions.ItemsUtilities
import fr.synchrone.glpisupport.presentation.utilities.picker.GenericBottomPopup
import fr.synchrone.glpisupport.presentation.utilities.picker.GenericBottomPopupSize
import fr.synchrone.glpisupport.presentation.utilities.picker.PickerElement
import fr.synchrone.glpisupport.presentation.utilities.picker.PickerRow
import kotlinx.coroutines.delay

/**
 * Home screen. Change entity and profile, find and create items.
 *
 * @see HomeViewModel
 */

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalAnimationApi
@androidx.camera.core.ExperimentalGetImage
@Composable
fun HomeComposable(
    screenHeight: Dp,
    navController: NavController,
    homeViewModel: HomeViewModel
) {

    val pickerRowHeight = 50.dp
    val buttonsContainerHeight = 100.dp
    val buttonsPercentHeightInsideContainer = 0.6f

    //State

    var isLoggedIn by remember { mutableStateOf(true) }
    var setContentAppeared by remember { mutableStateOf(true) }
    var isContentAppeared by remember { mutableStateOf(false) }
    var isSearchPopupAppeared by remember { mutableStateOf(false) }
    var searchStringValue by remember { mutableStateOf("") }
    var isScanVisible by remember { mutableStateOf(false) }
    var isChangeProfileDialogVisible by remember { mutableStateOf(false) }

    //Interaction

    val interactionSource = remember { MutableInteractionSource() }
    val coroutineScope = rememberCoroutineScope()

    //Permissions

    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )

    //View model binding

    val noDeviceFound by homeViewModel.noDeviceFound.observeAsState(initial = false)
    val multiplesDevicesFound by homeViewModel.multiplesDevicesFound.observeAsState(initial = null)
    val isMultiplesDevicesDialogVisible by homeViewModel.isMultiplesDevicesFound.observeAsState(initial = false)
    val searchResult by homeViewModel.searchResult.observeAsState(initial = null)
    val selectedTypeName by homeViewModel.selectedDeviceTypeName.observeAsState(initial = "")
    val selectablesTemplatesElements by homeViewModel.selectablesTemplates.observeAsState(initial = mapOf())
    val selectedTemplateName by homeViewModel.selectedTemplateName.observeAsState(initial = "")
    val devicesHistories by homeViewModel.devicesHistories.observeAsState(initial = listOf())
    val isAddItemPopupAppeared by homeViewModel.showAddItemDialog.observeAsState(initial = false)
    val scanSerial by homeViewModel.scanSerial.observeAsState(initial = "")
    val areCurrentUserProfileAndEntityLoading by homeViewModel.areCurrentUserProfileAndEntityLoading.observeAsState(initial = false)
    val isMyProfilesLoading by homeViewModel.isMyProfilesLoading.observeAsState(initial = false)
    val isMyEntityLoading by homeViewModel.isMyProfilesLoading.observeAsState(initial = false)
    val selectablesProfiles by homeViewModel.selectablesUserProfiles.observeAsState(initial = mapOf())
    val selectablesEntities by homeViewModel.selectablesUserEntities.observeAsState(initial = mapOf())
    val selectedProfileName by homeViewModel.selectedUserProfileName.observeAsState(initial = null)
    val selectedEntityName by homeViewModel.selectedUserEntityName.observeAsState(initial = null)
    val currentProfileName by homeViewModel.currentUserProfileName.observeAsState(initial = "")
    val currentEntityName by homeViewModel.currentUserEntityName.observeAsState(initial = "")
    val errorOccurred by homeViewModel.errorOccurred.observeAsState(initial = null)

    //Animations

    val contentAlpha by animateFloatAsState(targetValue = if (isContentAppeared) 1f else 0f)

    val context = LocalContext.current

    fun showSearchDialog() {
        searchStringValue = ""
        isSearchPopupAppeared = true
    }

    fun hideSearchDialog() {
        searchStringValue = ""
        isSearchPopupAppeared = false
    }

    fun showScanDialog(){

        if (cameraPermissionState.hasPermission) {
            isScanVisible = true
        } else {
            if (cameraPermissionState.shouldShowRationale) {
                homeViewModel.notifyCameraPermissionDenied()
            } else {
                cameraPermissionState.launchPermissionRequest()
            }
        }
    }

    fun showChangeProfileDialog() {
        isChangeProfileDialogVisible = true
    }

    fun hideChangeProfileDialog() {
        isChangeProfileDialogVisible = false
    }

    fun navigateToItemDetails(itemId: Int, itemType: String, isCreation: Boolean, overrideSerial: String? = null) {
        navController.navigate("item/${itemType}/${itemId}?isItemCreation=${isCreation}${if(overrideSerial != null) "&overrideSerial=${overrideSerial}" else ""}")
    }

    fun searchWithSerial() {
        homeViewModel.searchItemBySerialNumber(searchStringValue, false)
    }

    fun onTapAddDevice() {
        homeViewModel.notifyTapOnAddDevice()
    }

    fun onTapHistoryItem(item: DeviceHistoryDb) {
        coroutineScope.launch {
            setContentAppeared = false
            delay(150L)
            navigateToItemDetails(itemId = item.id, itemType = item.itemType, isCreation = false)
        }
    }

    fun onConfirmAddItem() {
        val addItemPopupResult = homeViewModel.getAddItemResult()

        navigateToItemDetails(
            itemType = addItemPopupResult.itemType,
            itemId = addItemPopupResult.itemId,
            isCreation = true,
            overrideSerial = scanSerial)
        homeViewModel.cancelSearch()
    }

    fun logout() {
        coroutineScope.launch {
            homeViewModel.logout().collect {
                when(it) {
                    is ViewState.Success -> isLoggedIn = false
                }
            }
        }
    }

    LaunchedEffect(setContentAppeared) {
        isContentAppeared = setContentAppeared
    }

    LaunchedEffect(searchResult) {
        val searchResultRef = searchResult?.copy()
        if (searchResultRef != null) {
            navigateToItemDetails(itemId = searchResultRef.itemId, itemType = searchResultRef.itemType, isCreation = false)
            homeViewModel.cancelSearch()
        }
    }

    LaunchedEffect(isLoggedIn) {
        if (!isLoggedIn) {
            setContentAppeared = false
            delay(150L)
            navController.navigateUp()
        }
    }

    LaunchedEffect(errorOccurred) {
        if (errorOccurred != null) {
            Toast.makeText(navController.context, errorOccurred, Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val topBarHeight = 100.dp
        val buttonsWeight = 0.25f

        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .alpha(contentAlpha),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val centerWeight = 0.5f
            val sidesWeight = (1f - centerWeight) / 2f

            Spacer(
                modifier = Modifier
                    .weight(sidesWeight)
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .weight(centerWeight)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painterResource(id = R.drawable.logo_glpi_white),
                    "logo glpi",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onBackground),
                    modifier = Modifier
                        .fillMaxSize(0.5f),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(
                modifier = Modifier
                    .weight(sidesWeight)
                    .fillMaxHeight()
            )
        }
        ProfileButton(
            profile = currentProfileName ?: "",
            entity = currentEntityName ?: "",
            enabled = true,
            onClick = { showChangeProfileDialog() },
            modifier = Modifier
                .alpha(contentAlpha)
                .fillMaxWidth(0.8f)
                .height(50.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .weight(1f)
                .alpha(contentAlpha),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (devicesHistories.isEmpty()) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(0.8f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        context.getString(R.string.home_no_history),
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        fontFamily = AppFonts.openSans,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        fontSize = 26.sp,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Text(
                    context.getString(R.string.history),
                    modifier = Modifier.fillMaxWidth(0.8f),
                    fontFamily = AppFonts.openSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colors.onBackground
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(0.dp, 16.dp)
                ) {
                    devicesHistories.forEach {
                        item {
                            PickerRow(
                                element = PickerElement(
                                    id = it.id,
                                    name = it.name,
                                    iconResourceId = ItemsUtilities.getIconDrawableIdForItem(itemType = it.itemType)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(pickerRowHeight)
                                    .clickable(
                                        interactionSource = interactionSource,
                                        indication = null
                                    ) {
                                        onTapHistoryItem(item = it)
                                    },
                                background = MaterialTheme.colors.primary,
                                contentColor = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(buttonsContainerHeight))
    }

    GenericBottomPopup(
        screenHeight = screenHeight,
        isVisible = isContentAppeared,
        size = GenericBottomPopupSize(height = buttonsContainerHeight),
        hideDarkBackground = true,
        background = MaterialTheme.colors.secondary,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                HomeButton(
                    context.getString(R.string.scan),
                    drawable = painterResource(id = R.drawable.qrcode),
                    onClick = {
                        showScanDialog()
                    },
                    enabled = !areCurrentUserProfileAndEntityLoading,
                    modifier = Modifier
                        .fillMaxHeight(buttonsPercentHeightInsideContainer)
                        .aspectRatio(1f)
                        .alpha(contentAlpha)
                )
                HomeButton(
                    context.getString(R.string.search),
                    drawable = painterResource(id = R.drawable.search),
                    onClick = {showSearchDialog()},
                    enabled = !areCurrentUserProfileAndEntityLoading,
                    modifier = Modifier
                        .fillMaxHeight(buttonsPercentHeightInsideContainer)
                        .aspectRatio(1f)
                        .alpha(contentAlpha)
                )
                HomeButton(
                    context.getString(R.string.home_add_device),
                    drawable = painterResource(id = R.drawable.plus),
                    onClick = {onTapAddDevice()},
                    enabled = true,
                    modifier = Modifier
                        .fillMaxHeight(buttonsPercentHeightInsideContainer)
                        .aspectRatio(1f)
                        .alpha(contentAlpha)
                )
                HomeButton(
                    context.getString(R.string.logout),
                    drawable = painterResource(id = R.drawable.sign_out),
                    onClick = {logout()},
                    enabled = true,
                    modifier = Modifier
                        .fillMaxHeight(buttonsPercentHeightInsideContainer)
                        .aspectRatio(1f)
                        .alpha(contentAlpha)
                )
            }
        }
    )

    TextViewAlertDialog(
        screenHeight = screenHeight,
        title = context.getString(R.string.home_search_device),
        description = context.getString(R.string.home_prompt_search_device),
        isVisible = isSearchPopupAppeared && !isMultiplesDevicesDialogVisible,
        value = searchStringValue,
        label = { Text(context.getString(R.string.serial_number)) },
        onValueChange = { searchStringValue = it },
        onTapCancel = {
            hideSearchDialog()
            homeViewModel.cancelSearch()
          },
        onTapConfirm = {
            searchWithSerial()
        },
        errorMessage = if (noDeviceFound) context.getString(R.string.home_no_device_found) else null
    )

    AddItemDialog(
        screenHeight = screenHeight,
        title = context.getString(R.string.home_add_device),
        description = context.getString(R.string.home_select_device_type_and_model),
        serial = scanSerial,
        isVisible = isAddItemPopupAppeared,
        typesPickerElements = homeViewModel.selectablesTypesElements,
        typeNameValue = selectedTypeName,
        onTypeIdChange = {
            homeViewModel.setSelectedTypeId(id = it)
        },
        onTemplateIdChange = {
             homeViewModel.setSelectedTemplateId(id = it)
        },
        templatesPickerElements = selectablesTemplatesElements,
        templateNameValue = selectedTemplateName,
        onTapCancel = { homeViewModel.cancelSearch() },
        onTapConfirm = {
            onConfirmAddItem()
        }
    )

    MultipleItemsFoundDialog(
        screenHeight = screenHeight,
        pickerRowHeight = pickerRowHeight,
        elementsFound = multiplesDevicesFound ?: listOf(),
        onTapConfirm = {
            homeViewModel.setSelectedDeviceFromMultipleDevicesFound(selectedDevicePickerElement = it)
        },
        onTapCancel = {
            homeViewModel.cancelSearch()
        },
        isVisible = isMultiplesDevicesDialogVisible
    )

    ScanComposable(
        screenHeight = screenHeight,
        isVisible = isScanVisible,
        onCodeDetected = { code ->
            isScanVisible = false
            homeViewModel.setScan(code)
        },
        onCancel = {
            isScanVisible = false
            homeViewModel.cancelSearch()
        }
    )

    ChangeProfileDialog(
        screenHeight = screenHeight,
        title = context.getString(R.string.home_change_profile_title),
        description = context.getString(R.string.home_select_entity_and_profile),
        areProfilesLoading = isMyProfilesLoading,
        areEntitiesLoading = isMyEntityLoading,
        isVisible = isChangeProfileDialogVisible,
        profilesPickerElements = selectablesProfiles,
        profilesNameValue = selectedProfileName,
        onProfileIdChange = {
            homeViewModel.setUserProfileIdSelected(id = it)
        },
        onEntityIdChange = {
            homeViewModel.setUserEntityIdSelected(id = it)
        },
        entitiesPickerElements = selectablesEntities,
        entitiesNameValue = selectedEntityName,
        onTapConfirm = {
            hideChangeProfileDialog()
        }
    )
}