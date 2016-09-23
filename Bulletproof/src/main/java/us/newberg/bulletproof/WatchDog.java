package us.newberg.bulletproof;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * FTC team 6712 Bulletproof
 */
public class WatchDog
{
    private static final AtomicBoolean _shouldStop = new AtomicBoolean(false);

    private static void Reset()
    {
        _shouldStop.set(false);
    }

    // TODO(Garrison): Test this...
    public static void Watch(final Runnable task, final long millis)
    {
        final Thread slave = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(millis);
                } catch (InterruptedException e)
                {
                    // TODO(Garrison): Error handling
                    e.printStackTrace();
                }

                if (!_shouldStop.get())
                {
                    task.run();
                }
            }
        });

        final Thread master = new Thread(new Runnable()
         {
             @Override
             public void run()
             {
                slave.start();

                try
                {
                    slave.join();
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                Reset();
             }
         });

        master.start();
    }

    // TODO(Garrison): Guarantee this works, and {@link WatchDog} won't still run whatever it was told to
    public static void Stop()
    {
        _shouldStop.set(true);
    }
}
