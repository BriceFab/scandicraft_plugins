package net.minecraft.util;

import java.util.Random;
import java.util.UUID;

public class MathHelper
{
    public static final float SQRT_2 = sqrt_float(2.0F);
    private static final int SIN_BITS = 12;
    private static final int SIN_MASK = 4095;
    private static final int SIN_COUNT = 4096;
    public static final float PI = (float)Math.PI;
    public static final float PI2 = ((float)Math.PI * 2F);
    public static final float PId2 = ((float)Math.PI / 2F);
    private static final float radFull = ((float)Math.PI * 2F);
    private static final float degFull = 360.0F;
    private static final float radToIndex = 651.8986F;
    private static final float degToIndex = 11.377778F;
    public static final float deg2Rad = 0.017453292F;
    private static final float[] SIN_TABLE_FAST = new float[4096];
    public static boolean fastMath = false;

    /**
     * A table of sin values computed from 0 (inclusive) to 2*pi (exclusive), with steps of 2*PI / 65536.
     */
    private static final float[] SIN_TABLE = new float[65536];

    /**
     * Though it looks like an array, this is really more like a mapping.  Key (index of this array) is the upper 5 bits
     * of the result of multiplying a 32-bit unsigned integer by the B(2, 5) De Bruijn sequence 0x077CB531.  Value
     * (value stored in the array) is the unique index (from the right) of the leftmost one-bit in a 32-bit unsigned
     * integer that can cause the upper 5 bits to get that value.  Used for highly optimized "find the log-base-2 of
     * this number" calculations.
     */
    private static final int[] multiplyDeBruijnBitPosition;
    private static final double FRAC_BIAS;
    private static final double[] ASINE_TAB;
    private static final double[] COS_TAB;
    ////private static final String __OBFID = "CL_00001496";

    /**
     * sin looked up in a table
     */
    public static float sin(float value)
    {
        return fastMath ? SIN_TABLE_FAST[(int)(value * 651.8986F) & 4095] : SIN_TABLE[(int)(value * 10430.378F) & 65535];
    }

    /**
     * cos looked up in the sin table with the appropriate offset
     */
    public static float cos(float value)
    {
        return fastMath ? SIN_TABLE_FAST[(int)((value + ((float)Math.PI / 2F)) * 651.8986F) & 4095] : SIN_TABLE[(int)(value * 10430.378F + 16384.0F) & 65535];
    }

    public static float sqrt_float(float value)
    {
        return (float)Math.sqrt((double)value);
    }

    public static float sqrt_double(double value)
    {
        return (float)Math.sqrt(value);
    }

    /**
     * Returns the greatest integer less than or equal to the float argument
     */
    public static int floor_float(float value)
    {
        int i = (int)value;
        return value < (float)i ? i - 1 : i;
    }

    /**
     * returns par0 cast as an int, and no greater than Integer.MAX_VALUE-1024
     */
    public static int truncateDoubleToInt(double value)
    {
        return (int)(value + 1024.0D) - 1024;
    }

    /**
     * Returns the greatest integer less than or equal to the double argument
     */
    public static int floor_double(double value)
    {
        int i = (int)value;
        return value < (double)i ? i - 1 : i;
    }

    /**
     * Long version of floor_double
     */
    public static long floor_double_long(double value)
    {
        long i = (long)value;
        return value < (double)i ? i - 1L : i;
    }

    public static int func_154353_e(double value)
    {
        return (int)(value >= 0.0D ? value : -value + 1.0D);
    }

    public static float abs(float value)
    {
        return value >= 0.0F ? value : -value;
    }

    /**
     * Returns the unsigned value of an int.
     */
    public static int abs_int(int value)
    {
        return value >= 0 ? value : -value;
    }

    public static int ceiling_float_int(float value)
    {
        int i = (int)value;
        return value > (float)i ? i + 1 : i;
    }

    public static int ceiling_double_int(double value)
    {
        int i = (int)value;
        return value > (double)i ? i + 1 : i;
    }

