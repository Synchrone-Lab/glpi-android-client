package fr.synchrone.glpisupport.presentation.scan

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.compose.BackHandler
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import fr.synchrone.glpisupport.R
import fr.synchrone.glpisupport.presentation.home.HomeComposable
import fr.synchrone.glpisupport.presentation.utilities.PowEasing
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Scan screen showed by HomeComposable.
 *
 * @see HomeComposable
 */
@SuppressLint("UnsafeExperimentalUsageError", "InflateParams")
@androidx.camera.core.ExperimentalGetImage
@Composable
fun ScanComposable(screenHeight : Dp,
                   isVisible : Boolean,
                   onCodeDetected : (String) -> Unit,
                   onCancel : () -> Unit
){

    var cameraExecutor: ExecutorService? = remember {
        null
    }

    //State

    var isCodeDetected by remember { mutableStateOf(false) }

    //Interaction

    val interactionSource = remember { MutableInteractionSource()}

    //Animations

    val contentYOffset by animateDpAsState(
        targetValue = if(isVisible) 0.dp else screenHeight,
        tween(durationMillis = 200, easing = PowEasing(1/3f)))

    LaunchedEffect(isVisible){
        if(isVisible){
            isCodeDetected = false
        } else {
            cameraExecutor?.shutdownNow()
        }
    }

    BackHandler {
        onCancel()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .offset(y = contentYOffset),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (isVisible) {
            Box {
                val lifeCycleOwner = LocalLifecycleOwner.current
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        val view = LayoutInflater.from(context)
                            .inflate(R.layout.camera_layout, null) as ConstraintLayout

                        val viewFinder = view.findViewById<PreviewView>(R.id.viewFinder)

                        cameraExecutor?.shutdownNow()
                        cameraExecutor = Executors.newSingleThreadExecutor()

                        val cameraProviderFuture =
                            ProcessCameraProvider.getInstance(context)

                        val options = BarcodeScannerOptions.Builder()
                            .setBarcodeFormats(
                                Barcode.FORMAT_QR_CODE,
                                Barcode.FORMAT_AZTEC,
                                Barcode.FORMAT_CODE_128,
                                Barcode.FORMAT_CODE_39,
                                Barcode.FORMAT_CODE_93,
                                Barcode.FORMAT_CODABAR,
                                Barcode.FORMAT_EAN_13,
                                Barcode.FORMAT_EAN_8,
                                Barcode.FORMAT_ITF,
                                Barcode.FORMAT_UPC_A,
                                Barcode.FORMAT_UPC_E,
                                Barcode.FORMAT_PDF417,
                                Barcode.FORMAT_DATA_MATRIX
                            )
                            .build()

                        val imageAnalyzer = ImageAnalysis.Builder()
                            .build()
                            .also { it ->
                                it.setAnalyzer(cameraExecutor!!) { imageProxy ->
                                    val mediaImage = imageProxy.image
                                    if (mediaImage != null) {
                                        val image = InputImage.fromMediaImage(
                                            mediaImage,
                                            imageProxy.imageInfo.rotationDegrees
                                        )
                                        val scanner = BarcodeScanning.getClient(options)

                                        val result = scanner.process(image)
                                            .addOnSuccessListener { barcodes ->
                                                for (barcode in barcodes) {
                                                    val scan = barcode.rawValue
                                                    if (!scan.isNullOrEmpty() && !isCodeDetected) {
                                                        isCodeDetected = true
                                                        onCodeDetected(scan)
                                                    }
                                                }
                                            }
                                            .addOnFailureListener {
                                                Log.wtf("exception", "$it")
                                            }
                                            .addOnCompleteListener {
                                                imageProxy.close()
                                            }
                                    }
                                }
                            }

                        cameraProviderFuture.addListener({
                            // Used to bind the lifecycle of cameras to the lifecycle owner
                            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                            // Preview
                            val preview = Preview.Builder()
                                .build()
                                .also { preview ->
                                    preview.setSurfaceProvider(viewFinder.surfaceProvider)
                                }

                            // Select back camera as a default
                            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                            try {
                                // Unbind use cases before rebinding
                                cameraProvider.unbindAll()

                                // Bind use cases to camera
                                cameraProvider.bindToLifecycle(
                                    lifeCycleOwner, cameraSelector, preview, imageAnalyzer
                                )

                            } catch (exc: Exception) {
                                Log.e("ScanComposable", "Use case binding failed", exc)
                            }

                        }, ContextCompat.getMainExecutor(context))

                        view
                    }
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .offset(y = contentYOffset)
    ) {
        val size = 50.dp
        Image(
            modifier = Modifier
                .height(size)
                .width(size)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onCancel()
                },
            colorFilter = ColorFilter.tint(Color.White),
            painter = painterResource(id = R.drawable.times),
            contentDescription = "cancel button"
        )
    }
}