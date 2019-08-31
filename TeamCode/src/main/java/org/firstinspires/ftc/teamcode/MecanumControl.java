package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="Mecanum_Control", group="Test")

public class MecanumControl extends OpMode
{

    MecanumDrive robot = new MecanumDrive();

    @Override
    public void init() {
        robot.init(hardwareMap);

        msStuckDetectLoop = 8000;

        telemetry.addData("Hello","It's time");
        telemetry.addData("Loop_Timeout",msStuckDetectLoop);
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("RSx",gamepad1.right_stick_x);
        telemetry.addData("LSy",gamepad1.left_stick_y);
        telemetry.addData("LSx",gamepad1.left_stick_x);
        telemetry.update();

        //Set everything to zero when neither stick is in use
        if (gamepad1.left_stick_y==0 && gamepad1.left_stick_x==0 && gamepad1.right_stick_x==0){
            robot.rightBack.setPower(0);
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftBack.setPower(0);
        }

        //Control of left and right movement
        if (gamepad1.left_stick_x!=0 && gamepad1.left_stick_y==0){
            robot.rightBack.setPower(gamepad1.left_stick_x);
            robot.leftFront.setPower(gamepad1.left_stick_x);
            robot.rightFront.setPower(-gamepad1.left_stick_x);
            robot.leftBack.setPower(-gamepad1.left_stick_x);
        }

        //Control of forward and backward movement (Kylie)
        if (gamepad1.left_stick_x==0 && gamepad1.left_stick_y!=0) {
            robot.rightBack.setPower(-gamepad1.left_stick_y);
            robot.leftBack.setPower(-gamepad1.left_stick_y);
            robot.rightFront.setPower(-gamepad1.left_stick_y);
            robot.leftFront.setPower(-gamepad1.left_stick_y);
        }

        //Control of diagonal movement (S!am)
        //Quadrant i
        if (gamepad1.left_stick_x>0 && gamepad1.left_stick_y<0){
            robot.leftFront.setPower(gamepad1.left_stick_x);
            robot.rightBack.setPower(gamepad1.left_stick_x);
            robot.leftBack.setPower(0);
            robot.rightFront.setPower(0);
        }
        //Quadrant ii
        if (gamepad1.left_stick_x<0 && gamepad1.left_stick_y<0){
            robot.leftFront.setPower(0);
            robot.rightBack.setPower(0);
            robot.leftBack.setPower(-gamepad1.left_stick_y);
            robot.rightFront.setPower(-gamepad1.left_stick_y);
        }
        //Quadrant iii
        if (gamepad1.left_stick_x<0 && gamepad1.left_stick_y>0){
            robot.leftFront.setPower(gamepad1.left_stick_x);
            robot.rightBack.setPower(gamepad1.left_stick_x);
            robot.leftBack.setPower(0);
            robot.rightFront.setPower(0);
        }
        //Quadrant iv
        if (gamepad1.left_stick_x>0 && gamepad1.left_stick_y>0){
            robot.leftFront.setPower(0);
            robot.rightBack.setPower(0);
            robot.leftBack.setPower(-gamepad1.left_stick_y);
            robot.rightFront.setPower(-gamepad1.left_stick_y);
        }

        //Control of left and right turning (Perspective shift) (Kevin)
        if (gamepad1.right_stick_x!=0){
            robot.rightBack.setPower(-gamepad1.right_stick_x);
            robot.leftFront.setPower(gamepad1.right_stick_x);
            robot.rightFront.setPower(-gamepad1.right_stick_x);
            robot.leftBack.setPower(gamepad1.right_stick_x);
        }
    }
}