    /**
     * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
     * third parameters.
     */
    public static int clamp_int(int num, int min, int max)
    {
        return num < min ? min : (num > max ? max : num);
    }

    /**
     * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
     * third parameters
     */
    public static float clamp_float(float num, float min, float max)
    {
        return num < min ? min : (num > max ? max : num);
    }

    public static double clamp_double(double num, double min, double max)
    {
        return num < min ? min : (num > max ? max : num);
    }

    public static double denormalizeClamp(double lowerBnd, double upperBnd, double slide)
    {
        return slide < 0.0D ? lowerBnd : (slide > 1.0D ? upperBnd : lowerBnd + (upperBnd - lowerBnd) * slide);
    }

    /**
     * Maximum of the absolute value of two numbers.
     */
    public static double abs_max(double x, double y)
    {
        if (x < 0.0D)
        {
            x = -x;
        }

        if (y < 0.0D)
        {
            y = -y;
        }

        return x > y ? x : y;
    }

    /**
     * Buckets an integer with specifed bucket sizes.  Args: i, bucketSize
     */
    public static int bucketInt(int x, int y)
    {
        return x < 0 ? -((-x - 1) / y) - 1 : x / y;
    }

    public static int getRandomIntegerInRange(Random random, int minimum, int maximum)
    {
        return minimum >= maximum ? minimum : random.nextInt(maximum - minimum + 1) + minimum;
    }

    public static float randomFloatClamp(Random random, float minimum, float maximum)
    {
        return minimum >= maximum ? minimum : random.nextFloat() * (maximum - minimum) + minimum;
    }

    public static double getRandomDoubleInRange(Random random, double minimum, double maximum)
    {
        return minimum >= maximum ? minimum : random.nextDouble() * (maximum - minimum) + minimum;
    }

    public static double average(long[] values)
    {
        long i = 0L;

        for (long j : values)
        {
            i += j;
        }

        return (double)i / (double)values.length;
    }

    public static boolean epsilonEquals(float x, float y)
    {
        return abs(y - x) < 1.0E-5F;
    }

    public static int normalizeAngle(int x, int y)
    {
        return (x % y + y) % y;
    }

    /**
     * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
     */
    public static float wrapAngleTo180_float(float value)
    {
        value = value % 360.0F;

        if (value >= 180.0F)
        {
            value -= 360.0F;
        }

        if (value < -180.0F)
        {
            value += 360.0F;
        }

        return value;
    }

    /**
     * the angle is reduced to an angle between -180 and +180 by mod, and a 360 check
     */
    public static double wrapAngleTo180_double(double value)
    {
        value = value % 360.0D;

        if (value >= 180.0D)
        {
            value -= 360.0D;
        }

        if (value < -180.0D)
        {
            value += 360.0D;
        }

        return value;
    }

    /**
     * parses the string as integer or returns the second parameter if it fails
     */
    public static int parseIntWithDefault(String value, int defaultValue)
    {
        try
        {
            return Integer.parseInt(value);
        }
        catch (Throwable var3)
        {
            return defaultValue;
        }
    }

    /**
     * parses the string as integer or returns the second parameter if it fails. this value is capped to par2
     */
    public static int parseIntWithDefaultAndMax(String value, int defaultValue, int max)
    {
        return Math.max(max, parseIntWithDefault(value, defaultValue));
    }

    /**
     * parses the string as double or returns the second parameter if it fails.
     */
    public static double parseDoubleWithDefault(String p_82712_0_, double p_82712_1_)
    {
        try
        {
            return Double.parseDouble(p_82712_0_);
        }
        catch (Throwable var4)
        {
            return p_82712_1_;
        }
    }

    public static double parseDoubleWithDefaultAndMax(String p_82713_0_, double p_82713_1_, double p_82713_3_)
    {
        return Math.max(p_82713_3_, parseDoubleWithDefault(p_82713_0_, p_82713_1_));
    }

