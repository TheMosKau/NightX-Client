package net.ccbluex.liquidbounce.features.module.modules.world

import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.UpdateEvent
import net.ccbluex.liquidbounce.event.WorldEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.utils.MovementUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.FloatValue

@ModuleInfo(name = "Timer", category = ModuleCategory.WORLD)
class Timer : Module() {

    private val speedValue = FloatValue("Speed", 2F, 0.1F, 10F, "x")
    private val onMoveValue = BoolValue("OnMove", false)
    private val autoDisableValue = BoolValue("AutoDisable", false)

    override fun onDisable() {
        mc.timer.timerSpeed = 1F
    }

    @EventTarget
    fun onUpdate(event: UpdateEvent) {
        if (mc.thePlayer == null || mc.theWorld == null) return

        if (MovementUtils.isMoving() || !onMoveValue.get()) {
            mc.timer.timerSpeed = speedValue.get()
            return
        }

        mc.timer.timerSpeed = 1F
    }

    @EventTarget
    fun onWorld(event: WorldEvent) {
        if (event.worldClient != null)
            return

        if (autoDisableValue.get()) state = false
    }
}
