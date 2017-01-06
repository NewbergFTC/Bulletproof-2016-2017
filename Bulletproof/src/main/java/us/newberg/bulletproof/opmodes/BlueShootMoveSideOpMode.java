package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.modules.Flipper;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Blue Shoot Move Side", group = "Blue")
public class BlueShootMoveSideOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
//        _flipper.StartAutoMove();
//
//        while (_flipper.GetState() == Flipper.State.AUTO)
//        {
//            sleep(1);
//            Update();
//        }
//
////        Motors.Collector.setPower(-1.0f);
////        sleep(3000);
////        Motors.Collector.setPower(0);
//
//        _flipper.StartAutoMove();
//
//        while (_flipper.GetState() == Flipper.State.AUTO)
//        {
//            sleep(1);
//            Update();
//        }

        _driveTrain.Drive(Direction.SOUTH_EAST, 0.5f, 3.0f * 12.0f, 5000, this);
        sleep(500);
        _driveTrain.Drive(Direction.SOUTH_WEST, 0.5f, 4.0f * 12.0f, 10000, this);
    }
}
