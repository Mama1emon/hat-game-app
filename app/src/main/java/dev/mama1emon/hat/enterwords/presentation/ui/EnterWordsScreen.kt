package dev.mama1emon.hat.enterwords.presentation.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import dev.mama1emon.hat.R
import dev.mama1emon.hat.ds.components.AppBar
import dev.mama1emon.hat.ds.components.CardAction
import dev.mama1emon.hat.ds.components.CardActionItem
import dev.mama1emon.hat.ds.theme.*
import dev.mama1emon.hat.enterwords.presentation.models.EnterWordFieldModel
import dev.mama1emon.hat.enterwords.presentation.models.Word
import dev.mama1emon.hat.enterwords.presentation.states.EnterWordAvailability
import dev.mama1emon.hat.enterwords.presentation.states.EnterWordsStateHolder
import dev.mama1emon.hat.enterwords.presentation.states.RemoveWordsAvailability

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun EnterWordsScreen(stateHolder: EnterWordsStateHolder) {
    Scaffold(
        topBar = { AppBar(titleId = R.string.prepare_to_game) },
        floatingActionButton = {
            if (stateHolder is EnterWordsStateHolder.Ready) {
                ReadyFloatButton(onClick = stateHolder.onReadyButtonClick)
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(LostInSadness)
        ) {
            stickyHeader { Header(stateHolder = stateHolder) }

            if (stateHolder is RemoveWordsAvailability) {
                itemsIndexed(stateHolder.wordlist) { index, word ->
                    AddedWordItem(
                        word = word,
                        index = index,
                        words = stateHolder.wordlist,
                        onRemoveWordClick = stateHolder.onRemoveWordButtonClick
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding56)))
            }
        }
    }
}

@Composable
private fun ReadyFloatButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(size = dimensionResource(id = R.dimen.size56))
            .background(
                brush = Brush.linearGradient(GoldenLinearGradient),
                shape = CircleShape
            )
            .clip(shape = CircleShape)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_25_check_mark),
            contentDescription = null,
            tint = White
        )
    }
}

@Composable
private fun Header(stateHolder: EnterWordsStateHolder) {
    Column {
        ProgressIndicator(
            progress = stateHolder.progress,
            name = stateHolder.playerName
        )

        if (stateHolder is EnterWordAvailability) {
            InputField(model = stateHolder.fieldModel)
        } else {
            PlayerReadyBanner()
        }
    }
}

@Composable
private fun ProgressIndicator(progress: Float, name: String) {
    Column(
        modifier = Modifier
            .padding(
                top = dimensionResource(id = R.dimen.padding48),
                bottom = dimensionResource(id = R.dimen.padding36)
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding24))
    ) {
        val animatePercentage = animateFloatAsState(
            targetValue = progress,
            animationSpec = tween(durationMillis = 1000, delayMillis = 0)
        )

        Crossfade(targetState = animatePercentage) {
            Box(modifier = Modifier.size(dimensionResource(id = R.dimen.size180))) {
                CircularProgressIndicator(
                    progress = it.value,
                    modifier = Modifier.fillMaxSize(),
                    strokeWidth = dimensionResource(id = R.dimen.size6),
                    color = CitrusZest,
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    modifier = Modifier.align(Alignment.Center),
                    color = White,
                    maxLines = 1,
                    style = HatTypography.Regular42
                )
            }
        }

        Text(
            text = name,
            color = White,
            maxLines = 1,
            style = HatTypography.Regular18
        )
    }
}

@Composable
private fun InputField(model: EnterWordFieldModel) {
    BasicTextField(
        value = model.value,
        onValueChange = model.onValueChanged,
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding16))
            .fillMaxWidth()
            .height(height = dimensionResource(id = R.dimen.padding50))
            .border(
                width = dimensionResource(id = R.dimen.size1),
                color = if (model.hasError) Color.Red else CitrusZest,
                shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.radius16))
            ),
        textStyle = HatTypography.Regular18.copy(color = White),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { if (!model.hasError) model.onDoneClick() }
        ),
        singleLine = true,
        maxLines = 1,
        cursorBrush = if (model.hasError) SolidColor(Color.Red) else SolidColor(CitrusZest)
    ) { innerTextField ->
        Row(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding16),
                vertical = dimensionResource(id = R.dimen.padding6)
            ),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding16)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding2))
            ) {
                Box {
                    if (model.value.isBlank()) {
                        Text(
                            text = stringResource(R.string.word),
                            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding2)),
                            color = BattleshipGrey,
                            maxLines = 1,
                            style = HatTypography.Regular18
                        )
                    }
                    innerTextField()
                }
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_36_plane),
                contentDescription = null,
                modifier = Modifier
                    .clip(shape = CircleShape)
                    .clickable(
                        onClick = model.onDoneClick,
                        enabled = model.addWordButtonAvailability
                    ),
                tint = if (model.addWordButtonAvailability) CitrusZest else BlackEel
            )
        }
    }
}

@Composable
private fun PlayerReadyBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = dimensionResource(id = R.dimen.padding50))
            .background(SpanishRoast),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.size6))
                .height(height = dimensionResource(id = R.dimen.padding50))
                .background(CitrusZest)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding36)))
        Icon(
            painter = painterResource(id = R.drawable.ic_28_ready),
            contentDescription = null,
            tint = CitrusZest
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding16)))
        Text(
            text = stringResource(R.string.words_successfully_added),
            color = White,
            maxLines = 1,
            style = HatTypography.Regular14
        )
    }
}

@Composable
fun AddedWordItem(word: Word, index: Int, words: List<Word>, onRemoveWordClick: (Int) -> Unit) {
    Spacer(
        modifier = Modifier.height(
            height = dimensionResource(
                id = if (index == 0) R.dimen.padding12 else R.dimen.padding10
            )
        )
    )
    CardActionItem(
        text = word.value,
        cardAction = CardAction.Remove,
        onClick = { onRemoveWordClick(index) },
        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding16))
    )
    if (index == words.lastIndex) {
        Spacer(
            modifier = Modifier.height(height = dimensionResource(id = R.dimen.padding24))
        )
    }
}