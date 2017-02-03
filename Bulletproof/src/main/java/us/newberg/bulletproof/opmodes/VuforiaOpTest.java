package us.newberg.bulletproof.opmodes;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.CameraCalibration;
import com.vuforia.Image;
import com.vuforia.Matrix34F;
import com.vuforia.Tool;
import com.vuforia.Vec3F;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.Arrays;

import us.newberg.bulletproof.Direction;
import us.or.k12.newberg.newbergcommon.math.VuforiaUtil;
import us.or.k12.newberg.newbergcommon.math.MathUtil;
import us.or.k12.newberg.newbergcommon.math.Vector2f;

@TeleOp(name = "VuforiaTest", group = "Test")
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

        final float horizontalPower = 0.08f;
        final float forwardPower = 0.13f;

        float[] poseData  = _wheelsListener.getRawPose().getData();

        Telemetry.Item place = telemetry.addData("Place", "Starting");

        while (opModeIsActive())
        {
            if (_wheelsListener.getPose() != null)
            {
                translation = VuforiaUtil.NavOffWall(_wheelsListener.getPose().getTranslation(),
                        Math.toDegrees(angles.get(0)) - 90, new VectorF(500, 0, 0));

                angles = VuforiaUtil.AnglesFromTarget(_wheelsListener);

                poseData = _wheelsListener.getRawPose().getData();
            }

            if (_wheelsListener.isVisible())
            {
                if (translation.get(0) < -575)
                {
                    place.setValue("Right");
                    leftPower.x = horizontalPower;
                    leftPower.y = horizontalPower; 

                    rightPower.x = horizontalPower;
                    rightPower.y = horizontalPower; 
                }
                else if (translation.get(0) > -425)
                {
                    place.setValue("Left");
                    leftPower.x = -horizontalPower;
                    leftPower.y = -horizontalPower; 

                    rightPower.x = -horizontalPower;
                    rightPower.y = -horizontalPower; 
                }
                else if (poseData[1] < 0.91f)
                {
                    if (poseData[2] > 0)
                    {
                        // Right
                        place.setValue("Moving Right");
                        leftPower.x = horizontalPower;
                        leftPower.y = horizontalPower;

                        rightPower.x = -horizontalPower;
                        rightPower.y = -horizontalPower;
                        
                    }
                    else
                    {
                        // Left
                        place.setValue("Moving Left");
                        leftPower.x = -horizontalPower;
                        leftPower.y = -horizontalPower;

                        rightPower.x = horizontalPower;
                        rightPower.y = horizontalPower;
                    }
                }
                else
                {
                    place.setValue("Straight");
                    leftPower.x = -forwardPower;
                    leftPower.y = forwardPower; 

                    rightPower.x = -forwardPower;
                    rightPower.y = forwardPower; 
                }
            }
            else
            {
                leftPower.x = 0;
                leftPower.y = 0; 

                rightPower.x = 0;
                rightPower.y = 0; 
            }

           _driveTrain.Drive(leftPower, rightPower);

            telemetry.update();
            idle();
        }

        // Analyze the beacon


    }
}
