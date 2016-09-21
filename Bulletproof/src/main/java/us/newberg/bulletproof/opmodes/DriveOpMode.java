package us.newberg.bulletproof.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * FTC team 6712 Bulletproof
 */

@TeleOp(name = "DriverOpMode", group = "Driver")
public class DriveOpMode extends BulletproofOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        Init();

        waitForStart();


    }
}
