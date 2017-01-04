package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.hitechnic.HiTechnicNxtLightSensor;
import us.newberg.bulletproof.Motor;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.math.Vector2f;


/**
 * Created by thesm_000 on 11/11/2016.
 */
@Autonomous(name = "Blue Autonomous", group = "Autonomous")
public class BlueAutonomous extends BulletproofOpMode {
    @Override

    public void runOpMode() throws InterruptedException
    {
        Init();
        boolean ColorDetect = false;

        waitForStart();
        while (opModeIsActive()){

            if (lightSensor(LightSensor)) {
                Motors.BackLeft().SetPower(0);
                Motors.FrontLeft().SetPower(0);
                Motors.BackRight().SetPower(0);
                Motors.FrontRight().SetPower(0);

                return;
            }



        }
    }
}
