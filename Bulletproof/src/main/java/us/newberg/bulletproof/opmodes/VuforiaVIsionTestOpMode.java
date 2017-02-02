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
    final float MM_PER_INCH = 25.4f;
    final float MM_BOT_WIDTH = 17.0f * MM_PER_INCH;
    final float MM_FTC_FIELD_WIDTH = (12.0f * 12.0f - 2.0f) * MM_PER_INCH; 

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

            if (_vuforia.GetImage() != null)
            {
//.               Bitmap bm = Bitmap.createBitmap(_vuforia.GetImage().getBufferWidth(), _vuforia.GetImage().getBufferHeight(),
 //                           Bitmap.Config.RGB_565);
  //              bm.copyPixelsFromBuffer(_vuforia.GetImage().getPixels());
            }

            OpenGLMatrix pose =  _wheelsListener.getRawPose();

            if (pose != null)
            {
                float[]  poseData = _wheelsListener.getRawPose().getData();
                telemetry.addData("X?", poseData[1]);
                telemetry.addData("X2?", poseData[2]);

                telemetry.update();
            }

            idle();
        }


    }
}
