package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class MecanumDrive
{
    //This class will contain the drive motors on the robot, gyro sensor
    //Methods will include gyro aided turn, speed control (TeleOP), Odometry for autonomous(?)

    /* Public OpMode members. */
    public DcMotor  leftBack    = null;
    public DcMotor  rightBack   = null;
    public DcMotor  leftFront   = null;
    public DcMotor  rightFront  = null;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;

    private ElapsedTime period  = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 537.6 ;    // Orbital 20 Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public BNO055IMU imu;
    Orientation lastAngles = new Orientation();
    double globalAngle, power = .30, correction;

    /* Constructor */
    public MecanumDrive(){

    }
    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftBack  = hwMap.get(DcMotor.class, "left_back");
        rightBack = hwMap.get(DcMotor.class, "right_back");
        leftFront = hwMap.get(DcMotor.class, "left_front");
        rightFront= hwMap.get(DcMotor.class, "right_front");
        //define motor direction
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setPower(0);
        rightFront.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        /*leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        */

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode                = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled      = false;
        parameters.calibrationDataFile = "RoboBaconIMUCalibration.json";

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = hwMap.get(BNO055IMU.class, "imu 1");

        imu.initialize(parameters);
    }
    private void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        globalAngle = 0;
    }
    public double getAngle()
    {
        // We experimentally determined the Z axis is the axis we want to use for heading angle.
        // We have to process the angle because the imu works in euler angles so the Z axis is
        // returned as 0 to +180 or 0 to -180 rolling back to -179 or +179 when rotation passes
        // 180 degrees. We detect this transition and track the total cumulative angle of rotation.

        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;

        if (deltaAngle < -180)
            deltaAngle += 360;
        else if (deltaAngle > 180)
            deltaAngle -= 360;

        globalAngle += deltaAngle;

        lastAngles = angles;

        return globalAngle;
    }
    void encoderDrive(double speed,
                      double lBDis, double rBDis) {
        int newLBTarget;
        int newRBTarget;
        int newLFTarget;
        int newRFTarget;

        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Determine new target position, and pass to motor controller
        newLBTarget = leftBack.getCurrentPosition() + (int)(lBDis * COUNTS_PER_INCH);
        newRBTarget = rightBack.getCurrentPosition() + (int)(rBDis * COUNTS_PER_INCH);
        newLFTarget = leftFront.getCurrentPosition() + (int)(lBDis * COUNTS_PER_INCH);
        newRFTarget = rightFront.getCurrentPosition() + (int)(rBDis * COUNTS_PER_INCH);
        leftBack.setTargetPosition(newLBTarget);
        rightBack.setTargetPosition(newRBTarget);
        leftFront.setTargetPosition(newLFTarget);
        rightFront.setTargetPosition(newRFTarget);

        // Turn On RUN_TO_POSITION
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // start motion.
        leftBack.setPower(Math.abs(speed));
        rightBack.setPower(Math.abs(speed));
        leftFront.setPower(Math.abs(speed));
        rightFront.setPower(Math.abs(speed));

        while (leftBack.isBusy() && rightBack.isBusy() && leftFront.isBusy() && rightFront.isBusy());

        // Stop all motion;
        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setPower(0);
        rightFront.setPower(0);

        // Turn off RUN_TO_POSITION
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    } //end of encoder drive method
    public void linearDrive (double speed, double distance){
        encoderDrive(speed, distance,distance);
    }
    public void sideDrive (double speed, double distance){
        //positive speed moves right and negative moves left
        double runtime = period.time();

        rightBack.setPower(speed);
        leftFront.setPower(speed);
        rightFront.setPower(speed);
        leftBack.setPower(speed);

        while (.6 > period.time() - runtime );

        rightBack.setPower(0);
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
    }
    public void diagonalDrive (double speed, double distance){

    }
    private void rotate(int degrees, double power) {
        double leftPower, rightPower;

        // restart imu movement tracking.
        resetAngle();

        // getAngle() returns + when rotating counter clockwise (left) and - when rotating
        // clockwise (right).

        if (degrees < 0) {   // turn right.
            leftPower = -power;
            rightPower = power;
        } else if (degrees > 0) {   // turn left.
            leftPower = power;
            rightPower = -power;
        } else return;

        // set power to rotate.
        leftBack.setPower(leftPower);
        rightBack.setPower(rightPower);
        leftFront.setPower(leftPower);
        rightFront.setPower(rightPower);

        // rotate until turn is completed.
        if (degrees < 0) {
            // On right turn we have to get off zero first.
            while (getAngle() == 0) {
            }

            while (getAngle() > degrees) {
            }
        } else    // left turn.
            while (getAngle() < degrees) {
            }

        // turn the motors off.
        rightBack.setPower(0);
        leftBack.setPower(0);
        rightFront.setPower(0);
        leftFront.setPower(0);
    }
    //end internal gyro code
    public void gStatTurn(double speed, int degrees){
        rotate(degrees,speed);
        //left is + degrees
        //right is - degrees
    }
    public int getLBencoder(){
        return leftBack.getCurrentPosition();
    }
    public int getRBencoder(){
        return rightBack.getCurrentPosition();
    }
    public int getLFencoder() { return leftFront.getCurrentPosition();}
    public int getFFencoder() { return rightFront.getCurrentPosition();}
}
