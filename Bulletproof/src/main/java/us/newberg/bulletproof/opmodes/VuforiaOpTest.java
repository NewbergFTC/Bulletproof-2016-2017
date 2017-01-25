package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;

import us.newberg.bulletproof.Direction;
import us.or.k12.newberg.newbergcommon.math.VuforiaUtil;

@Autonomous(name = "VuforiaTest", group = "Test")
public class VuforiaOpTest extends BulletproofOpMode
{
    @Override
    public void Run() throws InterruptedException
    {
        // Shoot
        //

        // Drive towards the beacon
        _driveTrain.Drive(Direction.SOUTH_EAST, 0.5f, 2.5f * 12.0f, 5000, this);
        MonitoredSleep(200);

        // Start rotating towards the beacon until we can see it
        _driveTrain.Drive(-0.5f, -0.5f);

        while (opModeIsActive() && _wheelsListener.getRawPose() == null)
        {
            idle();
        }

        _driveTrain.StopAll();

        // TODO(Garrison): Analyze beacon

        VectorF angles = VuforiaUtil.AnglesFromTarget(_wheelsListener);
        VectorF translation = VuforiaUtil.NavOffWall(_wheelsListener.getPose().getTranslation(),
                Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));

        if (translation.get(0) > 0)
        {
            // This should rotate right
            _driveTrain.Drive(-0.5f, -0.5f);
        }
        else
        {
            _driveTrain.Drive(0.5f, 0.5f);
        }

        do
        {
            if (_wheelsListener.getPose() != null)
            {
                translation = VuforiaUtil.NavOffWall(_wheelsListener.getPose().getTranslation(),
                        Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));
            }

            idle();
        } while (opModeIsActive() && Math.abs(translation.get(0)) > 30);

        // We are now properly facing the beacons

        _driveTrain.StopAll();
    }
}
