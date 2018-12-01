package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Drive_Test", group="Pushbot")

public class TeleOpTest extends OpMode {

    RoverDrive    robot   =  new RoverDrive();
    CollectSystem sweeper =  new CollectSystem();
    LiftSystem    lift    =  new LiftSystem();

    @Override
    public void init() {
        robot.init(hardwareMap);
        sweeper.init(hardwareMap);
        lift.init(hardwareMap);
        telemetry.addData("Hello","this is a test");
    }
    @Override
    public void loop() {
        //manual control of the drive motors
        robot.controlDrive(gamepad1.left_stick_y,gamepad1.right_stick_y);

        //manual control of the sweeper
        sweeper.controlDrive(gamepad1.right_trigger);
        sweeper.controlDrive(-gamepad1.left_trigger);

        //Setting hook position
        if (gamepad1.dpad_right) {lift.hookSet(HookOnOff.HOOK);}
        if (gamepad1.dpad_left) {lift.hookSet(HookOnOff.DROP);}

        //moving vertical lift manually
        if (gamepad1.dpad_up) {lift.liftControl(.5,LiftDirection.UP);}
        if (gamepad1.dpad_down) {lift.liftControl(.5,LiftDirection.DOWN);}

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
    }
    @Override
    public void stop(){
        robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
