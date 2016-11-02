package us.newberg.bulletproof;

/**
 * FTC team 6712 Bulletproof
 */
public enum Direction
{
    NORTH(90),
    NORTH_EAST(45),
    EAST(0),
    SOUTH_EAST(-45),
    SOUTH(-90),
    SOUTH_WEST(45),
    WEST(0),
    NORTH_WEST(-45);

    int _angleWithX;

    Direction(int angleWithX)
    {
        _angleWithX = angleWithX;
    }

    public int GetAngleWithX()
    {
        return _angleWithX;
    }
}
