package com.example.carapp.carSelect.presentation.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.carapp.ui.theme.MediumGray
import com.example.carapp.ui.theme.TextWhite
import com.example.carapp.ui.theme.White
import com.example.carapp.ui.theme.spacing

@Composable
fun ComposeBorderText(
    modifier: Modifier = Modifier,
    text: String = "",
    label: String = "Car Label",
    fraction: Float = 1f,
    icon: Int,
    radius: Dp = MaterialTheme.spacing.view_2x,
    textStyle: TextStyle = MaterialTheme.typography.body1,
    borderColor: Color = MediumGray,
    backgroundColor: Color = MediumGray,
    strokeWidth: Dp = MaterialTheme.spacing.view_1x,
    onClick: () -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxWidth(fraction)
            .clip(RoundedCornerShape(radius))
            .border(
                BorderStroke(strokeWidth, borderColor),
                shape = RoundedCornerShape(radius)
            )
            .background(backgroundColor)
            .padding(
                horizontal = MaterialTheme.spacing.view_4x,
                vertical = MaterialTheme.spacing.view_3x
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier
                    .size(MaterialTheme.spacing.view_6x),
                painter = painterResource(id = icon),
                colorFilter = ColorFilter.tint(color = TextWhite),
                contentDescription = "LeadingIcon"
            )

            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(start = MaterialTheme.spacing.view_4x)) {
                Text(
                    modifier = Modifier,
                    text = label,
                    style = textStyle.copy(color = TextWhite),
                    fontSize = 14.sp,
                )
                Text(
                    modifier = Modifier,
                    text = text,
                    style = textStyle.copy(color = TextWhite, fontWeight = FontWeight.Bold),
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Outlined.KeyboardArrowDown, contentDescription = "dropDownIcon", tint = White)

        }
    }
}