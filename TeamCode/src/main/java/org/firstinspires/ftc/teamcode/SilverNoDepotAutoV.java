package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Silver: No Depot View", group="Vuforia")
//@Disabled
public class SilverNoDepotAutoV extends OpMode{

    private int stateMachineFlow;
    RoverDrive robot      = new RoverDrive();
    LiftSystem lift       = new LiftSystem();
    MineralTFOD view      = new MineralTFOD();

    MineralPosition goldPos = MineralPosition.UNKNOWN;
    double time;
    int initView =0;
    private ElapsedTime     runtime = new ElapsedTime();

    @Override
    public void init() {
        msStuckDetectInit = 11500;

        telemetry.log().add("before init");
        robot.init(hardwareMap);
        telemetry.log().add("after robot");
        lift.init(hardwareMap);
        telemetry.log().add("after lift");
        //view.init(hardwareMap);
        //telemetry.log().add("after Vuforia");

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
            telemetry.log().add("After Vuforia");
            initView = 4;
        }

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
                // drop the robot from lander and lower arm to robot
                stateMachineFlow++;
                break;
            case 2:
                robot.linearDrive(.45,-6);
                // move forward through the middle element and into the depot
                stateMachineFlow++;
                break;
            case 3:
                robot.gStatTurn(.6,88);
                stateMachineFlow++;
                break;
            case 4:
                robot.linearDrive(.45,3);
                stateMachineFlow++;
                break;
            case 5:
                robot.gStatTurn(.6,88);
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 6:
                goldPos = view.MineralRecog();
                telemetry.addData("GoldPos",goldPos);
                telemetry.update();
                if (getRuntime() - time > 2) {
                    stateMachineFlow++;
                }
                break;
            case 7:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.6,-30);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.6,30);
                }
                stateMachineFlow++;
                break;
            case 8:
                if (goldPos == MineralPosition.LEFT){
                    robot.linearDrive(.45,30);
                }else if (goldPos == MineralPosition.CENTER || goldPos == MineralPosition.UNKNOWN){
                    robot.linearDrive(.45,34);
                    stateMachineFlow = 10;
                    break;
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.linearDrive(.45,30);
                }
                stateMachineFlow++;
                break;
            case 9:
                if (goldPos == MineralPosition.LEFT){
                    robot.gStatTurn(.6,30);
                }else if (goldPos == MineralPosition.RIGHT){
                    robot.gStatTurn(.6,-30);
                }
                stateMachineFlow++;
                break;
            case 10:
                robot.linearDrive(.45,3);
                stateMachineFlow++;
                break;
        }
    }
}//end of class

