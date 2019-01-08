package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Color_Test", group="Pushbot")
@Disabled
public class ColorTest extends OpMode {
    ColorSens     color   =  new ColorSens();

    @Override
    public void init() {
        color.init(hardwareMap);
        color.lColorLED(true);
        color.rColorLED(true);
        telemetry.addData("Hello","this is a test");
        telemetry.update();
    }
    @Override
    public void loop() {
        if (color.lColorSens() == MineralColor.GOLD){
            telemetry.addData("lColor","Gold");
        }else if (color.lColorSens() == MineralColor.SILVER){
            telemetry.addData("lColor","Silver");
        }else {
            telemetry.addData("lColor","UNKNOWN");
        }
        /*if (color.rColorSens() == MineralColor.GOLD){
            telemetry.addData("rColor","Gold");
        }else if (color.rColorSens() == MineralColor.SILVER){
            telemetry.addData("rColor","Silver");
        }else {
            telemetry.addData("rColor","UNKNOWN");
        }*/
        telemetry.addData("Red",color.lRedVal());
        telemetry.addData("Green",color.lGreenVal());
        telemetry.addData("Blue",color.lBlueVal());
        telemetry.addData("Luminosity",color.lLuminosity());
        //telemetry.addData("RGB",color.lCombinedColor());
        telemetry.update();
    }
}
