package com.stuypulse.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.Command;

import com.stuypulse.robot.subsystems.drivetrain.Drivetrain;
import com.stuypulse.stuylib.input.Gamepad;

public class DriveDefault extends Command {
    private final Gamepad gamepad;
    private final boolean squared;
    private final double xInput;

    public DriveDefault(Gamepad gamepad,
                        boolean squared) {
        this.gamepad = gamepad;
        this.squared = squared;
        this.xInput = gamepad.getRightTrigger() + -gamepad.getLeftTrigger();
        

        addRequirements(Drivetrain.getInstance());
    }

    @Override
    public void execute() {
        Drivetrain.getInstance().driveArcade(xInput, gamepad.getLeftStick().x, squared);
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
