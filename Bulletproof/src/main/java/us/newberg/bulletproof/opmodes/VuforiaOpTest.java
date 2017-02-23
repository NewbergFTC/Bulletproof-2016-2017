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
        while (opModeIsActive() && !_legoListener.isVisible())
        {
            idle();
        }

        AnalyzeAndDeployBlue(_legoListener);
        GetToBeacon(_legoListener);

        _driveTrain.Drive(Direction.WEST, 0.5f, 5, 750, this);
        _driveTrain.Drive(Direction.EAST, 0.2f, 3, 1000, this);
    }
}
