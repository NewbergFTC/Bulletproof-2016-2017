package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.modules.Flipper;
import us.newberg.bulletproof.modules.Beacon;
import us.or.k12.newberg.newbergcommon.math.Vector2f;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Blue Beacon Test", group = "Test")
public class BeaconTestOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
        _driveTrain.Drive(Direction.SOUTH_EAST, 0.5f, 2.5f * 12.0f, 5000, this);
        sleep(500);

        _driveTrain.Drive(-0.5f, -0.5f);
        sleep(550);
        _driveTrain.StopAll();

        sleep(500);
    
        float power = 0.5f;

        Vector2f leftPower  = new Vector2f();
        Vector2f rightPower = new Vector2f();

        leftPower.x = -power;
        leftPower.y = 0;

        rightPower.x = 0;
        rightPower.y = power;

        _driveTrain.Drive(leftPower, rightPower);

        _lineFollower.enableLed(true);

        while (_lineFollower.getRawLightDetected() < 1.5)
        {
            telemetry.addData("Light", _lineFollower.getRawLightDetected());
            telemetry.update();

            sleep(1);   
        }

        _driveTrain.StopAll();

        _buttonPusher.DeployLeft();
        sleep(1000);

        leftPower.x = -power;
        leftPower.y = power;
        rightPower.x = -power;
        rightPower.y = power;

        _driveTrain.Drive(leftPower, rightPower);

        while (_sonar.getUltrasonicLevel() >= 15)
        {
           double sonarLevel = _sonar.getUltrasonicLevel();

           telemetry.addData("Sonar", sonarLevel);

           telemetry.update();
            sleep(1);
        }

        _driveTrain.StopAll();

        while (true)
        {
           double sonarLevel = _sonar.getUltrasonicLevel();

           telemetry.addData("Sonar", sonarLevel);

           if (hasNewFrame())
           {
               _beacon.Update(beacon.getAnalysis());
               _beacon.UpdateTelemetry(telemetry);
           }


          telemetry.update();
        }
    }
}
