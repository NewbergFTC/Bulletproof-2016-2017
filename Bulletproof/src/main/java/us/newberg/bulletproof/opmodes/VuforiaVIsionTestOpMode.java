package us.newberg.bulletproof.opmodes;

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

    @Override
    protected void Run() throws InterruptedException
    {
        while (opModeIsActive())
        {
            if (_wheelsListener.isVisible())
            {
                if (_vuforia.GetImage() != null)
                {
                    BeaconUtil.BeaconStatus result = BeaconUtil.GetBeaconStatus(_vuforia.GetImage(), _wheelsListener,
                                                                     _vuforia.getCameraCalibration());

                    telemetry.addData("Beacon", result);
                }
            }

            telemetry.update();
            idle();
        }
    }
}
