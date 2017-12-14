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
		// TODO Auto-generated method stub
        initAngle = drivetrain.getGyroAngle();

		super.initialize();
	}

	@Override
	protected void execute() {
		System.out.println("driveuntil working");
		// TODO Auto-generated method stub
        averageRaw = ai.getAverageValue();
        averageVoltage = ai.getAverageVoltage();
        System.out.println("average voltage: " + averageVoltage);
        double deltaAngle = drivetrain.getGyroAngle() - initAngle;
        double velocity;
        if(averageVoltage > distance + .2) {
			velocity = speed * (((deltaAngle) / 50) + 1);
			velocity *= (1-distance);
		}else {
			velocity = speed * (((deltaAngle) / 50) + 1);

		}
		drivetrain.setTalonTargetSpeed(velocity, velocity);
	}

	@Override
	protected void end() {
        drivetrain.setTalonTargetSpeed(0, 0);

		// TODO Auto-generated method stub
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
