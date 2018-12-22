package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Blue: Gold Side", group="Pushbot")
//@Disabled
public class BlueGoldAuto extends OpMode{

    private int stateMachineFlow;
    RoverDrive robot       = new RoverDrive();
    CollectSystem sweeper = new CollectSystem();
    LiftSystem lift = new LiftSystem();
    ColorSens color = new ColorSens();

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
                // lower robot from lander and lower arm onto robot
                stateMachineFlow++;
                break;
            case 2:
                robot.linearDrive(-.5,5);
                robot.statTurn(.5,180);
            case 3:
                robot.linearDrive(.5,17);
                // move forward a little bit
                stateMachineFlow++;
                break;
            case 4:
                robot.statTurn(.5,90);
                // turn right towards the last element
                stateMachineFlow++;
                break;
            case 5:
                robot.linearDrive(.5,14);
                // move forward a little bit
                stateMachineFlow++;
                break;
            case 6:
                if (color.rColorSens() == MineralColor.GOLD) {
                    robot.statTurn(.5,45);
                    robot.statTurn(.5,-45);
                    // Knocking the gold block away from the tape
                    robot.linearDrive(-.5,34);
                    stateMachineFlow = 7;
                }
                else if (color.rColorSens() == MineralColor.SILVER) {
                    robot.linearDrive(-.5,14.5);
                    stateMachineFlow++;
                }
                break;
                // move backwards and test the color of the element
            case 7:
                if (color.rColorSens() == MineralColor.GOLD) {
                    robot.statTurn(.5,-45);
                    robot.statTurn(.5,45);
                    // Knocking the gold block away from the tape
                    robot.linearDrive(-.5,19.5);
                    stateMachineFlow++;
                }
                else if (color.rColorSens() == MineralColor.SILVER) {
                    robot.linearDrive(-.5,14.5);
                    robot.statTurn(.5,-45);
                    robot.statTurn(.5,45);
                    robot.linearDrive(-.5,5);
                    // Knocking the gold block away from the tape
                    stateMachineFlow++;
                }
                break;
            case 8:
                robot.statTurn(.5,-45);
             // turn towards the blue depot
                stateMachineFlow++;
                break;
            case 9:
                robot.linearDrive(.5,20);
                //move up to the blue depot
                stateMachineFlow++;
                break;
            case 10:
                robot.statTurn(.5,180);
                //turn around
                stateMachineFlow++;
                break;
            case 11:
                lift.armPos(ArmPosition.TOP);
                lift.armPos(ArmPosition.BOTTOM);
                // Put team marker into blue depot
                stateMachineFlow++;
                break;
            case 12:
                robot.statTurn(.5,-90);
               // turn left toward the crater
                stateMachineFlow++;
                break;
            case 13:
                robot.linearDrive(.5,91);
                // move forward until you are partially parked in the crater
                stateMachineFlow++;
                break;

        }
    }
}//end of class