    /**
     * Returns the input value rounded up to the next highest power of two.
     */
    public static int roundUpToPowerOfTwo(int value)
    {
        int i = value - 1;
        i = i | i >> 1;
        i = i | i >> 2;
        i = i | i >> 4;
        i = i | i >> 8;
        i = i | i >> 16;
        return i + 1;
    }

    /**
     * Is the given value a power of two?  (1, 2, 4, 8, 16, ...)
     */
    private static boolean isPowerOfTwo(int value)
    {
        return value != 0 && (value & value - 1) == 0;
    }

    /**
     * Uses a B(2, 5) De Bruijn sequence and a lookup table to efficiently calculate the log-base-two of the given
     * value.  Optimized for cases where the input value is a power-of-two.  If the input value is not a power-of-two,
     * then subtract 1 from the return value.
     */
    private static int calculateLogBaseTwoDeBruijn(int value)
    {
        value = isPowerOfTwo(value) ? value : roundUpToPowerOfTwo(value);
        return multiplyDeBruijnBitPosition[(int)((long)value * 125613361L >> 27) & 31];
    }

    /**
     * Efficiently calculates the floor of the base-2 log of an integer value.  This is effectively the index of the
     * highest bit that is set.  For example, if the number in binary is 0...100101, this will return 5.
     */
    public static int calculateLogBaseTwo(int value)
    {
        return calculateLogBaseTwoDeBruijn(value) - (isPowerOfTwo(value) ? 0 : 1);
    }

    public static int roundUp(int number, int interval)
    {
        if (interval == 0)
        {
            return 0;
        }
        else if (number == 0)
        {
            return interval;
        }
        else
        {
            if (number < 0)
            {
                interval *= -1;
            }

            int i = number % interval;
            return i == 0 ? number : number + interval - i;
        }
    }

    public static int rgb(float rIn, float gIn, float bIn)
    {
        return rgb(floor_float(rIn * 255.0F), floor_float(gIn * 255.0F), floor_float(bIn * 255.0F));
    }

    public static int rgb(int rIn, int gIn, int bIn)
    {
        int i = (rIn << 8) + gIn;
        i = (i << 8) + bIn;
        return i;
    }

    public static int func_180188_d(int p_180188_0_, int p_180188_1_)
    {
        int i = (p_180188_0_ & 16711680) >> 16;
        int j = (p_180188_1_ & 16711680) >> 16;
        int k = (p_180188_0_ & 65280) >> 8;
        int l = (p_180188_1_ & 65280) >> 8;
        int i1 = (p_180188_0_ & 255) >> 0;
        int j1 = (p_180188_1_ & 255) >> 0;
        int k1 = (int)((float)i * (float)j / 255.0F);
        int l1 = (int)((float)k * (float)l / 255.0F);
        int i2 = (int)((float)i1 * (float)j1 / 255.0F);
        return p_180188_0_ & -16777216 | k1 << 16 | l1 << 8 | i2;
    }

    public static double frac(double number)
    {
        return number - Math.floor(number);
    }

    public static long getPositionRandom(Vec3i pos)
    {
        return getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
    }

    public static long getCoordinateRandom(int x, int y, int z)
    {
        long i = (long)(x * 3129871) ^ (long)z * 116129781L ^ (long)y;
        i = i * i * 42317861L + i * 11L;
        return i;
    }

    public static UUID getRandomUuid(Random rand)
    {
        long i = rand.nextLong() & -61441L | 16384L;
        long j = rand.nextLong() & 4611686018427387903L | Long.MIN_VALUE;
        return new UUID(i, j);
    }

    public static double pct(double p_181160_0_, double p_181160_2_, double p_181160_4_)
    {
        return (p_181160_0_ - p_181160_2_) / (p_181160_4_ - p_181160_2_);
    }

