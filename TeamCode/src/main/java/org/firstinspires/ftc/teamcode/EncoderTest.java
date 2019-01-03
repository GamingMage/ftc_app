package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name="Encoder_Test", group="Pushbot")
//@Disabled
public class EncoderTest extends OpMode {

    static final double     COUNTS_PER_MOTOR_REV    = 537.6 ;    // Orbital 20 Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    RoverDrive robot = new RoverDrive();

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        robot.leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addData("Hello","this is a test");
        telemetry.update();
    }
    @Override
    public void loop() {
        telemetry.addData("LB",robot.getLBencoder());
        telemetry.addData("RB",robot.getRBencoder());
        telemetry.update();
        if (gamepad1.a) {
            int newLBTarget;
            int newRBTarget;

            // Determine new target position, and pass to motor controller
            newLBTarget = robot.leftBack.getCurrentPosition() + (int)(45 * COUNTS_PER_INCH);
            newRBTarget = robot.rightBack.getCurrentPosition() + (int)(45 * COUNTS_PER_INCH);
            robot.leftBack.setTargetPosition(newLBTarget);
            robot.rightBack.setTargetPosition(newRBTarget);

            // Turn On RUN_TO_POSITION
            robot.leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // start motion.
            robot.leftBack.setPower(Math.abs(.35));
            robot.rightBack.setPower(Math.abs(.35));

            while (robot.leftBack.isBusy() && robot.rightBack.isBusy());

            // Stop all motion;
            robot.leftBack.setPower(0);
            robot.rightBack.setPower(0);
        }
    }
}