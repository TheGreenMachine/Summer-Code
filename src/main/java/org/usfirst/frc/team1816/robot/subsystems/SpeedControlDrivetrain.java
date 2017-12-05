package org.usfirst.frc.team1816.robot.subsystems;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.edinarobotics.utils.subsystems.Subsystem1816;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.I2C;


public class SpeedControlDrivetrain extends Subsystem1816 {
    private static final int TICKS_PER_REV = 9900;
    public static final double TICKS_PER_INCH = 1.018;
    private CANTalon rightMain, rightSlaveOne, rightSlaveTwo, leftMain, leftSlaveOne, leftSlaveTwo;
    private double p = 0.2;
    private double i = 0;
    private double d = 0;
    private double f = 0;
    private int izone = 100;
    private double ramprate = 36;
    private int profile = 0;
    private double maxSpeed = 250.0;
    private AHRS navx;

    public SpeedControlDrivetrain(int rightMain, int rightSlaveOne, int rightSlaveTwo, int leftMain, int leftSlaveOne, int leftSlaveTwo) {
        super();
        this.rightMain = new CANTalon(rightMain);
        this.rightSlaveOne = new CANTalon(rightSlaveOne);
        this.rightSlaveTwo = new CANTalon(rightSlaveTwo);
        this.leftMain = new CANTalon(leftMain);
        this.leftSlaveOne = new CANTalon(leftSlaveOne);
        this.leftSlaveTwo = new CANTalon(leftSlaveTwo);

        navx = new AHRS(I2C.Port.kMXP);

        this.leftMain.setInverted(true);
        this.leftSlaveOne.setInverted(true);
        this.leftSlaveTwo.setInverted(true);

        this.leftMain.configEncoderCodesPerRev(9843 / 4);
        this.rightMain.configEncoderCodesPerRev(9843 / 4);

        this.rightMain.enableBrakeMode(true);
        this.rightSlaveOne.enableBrakeMode(true);
        this.rightSlaveTwo.enableBrakeMode(true);
        this.leftMain.enableBrakeMode(true);
        this.leftSlaveOne.enableBrakeMode(true);
        this.leftSlaveTwo.enableBrakeMode(true);

        this.rightSlaveOne.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.rightSlaveOne.set(this.rightMain.getDeviceID());
        this.rightSlaveTwo.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.rightSlaveTwo.set(this.rightMain.getDeviceID());
        this.leftSlaveOne.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.leftSlaveOne.set(this.leftMain.getDeviceID());
        this.leftSlaveTwo.changeControlMode(CANTalon.TalonControlMode.Follower);
        this.leftSlaveTwo.set(this.leftMain.getDeviceID());

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

    public void setTalonTargetSpeed(double joyRight, double joyLeft) {
        rightMain.set(joyRight * maxSpeed);
        leftMain.set(joyLeft * maxSpeed);

    }

    public void getTalonSpeed() {
        //System.out.println("Right: " + rightMain.getSpeed());
        //System.out.println("Left: " + leftMain.getSpeed());
        //System.out.println("Left Enc Pos: " + leftMain.getEncPosition() + " Left Revs" + leftMain.getPosition());
        //System.out.println("Right Enc Pos: " + leftMain.getEncPosition() + " Right Revs: " + rightMain.getPosition());
    }

    public void changePID(double p, double i, double d, double f) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.f = f;

        System.out.println("PID Updated!");
    }

    public CANTalon getRightMain() {
        return rightMain;
    }

    public CANTalon getLeftMain() {
        return leftMain;
    }

    public double getGyroAngle() {
        return navx.getAngle();
    }

    public double talonPositionRight() {
        return rightMain.getPosition();
    }

    public double talonPositionLeft() {
        return leftMain.getPosition() * -1;
    }

    @Override
    public void update() {

    }
}

