package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Sensor_Test", group="Pushbot")
@Disabled
public class SensorTest extends OpMode {

    LiftSystem    lift    =  new LiftSystem();
    ColorSens     color   =  new ColorSens();

    @Override
    public void init() {
        lift.init(hardwareMap);
        color.init(hardwareMap);
        lift.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addData("Hello","this is a test");
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("armPos",lift.getArmPosition());
        telemetry.addData("Blue",color.lBlueVal());
        if (lift.REVTouchTop.getState()) {
            telemetry.addData("TopTouch", "Is not Pressed");
        } else {
            telemetry.addData("TopTouch", "Is Pressed");
        }
       if (lift.REVTouchBottom.getState()) {
            telemetry.addData("BottomTouch", "Is not Pressed");
       } else {
            telemetry.addData("BottomTouch", "Is Pressed");
       }
        telemetry.update();

        //Setting hook position
        if (gamepad2.dpad_right) {lift.hookSet(HookOnOff.HOOK);}
        if (gamepad2.dpad_left) {lift.hookSet(HookOnOff.OFF);}

        //moving vertical lift manually
        if (gamepad1.dpad_right) {lift.liftControl(.5,LiftDirection.UP);}
        if (gamepad1.dpad_left) {lift.liftControl(.5,LiftDirection.DOWN);}

        //Setting robot into hook or drop states using automated code
        if (gamepad2.dpad_up) {
            lift.liftHookOnOff(HookOnOff.HOOK);
        }
        if (gamepad2.dpad_down) {
            lift.liftHookOnOff(HookOnOff.DROP);
        }

        if (gamepad2.y) {
            lift.armPos(ArmPosition.TOP);
        }
        if (gamepad2.a) {
            lift.armPos(ArmPosition.BOTTOM);
        }
        lift.liftMotor.setPower(gamepad2.left_stick_y);
        lift.armPower(gamepad2.right_stick_y);
    }
    @Override
    public void stop(){
        lift.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
