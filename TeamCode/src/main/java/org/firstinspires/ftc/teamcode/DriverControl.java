package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Driver_Control", group="Competition")

public class DriverControl extends OpMode {

    RoverDrive robot   =  new RoverDrive();
    CollectSystem sweeper =  new CollectSystem();
    LiftSystem    lift    =  new LiftSystem();

    int liftToggle = 0;
    int armToggle = 0;

    @Override
    public void init() {
        robot.init(hardwareMap);
        sweeper.init(hardwareMap);
        lift.init(hardwareMap);

        msStuckDetectLoop = 8000;

        lift.liftControl(.5,LiftDirection.DOWN);

        robot.leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addData("Hello","Driver control is key");
        telemetry.addData("Loop_Timeout",msStuckDetectLoop);
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("armToggle",armToggle);
        telemetry.addData("liftToggle",liftToggle);
        telemetry.update();
        //manual control of the drive motors
        robot.controlDrive(gamepad1.left_stick_y,gamepad1.right_stick_y);

        //manual control of the sweeper
        sweeper.controlDrive(gamepad1.right_trigger);
        sweeper.controlDrive(-gamepad1.left_trigger);

        if (gamepad1.dpad_left) {lift.hookSet(HookOnOff.HOOK);}
        if (gamepad1.dpad_right) {lift.hookSet(HookOnOff.OFF);}

        //moving vertical lift manually
        if (gamepad2.dpad_up) {
            robot.rightBack.setPower(0);
            robot.leftBack.setPower(0);
            lift.liftControl(.85,LiftDirection.UP);
        }
        if (gamepad2.dpad_down) {
            robot.rightBack.setPower(0);
            robot.leftBack.setPower(0);
            lift.liftControl(.85,LiftDirection.DOWN);
        }

        //Setting robot into hooked or dropped states using automated code
        if (gamepad1.y && liftToggle==0) {
            robot.rightBack.setPower(0);
            robot.leftBack.setPower(0);
            lift.liftHookOnOff(HookOnOff.HOOK);
            liftToggle = 1;
        }
        if (gamepad1.y && liftToggle==1) {
            robot.rightBack.setPower(0);
            robot.leftBack.setPower(0);
            lift.liftHookOnOff(HookOnOff.DROP);
            liftToggle = 0;
        }
        //code for holding robot in place on lander
        if (gamepad1.dpad_up && liftToggle==1) {
            lift.liftMotor.setPower(-.2);
        }
        if (gamepad1.dpad_down && liftToggle==1) {
            lift.liftMotor.setPower(0);
        }
        //setting the arm into dumping and starting states
        if (gamepad1.x && armToggle==0) {
            robot.rightBack.setPower(0);
            robot.leftBack.setPower(0);
            lift.armPos(ArmPosition.TOP);
            armToggle = 1;
        }
        if (gamepad1.x && armToggle==1) {
            robot.rightBack.setPower(0);
            robot.leftBack.setPower(0);
            lift.armPos(ArmPosition.BOTTOM);
            armToggle = 0;
        }
        //fail-safe for toggle variables just in case
        if (gamepad2.y && liftToggle==0) {liftToggle = 1;}
        if (gamepad2.b && liftToggle==1) {liftToggle = 0;}
        if (gamepad2.x && armToggle==0) {armToggle = 1;}
        if (gamepad2.a && armToggle==1) {armToggle = 0;}

        if ((gamepad2.left_stick_y <0 || gamepad2.left_stick_y >0) && liftToggle == 0){
            lift.liftMotor.setPower(-gamepad2.left_stick_y);
        }
    }
    @Override
    public void stop(){
        robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
