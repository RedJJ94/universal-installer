package app.pwhs.updater.presentation.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.pwhs.core.ui.component.ShimmerBox
import app.pwhs.core.ui.theme.Spacing

@Composable
fun UpdatesSkeleton(
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = Spacing.L,
            end = Spacing.L,
            top = Spacing.M,
            bottom = 100.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(Spacing.M),
    ) {
        items(count = 5) {
            UpdatesCardSkeleton()
        }
    }
}

@Composable
fun UpdatesCardSkeleton(
    modifier: Modifier = Modifier,
) {
    OutlinedCard(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraLarge,
        colors = CardDefaults.outlinedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.6f),
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.L),
        ) {
            // Header: Icon + Titles + Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ShimmerBox(
                    modifier = Modifier.size(44.dp),
                    shape = MaterialTheme.shapes.medium,
                )
                Spacer(modifier = Modifier.width(Spacing.M))
                Column(modifier = Modifier.weight(1f)) {
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(18.dp),
                        shape = RoundedCornerShape(4.dp),
                    )
                    Spacer(modifier = Modifier.height(Spacing.XS))
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .height(12.dp),
                        shape = RoundedCornerShape(4.dp),
                    )
                }
                Spacer(modifier = Modifier.width(Spacing.S))
                ShimmerBox(
                    modifier = Modifier
                        .width(72.dp)
                        .height(24.dp),
                    shape = MaterialTheme.shapes.small,
                )
            }

            Spacer(modifier = Modifier.height(Spacing.M))

            // Versions info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ShimmerBox(
                    modifier = Modifier
                        .width(110.dp)
                        .height(14.dp),
                    shape = RoundedCornerShape(4.dp),
                )
                ShimmerBox(
                    modifier = Modifier
                        .width(110.dp)
                        .height(14.dp),
                    shape = RoundedCornerShape(4.dp),
                )
            }

            Spacer(modifier = Modifier.height(Spacing.M))

            // Footer: Timestamp + Action button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ShimmerBox(
                    modifier = Modifier
                        .width(90.dp)
                        .height(12.dp),
                    shape = RoundedCornerShape(4.dp),
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    ShimmerBox(
                        modifier = Modifier.size(36.dp),
                        shape = MaterialTheme.shapes.medium,
                    )
                    Spacer(modifier = Modifier.width(Spacing.S))
                    ShimmerBox(
                        modifier = Modifier
                            .width(84.dp)
                            .height(36.dp),
                        shape = MaterialTheme.shapes.medium,
                    )
                }
            }
        }
    }
}
