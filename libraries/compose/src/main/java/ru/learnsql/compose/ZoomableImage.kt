package ru.learnsql.compose

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateTo
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import ru.learnsql.app_api.theme.LearnSqlTheme
import timber.log.Timber

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ZoomableImage(url: String) {
    val scale = remember { mutableStateOf(1f) }
    val offset = remember { mutableStateOf(Offset.Zero) }
    val transformableState = rememberTransformableState { zoomChange, panChange, _ ->
        scale.value *= zoomChange
        offset.value += panChange * 1.5f
    }
    var layout: LayoutCoordinates? = null
    Box(
        modifier = Modifier
            .clip(RectangleShape) // Clip the box content
            .fillMaxSize()
            .transformable(state = transformableState)
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale.value *= zoom
                    if (layout == null) return@detectTransformGestures
                    val maxX = layout!!.size.width * (scale.value - 1) / 2f
                    val maxY = layout!!.size.height * (scale.value - 1) / 2f
                    val targetTranslation = pan * 1.5f + offset.value
                    if (targetTranslation.x > -maxX && targetTranslation.x < maxX &&
                        targetTranslation.y > -maxY && targetTranslation.y < maxY
                    ) {
                        offset.value = targetTranslation
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {

        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .build(),
            modifier = Modifier
                .onPlaced { layout = it }
                .graphicsLayer(
                    // adding some zoom limits (min 50%, max 200%)
                    scaleX = maxOf(1f, minOf(3f, scale.value)),
                    scaleY = maxOf(1f, minOf(3f, scale.value)),
                    translationX = if (scale.value > 1f) offset.value.x else 0f,
                    translationY = if (scale.value > 1f) offset.value.y else 0f
                )
                .fillMaxWidth(),
            contentDescription = null,
            loading = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = LearnSqlTheme.colors.secondary)
                }
            }, onLoading = {
                Timber.e("loading image")
            }, onError = {
                Timber.e(it.toString())
            }, onSuccess = {
                Timber.e("success")
            }
        )
        LaunchedEffect(transformableState.isTransformInProgress) {
            if (!transformableState.isTransformInProgress) {
                if (scale.value < 1f) {
                    val originScale = scale.value
                    val originTranslation = offset.value
                    AnimationState(initialValue = 0f).animateTo(
                        1f,
                        SpringSpec(stiffness = Spring.StiffnessLow)
                    ) {
                        scale.value = originScale + (1 - originScale) * this.value
                        offset.value = originTranslation * (1 - this.value)
                    }
                } else {
                    if (layout == null) return@LaunchedEffect
                    val maxX = layout!!.size.width * (scale.value - 1) / 2f
                    val maxY = layout!!.size.height * (scale.value - 1) / 2f
                    val target = Offset(
                        offset.value.x.coerceIn(-maxX, maxX),
                        offset.value.y.coerceIn(-maxY, maxY)
                    )
                    AnimationState(
                        typeConverter = Offset.VectorConverter,
                        initialValue = offset.value
                    ).animateTo(target, SpringSpec(stiffness = Spring.StiffnessLow)) {
                        offset.value = this.value
                    }
                }
            }
        }
    }
}