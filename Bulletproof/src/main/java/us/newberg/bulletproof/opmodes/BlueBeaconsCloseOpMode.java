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
        _driveTrain.Drive(Direction.SOUTH_WEST, 0.5f, 45, 5000, this);

        _driveTrain.Drive(new Vector2f(-.2f, -.2f), new Vector2f(-.2f, -.2f));
        sleep(700);
        _driveTrain.StopAll();

        if (_wheelsListener.isVisible())
        {
            AnalyzeBeacon(_wheelsListener);
            GetToBeacon(_wheelsListener);
        }

        _driveTrain.Drive(Direction.EAST, 0.3f, 3, 500, this);
    }
}
