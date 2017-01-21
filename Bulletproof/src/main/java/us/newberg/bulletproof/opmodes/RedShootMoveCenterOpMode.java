package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.lib.Motors;
import us.newberg.bulletproof.modules.Flipper;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Red Shoot Move Center", group = "Red")
public class RedShootMoveCenterOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
        _flipper.AutoMoveBlocking(this);

        Motors.Collector.setPower(-1.0f);
        sleep(3000);
        Motors.Collector.setPower(0);

        _flipper.AutoMoveBlocking(this);

        _driveTrain.Drive(Direction.NORTH_EAST, 0.75f, 3.15f * 12.0f, 5000, this);
        sleep(500);
        _driveTrain.Drive(Direction.SOUTH_EAST, 0.75f, 3.3f * 12.0f, 10000, this);
    }
}
