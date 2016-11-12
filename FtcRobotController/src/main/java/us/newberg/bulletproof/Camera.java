package us.newberg.bulletproof;

import android.app.Activity;
import android.graphics.Bitmap;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

import java.lang.ref.WeakReference;

/**
 * FTC team 6712 Bulletproof
 */
public class Camera
{
    private static WeakReference<FtcRobotControllerActivity> _activityRef;
    private static Bitmap _bitmap;

    public static void UpdateActivity(FtcRobotControllerActivity activity)
    {
        _activityRef = new WeakReference<FtcRobotControllerActivity>(activity);
    }

    public static void TakePhoto()
    {
        _activityRef.get().dispatchTakePictureIntent();
    }

    public static void SetBitmap(Bitmap bitmap)
    {
        _bitmap = bitmap;
    }

    public static Bitmap GetBitmap()
    {
        return _bitmap;
    }

    public static FtcRobotControllerActivity GetAcitivity()
    {
        return _activityRef.get();
    }
}
