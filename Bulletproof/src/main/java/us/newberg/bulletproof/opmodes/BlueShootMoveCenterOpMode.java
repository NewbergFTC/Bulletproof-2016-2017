package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.modules.Flipper;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Blue Shoot Move Center", group = "Blue")
public class BlueShootMoveCenterOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
        _flipper.StartAutoMove();

        while (_flipper.GetState() == Flipper.State.AUTO)
        {
            sleep(1);
            Update();
        }

        _driveTrain.Drive(Direction.NORTH, 1.0f, 6.5f * 12.0f, 10000, this);
    }
}
