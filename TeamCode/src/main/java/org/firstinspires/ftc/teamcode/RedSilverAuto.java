package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;


@Autonomous(name="Red: Silver Side", group="Pushbot")
//@Disabled
public class RedSilverAuto extends OpMode{

    private int stateMachineFlow;
    RoverDrive robot       = new RoverDrive();
    CollectSystem sweeper = new CollectSystem();
    LiftSystem lift = new LiftSystem();

    double time;
    private ElapsedTime     runtime = new ElapsedTime();

    //VuforiaLocalizer vuforia;
    /*int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
    VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
    VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
    VuforiaTrackable relicTemplate = relicTrackables.get(0);
*/

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

        sweeper.init(hardwareMap);
        telemetry.addData("after sweeper","here");
        telemetry.update();

        telemetry.addData("after init","here");
        telemetry.update();

        //code for gripping glyph and moving arm slightly up
        //gilgearmesh.clawPos(1);
        //wait needed? Also... guessed parameters
        //gilgearmesh.armPos(800,.6);
        stateMachineFlow = 0;

        lift.liftHookOnOff(HookOnOff.HOOK);
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
                // Robot lowers itself from the lander and lowers the arm back onto the robot
                stateMachineFlow++;
                break;
            case 2:
                robot.linearDrive(.5,1);
                // Move forward a little bit so you can turn near the minerals
                stateMachineFlow++;
                break;
            case 3:
                robot.pivotTurn(.5,45,RobotDirection.RIGHT);
                //turn right away from lander so you can sense the first block
                stateMachineFlow++;
                break;
            case 4:
                robot.linearDrive(.5,1);
                //move towards the minerals
                stateMachineFlow++;
                break;
            case 5:
                robot.pivotTurn(.5,90,RobotDirection.LEFT);
                //turn towards the wall closest to the red depot so you can start moving forward and sensing the minerals
                stateMachineFlow++;
                break;
            case 6:
               robot.linearDrive(.5,1);
                // Test color of element while moving forward
                stateMachineFlow++;
                break;
            case 7:
                // Robot stops when it senses the gold block
                stateMachineFlow++;
                break;
            case 8:
             robot.pivotTurn(.5,60,RobotDirection.RIGHT);
             robot.statTurn(.5,-60);
                // Knocking the gold block away from the tape; negative degrees in the static turn mean turning left
                stateMachineFlow++;
                break;
            case 9:
                robot.pivotTurn(.5,90,RobotDirection.LEFT);
                //turn towards the red depot
                stateMachineFlow++;
                break;
            case 10:
                robot.linearDrive(.5,1);
                // move until you are in front of the red depot
                stateMachineFlow++;
                break;
            case 11:
                // Put the team marker in the red depot
                stateMachineFlow++;
                break;
            case 12:
               robot.statTurn(.5,180);
               // Turn around towards the crater
                stateMachineFlow++;
                break;
            case 13:
                robot.linearDrive(.5,1);
                // move forward until you are partially parked in the crater
                stateMachineFlow++;
                break;

        }
    }
}//end of class