    public static double atan2(double p_181159_0_, double p_181159_2_)
    {
        double d0 = p_181159_2_ * p_181159_2_ + p_181159_0_ * p_181159_0_;

        if (Double.isNaN(d0))
        {
            return Double.NaN;
        }
        else
        {
            boolean flag = p_181159_0_ < 0.0D;

            if (flag)
            {
                p_181159_0_ = -p_181159_0_;
            }

            boolean flag1 = p_181159_2_ < 0.0D;

            if (flag1)
            {
                p_181159_2_ = -p_181159_2_;
            }

            boolean flag2 = p_181159_0_ > p_181159_2_;

            if (flag2)
            {
                double d1 = p_181159_2_;
                p_181159_2_ = p_181159_0_;
                p_181159_0_ = d1;
            }

            double d9 = fastInvSqrt(d0);
            p_181159_2_ = p_181159_2_ * d9;
            p_181159_0_ = p_181159_0_ * d9;
            double d2 = FRAC_BIAS + p_181159_0_;
            int i = (int)Double.doubleToRawLongBits(d2);
            double d3 = ASINE_TAB[i];
            double d4 = COS_TAB[i];
            double d5 = d2 - FRAC_BIAS;
            double d6 = p_181159_0_ * d4 - p_181159_2_ * d5;
            double d7 = (6.0D + d6 * d6) * d6 * 0.16666666666666666D;
            double d8 = d3 + d7;

            if (flag2)
            {
                d8 = (Math.PI / 2D) - d8;
            }

            if (flag1)
            {
                d8 = Math.PI - d8;
            }

            if (flag)
            {
                d8 = -d8;
            }

            return d8;
        }
    }

    public static double fastInvSqrt(double number)
    {
        double d0 = 0.5D * number;
        long i = Double.doubleToRawLongBits(number);
        i = 6910469410427058090L - (i >> 1);
        number = Double.longBitsToDouble(i);
        number = number * (1.5D - d0 * number * number);
        return number;
    }

    public static int hsvToRGB(float hue, float saturation, float value)
    {
        int i = (int)(hue * 6.0F) % 6;
        float f = hue * 6.0F - (float)i;
        float f1 = value * (1.0F - saturation);
        float f2 = value * (1.0F - f * saturation);
        float f3 = value * (1.0F - (1.0F - f) * saturation);
        float f4;
        float f5;
        float f6;

        switch (i)
        {
            case 0:
                f4 = value;
                f5 = f3;
                f6 = f1;
                break;

            case 1:
                f4 = f2;
                f5 = value;
                f6 = f1;
                break;

            case 2:
                f4 = f1;
                f5 = value;
                f6 = f3;
                break;

            case 3:
                f4 = f1;
                f5 = f2;
                f6 = value;
                break;

            case 4:
                f4 = f3;
                f5 = f1;
                f6 = value;
                break;

            case 5:
                f4 = value;
                f5 = f1;
                f6 = f2;
                break;

            default:
                throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }

        int j = clamp_int((int)(f4 * 255.0F), 0, 255);
        int k = clamp_int((int)(f5 * 255.0F), 0, 255);
        int l = clamp_int((int)(f6 * 255.0F), 0, 255);
        return j << 16 | k << 8 | l;
    }

    static
    {
        for (int i = 0; i < 65536; ++i)
        {
            SIN_TABLE[i] = (float)Math.sin((double)i * Math.PI * 2.0D / 65536.0D);
        }

        for (int j = 0; j < 4096; ++j)
        {
            SIN_TABLE_FAST[j] = (float)Math.sin((double)(((float)j + 0.5F) / 4096.0F * ((float)Math.PI * 2F)));
        }

        for (int l = 0; l < 360; l += 90)
        {
            SIN_TABLE_FAST[(int)((float)l * 11.377778F) & 4095] = (float)Math.sin((double)((float)l * 0.017453292F));
        }

        multiplyDeBruijnBitPosition = new int[] {0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9};
        FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
        ASINE_TAB = new double[257];
        COS_TAB = new double[257];

        for (int k = 0; k < 257; ++k)
        {
            double d1 = (double)k / 256.0D;
            double d0 = Math.asin(d1);
            COS_TAB[k] = Math.cos(d0);
            ASINE_TAB[k] = d0;
        }
    }
}
