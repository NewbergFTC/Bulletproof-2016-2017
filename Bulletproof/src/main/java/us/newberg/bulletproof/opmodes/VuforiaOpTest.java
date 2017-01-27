package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

import us.newberg.bulletproof.Direction;
import us.or.k12.newberg.newbergcommon.math.VuforiaUtil;
import us.or.k12.newberg.newbergcommon.math.MathUtil;
import us.or.k12.newberg.newbergcommon.math.Vector2f;

@Autonomous(name = "VuforiaTest", group = "Test")
public class VuforiaOpTest extends BulletproofOpMode
{
    @Override
    public void Run() throws InterruptedException
    {
        // Shoot
        //

       // _driveTrain.Drive(Direction.SOUTH_EAST, 0.5f, 2.5f * 12.0f, 5000, this);
//        MonitoredSleep(200);

        // Start rotating towards the beacon until we can see it
        //_driveTrain.Drive(-0.01f, -0.01f);

        while (opModeIsActive() && !_wheelsListener.isVisible())
        {  
            telemetry.addData("Wheels", _wheelsListener.isVisible());
            telemetry.update();

            idle();
        }

       // _driveTrain.StopAll();

        VectorF angles = VuforiaUtil.AnglesFromTarget(_wheelsListener);
        VectorF translation = VuforiaUtil.NavOffWall(_wheelsListener.getPose().getTranslation(),
                Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));

        telemetry.addData("Found!", true);
        telemetry.update();

        Vector2f leftPower = new Vector2f();
        Vector2f rightPower = new Vector2f();

        final float horizontalPower = 0.05f;
        final float forwardPower = 0.1f;

        boolean preivousFrameVisible = false;

        while (opModeIsActive())
        {
            telemetry.addData("X", translation.get(0));
            telemetry.addData("Z", translation.get(2));
            telemetry.update();

            if (_wheelsListener.isVisible())
            {
                if (translation.get(0) > -500)
                {
                    leftPower.x = -horizontalPower;
                    leftPower.y = -horizontalPower; 

                    rightPower.x = -horizontalPower;
                    rightPower.y = -horizontalPower; 
                }
                else
                {
                    leftPower.x = horizontalPower;
                    leftPower.y = horizontalPower; 

                    rightPower.x =  horizontalPower;
                    rightPower.y = horizontalPower; 
                }

                if (translation.get(2) < -200)
                {
                    leftPower.x = (leftPower.x + -forwardPower) / 2;
                    leftPower.y = (leftPower.y + forwardPower) / 2;

                    rightPower.x = (rightPower.x + -forwardPower) / 2;
                    rightPower.y = (rightPower.y + forwardPower) / 2;
                }

                preivousFrameVisible = true;
            }
            else
            {
                if (preivousFrameVisible)
                {
                    leftPower.x *= -1;
                    leftPower.y *= -1;

                    rightPower.x *= -1;
                    rightPower.y *= -1;
                }

                preivousFrameVisible = false;
            }

            _driveTrain.Drive(leftPower, rightPower);

            if (_wheelsListener.getPose() != null)
            {
                translation = VuforiaUtil.NavOffWall(_wheelsListener.getPose().getTranslation(),
                        Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));
            }

            idle();
        }

        telemetry.addData("Facing", true);
        telemetry.update();
        // We are now properly facing the beacons

        _driveTrain.StopAll();
    }
}
