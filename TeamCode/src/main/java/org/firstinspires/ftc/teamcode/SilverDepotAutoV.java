package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Silver: Depot View", group="Vuforia")
//@Disabled
public class SilverDepotAutoV extends OpMode{

    private int stateMachineFlow;
    RoverDrive robot       = new RoverDrive();
    LiftSystem lift = new LiftSystem();

    MineralPosition goldPos;
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
                robot.linearDrive(.45,6);
                // move forward through the middle element and into the depot
                stateMachineFlow++;
                break;
            case 3:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.6,-40);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.6,40);
                }
                stateMachineFlow++;
                break;
            case 4:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.45,29);
                }else if (goldPos == MineralPosition.CENTER){
                    robot.linearDrive(.45,21);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.45,29);
                }
                stateMachineFlow++;
                break;
            case 5:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.45,-29);
                }else if (goldPos == MineralPosition.CENTER){
                    robot.linearDrive(.45,-7);
                    stateMachineFlow = 8;
                    break;
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.45,-29);
                }
                stateMachineFlow++;
                break;
            case 6:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.6,40);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.6,-40);
                }
                stateMachineFlow++;
                break;
            case 7:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.45,-1);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.45,-1);
                }
                stateMachineFlow++;
                break;
            case 8:
                robot.gStatTurn(.6,90);
                stateMachineFlow++;
                break;
            case 9:
                robot.linearDrive(.45,55);
                // turn right
                stateMachineFlow++;
                break;
            case 10:
                robot.gStatTurn(.6,37);
                // move forward a little bit
                stateMachineFlow++;
                break;
            case 11:
                robot.linearDrive(.45,25);
                // turn so you can start testing the color of the elements
                stateMachineFlow++;
                break;
            case 12:
                lift.armPos(ArmPosition.TOP);
                stateMachineFlow++;
                break;
            case 13:
                lift.armPos(ArmPosition.BOTTOM);
                stateMachineFlow++;
                break;
            case 14:
                robot.linearDrive(.65,-68);
                // move forward to the blue depot
                stateMachineFlow++;
                break;
        }
    }
}//end of class

