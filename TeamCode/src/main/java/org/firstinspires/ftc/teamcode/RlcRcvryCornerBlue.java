package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * This will be our Autonomous and our first try at a state machine (comment one)
 * Created by Joseph Liang on 10/30/2017.
 */

@Autonomous(name="Relic Recovery: Blue Corner", group="Pushbot")
//@Disabled
public class RlcRcvryCornerBlue extends OpMode{

    private int stateMachineFlow;
    RelicDrive robot       = new RelicDrive();
    private ElapsedTime     runtime = new ElapsedTime();

    RelicRecoveryVuMark glyph;
    GlyphArm gilgearmesh = new GlyphArm();

    JewelSystem sensArm = new JewelSystem();
    String jewelColor;

    VuforiaLocalizer vuforia;
    int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
    VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
    VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
    VuforiaTrackable relicTemplate = relicTrackables.get(0);

    @Override
    public void init() {
        robot.init(hardwareMap);
        gilgearmesh.init(hardwareMap);
        //sensArm.init(hardwareMap);
        parameters.vuforiaLicenseKey = "Ab47Iov/////AAAAGVSivzkE2UEEoiMKAm72knw+f69pC3+FWtnwmp26yNKLBnQ7o48HaEaAIbAMmi4KE/YqAOa1hWE6uV+U5eOZyTSDhJOQQqMhHKtFymevtYLWk+CsXyFA4ipONM9Yfi06TN3sAJUDqqm3sWR8pWgTAvs2M/VoRDw9ZNwg1MzxZPmU5VVmr9ifsv0rGbcoE585jWH+jzTnnnxnRN+3i/AoE1nTthvv9KIq6ZSNpgR2hguJUcBv8B43gg122D0akqbG+pAIGp78TiMn5BZqciaHRSzvZV2JOcIMZzk5FPp96rn7sWhyHZMI5mpUpgA25CG8gTC8e+8NoxMyN277hid7VFubrb4VbsH5qUxDzfDCcmOV";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        //code for gripping glyph and moving arm slightly up
        gilgearmesh.clawPos(0);
        //wait needed? Also... guessed parameters
        gilgearmesh.armPos(2, 1);
        stateMachineFlow = 0;
        relicTrackables.activate();
    }


    @Override
    public void loop() {
        switch(stateMachineFlow){
            case 0:
                runtime.reset();
                stateMachineFlow++;
                break;
            case 1:
                RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.UNKNOWN;
                while (vuMark == RelicRecoveryVuMark.UNKNOWN) {
                    vuMark = RelicRecoveryVuMark.from(relicTemplate);
                    //viewforia stuff goes here
                    if (vuMark == RelicRecoveryVuMark.CENTER){glyph = RelicRecoveryVuMark.CENTER;}
                    else if (vuMark == RelicRecoveryVuMark.LEFT){glyph = RelicRecoveryVuMark.LEFT;}
                    else if (vuMark == RelicRecoveryVuMark.RIGHT){glyph = RelicRecoveryVuMark.RIGHT;}
                }
                stateMachineFlow++;
                break;
            case 2://look at this
                sensArm.armPos(.444);
                if (sensArm.colorSens() == "blue"){
                    jewelColor = "blue";}
                else if (sensArm.colorSens() == "red"){
                    jewelColor = "red";}
                stateMachineFlow++;
                break;
            case 3:
                //knock off correct jewel
                if (jewelColor == "blue"){robot.statTurn(.5,8);
                    robot.statTurn(.5,-8);}
                else if (jewelColor == "red"){robot.statTurn(.5,-8);
                    robot.statTurn(.5,8);}
                //move arm up
                sensArm.armPos(1);
                stateMachineFlow++;
                break;
            case 4:
                //move off balancing stone and move towards box
                if (glyph == RelicRecoveryVuMark.LEFT) {
                    robot.statTurn(.7,180);//face direction of box
                    robot.linearDrive(1,20); //in position to place glyph
                    robot.statTurn(.7,-90);//face box
                }
                else if (glyph == RelicRecoveryVuMark.CENTER) {
                    robot.statTurn(.7,180); //turn to face box
                    robot.linearDrive(1,27.4); //drive to middle of box
                    robot.statTurn(.7,-90); //turn to face box
                }
                else if (glyph == RelicRecoveryVuMark.RIGHT) {
                    robot.statTurn(.7,180);//face box
                    robot.linearDrive(1,35);//move to box
                    robot.statTurn(.7,-90);//face box
                }
            stateMachineFlow++;
                break;
            case 5:
                //not sure if the move needs to before or after we let go of glyph
                gilgearmesh.clawPos(1);
                robot.linearDrive(1,-3.5);
                gilgearmesh.clawPos(0);
                robot.linearDrive(1,6);

                stateMachineFlow++;
                break;
            case 6:
                //end?
                break;
        }
    }
}//end of class

