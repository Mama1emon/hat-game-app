package dev.mama1emon.hat.enterwords.presentation.ui

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
import androidx.compose.runtime.Composable
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

private const val MIN_PROGRESS = 0.009f

/**
 * @author Andrew Khokhlov on 22/03/2023
 */
@Composable
fun EnterWordsScreen(
    onBackButtonClick: () -> Unit,
    onReadyClick: () -> Unit
) {
    Scaffold(
        topBar = { AppBar(titleId = R.string.prepare_to_game, onClick = onBackButtonClick) },
        floatingActionButton = { ReadyFloatButton(onClick = onReadyClick) },
        floatingActionButtonPosition = FabPosition.End
    ) {
//        ReadyEnterWordsScreen(
//            name = name,
//            words = words,
//            onRemoveWordClick = onRemoveWordClick,
//            modifier = Modifier
//                .padding(it)
//                .fillMaxSize()
//                .background(LostInSadness)
//        )
    }
}

@Composable
private fun EmptyEnterWordsScreen(
    name: String,
    enterWordFieldModel: EnterWordFieldModel,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        ProgressIndicator(progress = MIN_PROGRESS, name = name)
        InputField(model = enterWordFieldModel)
    }
}

@Composable
private fun NotYetEnterWordsScreen(
    progress: Float,
    name: String,
    enterWordFieldModel: EnterWordFieldModel,
    words: List<Word>,
    onRemoveWordClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier) {
        item { ProgressIndicator(progress = progress, name = name) }
        item { InputField(model = enterWordFieldModel) }
        itemsIndexed(words) { index, word ->
            AddedWordItem(word, index, words, onRemoveWordClick)
        }
    }
}

@Composable
private fun ReadyEnterWordsScreen(
    name: String,
    words: List<Word>,
    onRemoveWordClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        item { ProgressIndicator(progress = 1f, name = name) }
        item { PlayerReadyBanner() }
        itemsIndexed(words) { index, word ->
            AddedWordItem(word, index, words, onRemoveWordClick)
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
        Box(modifier = Modifier.size(dimensionResource(id = R.dimen.size180))) {
            CircularProgressIndicator(
                progress = progress,
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

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimensionResource(id = R.dimen.size1))
                        .background(
                            if (model.hasError) SolidColor(Color.Red) else SolidColor(
                                CitrusZest
                            )
                        )
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_36_plane),
                contentDescription = null,
                modifier = Modifier
                    .clickable(onClick = model.onDoneClick, enabled = !model.hasError)
                    .clip(shape = RoundedCornerShape(dimensionResource(id = R.dimen.radius8))),
                tint = if (model.hasError) BlackEel else CitrusZest
            )
        }
    }
}

@Composable
private fun PlayerReadyBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(dimensionResource(id = R.dimen.size54))
            .background(SpanishRoast),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.size6))
                .height(dimensionResource(id = R.dimen.size54))
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