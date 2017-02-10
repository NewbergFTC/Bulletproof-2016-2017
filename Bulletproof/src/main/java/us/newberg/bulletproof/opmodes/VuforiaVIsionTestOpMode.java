package us.newberg.bulletproof.opmodes;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import us.or.k12.newberg.newbergcommon.BeaconUtil;

@TeleOp(name = "Vuforia Vision Test", group = "Test")
public class VuforiaVIsionTestOpMode extends BulletproofOpMode
{
    @Override
    protected void Init()
    {
        InitVuforia();
    }

    protected BeaconUtil.BeaconStatus AnalyzeBeacon()
    {
        BeaconUtil.BeaconStatus status = BeaconUtil.GetBeaconStatus(_vuforia.GetImage(), _wheelsListener,
                _vuforia.getCameraCalibration());

        return status;
    }

    @Override
    protected void Run() throws InterruptedException
    {
        Button button = (Button) ((Activity) hardwareMap.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.button);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BeaconUtil.BeaconStatus result = AnalyzeBeacon();

                telemetry.addData("Beacon", result);
                telemetry.update();
            }
        });

        while (opModeIsActive())
        {
            idle();
        }
    }
}
