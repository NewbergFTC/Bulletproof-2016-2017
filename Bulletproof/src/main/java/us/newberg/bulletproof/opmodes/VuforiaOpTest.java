package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.lib.Servos;
import us.or.k12.newberg.newbergcommon.BeaconUtil;
import us.or.k12.newberg.newbergcommon.vuforia.VuforiaUtil;
import us.or.k12.newberg.newbergcommon.math.Vector2f;

@TeleOp(name = "VuforiaTest", group = "Test")
public class VuforiaOpTest extends BulletproofOpMode
{
    @Override
    public void Run() throws InterruptedException
    {
        while (opModeIsActive() && !_wheelsListener.isVisible())
        {
            idle();
        }

        AnalyzeAndDeployBlue(_wheelsListener);
        GetToBeacon(_wheelsListener);

        _driveTrain.Drive(Direction.WEST, 0.5f, 2, 750, this);
        _driveTrain.Drive(Direction.EAST, 0.2f, 3f, 1000, this);

        while (AnalyzeBeacon(_wheelsListener) != BeaconUtil.BeaconStatus.BEACON_ALL_BLUE)
        {
            GetToBeacon(_wheelsListener);
            _driveTrain.Drive(Direction.WEST, 0.5f, 2, 2000, this);
            sleep(500);
        }

        _driveTrain.StopAll();

        _driveTrain.Drive(Direction.EAST, 0.5f, 3f, 2000, this);

        _driveTrain.Drive(Direction.SOUTH, 0.3f, 8f, 3000, this);

        _driveTrain.Drive(new Vector2f(-.2f, .2f), new Vector2f(.2f, -.2f));
        sleep(500);
        _driveTrain.StopAll();
    }
}
