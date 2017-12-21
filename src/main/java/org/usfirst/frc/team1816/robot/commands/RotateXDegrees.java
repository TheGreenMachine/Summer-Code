package org.usfirst.frc.team1816.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team1816.robot.Components;
import org.usfirst.frc.team1816.robot.subsystems.SpeedControlDrivetrain;

public class RotateXDegrees extends Command {
    private SpeedControlDrivetrain drivetrain;
    private double degreesStarted;
    private double degreesToTurn;
    private double target;

    public RotateXDegrees(double degreesToTurn) {
        super("rotatexdegreescommand");
        this.degreesToTurn = degreesToTurn;
        drivetrain = Components.getInstance().drivetrain;
    }



    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    protected void initialize() {
        degreesStarted = drivetrain.getGyroAngle();
        target = degreesStarted + degreesToTurn;

        System.out.println("degreesStarted = " + degreesStarted + " target = " + target);
    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    protected void execute() {
//        if (target > 0 && target < 180) {
        if(target - degreesStarted < 0) {
            drivetrain.setTalonTargetSpeed(-.5, .5);
//            System.out.println("turn left");
        } else {
            drivetrain.setTalonTargetSpeed(.5, -.5);
//            System.out.println("turn right");
        }
    }

    protected boolean isFinished() {
        if (drivetrain.getGyroAngle() - target <= 1 && drivetrain.getGyroAngle() - target >= -1) {
        	System.out.println("finishing");
            drivetrain.getRightMain().setPosition(0);
            drivetrain.getLeftMain().setPosition(0);
            return true;
        } else {
            System.out.println("Current Degrees: " + drivetrain.getGyroAngle() + " target: " + target);
            return false;
        }
    }

    protected void end() {
        drivetrain.setTalonTargetSpeed(0, 0);
        System.out.println("beep boop :)");
    }

    protected void interrupted() {
        end();
    }
}
