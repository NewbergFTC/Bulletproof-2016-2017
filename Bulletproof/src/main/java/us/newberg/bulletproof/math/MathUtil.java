package us.newberg.bulletproof.math;

/**
 * FTC team 6712 Bulletproof
 */

public class MathUtil
{
    private static final double EPSILON = 0.000001; // A very tiny number (10e-6)

    public static boolean FuzzyEquals(double value1, double value2)
    {
        return FuzzyEquals(value1, value2, EPSILON);
    }

    public static boolean FuzzyEquals(double value1, double value2, double epsilon)
    {
        boolean result = false;

        double diff = value1 - value2;

        if (Math.abs(diff) < epsilon)
        {
            result = true;
        }

        return result;
    }

    public static float FeetToInches(float feet)
    {
        float result = feet / 12.0f;

        return result;
    }
}
