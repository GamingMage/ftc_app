package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.TouchSensor;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left_drive"
 * Motor channel:  Right drive motor:        "right_drive"
 * Motor channel:  Manipulator drive motor:  "left_arm"
 * Servo channel:  Servo to open left claw:  "left_hand"
 * Servo channel:  Servo to open right claw: "right_hand"
 */
public class LiftSystem
{
    /* Public OpMode members. */
    public DcMotor  liftMotor   = null;
    public DcMotor  armMotor    = null;

    DigitalChannel REVTouch;
    public TouchSensor modernTouch;
    /**
     * The REV Robotics Touch Sensor
     * is treated as a digital channel.  It is HIGH if the button is unpressed.
     * It pulls LOW if the button is pressed.
     *
     * Also, when you connect a REV Robotics Touch Sensor to the digital I/O port on the
     * Expansion Hub using a 4-wire JST cable, the second pin gets connected to the Touch Sensor.
     * The lower (first) pin stays unconnected.*
     */

    public static final int THROW = 2000; //Need to measure
    public static final int DROP = 1500;  //Need to measure
    public static final int HOOK = 0; //Should be fine since we start here

    public static final double ARM_UP_POWER    =  0.45 ;
    public static final double ARM_DOWN_POWER  = -0.45 ;

    /* local OpMode members. */
    HardwareMap hwMap           =  null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public LiftSystem(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        armMotor = hwMap.get(DcMotor.class, "arm_drive");
        armMotor.setDirection(DcMotor.Direction.FORWARD); // Set to REVERSE if using AndyMark motors
        liftMotor = hwMap.get(DcMotor.class, "lift_drive");
        liftMotor.setDirection(DcMotor.Direction.FORWARD); //subject to change
        // Set all motors to zero power
        armMotor.setPower(0);
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        liftMotor.setPower(0);
        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Set all motors to run using encoders if encoders are installed... or else, set to run without
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        armMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        // Define and initialize ALL installed sensors
        REVTouch = hwMap.get(DigitalChannel.class, "REV_Touch");
        modernTouch = hwMap.get(TouchSensor.class, "Modern_Touch");
        // set the digital channel to input.
        REVTouch.setMode(DigitalChannel.Mode.INPUT);
    }
    public void armPos(int pos, double speed){
        double newPos = (pos/100.0*THROW);
        int i = (int) (newPos);
        //if (i > THROW){i = THROW;}
        //if (i < 0){i = 0;}
        armMotor.setTargetPosition(i);
        armMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        armMotor.setPower(Math.abs(speed));
        while (armMotor.isBusy());
        armMotor.setPower(0);
        armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }
    public void liftControl (double power, LiftDirection upOrDown) {
        // if the digital channel returns true it's HIGH and the button is unpressed.
        if (upOrDown == LiftDirection.DOWN && REVTouch.getState() == true) {
            while (REVTouch.getState() == true) {
                liftMotor.setPower(-power);} //May need to be fixed
        } else if (upOrDown == LiftDirection.UP && modernTouch.isPressed() == false) {
            while (modernTouch.isPressed() == false) {
                liftMotor.setPower(power);
            }
        }
    }
    public void liftHookOnOff (double power, HookOnOff hookOnOff) {
        if (hookOnOff == HookOnOff.HOOK) {
            liftMotor.setTargetPosition(HOOK);
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftMotor.setPower(Math.abs(power));
            while (liftMotor.isBusy()) {}
            liftMotor.setPower(0);
            liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        else if (hookOnOff == HookOnOff.DROP) {
            liftMotor.setTargetPosition(DROP);
            liftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            liftMotor.setPower(Math.abs(power));
            while (liftMotor.isBusy()) {}
            liftMotor.setPower(0);
            liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }
    public void armPower (double power){
        armMotor.setPower(power);
    }
    public int getLiftPosition(){
        return liftMotor.getCurrentPosition();
    }
    public int getArmPosition() {
        return armMotor.getCurrentPosition();
    }
 }

