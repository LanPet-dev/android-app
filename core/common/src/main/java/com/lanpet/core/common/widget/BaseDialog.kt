package com.lanpet.core.common.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lanpet.core.designsystem.theme.LanPetDimensions

@Composable
fun BaseDialog(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    dismiss: @Composable (() -> Unit)? = null,
    confirm: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
) {
    Surface(
        shape = RoundedCornerShape(LanPetDimensions.Corner.medium),
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier
                    .padding(
                        top = LanPetDimensions.Spacing.large,
                        start = LanPetDimensions.Spacing.large,
                        end = LanPetDimensions.Spacing.large,
                        bottom = LanPetDimensions.Spacing.medium,
                    ).fillMaxWidth(
                        0.9f,
                    ).background(
                        color = MaterialTheme.colorScheme.background,
                        shape =
                            RoundedCornerShape(
                                LanPetDimensions.Corner.medium,
                            ),
                    ),
        ) {
            title?.let {
                it.invoke()
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
            }
            content?.let {
                it.invoke()
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                dismiss?.let {
                    it.invoke()
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                }
                confirm?.invoke()
            }
        }
    }
}
