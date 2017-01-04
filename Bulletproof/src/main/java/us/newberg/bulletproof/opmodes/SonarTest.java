
package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import us.newberg.bulletproof.Motor;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.math.Vector2f;

/**
 * Created by thesm_000 on 11/11/2016.
 */
@Autonomous(name = "Sonar Test", group = "Autonomous")
public class SonarTest extends BulletproofOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Init();
        waitForStart();
        double ultraSonicValue = ultrasonicSensor.getUltrasonicLevel();
        while (opModeIsActive()) {
            telemetry.addData("ultra Sonic Value",ultraSonicValue);
            ultraSonicValue = ultrasonicSensor.getUltrasonicLevel();
            telemetry.update();
        }
    }
}



//11
//80 green detect red
//