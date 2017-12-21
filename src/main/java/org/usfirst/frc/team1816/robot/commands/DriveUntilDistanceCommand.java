package org.usfirst.frc.team1816.robot.commands;

import org.usfirst.frc.team1816.robot.Components;
import org.usfirst.frc.team1816.robot.subsystems.SpeedControlDrivetrain;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;

public class DriveUntilDistanceCommand extends Command {

    private SpeedControlDrivetrain drivetrain;
    private double distance;
    AnalogInput ai;
    int averageRaw;
    double averageVoltage;
    private double speed;
    private double initAngle;


    public DriveUntilDistanceCommand(double distance, double speed) {
        super("driveuntildistancecommand");
        drivetrain = Components.getInstance().drivetrain;
        this.distance = distance;
        this.speed = speed;
        ai = Components.getInstance().ai;
        ai.setOversampleBits(4);
        ai.setAverageBits(2);
        AnalogInput.setGlobalSampleRate(62500);
        requires(drivetrain);
    }


    @Override
    protected void initialize() {
        initAngle = drivetrain.getGyroAngle();

        super.initialize();
    }

    @Override
    protected void execute() {
        System.out.println("drive until working");
        averageRaw = ai.getAverageValue();
        averageVoltage = ai.getAverageVoltage();
        double deltaAngle = drivetrain.getGyroAngle() - initAngle;
        double velocity;

        velocity = speed * (((deltaAngle) / 50) + 1);
        velocity *= (distance - averageVoltage);

        if (deltaAngle > 0) {
            System.out.println("Angle: " + deltaAngle);
            drivetrain.setTalonTargetSpeed(velocity * 0.9, velocity);
            System.out.println("Velocity: " + velocity);
        } else if (deltaAngle < 0) {
            System.out.println("Angle: " + deltaAngle);
            drivetrain.setTalonTargetSpeed(velocity, velocity * .9);
            System.out.println("Velocity: " + velocity);
        } else {
            System.out.println("Angle: " + deltaAngle);
            drivetrain.setTalonTargetSpeed(velocity, velocity);
            System.out.println("Velocity: " + velocity);
        }
    }

    @Override
    protected void end() {
        drivetrain.setTalonTargetSpeed(0, 0);
        super.end();
    }

    @Override
    protected void interrupted() {
        // TODO Auto-generated method stub
        end();
        super.interrupted();
    }


    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        System.out.println("is finished");

        return averageVoltage > distance;
    }

}
