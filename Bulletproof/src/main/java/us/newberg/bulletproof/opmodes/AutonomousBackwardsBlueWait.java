
package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import us.newberg.bulletproof.Motor;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.math.Vector2f;

/**
 * Created by thesm_000 on 11/11/2016.
 */
@Autonomous(name = "Backwards Autonomous Blue Wait", group = "Autonomous")
public class AutonomousBackwardsBlueWait extends BulletproofOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();

        while (opModeIsActive()) {
            sleep(10000);

            DriveBackwards(1200);
            DriveStop();

            TurnCounterClockWise(1100);
            DriveStop();

            Shoot(-.5);

            Collector(1550);

            Shoot(-.7);

            TurnCounterClockWise(1150);
            DriveStop();

            DriveBackwards(1550);
            DriveStop();

            TurnClockWise(650);
            DriveStop();

            TurnCounterClockWise(650);
            DriveStop();

            DriveBackwards(1750); //have if not doing beacon
            DriveStop();
//
//            DriveLeft(1900);
//            DriveStop();
//
//            //go backward, search for white line
//            if (lightSensor(LightSensor)) {
//                DriveStop();
//                return;
//            }
//            sleep(2000);
//
//            TurnCounterClockWise(1050);
//
//            double ultraSonicValue = ultrasonicSensor.getUltrasonicLevel();
//
//            while (ultraSonicValue > 12) {
//                telemetry.addData("ultra Sonic Value",ultraSonicValue);
//                ultraSonicValue = ultrasonicSensor.getUltrasonicLevel();
//                //right
//                DriveRight(0);
//                telemetry.update();
//            }
//
//            DriveStop();
//
//            sleep(1000);
//
//            //detect color
//            colorSensor();
//
//            sleep(250);
//            DriveRight(500);
//            DriveStop();
//            DriveLeft(500);
//            DriveStop();
            break;
        }
    }
}

