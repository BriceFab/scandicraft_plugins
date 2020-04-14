package net.minecraft.client.model;

import net.minecraft.util.Vec3;

public class PositionTextureVertex
{
    public Vec3 vector3D;
    public float texturePositionX;
    public float texturePositionY;

    public PositionTextureVertex(float x, float y, float z, float texU, float texV)
    {
        this(new Vec3((double)x, (double)y, (double)z), texU, texV);
    }

    public PositionTextureVertex setTexturePosition(float texU, float texV)
    {
        return new PositionTextureVertex(this, texU, texV);
    }

    public PositionTextureVertex(PositionTextureVertex textureVertex, float texturePositionXIn, float texturePositionYIn)
    {
        this.vector3D = textureVertex.vector3D;
        this.texturePositionX = texturePositionXIn;
        this.texturePositionY = texturePositionYIn;
    }

    public PositionTextureVertex(Vec3 vector3DIn, float texturePositionXIn, float texturePositionYIn)
    {
        this.vector3D = vector3DIn;
        this.texturePositionX = texturePositionXIn;
        this.texturePositionY = texturePositionYIn;
    }
}
