package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;


@Autonomous(name="Silver: No Depot", group="Pushbot")
//@Disabled
public class SilverNoDepotAuto extends OpMode{

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
                robot.linearDrive(.45,37);
                stateMachineFlow++;
                break;
            case 3:
                //robot.linearDrive(.45,-23);
                stateMachineFlow++;
                break;
        }
    }
}//end of class

