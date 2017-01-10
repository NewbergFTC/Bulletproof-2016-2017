package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.modules.Flipper;

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

        _driveTrain.Drive(Direction.NORTH_WEST, 0.5f, 4.0f * 12.0f, 5000, this);
        sleep(500);
        _driveTrain.Drive(Direction.NORTH_EAST, 1.0f, 4.0f * 12.0f, 10000, this);
    }
}
