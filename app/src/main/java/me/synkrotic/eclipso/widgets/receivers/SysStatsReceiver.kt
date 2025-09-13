package me.synkrotic.eclipso.widgets.receivers

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import me.synkrotic.eclipso.widgets.SysStatsWidget

class SysStatsReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = SysStatsWidget()
}