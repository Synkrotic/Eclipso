package me.synkrotic.eclipso.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontFamily
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import me.synkrotic.eclipso.ui.theme.Background
import me.synkrotic.eclipso.ui.theme.Snow



class SysStatsWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            SysStatsWidgetContent()
        }
    }
}

object WidgetPrefsKey {
    val STAT_INDEX = intPreferencesKey("stat_index")
}

class UpdateStatAction : ActionCallback {
    override suspend fun onAction(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        updateAppWidgetState(context, glanceId) { prefs ->
            val currentIndex = prefs[WidgetPrefsKey.STAT_INDEX] ?: 0
            val nextIndex = if (currentIndex < 4) currentIndex + 1 else 0
            prefs[WidgetPrefsKey.STAT_INDEX] = nextIndex
        }

        SysStatsWidget().update(context, glanceId)
    }
}


@Composable
fun SysStatsWidgetContent() {
    val stats = HashMap(
        mapOf(
            0 to Pair("CPU", "53.0"),
            1 to Pair("RAM", "10.5 / 12.0 GB"),
            2 to Pair("Battery", "80%"),
            3 to Pair("Storage", "128.0 / 256.0 GB"),
            4 to Pair("Network", "12.3kb/s")
        )
    )
    val statIndex = currentState(key = WidgetPrefsKey.STAT_INDEX) ?: 0

    Column(
        modifier = GlanceModifier
            .clickable(actionRunCallback<UpdateStatAction>())
            .fillMaxSize()
            .background(ColorProvider(Background))
    ) {
        StatItem(stats[statIndex]?.first ?: "N/A", stats[statIndex]?.second ?: "N/A")
    }
}

@Composable
fun StatItem(
    title: String,
    value: String
) {
    Box {
        Column(
            verticalAlignment = Alignment.Vertical.Top,
            horizontalAlignment = Alignment.Horizontal.Start,
            modifier = GlanceModifier
                .fillMaxSize()
                .height(165.dp)
                .padding(16.dp, 2.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = ColorProvider(Snow),
                    fontSize = 20.sp, // 60.sp
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily("sans-serif"),
                    textAlign = TextAlign.Center
                )
            )
        }
        Column(
            verticalAlignment = Alignment.Vertical.Bottom,
            horizontalAlignment = Alignment.Horizontal.End,
            modifier = GlanceModifier
                .fillMaxSize()
                .height(165.dp)
                .padding(16.dp, 2.dp)
        ) {
            Text(
                text = value,
                style = TextStyle(
                    color = ColorProvider(Snow),
                    fontSize = 50.sp, // 60.sp
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily("sans-serif"),
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}