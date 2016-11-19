package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.lasarobotics.vision.ftc.resq.Beacon;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.modules.Flipper;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Blue Main")
public class BlueOneAutoOpMode extends BulletproofOpMode
{
    @Override
    protected void Init()
    {
        super.Init();
    }

    /**
     *
     *
     * @return -1 if timeout; 0 if its not blue; 1 if it is
     * @throws InterruptedException
     */
    private int GetRightSideBlue() throws InterruptedException
    {
        int result = 0;
        long totalTimeSlept = 0;

        while (opModeIsActive())
        {
            if (hasNewFrame())
            {
                Beacon.BeaconAnalysis beaconAnalysis = beacon.getAnalysis();

                if (beaconAnalysis.isBeaconFound())
                {
                    if (beaconAnalysis.isLeftKnown())
                    {
                        result = beaconAnalysis.isRightBlue() ? 1 : 0;

                        break;
                    }
                }
            }
            else
            {
                if (totalTimeSlept > 4000)
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
//        // Launch the balls
//        _flipper.StartAutoMove();
//
//        while (_flipper.GetState() == Flipper.State.AUTO)
//        {
//            Update();
//        }
//
//        sleep(100);
//
//        // Release the door
//        _hopperDoor.Deploy();
//
//        _flipper.StartAutoMove();
//
//        while (_flipper.GetState() == Flipper.State.AUTO)
//        {
//            Update();
//        }
//
//        sleep(100);

        // TODO(Garrison): Move towards the beacon
        _driveTrain.Drive(Direction.SOUTH_WEST, 0.5f, 12.0f * 4.0f, 10000, this);

        final double FLIPPER_DELAY = 6000.0;

        double startTime = getRuntime();
        double targetTime = startTime + FLIPPER_DELAY;

        while (startTime < targetTime)
        {
            _flipper.SetPower(1.0f);
        }

        _flipper.SetPower(0);

        Update();
        sleep(500);

        startTime = getRuntime();
        targetTime = startTime + FLIPPER_DELAY;

        while (startTime < targetTime)
        {
            _flipper.SetPower(1);
        }

        _flipper.SetPower(0);

        _driveTrain.Drive(Direction.NORTH, 0.5f, 5, 4000, this);

        int colorStatus1 = GetRightSideBlue();

        _driveTrain.Drive(Direction.NORTH, 0.3f, 5.0f, 3000, this);

        int colorStatus2 = GetRightSideBlue();
        int colorStatus = -1;

        if (colorStatus1 != colorStatus2)
        {
            colorStatus = GetRightSideBlue();
        }

        if (colorStatus == -1)
        {
            // TODO(Garrison): Error handling
        }

        boolean leftSideBlue = (colorStatus == 1);

        if (leftSideBlue)
        {
            _buttonPusher.DeployLeft();
        }
        else
        {
            _buttonPusher.DeployRight();
        }
    }
}
