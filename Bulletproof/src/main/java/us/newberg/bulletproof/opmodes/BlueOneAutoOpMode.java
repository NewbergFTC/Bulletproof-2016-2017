package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.lasarobotics.vision.ftc.resq.Beacon;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.modules.Flipper;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Blue Main", group = "Blue")
public class BlueOneAutoOpMode extends BulletproofOpMode
{
    @Override
    protected void Init()
    {
        super.Init();
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

        final long FLIPPER_DELAY = 8000;
        final long COLLECTOR_DELAY = 3500;

        _flipper.SetPower(1);

        sleep(FLIPPER_DELAY);

        _flipper.SetPower(0);

        Motors.Collector.SetPower(-1.0);

        sleep(COLLECTOR_DELAY);

        Motors.Collector.SetPower(0);

        _flipper.SetPower(1);

        sleep((long)(FLIPPER_DELAY * 1.2));

        _flipper.SetPower(0);

        // TODO(Garrison): Move towards the beacon
        _driveTrain.Drive(Direction.SOUTH_EAST, 1f, 12.0f * 6.2f, 10000, this);

        _driveTrain.Drive(0.4f, 0.4f);
        sleep(500);
        _driveTrain.StopAll();

        while (!hasNewFrame())
        {

        }

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

        sleep(750);

        _driveTrain.Drive(Direction.WEST, 1f, 24, 4000, this);

        _driveTrain.Drive(Direction.EAST, 0.4f, 6, 2000, this);
//
//        _driveTrain.Drive(Direction.SOUTH, 0.4f, 32, 6000, this);
//
//        Update();
//
//        colorStatus = GetRightSideBlue();
//
//        telemetry.addData("color status", colorStatus);
//        telemetry.update();
//
//        if (colorStatus == -1)
//        {
//            // TODO(Garrison): Error handling
//            throw new RuntimeException("Fuck this too");
//        }
//
//        rightSideBlue = (colorStatus == 1);
//
//        if (rightSideBlue)
//        {
//            _buttonPusher.DeployRight();
//        }
//        else
//        {
//            _buttonPusher.DeployLeft();
//        }
//
//        _driveTrain.Drive(Direction.WEST, 0.5f, 6, 2000, this);
    }
}
