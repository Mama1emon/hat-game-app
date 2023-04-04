package dev.mama1emon.hat.addteam.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.mama1emon.hat.R
import dev.mama1emon.hat.ds.components.AppBar
import dev.mama1emon.hat.ds.components.CardAction
import dev.mama1emon.hat.ds.components.CardActionItem
import dev.mama1emon.hat.ds.components.LargeButton
import dev.mama1emon.hat.ds.theme.*
import dev.mama1emon.hat.features.addteam.presentation.models.TeamField
import dev.mama1emon.hat.features.addteam.presentation.states.AddTeamStateHolder
import dev.mama1emon.hat.features.addteam.presentation.states.ShowAlertAvailability
import dev.mama1emon.hat.features.addteam.presentation.ui.AddTeamAlert
import java.util.*

@Composable
internal fun AddTeamsScreen(stateHolder: AddTeamStateHolder) {
    when (stateHolder) {
        is AddTeamStateHolder.Empty -> {
            EmptyAddTeamsScreen(
                enterWordsButtonEnabled = stateHolder.enterWordsButtonEnabled,
                onAddTeamButtonClick = stateHolder.onAddTeamButtonClick,
                onEnterWordsButtonClick = stateHolder.onEnterWordsButtonClick,
            )
        }
        is AddTeamStateHolder.NotYet -> {
            NotYetAddTeamsScreen(
                teams = stateHolder.teams,
                onAddTeamButtonClick = stateHolder.onAddTeamButtonClick,
                onRemoveButtonClick = stateHolder.onRemoveButtonClick,
                enterWordsButtonEnabled = stateHolder.enterWordsButtonEnabled,
                onEnterWordsButtonClick = stateHolder.onEnterWordsButtonClick,
            )
        }
        is AddTeamStateHolder.Ready -> {
            ReadyAddTeamsScreen(
                teams = stateHolder.teams,
                onRemoveButtonClick = stateHolder.onRemoveButtonClick,
                onEnterWordsButtonClick = stateHolder.onEnterWordsButtonClick,
            )
        }
    }

    if (stateHolder is ShowAlertAvailability && stateHolder.isAddTeamAlertDrawn) {
        AddTeamAlert(model = stateHolder.addTeamAlertModel)
    }
}

@Composable
private fun EmptyAddTeamsScreen(
    enterWordsButtonEnabled: Boolean,
    onAddTeamButtonClick: () -> Unit,
    onEnterWordsButtonClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.radialGradient(colors = LightInDarkRadialGradient))
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding24))
        ) {
            AppBar(titleId = R.string.prepare_to_game)
            AddTeamButton(onClick = onAddTeamButtonClick)
        }

        Image(
            painter = painterResource(id = R.drawable.ill_empty_shopping_bag),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Center)
                .size(size = dimensionResource(id = R.dimen.size174))
        )

        LargeButton(
            textId = R.string.enter_words,
            enabled = enterWordsButtonEnabled,
            onClick = onEnterWordsButtonClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = dimensionResource(id = R.dimen.padding16))
                .padding(bottom = dimensionResource(id = R.dimen.padding24))
        )
    }
}

@Composable
private fun NotYetAddTeamsScreen(
    enterWordsButtonEnabled: Boolean,
    teams: List<TeamField>,
    onAddTeamButtonClick: () -> Unit,
    onRemoveButtonClick: (UUID) -> Unit,
    onEnterWordsButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LostInSadness)
    ) {
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            AppBar(titleId = R.string.prepare_to_game)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding24)))
            AddTeamButton(onClick = onAddTeamButtonClick)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding12)))
            teams.forEach { team ->
                CardActionItem(
                    text = team.name,
                    cardAction = CardAction.Remove,
                    onClick = { onRemoveButtonClick(team.id) },
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding16))
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding8)))
            }
        }

        LargeButton(
            textId = R.string.enter_words,
            enabled = enterWordsButtonEnabled,
            onClick = onEnterWordsButtonClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = dimensionResource(id = R.dimen.padding16))
                .padding(bottom = dimensionResource(id = R.dimen.padding24))
        )
    }
}

@Composable
private fun ReadyAddTeamsScreen(
    teams: List<TeamField>,
    onRemoveButtonClick: (UUID) -> Unit,
    onEnterWordsButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LostInSadness)
    ) {
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            AppBar(titleId = R.string.prepare_to_game)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding24)))
            TeamsReadyBanner()
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding12)))
            teams.forEach { team ->
                CardActionItem(
                    text = team.name,
                    cardAction = CardAction.Remove,
                    onClick = { onRemoveButtonClick(team.id) },
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding16))
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding8)))
            }
        }

        LargeButton(
            textId = R.string.enter_words,
            enabled = true,
            onClick = onEnterWordsButtonClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = dimensionResource(id = R.dimen.padding16))
                .padding(bottom = dimensionResource(id = R.dimen.padding24))
        )
    }
}

@Composable
private fun AddTeamButton(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding16))
            .fillMaxWidth()
            .background(
                color = SpanishRoast,
                shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.radius16))
            )
            .clip(RoundedCornerShape(size = dimensionResource(id = R.dimen.radius16)))
            .clickable(onClick = onClick)
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding24),
                vertical = dimensionResource(id = R.dimen.padding16)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding6))
        ) {
            Text(
                text = stringResource(R.string.add_team),
                color = White,
                maxLines = 1,
                style = HatTypography.Regular16
            )

            Text(
                text = stringResource(R.string.is_not_greather_then_four),
                color = White,
                maxLines = 1,
                style = HatTypography.Regular12
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_27_outlined_plus),
            contentDescription = null,
            tint = CitrusZest
        )
    }
}

@Composable
private fun TeamsReadyBanner() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(dimensionResource(id = R.dimen.size68))
            .background(SpanishRoast),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(dimensionResource(id = R.dimen.size6))
                .heightIn(dimensionResource(id = R.dimen.size68))
                .background(CitrusZest)
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding36)))
        Icon(
            painter = painterResource(id = R.drawable.ic_28_ready),
            contentDescription = null,
            tint = CitrusZest
        )
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding16)))
        Column(verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding2))) {
            Text(
                text = stringResource(R.string.teams_ready),
                color = White,
                maxLines = 1,
                style = HatTypography.Regular12
            )
            Text(
                text = stringResource(R.string.enter_words_left),
                color = White,
                maxLines = 1,
                style = HatTypography.Regular12
            )
        }
    }
}