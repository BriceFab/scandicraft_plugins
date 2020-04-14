package net.minecraft.util;

public class Vec4b
{
    private byte field_176117_a;
    private byte x;
    private byte y;
    private byte rotation;

    public Vec4b(byte p_i45555_1_, byte p_i45555_2_, byte p_i45555_3_, byte p_i45555_4_)
    {
        this.field_176117_a = p_i45555_1_;
        this.x = p_i45555_2_;
        this.y = p_i45555_3_;
        this.rotation = p_i45555_4_;
    }

    public Vec4b(Vec4b p_i45556_1_)
    {
        this.field_176117_a = p_i45556_1_.field_176117_a;
        this.x = p_i45556_1_.x;
        this.y = p_i45556_1_.y;
        this.rotation = p_i45556_1_.rotation;
    }

    public byte getImage()
    {
        return this.field_176117_a;
    }

    public byte getX()
    {
        return this.x;
    }

    public byte getY()
    {
        return this.y;
    }

    public byte getRotation()
    {
        return this.rotation;
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof Vec4b))
        {
            return false;
        }
        else
        {
            Vec4b vec4b = (Vec4b)p_equals_1_;
            return this.field_176117_a != vec4b.field_176117_a ? false : (this.rotation != vec4b.rotation ? false : (this.x != vec4b.x ? false : this.y == vec4b.y));
        }
    }

    public int hashCode()
    {
        int i = this.field_176117_a;
        i = 31 * i + this.x;
        i = 31 * i + this.y;
        i = 31 * i + this.rotation;
        return i;
    }
}
