package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * FTC team 6712 Bulletproof
 */
@TeleOp(name = "Vision Test", group = "Test")
public class VisionTestOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
		Telemetry.Item tel = telemetry.addData("Side", "Left");

        while (opModeIsActive())
        {
            if (hasNewFrame())
            {
                boolean leftSideBlue = beacon.getAnalysis().isLeftBlue();

                if (leftSideBlue)
                {
                	tel.setValue("Left");
                    _buttonPusher.DeployLeft();
                    _buttonPusher.CloseRight();
                }
                else
                {
                	tel.setValue("Right");
                    _buttonPusher.DeployRight();
                    _buttonPusher.CloseLeft();
                }

            }

            sleep(500);
            Update();
        }
    }
}
