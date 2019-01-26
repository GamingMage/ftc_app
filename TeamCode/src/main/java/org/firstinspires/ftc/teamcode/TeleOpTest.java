package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Drive_Test", group="Pushbot")
@Disabled
public class TeleOpTest extends OpMode {

    RoverDrive robot   =  new RoverDrive();
    CollectSystem sweeper =  new CollectSystem();
    LiftSystem    lift    =  new LiftSystem();

    @Override
    public void init() {
        robot.init(hardwareMap);
        sweeper.init(hardwareMap);
        lift.init(hardwareMap);
        robot.leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        lift.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        telemetry.addData("Hello","this is a test");
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("armPos",lift.getArmPosition());
        telemetry.addData("TopTouch", lift.REVTouchTop.getState());
        telemetry.addData("BottomTouch", lift.REVTouchBottom.getState());
        telemetry.update();
        //manual control of the drive motors
        robot.controlDrive(gamepad1.left_stick_y,gamepad1.right_stick_y);

        //manual control of the sweeper
        sweeper.controlDrive(gamepad1.right_trigger);
        sweeper.controlDrive(-gamepad1.left_trigger);

        //Setting hook position
        if (gamepad1.dpad_right) {lift.hookSet(HookOnOff.HOOK);}
        if (gamepad1.dpad_left) {lift.hookSet(HookOnOff.OFF);}

        /*moving vertical lift manually
        if (gamepad2.dpad_right) {lift.liftControl(.5,LiftDirection.UP);}
        if (gamepad2.dpad_left) {lift.liftControl(.5,LiftDirection.DOWN);}

        //Setting robot into hook or drop states using automated code
        if (gamepad2.dpad_up) {
            lift.liftHookOnOff(HookOnOff.HOOK);
        }
        if (gamepad2.dpad_down) {
            lift.liftHookOnOff(HookOnOff.DROP);
        }*/

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
        robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.armMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
