package com.stuypulse.robot.commands.pivot.roller;

import com.stuypulse.robot.constants.Settings;

public class PivotAlgaeHold extends PivotRollerToSpeed {
    public PivotAlgaeHold() {
        super(Settings.Pivot.ALGAE_HOLD_SPEED.getAsDouble());
    }
}
