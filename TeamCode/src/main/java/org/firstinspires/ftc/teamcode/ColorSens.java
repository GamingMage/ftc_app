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
        red   = rightColorSensor.red();
        green = rightColorSensor.green();
        blue  = rightColorSensor.blue();
        if (rightColorSensor.blue() > 200) {//white's RGB value has 255 in each category
            rightColorSensor.enableLed(false);
            return MineralColor.SILVER;
        }else if (rightColorSensor.blue() < 50) {//gold's RGB value has a 0 in blue
            rightColorSensor.enableLed(false);
            return MineralColor.GOLD;
        }else {
            rightColorSensor.enableLed(false);
            return MineralColor.UNKNOWN;
        }
    }
    public MineralColor lColorSens() {
        leftColorSensor.enableLed(true);
        red   = leftColorSensor.red();
        green = leftColorSensor.green();
        blue  = leftColorSensor.blue();
        if (leftColorSensor.blue() > 200) {
            leftColorSensor.enableLed(false);
            return MineralColor.SILVER;
        }else if (leftColorSensor.blue() < 50) {
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

    public int lRedVal () {return leftColorSensor.red();}
    public int lGreenVal () {return  leftColorSensor.green();}
    public int lBlueVal () {return leftColorSensor.blue();}
}
