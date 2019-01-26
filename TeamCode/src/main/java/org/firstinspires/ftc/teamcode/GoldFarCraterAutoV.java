package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Gold: Far Crater View", group="Vuforia")
//@Disabled
public class GoldFarCraterAutoV extends OpMode{

    private int stateMachineFlow;
    RoverDrive robot      = new RoverDrive();
    LiftSystem lift       = new LiftSystem();
    MineralTFOD view      = new MineralTFOD();

    MineralPosition goldPos;
    double time;
    private ElapsedTime     runtime = new ElapsedTime();

    @Override
    public void init() {
        telemetry.log().add("before init");
        robot.init(hardwareMap);
        telemetry.log().add("after robot");
        lift.init(hardwareMap);
        telemetry.log().add("after lift");
        view.init(hardwareMap);
        telemetry.log().add("after Vuforia");

        msStuckDetectInit = 11500;

        telemetry.log().add("after hardware init");

        stateMachineFlow = 0;
        lift.liftControl(.65,LiftDirection.DOWN);
        lift.liftMotor.setPower(-.2);//hold robot on lander
        telemetry.log().add("after hook on to lander");
        telemetry.log().add("Case",stateMachineFlow);
    }

    @Override
    public void init_loop(){

    }

    @Override
    public void loop() {
        switch(stateMachineFlow){
            case 0:
                runtime.reset();

                telemetry.addData("GoldPos",goldPos);
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
                robot.linearDrive(.45,6);
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 3:
                goldPos = view.MineralRecog();
                telemetry.addData("GoldPos",goldPos);
                telemetry.update();
                stateMachineFlow++;
                break;
            case 4:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.6,30);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.6,-30);
                }
                stateMachineFlow++;
                break;
            case 5:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.45,40);
                }else if (goldPos == MineralPosition.CENTER || goldPos == MineralPosition.UNKNOWN){
                    robot.linearDrive(.45,39);
                    stateMachineFlow = 8;
                    break;
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.45,40);
                }
                stateMachineFlow++;
                break;
            case 6:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.6,-75);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.6,75);
                }
                stateMachineFlow++;
                break;
            case 7:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.45,20);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.45,20);
                }
                stateMachineFlow++;
                break;
            case 8:
                lift.armPos(ArmPosition.TOP);
                // dump the marker into depot
                stateMachineFlow++;
                break;
            case 9:
                lift.armPos(ArmPosition.BOTTOM);
                //lower the arm
                stateMachineFlow++;
                break;
            case 10:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.45,-20);
                }else if (goldPos == MineralPosition.CENTER || goldPos == MineralPosition.UNKNOWN){
                    robot.linearDrive(.45,-24);
                    stateMachineFlow = 15;
                    break;
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.45,-20);
                }
                stateMachineFlow++;
                break;
            case 11:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.6,75);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.6,-75);
                }
                stateMachineFlow++;
                break;
            case 12:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.45,-40);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.45,-40);
                }
                stateMachineFlow++;
                break;
            case 13:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.6,-30);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.6,30);
                }
                stateMachineFlow++;
                break;
            case 14:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.45,14);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.45,14);
                }
                stateMachineFlow++;
                break;
            case 15:
                robot.gStatTurn(.6,90);
            case 16:
                robot.linearDrive(.55,-33);
                //move towards the crater
                stateMachineFlow++;
                break;
            case 17:
                robot.linearDrive(.45,-28);
                //move towards the crater
                stateMachineFlow++;
                break;
            case 18:
                robot.gStatTurn(.6,-89);
                //turn around the side of the lander
                stateMachineFlow++;
                break;
            case 19:
                robot.linearDrive(.5,-78);
                // drive towards the other side of the silver side
                stateMachineFlow++;
                break;
            case 20:
                robot.gStatTurn(.6,130);
                // turn left toward the crater
                stateMachineFlow++;
                break;
            case 21:
                robot.linearDrive(.65,-32);
                // move forward until you are partially parked in the crater
                stateMachineFlow++;
                break;
        }
    }
}//end of class

