package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.Direction;

/**
 * FTC team 6712 Bulletproof
 */

@Autonomous(name = "Straight", group = "Common")
public class StraightOpMode extends BulletproofOpMode
{
    @Override
    protected void Run() throws InterruptedException
    {
        _driveTrain.Drive(Direction.NORTH, 0.8f, 39, 10000, this);
    }
}
