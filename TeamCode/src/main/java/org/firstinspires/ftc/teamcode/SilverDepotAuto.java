package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

@Autonomous(name="Silver: Depot", group="Pushbot")
@Disabled
public class SilverDepotAuto extends OpMode{

    private int stateMachineFlow;
    RoverDrive robot       = new RoverDrive();
    LiftSystem lift = new LiftSystem();


    double time;
    private ElapsedTime     runtime = new ElapsedTime();

    @Override
    public void init() {
        telemetry.addData("before init","here");
        telemetry.update();
        robot.init(hardwareMap);
        telemetry.addData("after robot","here");
        telemetry.update();
        lift.init(hardwareMap);
        telemetry.addData("after lift","here");
        telemetry.update();

        msStuckDetectInit = 10000;

        telemetry.addData("after init","here");
        telemetry.update();

        stateMachineFlow = 0;
        //lift.hookServo.setPosition(lift.HOOK_ON);
        lift.liftControl(.65,LiftDirection.DOWN);
        //lift.liftHookOnOff(HookOnOff.HOOK);
        lift.liftMotor.setPower(-.2);//hold robot on lander
        telemetry.addData("after hook on to lander", 0);
        telemetry.addData("Case",stateMachineFlow);
        telemetry.update();
    }

    /*@Override
    public void init_loop(){


        telemetry.addData("Arm Pos",gilgearmesh.getArmPosition());
        telemetry.addData("Color",jewelColor);
        telemetry.addData("Case",stateMachineFlow);
        telemetry.update();}*/

    @Override
    public void loop() {
        switch(stateMachineFlow){
            case 0:
                runtime.reset();

                telemetry.addData("Case",stateMachineFlow);
                telemetry.update();
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 1:
                lift.liftHookOnOff(HookOnOff.DROP);
                // drop the robot from lander and lower arm to robot
                stateMachineFlow++;
                break;
            case 2:
                robot.linearDrive(.45,27);
                stateMachineFlow++;
                break;
            case 3:
                robot.linearDrive(.45,-7);
                stateMachineFlow++;
                break;
            case 4:
                robot.gStatTurn(.6,90);
                stateMachineFlow++;
                break;
            case 5:
                robot.linearDrive(.45,55);
                // turn right
                stateMachineFlow++;
                break;
            case 6:
                robot.gStatTurn(.6,37);
                // move forward a little bit
                stateMachineFlow++;
                break;
            case 7:
                robot.linearDrive(.45,25);
                // turn so you can start testing the color of the elements
                stateMachineFlow++;
                break;
            case 8:
                lift.armPos(ArmPosition.TOP);
                stateMachineFlow++;
                break;
            case 9:
                lift.armPos(ArmPosition.BOTTOM);
                stateMachineFlow++;
                break;
            case 10:
               robot.linearDrive(.65,-68);
               // move forward to the blue depot
                stateMachineFlow++;
                break;
        }
    }
}//end of class

