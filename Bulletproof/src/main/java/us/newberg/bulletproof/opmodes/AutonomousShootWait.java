
package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import us.newberg.bulletproof.Motor;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.math.Vector2f;

/**
 * Created by thesm_000 on 11/11/2016.
 */
@Autonomous(name = "Shoot Autonomous Wait", group = "Autonomous")
public class AutonomousShootWait extends BulletproofOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();

        while (opModeIsActive()) {

            sleep(15000);

            //forward
            Motors.FrontRight().SetPower(-.5);
            Motors.FrontLeft().SetPower(.5);
            Motors.BackRight().SetPower(-.5);
            Motors.BackLeft().SetPower(.5);
            sleep(950);
            Motors.FrontRight().SetPower(0);
            Motors.FrontLeft().SetPower(0);
            Motors.BackRight().SetPower(0);
            Motors.BackLeft().SetPower(0);

            //shooting 2 balls

            Motors.Flipper().SetPower(-.5);
            sleep(900);
            Motors.Flipper().SetPower(0);
            sleep(250);
            Motors.Flipper().SetPower(.3);
            sleep(1000);
            Motors.Flipper().SetPower(0);
            Motors.Collector().SetPower(.4);
            sleep(1500);
            Motors.Collector().SetPower(0);
            sleep(2000);
            Motors.Flipper().SetPower(-.7);
            sleep(800);
            Motors.Flipper().SetPower(0);
            sleep(250);
            Motors.Flipper().SetPower(.3);
            sleep(850);
            Motors.Flipper().SetPower(0);

            break;
        }
    }
}
