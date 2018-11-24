package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class CollectSystem
{
    public DcMotor intake   = null;

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
    //Control Drive
    public void controlDrive (double speed){
        intake.setPower(speed);
    }
}//end of class
