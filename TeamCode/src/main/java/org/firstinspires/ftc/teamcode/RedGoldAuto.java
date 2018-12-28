package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Red: Gold Side", group="Pushbot")
//@Disabled
public class RedGoldAuto extends OpMode{

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
                // lower robot from lander and lower the arm onto the robot
                stateMachineFlow++;
                break;
            case 2:
                robot.linearDrive(-.5,5);
                robot.statTurn(.5,180);
                stateMachineFlow++;
                break;
            case 3:
                robot.linearDrive(.5,58);
                // move forward through the middle element and into the depot
                stateMachineFlow++;
                break;
            case 4:
                robot.statTurn(.5,180);
                // turn around to face out of the depot
                stateMachineFlow++;
                break;
            case 5:
                robot.linearDrive(.5,8);
                // move forward a little bit
                stateMachineFlow++;
                break;
            case 6:
                lift.armPos(ArmPosition.TOP);
                // dump the marker in to depot
                stateMachineFlow++;
                break;
            case 7:
                lift.armPos(ArmPosition.BOTTOM);
                //lower the arm
                stateMachineFlow++;
                break;
            case 8:
                robot.linearDrive(.5,33);
                robot.statTurn(.5,-90);
                // turn towards the crater
                stateMachineFlow++;
                break;
            case 9:
                robot.linearDrive(.5,28);
                //move towards the crater
                stateMachineFlow++;
                break;
            case 10:
                robot.statTurn(.5,90);
                //turn around the side of the lander
                break;
            case 11:
                robot.linearDrive(.5,52);
                // drive towards the other side of the silver side
                stateMachineFlow++;
                break;
            case 12:
                robot.statTurn(.5,-90);
                // turn left toward the crater
                stateMachineFlow++;
                break;
            case 13:
                robot.linearDrive(.5,40);
                // move forward until you are partially parked in the crater
                stateMachineFlow++;
                break;
        }
    }
}//end of class

