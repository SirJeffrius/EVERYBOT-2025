package com.stuypulse.robot.subsystems.pivot;

import com.stuypulse.robot.constants.Settings;
import com.stuypulse.robot.util.RobotVisualizer;
import com.stuypulse.stuylib.math.SLMath;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

public abstract class Pivot extends SubsystemBase {
    private static final Pivot instance;

    static { 
        instance = new PivotImpl();
    }

    public static Pivot getInstance() {
        return instance;
    }

    protected PivotState pivotState;
    protected PivotControlMode pivotControlMode;
    
    protected Pivot() {
        this.pivotState = PivotState.STOW_CORAL;
        this.pivotControlMode = PivotControlMode.MANUAL;
    }
    
    public enum PivotState {
        DEFAULT(Settings.Pivot.DEFAULT_ANGLE),
        STOW_CORAL(Settings.Pivot.CORAL_STOW_ANGLE),
        SCORE_CORAL(Settings.Pivot.CORAL_SCORE_ANGLE),
        INTAKE_ALGAE(Settings.Pivot.ALGAE_GROUND_ANGLE),
        STOW_ALGAE(Settings.Pivot.ALGAE_HOLDING_ANGLE),
        INTAKE_ALGAE_FROM_LOLIPOP(Settings.Pivot.ALGAE_LOLLIPOP_ANGLE),
        RESEAT_CORAL(Settings.Pivot.CORAL_RESEAT_ANGLE);

        Rotation2d targetAngle;

        private PivotState(Rotation2d targetAngle) {
            this.targetAngle = 
                Rotation2d.fromDegrees(SLMath.clamp(
                        targetAngle.getDegrees(), 
                        Settings.Pivot.DEFAULT_ANGLE.getDegrees(), 
                        Settings.Pivot.MAX_ANGLE.getDegrees()));
        }

        public Rotation2d getTargetAngle() {
            return this.targetAngle;
        }
    }

    public enum PivotControlMode {
        MANUAL(Settings.Pivot.CTRLMODE_MANUAL),
        USING_STATES(Settings.Pivot.CTRLMODE_STATES);

        String controlMode;

        private PivotControlMode(String controlMode) {
            this.controlMode = controlMode;
        }

        public String getPivotControlMode() {
            return this.controlMode;
        }

    }

    public PivotControlMode PivotControlMode() {
        return this.pivotControlMode;
    }

    public abstract void setPivotState(PivotState pivotState);

    public abstract PivotState getPivotState();

    public abstract void setRollerMotor(double speed);

    public abstract double getRollerMotor();

    public abstract void setPivotMotor(double speed);
    
    public abstract void resetPivotEncoder(double newEncoderPosition);

    public abstract void setPivotControlMode(PivotControlMode SetPivotStateMode);

    public abstract Rotation2d getPivotRotation();

    public abstract boolean atTargetAngle();

    @Override
    public void periodic() {
        SmartDashboard.putString("Pivot/Pivot State", pivotState.toString());
        SmartDashboard.putNumber("Pivot/Target Angle", this.pivotState.getTargetAngle().getDegrees());
        SmartDashboard.putBoolean("Pivot/At Target Angle", atTargetAngle());

        // RobotVisualizer.getInstance().updatePivotAngle(this.pivotState.getTargetAngle(), atTargetAngle());
        RobotVisualizer.getInstance().updatePivotAngle(getPivotRotation(), atTargetAngle());
        RobotVisualizer.getInstance().updateRollers(getRollerMotor());
    }

    public abstract SysIdRoutine getSysIdRoutine();
}
