package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * FTC team 6712 Bulletproof
 */
@TeleOp(name = "Vision Test", group = "Test")
public class VisionTestOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
        while (opModeIsActive())
        {
            if (hasNewFrame())
            {
                boolean leftSideBlue = beacon.getAnalysis().isLeftBlue();

                if (leftSideBlue)
                {
                    telemetry.addData("Left, mother fucker", 0);
                    _buttonPusher.DeployLeft();
                    _buttonPusher.CloseRight();
                }
                else
                {
                    telemetry.addData("Right", 0);
                    _buttonPusher.DeployRight();
                    _buttonPusher.CloseLeft();
                }

                sleep(500);
            }

            Update();
        }
    }
}
