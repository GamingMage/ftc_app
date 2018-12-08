package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Blue: Silver Side", group="Pushbot")
//@Disabled
public class BlueSilverAuto extends OpMode{

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
                // drop the robot from lander and lower arm to robot
                stateMachineFlow++;
                break;
            case 2:
                robot.linearDrive(.5,1);
                // move forward a little bit
                stateMachineFlow++;
                break;
            case 3:
                robot.statTurn(.5,60);
                // turn right
                stateMachineFlow++;
                break;
            case 4:
                robot.linearDrive(.5,1);
                // move forward a little bit
                stateMachineFlow++;
                break;
            case 5:
                robot.linearDrive(.5,1);
                // turn left so you can start testing the color of the elements
                stateMachineFlow++;
                break;
            case 6:
                robot.linearDrive(.5,1);
                // Test color of element as you move forward
                stateMachineFlow++;
                break;
            case 7:
                // stop when the gold block is detected
                stateMachineFlow++;
                break;
            case 8:
                robot.statTurn(.5,60);
                robot.statTurn(.5,-60);
                // Move the gold block away from the silver
                stateMachineFlow++;
                break;
            case 9:
                robot.statTurn(.5,-45);
                // turn left towards the blue depot
                stateMachineFlow++;
                break;
            case 10:
               robot.linearDrive(.5,1);
               // move forward to the blue depot
                stateMachineFlow++;
                break;
            case 11:
                // Put team marker into blue depot
                stateMachineFlow++;
                break;
            case 12:
              robot.statTurn(.5,270);
              //turn around towards the crater
                stateMachineFlow++;
                break;
            case 13:
                robot.linearDrive(.5,1);
                //move forward into the crater
                stateMachineFlow++;
                break;

        }
    }
}//end of class

