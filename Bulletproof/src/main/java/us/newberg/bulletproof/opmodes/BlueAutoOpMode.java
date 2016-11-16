package us.newberg.bulletproof.opmodes;

import org.lasarobotics.vision.ftc.resq.Beacon;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.motors.Flipper;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.lib.Servos;

/**
 * FTC team 6712 Bulletproof
 */
public class BlueAutoOpMode extends BulletproofOpMode
{
    private int _frame;

    @Override
    protected void Init()
    {
        super.Init();

        _frame = 0;
    }

    /**
     *
     *
     * @return -1 if timeout; 0 if its not blue; 1 if it is
     * @throws InterruptedException
     */
    private int GetLeftSideBlue() throws InterruptedException
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
                        result = beaconAnalysis.isLeftBlue() ? 1 : 0;

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
        // Launch the balls
        Motors.Flipper().StartAutoMove();

        while (Motors.Flipper().GetState() == Flipper.State.AUTO)
        {
            Update();
        }

        sleep(100);

        // Release the door
        _hopperDoor.Deploy();

        Motors.Flipper().StartAutoMove();

        while (Motors.Flipper().GetState() == Flipper.State.AUTO)
        {
            Update();
        }

        sleep(100);

        // TODO(Garrison): Move towards the beacon
        _driveTrain.Drive(Direction.NORTH_WEST, 0.5f, 12.0f * 4.0f, 10000, this);

        int colorStatus = GetLeftSideBlue();

        if (colorStatus == -1)
        {
            // TODO(Garrison): Error handling
        }

        boolean leftSideBlue = (colorStatus == 1);

        if (leftSideBlue)
        {
            // TODO(Garrison): Deploy left side
        }
        else
        {
            // TODO(Garrison): Deploy right side
        }

    }
}
