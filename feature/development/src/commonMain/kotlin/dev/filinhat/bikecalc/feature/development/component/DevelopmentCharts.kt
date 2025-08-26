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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bikecalcmp.feature.development.generated.resources.Res
import bikecalcmp.feature.development.generated.resources.axis_x_rear_teeth
import bikecalcmp.feature.development.generated.resources.axis_y_development_m
import bikecalcmp.feature.development.generated.resources.axis_y_ratio
import bikecalcmp.feature.development.generated.resources.distance_graph
import bikecalcmp.feature.development.generated.resources.ratio_title
import com.patrykandpatrick.vico.multiplatform.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.rememberAxisLabelComponent
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.lineSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.multiplatform.common.Fill
import com.patrykandpatrick.vico.multiplatform.common.Insets
import com.patrykandpatrick.vico.multiplatform.common.LayeredComponent
import com.patrykandpatrick.vico.multiplatform.common.component.ShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.TextComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberTextComponent
import com.patrykandpatrick.vico.multiplatform.common.shape.CorneredShape
import com.patrykandpatrick.vico.multiplatform.common.shape.MarkerCorneredShape
import com.patrykandpatrick.vico.multiplatform.common.vicoTheme
import dev.filinhat.bikecalc.core.common.util.formatDoubleToString
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcResult
import org.jetbrains.compose.resources.stringResource

@Composable
fun DevelopmentCharts(
    results: List<DevelopmentCalcResult>,
    modifier: Modifier = Modifier,
    showIndicator: Boolean = true,
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
        val labelBackground =
            rememberShapeComponent(
                fill = Fill(MaterialTheme.colorScheme.background),
                shape = MarkerCorneredShape(CorneredShape.Corner.Rounded),
                strokeFill = Fill(MaterialTheme.colorScheme.outline),
                strokeThickness = 1.dp,
            )
        val indicatorFrontComponent =
            rememberShapeComponent(Fill(MaterialTheme.colorScheme.surface), CorneredShape.Pill)
        val guideline = rememberAxisGuidelineComponent()

        val indicator =
            rememberDefaultCartesianMarker(
                label =
                    rememberTextComponent(
                        background = labelBackground,
                        padding = Insets(8.dp, 4.dp),
                        margins = Insets(top = 4.dp),
                        minWidth = TextComponent.MinWidth.fixed(40.dp),
                        style =
                            TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                            ),
                    ),
                guideline = guideline,
                indicator =
                    if (showIndicator) {
                        { color ->
                            LayeredComponent(
                                back =
                                    ShapeComponent(
                                        Fill(color.copy(alpha = 0.15f)),
                                        CorneredShape.Pill,
                                    ),
                                front =
                                    LayeredComponent(
                                        back =
                                            ShapeComponent(
                                                fill = Fill(color),
                                                shape = CorneredShape.Pill,
                                            ),
                                        front = indicatorFrontComponent,
                                        padding = Insets(5.dp),
                                    ),
                                padding = Insets(10.dp),
                            )
                        }
                    } else {
                        null
                    },
                indicatorSize = 36.dp,
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
                    marker = indicator,
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
                    marker = indicator,
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
