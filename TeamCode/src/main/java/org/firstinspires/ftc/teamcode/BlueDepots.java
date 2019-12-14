package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
@Disabled
@Autonomous(name="Blue: Depots", group="Test")
public class BlueDepots extends OpMode{

    private int stateMachineFlow;
    MecanumDrive robot = new MecanumDrive();
    Intake intake = new Intake();
    Lift lift = new Lift();
    Placing placing = new Placing();

    double time;
    int initView = 0;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        msStuckDetectInit = 11500;

        robot.init(hardwareMap);
        intake.init(hardwareMap);
        lift.init(hardwareMap);
        placing.init(hardwareMap);

        stateMachineFlow = 0;
    }

//
    @Override
    public void loop() {
        switch (stateMachineFlow){
            case 0:
                runtime.reset();
                time = getRuntime();
                stateMachineFlow++;
                break;
            case 1:
                //Move forward
                stateMachineFlow++;
                break;
            case 2:
                //Move right until vuforia identifies a skystone
                stateMachineFlow++;
                break;
            case 3:
                //Move forward and grab skystone
                stateMachineFlow++;
                break;
            case 4:
                //Back up
                stateMachineFlow++;
                break;
            case 5:
                //Move right
                stateMachineFlow++;
                break;
            case 6:
                //Move forward
                stateMachineFlow++;
                break;
            case 7:
                //Place skystone on foundation
                stateMachineFlow++;
                break;
            case 8:
                //Move left all the way to the leftmost wall
                stateMachineFlow++;
                break;
            case 9:
                //Use viewforia to sense a skystone, if not sky stone move right 10 inches
                stateMachineFlow++;
                break;
            case 10:
                //Move forward and grab skystone
                stateMachineFlow++;
                break;
            case 11:
                //Back up
                stateMachineFlow++;
                break;
            case 12:
                //Move right
                stateMachineFlow++;
                break;
            case 13:
                //Move forward
                stateMachineFlow++;
                break;
            case 14:
                //Place skystone on the foundation
                stateMachineFlow++;
                break;
            case 15:
                //Grab foundation and move backwards
                stateMachineFlow++;
                break;
            case 16:
                //Move left to park underneath red alliance bridge
                stateMachineFlow++;
                break;
        }
    }

}



