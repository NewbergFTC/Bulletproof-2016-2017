package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by bulletproof on 9/28/16.
 */

@TeleOp(name = "Test", group = "test")
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
