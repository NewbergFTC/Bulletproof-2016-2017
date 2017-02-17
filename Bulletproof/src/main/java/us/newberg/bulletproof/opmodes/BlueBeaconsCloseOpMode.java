package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.Direction;
import us.or.k12.newberg.newbergcommon.math.Vector2f;

@Autonomous(name = "Beacons Blue Close", group = "Blue")
public class BlueBeaconsCloseOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
        _driveTrain.Drive(Direction.SOUTH_WEST, 0.5f, 48, 5000, this);

        _driveTrain.Drive(new Vector2f(-.2f, -.2f), new Vector2f(-.2f, -.2f));
        sleep(500);
        _driveTrain.StopAll();

        while (!_wheelsListener.isVisible())
        {
            sleep(1000);

           if (_wheelsListener.isVisible())
           {
                break;
           }

           _driveTrain.Drive(new Vector2f(-.2f, -.2f), new Vector2f(-.2f, -.2f));
           sleep(400);
           _driveTrain.StopAll();
        }

        AnalyzeAndDeployBlue(_wheelsListener);
        GetToBeacon(_wheelsListener);

        _driveTrain.Drive(Direction.WEST, 0.3f, 5, 500, this);
        _driveTrain.Drive(Direction.EAST, 0.3f, 3, 500, this);
    }
}
