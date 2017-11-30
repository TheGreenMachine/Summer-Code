package org.usfirst.frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1816.robot.Components;
import org.usfirst.frc.team1816.robot.subsystems.SpeedControlDrivetrain;

public class DriveXInches extends Command {
    private SpeedControlDrivetrain drivetrain;
    private double inches;
    private double speed;
    private double ticks;
    private double initAngle;

    public DriveXInches(double inches, double speed) {
        super("drivexinchescommand");
        this.inches = inches;
        this.speed = speed;
        drivetrain = Components.getInstance().drivetrain;
        ticks = 300000000;
//        ticks = (int) (inches * drivetrain.TICKS_PER_INCH);
    }


    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    protected void initialize() {
        drivetrain.getRightMain().setEncPosition(0);
        initAngle = drivetrain.getGyroAngle();
    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    protected void execute() {
        double currentTicks = drivetrain.getRightMain().getEncPosition();
        double deltaAngle = drivetrain.getGyroAngle() - initAngle;
        double velocity;
        ticks -= currentTicks;

        if (ticks > 0) {
            velocity = speed * (((deltaAngle) / 50) + 1);
            drivetrain.setTalonTargetSpeed(velocity, velocity);
        } else {
            drivetrain.setTalonTargetSpeed(-0.1, -0.1);
        }
    }

    protected boolean isFinished() {
        if (ticks <= 0) {
            return true;
        } else {
            return false;
        }
    }

    protected void end() {
        drivetrain.setTalonTargetSpeed(0, 0);
    }


    protected void interrupted() {
        end();
    }
}
