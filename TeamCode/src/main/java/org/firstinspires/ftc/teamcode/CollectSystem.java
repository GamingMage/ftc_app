package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class CollectSystem
{
    public DcMotor intake    = null;
    //public DcMotor rakeArm   = null;

    //public Servo   rakeWrist = null;

    public static final int ARM_OUT = 1000;
    public static final int ARM_IN = 0;

    public static final double WRIST_UP = 0;
    public static final double WRIST_DOWN = 1.5;

    HardwareMap hwMap           =  null;

    public CollectSystem(){
    }

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        intake  = hwMap.get(DcMotor.class, "Sweeper");
        //Directions subject to change when motor facing is identified
        intake.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        intake.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        intake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    //Control Drive for sweeper
    public void controlDrive (double speed){
        intake.setPower(speed);
    }
}//end of class
