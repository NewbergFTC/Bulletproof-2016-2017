package us.newberg.bulletproof.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import us.newberg.bulletproof.opmodes.BulletproofOpMode;

/**
 * Created by bulletproof on 9/28/16.
 */

@TeleOp(name = "Test", group = "test")
@Disabled
public class TestOpMode extends BulletproofOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        waitForStart();

        while (opModeIsActive())
        {
            _driveTrain.Drive(1, 1);

            idle();
        }
    }
}
