package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ColorSens {

    ColorSensor rightColorSensor;
    ColorSensor leftColorSensor;

    int red;
    int green;
    int blue;

    HardwareMap hwMap           = null;

    public ColorSens(){

    }

    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;

        rightColorSensor = hwMap.get(ColorSensor.class,"right_color");
        leftColorSensor  = hwMap.get(ColorSensor.class,"left_color");

        rColorLED(false);
        lColorLED(false);
    }
    public void rColorLED(boolean onOff){
        if (onOff == true){rightColorSensor.enableLed(true);
        }else if (onOff == false){rightColorSensor.enableLed(false);}
    }
    public void lColorLED(boolean onOff){
        if (onOff == true){leftColorSensor.enableLed(true);
        }else if (onOff == false){leftColorSensor.enableLed(false);}
    }
    public MineralColor rColorSens() {
        rightColorSensor.enableLed(true);
        /*red   = rightColorSensor.red();
        green = rightColorSensor.green();
        blue  = rightColorSensor.blue();*/
        if (rightColorSensor.alpha() >= 29) {//white's luminosity is about 29-33 in our practice location
            rightColorSensor.enableLed(false);
            return MineralColor.SILVER;
        }else if (rightColorSensor.alpha() <= 28 || rightColorSensor.alpha() > 24) {//gold's luminosity value is lower than 28
            rightColorSensor.enableLed(false);
            return MineralColor.GOLD;
        }else {
            rightColorSensor.enableLed(false);
            return MineralColor.UNKNOWN;
        }
    }
    public MineralColor lColorSens() {
        leftColorSensor.enableLed(true);
        /*red   = leftColorSensor.red();
        green = leftColorSensor.green();
        blue  = leftColorSensor.blue();*/
        if (leftColorSensor.alpha() >= 29) {
            leftColorSensor.enableLed(false);
            return MineralColor.SILVER;
        }else if (leftColorSensor.alpha() <= 28 || leftColorSensor.alpha() > 24) {
            leftColorSensor.enableLed(false);
            return MineralColor.GOLD;
        }else {
            leftColorSensor.enableLed(false);
            return MineralColor.UNKNOWN;
        }
    }

    public int rRedVal () {return rightColorSensor.red();}
    public int rGreenVal () {return rightColorSensor.green();}
    public int rBlueVal () {return rightColorSensor.blue();}
    public int rLuminosity () {return rightColorSensor.alpha();}
    public int rCombinedColor () {return rightColorSensor.argb();}

    public int lRedVal () {return leftColorSensor.red();}
    public int lGreenVal () {return  leftColorSensor.green();}
    public int lBlueVal () {return leftColorSensor.blue();}
    public int lLuminosity () {return leftColorSensor.alpha();}
    public int lCombinedColor () {return leftColorSensor.argb();}
}
