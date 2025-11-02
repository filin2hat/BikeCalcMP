package dev.filinhat.bikecalc.feature.development.presentation.component.chart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.patrykandpatrick.vico.multiplatform.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.multiplatform.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.multiplatform.cartesian.data.lineSeries
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.multiplatform.cartesian.marker.rememberDefaultCartesianMarker
import com.patrykandpatrick.vico.multiplatform.common.Fill
import com.patrykandpatrick.vico.multiplatform.common.Insets
import com.patrykandpatrick.vico.multiplatform.common.LayeredComponent
import com.patrykandpatrick.vico.multiplatform.common.component.ShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.TextComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.multiplatform.common.component.rememberTextComponent
import com.patrykandpatrick.vico.multiplatform.common.fill
import com.patrykandpatrick.vico.multiplatform.common.shape.CorneredShape
import com.patrykandpatrick.vico.multiplatform.common.shape.MarkerCorneredShape
import dev.filinhat.bikecalc.core.common.util.formatDoubleToString
import dev.filinhat.bikecalc.core.model.development.DevelopmentCalcResult
import org.jetbrains.compose.resources.stringResource

/**
 * Компонент для отображения графиков развития метража.
 * Показывает два графика: график расстояния (метры на оборот) и график передаточного отношения.
 *
 * @param results Список результатов расчёта развития метража
 * @param modifier Модификатор для настройки внешнего вида компонента
 * @param showIndicator Флаг для отображения индикатора на графиках (по умолчанию true)
 */
@Composable
fun DevelopmentCharts(
    results: List<DevelopmentCalcResult>,
    modifier: Modifier = Modifier,
    showIndicator: Boolean = true,
) {
    if (results.isEmpty()) return

    val rearTeethList = remember(results) { results.map { it.rearTeeth }.distinct().sorted() }
    val frontTeethList = remember(results) { results.map { it.frontTeeth }.distinct().sorted() }

    val indicator = rememberChartIndicator(showIndicator)

    val developmentProducer = remember { CartesianChartModelProducer() }
    val ratioProducer = remember { CartesianChartModelProducer() }

    // --- Заполняем данные графиков
    LaunchedEffect(results) {
        developmentProducer.setLineSeries(
            series =
                buildMatrix(frontTeethList, rearTeethList) { f, r ->
                    results.find { it.frontTeeth == f && it.rearTeeth == r }?.developmentMeters?.toFloat()
                        ?: 0f
                },
        )

        ratioProducer.setLineSeries(
            series =
                buildMatrix(frontTeethList, rearTeethList) { f, r ->
                    if (r == 0) 0f else f.toFloat() / r.toFloat()
                },
        )
    }

    Column(modifier) {
        ChartSection(
            title = stringResource(Res.string.distance_graph),
            xLabel = stringResource(Res.string.axis_x_rear_teeth),
            yLabel = stringResource(Res.string.axis_y_development_m),
            rearTeethList = rearTeethList,
            modelProducer = developmentProducer,
            valueFormatter = { _, v, _ -> "${formatDoubleToString(v, 1)} м" },
            indicator = indicator,
        )
        Spacer(Modifier.height(8.dp))

        ChartSection(
            title = stringResource(Res.string.ratio_title),
            xLabel = stringResource(Res.string.axis_x_rear_teeth),
            yLabel = stringResource(Res.string.axis_y_ratio),
            rearTeethList = rearTeethList,
            modelProducer = ratioProducer,
            valueFormatter = { _, v, _ -> "${formatDoubleToString(v, 2)}×" },
            indicator = indicator,
        )
    }
}

/**
 * Устанавливает данные для линейного графика.
 *
 * @param series Список списков значений для каждой серии данных
 */
private suspend fun CartesianChartModelProducer.setLineSeries(series: List<List<Float>>) {
    runTransaction {
        lineSeries { series.forEach { values -> series(*values.toTypedArray()) } }
    }
}

/**
 * Строит матрицу данных для графиков на основе списков передних и задних зубьев.
 *
 * @param fronts Список передних зубьев
 * @param rears Список задних зубьев
 * @param block Функция для вычисления значения на основе пары передних и задних зубьев
 * @return Матрица значений для построения графиков
 */
private fun buildMatrix(
    fronts: List<Int>,
    rears: List<Int>,
    block: (Int, Int) -> Float,
): List<List<Float>> = fronts.map { f -> rears.map { r -> block(f, r) } }

/**
 * Создает индикатор для графиков с настраиваемым отображением.
 *
 * @param showIndicator Флаг для отображения индикатора
 * @return Настроенный индикатор для графиков
 */
@Composable
private fun rememberChartIndicator(showIndicator: Boolean): CartesianMarker {
    val labelBackground =
        rememberShapeComponent(
            fill = Fill(MaterialTheme.colorScheme.background),
            shape = MarkerCorneredShape(CorneredShape.Corner.Rounded),
            strokeFill = Fill(MaterialTheme.colorScheme.outline),
            strokeThickness = 1.dp,
        )
    val indicatorFront =
        rememberShapeComponent(Fill(MaterialTheme.colorScheme.surface), CorneredShape.Pill)
    val guideline =
        rememberAxisGuidelineComponent(
            thickness = 3.dp,
            fill = fill(MaterialTheme.colorScheme.primary),
        )

    return rememberDefaultCartesianMarker(
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
                        back = ShapeComponent(Fill(color.copy(alpha = 0.15f)), CorneredShape.Pill),
                        front =
                            LayeredComponent(
                                back = ShapeComponent(Fill(color), CorneredShape.Pill),
                                front = indicatorFront,
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
}
