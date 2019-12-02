package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lift {

    //This class wil contain the lift motor
    //Methods will include an up/down move, and maybe others

    public DcMotor liftDrive = null;

    HardwareMap hwMap        = null;

    DigitalChannel REVTouchBottom;

    public Lift(){
    }
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        // Define and Initialize Motors
        liftDrive  = hwMap.get(DcMotor.class, "lift_drive");

        //Directions subject to change when motor facing is identified
        liftDrive.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        liftDrive.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        liftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed sensors
        REVTouchBottom = hwMap.get(DigitalChannel.class, "Bottom_Touch");

        // set the digital channel to input.
        REVTouchBottom.setMode(DigitalChannel.Mode.INPUT);
    }
    //send the lift down to its lowest height
    public void liftDown (double power) {
        // if the digital channel returns true it's HIGH and the button is unpressed.
        if (REVTouchBottom.getState()) {
            liftDrive.setPower(-power);//May need to be fixed for direction
            while (REVTouchBottom.getState());
            liftDrive.setPower(0);
        }
    }
}
