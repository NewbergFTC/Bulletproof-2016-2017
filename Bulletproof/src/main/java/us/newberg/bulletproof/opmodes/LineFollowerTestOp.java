package us.newberg.bulletproof.opmodes;

public class LineFollowerTestOp extends BulletproofOpMode 
{
    @Override
    protected void Init()
    {
        super.Init();


    }   

    @Override
    public void Run() throws InterruptedException
    {
       while (opModeIsActive())
       {
    
           Update();
       }
    }   
}
