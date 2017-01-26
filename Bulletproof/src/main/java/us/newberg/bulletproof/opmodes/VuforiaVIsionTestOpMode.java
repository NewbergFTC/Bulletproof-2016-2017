package us.newberg.bulletproof.opmodes;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.vuforia.Matrix34F;
import com.vuforia.Tool;
import com.vuforia.Vec2F;
import com.vuforia.Vec3F;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

import java.util.Arrays;

@TeleOp(name = "Vuforia Vision Test", group = "Test")
public class VuforiaVIsionTestOpMode extends BulletproofOpMode
{
    @Override
    protected void Init()
    {
        InitVuforia();
    }

    @Override
    protected void Run() throws InterruptedException
    {
        boolean imageVisible = false;
        while (opModeIsActive())
        {
            imageVisible = _wheelsListener.getRawPose() != null;

            telemetry.addData("Wheels Visible", imageVisible);
            telemetry.update();

            if (_vuforia.GetImage() != null)
            {
                Bitmap bm = Bitmap.createBitmap(_vuforia.GetImage().getBufferWidth(), _vuforia.GetImage().getBufferHeight(),
                            Bitmap.Config.RGB_565);
                bm.copyPixelsFromBuffer(_vuforia.GetImage().getPixels());
            }

            for (VuforiaTrackable trackable : _beacons)
            {
                OpenGLMatrix pose = ((VuforiaTrackableDefaultListener) trackable.getListener()).getRawPose();

                if (pose != null)
                {
                    Matrix34F rawPose = new Matrix34F();
                    float[] poseData = Arrays.copyOfRange(pose.transposed().getData(), 0, 12);

                    rawPose.setData(poseData);

                    // Find the beacon center in the image
                    Vec2F topLeft = Tool.projectPoint(_vuforia.getCameraCalibration(), rawPose, new Vec3F(-127, 92, 0));
                    Vec2F topRight = Tool.projectPoint(_vuforia.getCameraCalibration(), rawPose, new Vec3F(127, 92, 0));
                    Vec2F bottomLeft = Tool.projectPoint(_vuforia.getCameraCalibration(), rawPose, new Vec3F(127, -92, 0));
                    Vec2F bottomRight = Tool.projectPoint(_vuforia.getCameraCalibration(), rawPose, new Vec3F(-127, -92, 0));

                    // topLeft.getData()[0] - x pos in the image of the upper left point
                }
            }

            idle();
        }


    }
}
