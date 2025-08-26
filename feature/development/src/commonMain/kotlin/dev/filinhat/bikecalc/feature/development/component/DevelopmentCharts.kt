package dev.filinhat.bikecalc.feature.development.component

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import bikecalcmp.feature.development.generated.resources.Res
import bikecalcmp.feature.development.generated.resources.axis_x_rear_teeth
import bikecalcmp.feature.development.generated.resources.axis_y_development_m
import bikecalcmp.feature.development.generated.resources.axis_y_ratio
import bikecalcmp.feature.development.generated.resources.distance_graph
import bikecalcmp.feature.development.generated.resources.legend_chainring_format
import bikecalcmp.feature.development.generated.resources.ratio_title
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.lineSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.multiplatform.common.Fill
import com.patrykandpatrick.vico.multiplatform.common.Insets
import com.patrykandpatrick.vico.multiplatform.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberTextComponent
import com.patrykandpatrick.vico.multiplatform.common.shape.CorneredShape
import dev.filinhat.bikecalc.core.common.util.formatDoubleToString
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcResult
import org.jetbrains.compose.resources.stringResource

@Composable
fun DevelopmentCharts(
    results: List<DevelopmentCalcResult>,
    modifier: Modifier = Modifier,
) {
    if (results.isEmpty()) return

    Column(modifier = modifier) {
        val modelProducer = remember { CartesianChartModelProducer() }
        val ratioModelProducer = remember { CartesianChartModelProducer() }
        val rearTeethList =
            remember(results) {
                results
                    .map { it.rearTeeth }
                    .distinct()
                    .sorted()
            }
        val frontTeethList =
            remember(results) {
                results
                    .map { it.frontTeeth }
                    .distinct()
                    .sorted()
            }

        // Маркер для графика развития
        val developmentMarker =
            rememberDefaultCartesianMarker(
                label =
                    rememberTextComponent(
                        background =
                            rememberShapeComponent(
                                fill = Fill(color = MaterialTheme.colorScheme.onSurfaceVariant),
                                shape = CorneredShape.rounded(4.dp),
                            ),
                        padding = Insets(horizontal = 8.dp, vertical = 4.dp),
                        margins = Insets(top = 4.dp),
                    ),
            )

        // Маркер для графика передаточных отношений
        val ratioMarker =
            rememberDefaultCartesianMarker(
                label =
                    rememberTextComponent(
                        background =
                            rememberShapeComponent(
                                fill = Fill(color = MaterialTheme.colorScheme.onSurfaceVariant),
                                shape = CorneredShape.rounded(4.dp),
                            ),
                        padding = Insets(horizontal = 8.dp, vertical = 4.dp),
                        margins = Insets(top = 4.dp),
                    ),
            )

        LaunchedEffect(results) {
            val seriesList: List<List<Float>> =
                frontTeethList.map { front ->
                    rearTeethList.map { rear ->
                        results
                            .find { it.frontTeeth == front && it.rearTeeth == rear }
                            ?.developmentMeters
                            ?.toFloat()
                            ?: 0f
                    }
                }
            modelProducer.runTransaction {
                lineSeries {
                    seriesList.forEach { values ->
                        series(*values.toTypedArray())
                    }
                }
            }

            val ratioSeriesList: List<List<Float>> =
                frontTeethList.map { front ->
                    rearTeethList.map { rear ->
                        if (rear == 0) 0f else front.toFloat() / rear.toFloat()
                    }
                }
            ratioModelProducer.runTransaction {
                lineSeries {
                    ratioSeriesList.forEach { values ->
                        series(*values.toTypedArray())
                    }
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                stringResource(Res.string.distance_graph),
                style = MaterialTheme.typography.titleMedium,
            )
        }

        if (frontTeethList.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                frontTeethList.forEach { front ->
                    Text(
                        text = stringResource(Res.string.legend_chainring_format, front),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
        }

        CartesianChartHost(
            chart =
                rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis =
                        VerticalAxis.rememberStart(
                            valueFormatter = { _, value, _ ->
                                "${formatDoubleToString(value, 1)} м"
                            },
                            label = rememberAxisLabelComponent(style = TextStyle(color = MaterialTheme.colorScheme.onBackground)),
                        ),
                    bottomAxis =
                        HorizontalAxis.rememberBottom(
                            valueFormatter = { _, value, _ ->
                                val index = value.toInt()
                                rearTeethList.getOrNull(index)?.toString() ?: index.toString()
                            },
                            label = rememberAxisLabelComponent(style = TextStyle(color = MaterialTheme.colorScheme.onBackground)),
                        ),
                    marker = developmentMarker,
                ),
            modelProducer = modelProducer,
            animationSpec = tween(durationMillis = 250),
            animateIn = true,
            placeholder = {},
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(320.dp),
        )

        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(Res.string.axis_x_rear_teeth),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = stringResource(Res.string.axis_y_development_m),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(Res.string.ratio_title),
                style = MaterialTheme.typography.titleMedium,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        CartesianChartHost(
            chart =
                rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis =
                        VerticalAxis.rememberStart(
                            valueFormatter = { _, value, _ ->
                                "${formatDoubleToString(value, 2)}×"
                            },
                            label = rememberAxisLabelComponent(style = TextStyle(color = MaterialTheme.colorScheme.onBackground)),
                        ),
                    bottomAxis =
                        HorizontalAxis.rememberBottom(
                            valueFormatter = { _, value, _ ->
                                val index = value.toInt()
                                rearTeethList.getOrNull(index)?.toString() ?: index.toString()
                            },
                            label = rememberAxisLabelComponent(style = TextStyle(color = MaterialTheme.colorScheme.onBackground)),
                        ),
                    marker = ratioMarker,
                ),
            modelProducer = ratioModelProducer,
            animationSpec = tween(durationMillis = 250),
            animateIn = true,
            placeholder = {},
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(320.dp),
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(Res.string.axis_x_rear_teeth),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = stringResource(Res.string.axis_y_ratio),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
