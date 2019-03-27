package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Gold: No Crater View", group="Vuforia")
//@Disabled
public class GoldNoCraterAutoV extends OpMode{

    private int stateMachineFlow;
    RoverDrive robot      = new RoverDrive();
    LiftSystem lift       = new LiftSystem();
    MineralTFOD view      = new MineralTFOD();

    MineralPosition goldPos = MineralPosition.UNKNOWN;
    double time;
    int initView = 0;
    private ElapsedTime     runtime = new ElapsedTime();

    @Override
    public void init() {
        msStuckDetectInit = 11500;

        telemetry.log().add("before init");
        robot.init(hardwareMap);
        telemetry.log().add("after robot");
        lift.init(hardwareMap);
        telemetry.log().add("after lift");
       // view.init(hardwareMap);
       // telemetry.log().add("after Vuforia");

        telemetry.log().add("after hardware init");

        stateMachineFlow = 0;
        lift.liftControl(.85,LiftDirection.DOWN);
        lift.liftMotor.setPower(-.2);//hold robot on lander
        telemetry.log().add("after hook on to lander");
        telemetry.log().add("Case",stateMachineFlow);
    }

    @Override
    public void init_loop(){
        if (initView == 0){
            view.init(hardwareMap);
            telemetry.log().add("after Vuforia");
            initView = 4;
        }
    }

    @Override
    public void loop() {
        switch(stateMachineFlow){
            case 0:
                runtime.reset();
              //  view.init(hardwareMap);
                //telemetry.log().add("after Vuforia");
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
                robot.linearDrive(.6,-9.5);
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 3:
                robot.gStatTurn(.65,85);
                stateMachineFlow++;
                break;
            case 4:
                robot.linearDrive(.55,3);
                stateMachineFlow++;
                break;
            case 5:
                robot.gStatTurn(.65,84);
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 6:
                if (getRuntime() - time > 1.25) {
                    goldPos = view.MineralRecog();
                    telemetry.addData("GoldPos",goldPos);
                    telemetry.update();
                    stateMachineFlow++;
                }
                break;
            case 7:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.65,35);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.65,-32);
                }
                stateMachineFlow++;
                break;
            case 8:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.55,38);
                }else if (goldPos == MineralPosition.CENTER || goldPos == MineralPosition.UNKNOWN){
                    robot.linearDrive(.55,38.5);
                    stateMachineFlow = 11;
                    break;
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.55,38);
                }
                stateMachineFlow++;
                break;
            case 9:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.65,-80);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.65,82);
                }
                stateMachineFlow++;
                break;
            case 10:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.55,17);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.55,17);
                }
                stateMachineFlow++;
                break;
            case 11:
                lift.armPosMark(ArmPosition.TOP);
                // dump the marker into depot
                stateMachineFlow++;
                break;
            case 12:
                lift.armPosMark(ArmPosition.BOTTOM);
                //lower the arm
                stateMachineFlow++;
                break;
            case 13:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.6,-17);
                }else if (goldPos == MineralPosition.CENTER || goldPos == MineralPosition.UNKNOWN){
                    robot.linearDrive(.6,-40);
                    stateMachineFlow = 17;
                    break;
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.6,-17);
                }
                stateMachineFlow++;
                break;
            case 14:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.6,80);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.6,-82);
                }
                stateMachineFlow++;
                break;
            case 15:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.6,-38);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.6,-38);
                }
                stateMachineFlow++;
                break;
            case 16:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.6,-35);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.6,32);
                }
                stateMachineFlow++;
                break;
        }
    }
}//end of class