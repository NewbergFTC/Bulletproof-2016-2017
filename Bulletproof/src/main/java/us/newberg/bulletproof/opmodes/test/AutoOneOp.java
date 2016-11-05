package us.newberg.bulletproof.opmodes.test;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import us.newberg.bulletproof.Direction;
import us.newberg.bulletproof.opmodes.BulletproofOpMode;

/**
 * FTC team 6712 Bulletproof
 */
@Autonomous(name = "Straight", group = "Shitty")
public class AutoOneOp extends BulletproofOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        waitForStart();

        _driveTrain.Drive(Direction.NORTH, 0.5f, 69, 10000, this);

        CleanUp();
    }
}
