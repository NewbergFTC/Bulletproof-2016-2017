package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.modules.Flipper;
import us.newberg.bulletproof.lib.Motors;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Red Shoot Move Side", group = "Red")
public class RedShootMoveSideOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
		_flipper.AutoMoveBlocking(this);

        Motors.Collector.setPower(-1.0f);
        sleep(3000);
        Motors.Collector.setPower(0);

        _flipper.AutoMoveBlocking(this);

        _driveTrain.Drive(Direction.NORTH_EAST, 0.5f, 3.7f * 12.0f, 5000, this);
        sleep(500);
        _driveTrain.Drive(-0.5f, -0.5f);
        sleep(500);
        _driveTrain.StopAll();
        sleep(500);
        _driveTrain.Drive(Direction.NORTH, 1.0f, 2.0f * 12.0f, 10000, this);
    }
}
