package dev.mama1emon.hat.features.game.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.github.theapache64.twyper.CardController
import com.github.theapache64.twyper.SwipedOutDirection
import com.github.theapache64.twyper.rememberCardController
import dev.mama1emon.hat.R
import dev.mama1emon.hat.ds.theme.*
import dev.mama1emon.hat.features.game.presentation.state.GameStateHolder

/**
 * @author Andrew Khokhlov on 03/04/2023
 */
@Composable
fun GameScreen(stateHolder: GameStateHolder) {
    when (stateHolder) {
        is GameStateHolder.Content -> GameContent(state = stateHolder)
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun GameContent(state: GameStateHolder.Content) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LostInSadness)
            .padding(
                top = dimensionResource(id = R.dimen.padding56),
                bottom = dimensionResource(id = R.dimen.padding70)
            )
    ) {
        val cardController = rememberCardController()
        Card(
            word = state.word,
            cardController = cardController,
            onGuessSuccess = state.onGuessSuccess,
            onGuessFailure = state.onGuessFailure,
            modifier = Modifier.align(Alignment.Center)
        )

        Text(
            text = state.currentTime,
            modifier = Modifier.align(Alignment.TopCenter),
            maxLines = 1,
            style = HatTypography.Regular42.copy(
                brush = Brush.linearGradient(GoldenLinearGradient)
            )
        )

        Buttons(
            cardController = cardController,
            onLeftButtonClick = state.onGuessFailure,
            onRightButtonClick = state.onGuessSuccess,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun Card(
    word: String,
    cardController: CardController,
    onGuessSuccess: () -> Unit,
    onGuessFailure: () -> Unit,
    modifier: Modifier = Modifier
) {
    val size = LocalConfiguration.current.screenWidthDp.dp
        .minus(dimensionResource(id = R.dimen.padding30))
        .minus(dimensionResource(id = R.dimen.padding30))

    Box(modifier = modifier.size(size)) {
        if (!cardController.isCardOut()) {
            Card(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectDragGestures(
                            onDragEnd = cardController::onDragEnd,
                            onDragCancel = cardController::onDragCancel,
                            onDrag = { change, dragAmount ->
                                if (change.positionChange() != Offset.Zero) change.consume()
                                cardController.onDrag(dragAmount)
                            }
                        )
                    }
                    .graphicsLayer(
                        translationX = cardController.cardX,
                        translationY = cardController.cardY,
                        rotationZ = cardController.rotation,
                    ),
                shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.radius16)),
                backgroundColor = BlueZodiac,
                elevation = dimensionResource(id = R.dimen.elevation6)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = word,
                        color = White,
                        textAlign = TextAlign.Center,
                        style = HatTypography.Regular24
                    )
                }
            }
        } else {
            cardController.swipedOutDirection?.let { outDirection ->
                when (outDirection) {
                    SwipedOutDirection.LEFT -> onGuessFailure
                    SwipedOutDirection.RIGHT -> onGuessSuccess
                }
            }
        }
    }
}

@Composable
private fun Buttons(
    cardController: CardController,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding56)),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(size = dimensionResource(id = R.dimen.size56))
                .background(
                    brush = Brush.linearGradient(CrimsonLinearGradient),
                    shape = CircleShape
                )
                .clip(shape = CircleShape)
                .clickable {
                    onLeftButtonClick()
                    cardController.swipeLeft()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_26_cross),
                contentDescription = null,
                tint = White
            )
        }

        Box(
            modifier = Modifier
                .size(size = dimensionResource(id = R.dimen.size56))
                .background(
                    brush = Brush.linearGradient(GoldenLinearGradient),
                    shape = CircleShape
                )
                .clip(shape = CircleShape)
                .clickable {
                    onRightButtonClick()
                    cardController.swipeRight()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_25_check_mark),
                contentDescription = null,
                tint = White
            )
        }
    }
}