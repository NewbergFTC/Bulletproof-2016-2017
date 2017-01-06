package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.lasarobotics.vision.ftc.resq.Beacon;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.lib.Motors;

/**
 * FTC team 6712 Bulletproof
 */

@Autonomous(name = "Red Main", group = "Red")
@Disabled
public class OldRedOneAutoOpMode extends BulletproofOpMode
{
    private int GetRightSideBlue() throws InterruptedException
    {
        int result = 0;
        long totalTimeSlept = 0;

        while (opModeIsActive())
        {
            if (hasNewFrame())
            {
                Beacon.BeaconAnalysis beaconAnalysis = beacon.getAnalysis();

                result = beaconAnalysis.isRightBlue() ? 1 : 0;

                break;
            }
            else
            {
                if (totalTimeSlept > 10000)
                {
                    result = -1;

                    break;
                }

                totalTimeSlept += 10;
                sleep(10);
            }

            Update();
        }

        return result;
    }

    @Override
    protected void Run() throws InterruptedException
    {
        // TODO(Garrison): Move towards the beacon
        _driveTrain.Drive(Direction.SOUTH_EAST, 1f, 12.0f * 6.2f, 10000, this);

        final long FLIPPER_DELAY = 8000;
        final long COLLECTOR_DELAY = 3500;

        _flipper.SetPower(1);

        sleep(FLIPPER_DELAY);

        _flipper.SetPower(0);

        Motors.Collector.setPower(1.0);

        sleep(COLLECTOR_DELAY);

        Motors.Collector.setPower(0);

        _flipper.SetPower(1);

        sleep((long)(FLIPPER_DELAY * 1.2));

        _flipper.SetPower(0);

        Update();

        int colorStatus = GetRightSideBlue();

        telemetry.addData("color status", colorStatus);
        telemetry.update();

        if (colorStatus == -1)
        {
            // TODO(Garrison): Error handling
            throw new RuntimeException("Fuck this");
        }

        boolean rightSideBlue = (colorStatus == 1);

        if (rightSideBlue)
        {
            _buttonPusher.DeployRight();
        }
        else
        {
            _buttonPusher.DeployLeft();
        }

        sleep(750);

        _driveTrain.Drive(Direction.EAST, 1f, 24, 4000, this);

        _driveTrain.Drive(Direction.WEST, 0.4f, 6, 2000, this);
    }
}
