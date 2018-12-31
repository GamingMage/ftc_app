package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Blue: Gold Side", group="Pushbot")
//@Disabled
public class BlueGoldAuto extends OpMode{

    private int stateMachineFlow;
    RoverDriveG robot       = new RoverDriveG();
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

        msStuckDetectInitLoop = 8000;

        telemetry.addData("after init","here");
        telemetry.update();

        stateMachineFlow = 0;

        lift.liftHookOnOff(HookOnOff.HOOK);
        lift.liftMotor.setPower(.15);//hold robot on lander
        telemetry.addData("after hook on to lander", 0);
        telemetry.addData("Case",stateMachineFlow);
        telemetry.update();
    }

    @Override
    public void init_loop(){

    }

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
                robot.linearDrive(.5,5);
                stateMachineFlow++;
                break;
            case 3:
                robot.linearDrive(.5,35);
                // move forward through the middle element and into the depot
                stateMachineFlow++;
                break;
            case 4:
                stateMachineFlow++;
                break;
            case 5:
                robot.linearDrive(.5,-8);
                // move forward a little bit
                stateMachineFlow++;
                break;
            case 6:
                /*if (color.rColorSens() == MineralColor.GOLD) {
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
                break;*/
                // move backwards and test the color of the element
                lift.armPos(ArmPosition.TOP);
                // dump the marker into depot
                stateMachineFlow++;
                break;
            case 7:
                /*if (color.rColorSens() == MineralColor.GOLD) {
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
                }*/
                lift.armPos(ArmPosition.BOTTOM);
                //lower the arm
                stateMachineFlow++;
                break;
            case 8:
                robot.linearDrive(.5,-33);
                robot.gStatTurn(.5,90);
                // turn towards the crater
                stateMachineFlow++;
                break;
            case 9:
                robot.linearDrive(.5,-28);
                //move towards the crater
                stateMachineFlow++;
                break;
            case 10:
                robot.gStatTurn(.5,-90);
                //turn around the side of the lander
                stateMachineFlow++;
                break;
            case 11:
                robot.linearDrive(.5,-52);
                // drive towards the other side of the silver side
                stateMachineFlow++;
                break;
            case 12:
                robot.gStatTurn(.5,-90);
                // turn left toward the crater
                stateMachineFlow++;
                break;
            case 13:
                robot.linearDrive(.5,-40);
                // move forward until you are partially parked in the crater
                stateMachineFlow++;
                break;

        }
    }
}//end of class

