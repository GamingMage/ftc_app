package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class RoverDriveOld
{
    /* Public OpMode members. */
    public DcMotor  leftBack    = null;
    public DcMotor  rightBack   = null;

    static final double     COUNTS_PER_MOTOR_REV    = 537.6 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.7;
    static final double     TURN_SPEED              = 0.65;

    //These are guesses
    public static final double MID_RAD = 7.8125;
    public static final double OUT_RAD = 9.85;

    //public static final double ARM_UP_POWER    =  0.45 ;
    //public static final double ARM_DOWN_POWER  = -0.45 ;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public RoverDriveOld(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        leftBack  = hwMap.get(DcMotor.class, "left_back");
        rightBack = hwMap.get(DcMotor.class, "right_back");
        //We have AndyMark motors, but our direction of drive is opposite of what is suggested in the below comments
        leftBack.setDirection(DcMotor.Direction.REVERSE); // Set to REVERSE if using AndyMark motors
        rightBack.setDirection(DcMotor.Direction.FORWARD);// Set to FORWARD if using AndyMark motors

        // Set all motors to zero power
        leftBack.setPower(0);
        rightBack.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    //encoder drive method
    void encoderDrive(double speed,
                             double lBDis, double rBDis) {
        int newLBTarget;
        int newRBTarget;

        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Determine new target position, and pass to motor controller
        newLBTarget = leftBack.getCurrentPosition() + (int)(lBDis * COUNTS_PER_INCH);
        newRBTarget = rightBack.getCurrentPosition() + (int)(rBDis * COUNTS_PER_INCH);
        leftBack.setTargetPosition(newLBTarget);
        rightBack.setTargetPosition(newRBTarget);

        // Turn On RUN_TO_POSITION
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // start motion.
        leftBack.setPower(Math.abs(speed));
        rightBack.setPower(Math.abs(speed));

        while (leftBack.isBusy() && rightBack.isBusy());

        // keep looping while we are still active, and there is time left, and both motors are running.
        /* while (opModeIsActive() &&
        (runtime.seconds() < timeoutS) &&
                (leftMid.isBusy() && rightMid.isBusy() && leftBack.isBusy() && rightBack.isBusy())) {

            // Display it for the driver.
            telemetry.addData("Path1",  "Running to %7d :%7d", newLeftTarget,  newRightTarget);
            telemetry.addData("Path2",  "Running at %7d :%7d",
                    robot.leftDrive.getCurrentPosition(),
                    robot.rightDrive.getCurrentPosition());
            telemetry.update();
        }*/

        // Stop all motion;
        leftBack.setPower(0);
        rightBack.setPower(0);

        // Turn off RUN_TO_POSITION
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    } //end of encoder drive method
    public void linearDrive (double speed, double distance){
        encoderDrive(speed, distance,distance);
    }
    public void statTurn(double speed, double degrees){ //positive degree for right, negative to left
        double midArc = 2 * 3.14 * MID_RAD * (degrees/360);
        encoderDrive(speed, midArc, -midArc);
    }
    public void pivotTurn(double speed, double degrees, RobotDirection direction){
        if (direction == RobotDirection.RIGHT){
            double midArc = 2 * 3.14 * MID_RAD * (degrees/360);
            encoderDrive(speed, midArc, 0);
        }else if (direction == RobotDirection.LEFT){
            double midArc = 2 * 3.14 * MID_RAD * (degrees/360);
            encoderDrive(speed, 0, midArc);
        }
    }
    public void controlDrive(double left, double right){
        leftBack.setPower(left);
        rightBack.setPower(right);
    }
    public int getLBencoder(){
        return leftBack.getCurrentPosition();
    }
    public int getRBencoder(){
        return rightBack.getCurrentPosition();
    }
 }

