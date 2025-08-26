package dev.filinhat.bikecalc.feature.development.component.chart

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianMeasuringContext
import com.patrykandpatrick.vico.multiplatform.cartesian.Zoom
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.Axis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberVicoZoomState

@Composable
internal fun ChartSection(
    title: String,
    xLabel: String,
    yLabel: String,
    rearTeethList: List<Int>,
    modelProducer: CartesianChartModelProducer,
    valueFormatter: (CartesianMeasuringContext, Double, Axis.Position.Vertical?) -> CharSequence,
    indicator: CartesianMarker?,
) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(title, style = MaterialTheme.typography.titleMedium)
    }

    CartesianChartHost(
        chart =
            rememberCartesianChart(
                rememberLineCartesianLayer(),
                startAxis =
                    VerticalAxis.rememberStart(
                        valueFormatter = valueFormatter,
                        label =
                            rememberAxisLabelComponent(
                                style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                            ),
                    ),
                bottomAxis =
                    HorizontalAxis.rememberBottom(
                        valueFormatter = { _, value, _ ->
                            val index = value.toInt()
                            rearTeethList.getOrNull(index)?.toString() ?: index.toString()
                        },
                        label =
                            rememberAxisLabelComponent(
                                style = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                            ),
                    ),
                marker = indicator,
            ),
        modelProducer = modelProducer,
        animationSpec = tween(250),
        animateIn = false,
        placeholder = {},
        zoomState =
            rememberVicoZoomState(
                initialZoom =
                    remember {
                        Zoom.min(Zoom.fixed(), Zoom.Content)
                    },
            ),
        modifier = Modifier.fillMaxWidth().height(320.dp),
    )

    Spacer(Modifier.height(4.dp))
    Text(
        xLabel,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.fillMaxWidth(),
    )
    Text(
        yLabel,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.fillMaxWidth(),
    )
}
