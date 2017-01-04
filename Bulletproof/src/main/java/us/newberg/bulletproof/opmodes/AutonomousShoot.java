
package us.newberg.bulletproof.opmodes;

        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

        import us.newberg.bulletproof.Motor;
        import us.newberg.bulletproof.lib.Motors;
        import us.newberg.bulletproof.math.Vector2f;

/**
 * Created by thesm_000 on 11/11/2016.
 */
@Autonomous(name = "Shoot Autonomous", group = "Autonomous")
public class AutonomousShoot extends BulletproofOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();

        while (opModeIsActive()){
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
            //right
//            Motors.FrontRight().SetPower(.5);
//            Motors.FrontLeft().SetPower(.5);
//            Motors.BackRight().SetPower(-.5);
//            Motors.BackLeft().SetPower(-.5);
//            sleep(450);
//            Motors.FrontRight().SetPower(0);
//            Motors.FrontLeft().SetPower(0);
//            Motors.BackRight().SetPower(0);
//            Motors.BackLeft().SetPower(0);

            //rotate right
//            Motors.FrontRight().SetPower(.5);
//            Motors.FrontLeft().SetPower(.5);
//            Motors.BackRight().SetPower(.5);
//            Motors.BackLeft().SetPower(.5);
//            sleep( 350);
//            Motors.FrontRight().SetPower(0);
//            Motors.FrontLeft().SetPower(0);
//            Motors.BackRight().SetPower(0);
//            Motors.BackLeft().SetPower(0);

            //go foward, search for white line
//            if (lightSensor(LightSensor)) {
//                Motors.BackLeft().SetPower(0);
//                Motors.FrontLeft().SetPower(0);
//                Motors.BackRight().SetPower(0);
//                Motors.FrontRight().SetPower(0);
//
//                return;
//            }
//            sleep(2000);
//
//            //backward
//            Motors.FrontRight().SetPower(.5);
//            Motors.FrontLeft().SetPower(-.5);
//            Motors.BackRight().SetPower(.5);
//            Motors.BackLeft().SetPower(-.5);
//            sleep(500);
//            Motors.FrontRight().SetPower(0);
//            Motors.FrontLeft().SetPower(0);
//            Motors.BackRight().SetPower(0);
//            Motors.BackLeft().SetPower(0);
//            sleep(2000);
//
//            double ultraSonicValue = ultrasonicSensor.getUltrasonicLevel();
//            while (ultraSonicValue > 12) {
//                telemetry.addData("ultra Sonic Value",ultraSonicValue);
//                ultraSonicValue = ultrasonicSensor.getUltrasonicLevel();
//                //right
//                Motors.FrontRight().SetPower(.5);
//                Motors.FrontLeft().SetPower(.5);
//                Motors.BackRight().SetPower(-.5);
//                Motors.BackLeft().SetPower(-.5);
//                telemetry.update();
//            }
//
//            Motors.FrontRight().SetPower(0);
//            Motors.FrontLeft().SetPower(0);
//            Motors.BackRight().SetPower(0);
//            Motors.BackLeft().SetPower(0);
//
//            sleep(1000);
//
//
            //right
//            Motors.FrontRight().SetPower(.5);
//            Motors.FrontLeft().SetPower(.5);
//            Motors.BackRight().SetPower(-.5);
//            Motors.BackLeft().SetPower(-.5);
//            sleep(250);
//            Motors.FrontRight().SetPower(0);
//            Motors.FrontLeft().SetPower(0);
//            Motors.BackRight().SetPower(0);
//            Motors.BackLeft().SetPower(0);
//            sleep(2000);

            //detect color
//            colorSensor();

//            Motors.FrontRight().SetPower(-.5);
//            Motors.FrontLeft().SetPower(-.5);
//            Motors.BackRight().SetPower(-.5);
//            Motors.BackLeft().SetPower(-.5);
//            sleep( 250);
//            Motors.FrontRight().SetPower(0);
//            Motors.FrontLeft().SetPower(0);
//            Motors.BackRight().SetPower(0);
//            Motors.BackLeft().SetPower(0);
//            sleep(2000);

//            Motors.FrontRight().SetPower(.5);
//            Motors.FrontLeft().SetPower(-.5);
//            Motors.BackRight().SetPower(.5);
//            Motors.BackLeft().SetPower(-.5);
//            sleep( 300);
//            Motors.FrontRight().SetPower(0);
//            Motors.FrontLeft().SetPower(0);
//            Motors.BackRight().SetPower(0);
//            Motors.BackLeft().SetPower(0);
//            sleep(2000);

//            Motors.FrontRight().SetPower(.5);
//            Motors.FrontLeft().SetPower(.5);
//            Motors.BackRight().SetPower(-.5);
//            Motors.BackLeft().SetPower(-.5);
//            sleep(200);
//            Motors.FrontRight().SetPower(0);
//            Motors.FrontLeft().SetPower(0);
//            Motors.BackRight().SetPower(0);
//            Motors.BackLeft().SetPower(0);
//            sleep(2000);

            //colorSensor();//color detect
            //sleep(2000);

            //go right, which is straight into the beacon
//            Motors.FrontRight().SetPower(.3);
//            Motors.FrontLeft().SetPower(.3);
//            Motors.BackRight().SetPower(-.3);
//            Motors.BackLeft().SetPower(-.3);
//            sleep(700);
//            Motors.FrontRight().SetPower(0);
//            Motors.FrontLeft().SetPower(0);
//            Motors.BackRight().SetPower(0);
//            Motors.BackLeft().SetPower(0);
//            sleep(2000);
//
//            //go left, which is away from the beacon
//            Motors.FrontRight().SetPower(-.5);
//            Motors.FrontLeft().SetPower(-.5);
//            Motors.BackRight().SetPower(.5);
//            Motors.BackLeft().SetPower(.5);
//            sleep(300);
//            Motors.FrontRight().SetPower(0);
//            Motors.FrontLeft().SetPower(0);
//            Motors.BackRight().SetPower(0);
//            Motors.BackLeft().SetPower(0);
//
//
//            break;

        }
    }
}
