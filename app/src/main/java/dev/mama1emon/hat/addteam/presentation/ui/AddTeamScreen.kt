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
import dev.mama1emon.hat.addteam.domain.models.Team
import dev.mama1emon.hat.ds.components.AppBar
import dev.mama1emon.hat.ds.components.CardAction
import dev.mama1emon.hat.ds.components.CardActionItem
import dev.mama1emon.hat.ds.components.LargeButton
import dev.mama1emon.hat.ds.theme.*

@Composable
fun AddTeamScreen() {
}

@Composable
private fun EmptyAddTeamScreen(
    onBackButtonClick: () -> Unit,
    onAddTeamButtonClick: () -> Unit,
    onEnterWordsButtonClick: () -> Unit
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
            AppBar(titleId = R.string.prepare_to_game, onClick = onBackButtonClick)
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
            enabled = false,
            onClick = onEnterWordsButtonClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = dimensionResource(id = R.dimen.padding16))
                .padding(bottom = dimensionResource(id = R.dimen.padding24))
        )
    }
}

@Composable
private fun NotYetAddTeamScreen(
    firstTeam: Team,
    onBackButtonClick: () -> Unit,
    onAddTeamButtonClick: () -> Unit,
    onRemoveButtonClick: (Int) -> Unit,
    onEnterWordsButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LostInSadness)
    ) {
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            AppBar(titleId = R.string.prepare_to_game, onClick = onBackButtonClick)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding24)))
            AddTeamButton(onClick = onAddTeamButtonClick)
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding12)))
            CardActionItem(
                text = firstTeam.name,
                cardAction = CardAction.Remove,
                onClick = { onRemoveButtonClick(firstTeam.id) },
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding16))
            )
        }

        LargeButton(
            textId = R.string.enter_words,
            enabled = false,
            onClick = onEnterWordsButtonClick,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = dimensionResource(id = R.dimen.padding16))
                .padding(bottom = dimensionResource(id = R.dimen.padding24))
        )
    }
}

@Composable
private fun ReadyAddTeamScreen(
    teams: List<Team>,
    onBackButtonClick: () -> Unit,
    onAddTeamButtonClick: () -> Unit,
    onRemoveButtonClick: (Int) -> Unit,
    onEnterWordsButtonClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LostInSadness)
    ) {
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            AppBar(titleId = R.string.prepare_to_game, onClick = onBackButtonClick)
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