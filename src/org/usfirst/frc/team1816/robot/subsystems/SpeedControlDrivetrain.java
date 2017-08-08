package org.usfirst.frc.team1816.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.edinarobotics.utils.subsystems.Subsystem1816;


public class SpeedControlDrivetrain extends Subsystem1816 {
	private CANTalon rightMain, rightSlave, leftMain, leftSlave;
	private double p = 0.1;
	private double i = 0;
	private double d = 1;
	private double f = 0.249;
	private int izone = 100;
	private double ramprate = 36;
	private int profile = 0;
	private double maxSpeed = 250.0;
	
	
    public SpeedControlDrivetrain(int rightMain, int rightSlave, int leftMain, int leftSlave) {
		super();
		this.rightMain = new CANTalon(rightMain);
		this.rightSlave = new CANTalon(rightSlave);
		this.leftMain = new CANTalon(leftMain);
		this.leftSlave = new CANTalon(leftSlave);
		
		this.leftMain.setInverted(true);
		this.leftSlave.setInverted(true);
		
		this.leftMain.configEncoderCodesPerRev(9843/4);
		this.rightMain.configEncoderCodesPerRev(9843/4);
		
		this.rightMain.enableBrakeMode(true);
		this.rightSlave.enableBrakeMode(true);
		this.leftMain.enableBrakeMode(true);
		this.leftSlave.enableBrakeMode(true);
		
		this.rightSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.rightSlave.set(this.rightMain.getDeviceID());
		this.leftSlave.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.leftSlave.set(this.leftMain.getDeviceID());
		
		this.rightMain.changeControlMode(CANTalon.TalonControlMode.Speed);
		this.leftMain.changeControlMode(CANTalon.TalonControlMode.Speed);
		
		this.rightMain.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		this.leftMain.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		this.rightMain.setPID(p, i, d, f, izone, ramprate, profile);
		this.leftMain.setPID(p, i, d, f, izone, ramprate, profile);
		
		this.leftMain.setEncPosition(0);
		this.rightMain.setEncPosition(0);
	}

	public void initDefaultCommand() {
		
    }
	
	public void setTalonTargetSpeed(double joyInput) {
		rightMain.set(joyInput * maxSpeed);
		leftMain.set(joyInput * maxSpeed);
	}
	
	public void getTalonSpeed() {
		System.out.println("Right: " + rightMain.getSpeed());
		System.out.println("Left: " + leftMain.getSpeed());
		System.out.println("Left Enc Pos: " + leftMain.getEncPosition() + " Left Revs" + leftMain.getPosition());
		System.out.println("Right Enc Pos: " + leftMain.getEncPosition() + " Right Revs: " + rightMain.getPosition());
	}
	
	public void changePID(double p, double i, double d, double f) {
		this.p = p;
		this.i = i;
		this.d = d;
		this.f = f;
		
		System.out.println("PID Updated!");
	}

	@Override
	public void update() {
		
	}
}

