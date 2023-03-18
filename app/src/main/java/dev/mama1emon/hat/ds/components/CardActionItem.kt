package dev.mama1emon.hat.ds.components

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import dev.mama1emon.hat.R
import dev.mama1emon.hat.ds.theme.BlueZodiac
import dev.mama1emon.hat.ds.theme.CitrusZest
import dev.mama1emon.hat.ds.theme.HatTypography
import dev.mama1emon.hat.ds.theme.White

enum class CardAction {
    Remove, Plus, Minus
}

@Composable
fun CardActionItem(@StringRes textId: Int, cardAction: CardAction, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = BlueZodiac,
                shape = RoundedCornerShape(size = dimensionResource(id = R.dimen.radius16))
            )
            .padding(
                horizontal = dimensionResource(id = R.dimen.padding24),
                vertical = dimensionResource(id = R.dimen.padding10)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = textId),
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding6)),
            color = White,
            maxLines = 1,
            style = HatTypography.Regular16
        )

        when (cardAction) {
            CardAction.Remove -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_27_trash),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = onClick),
                    tint = CitrusZest
                )
            }
            CardAction.Plus -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_21_plus),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = onClick),
                    tint = CitrusZest
                )
            }
            CardAction.Minus -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_21_minus),
                    contentDescription = null,
                    modifier = Modifier.clickable(onClick = onClick),
                    tint = CitrusZest
                )
            }
        }
    }
}