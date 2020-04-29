package net.minecraft.client.renderer;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import optifine.Config;
import optifine.TextureUtils;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;
import shadersmod.client.SVertexBuilder;

import java.nio.*;
import java.util.Arrays;
import java.util.BitSet;

public class WorldRenderer {
    private ByteBuffer byteBuffer;
    public IntBuffer rawIntBuffer;
    private ShortBuffer field_181676_c;
    public FloatBuffer rawFloatBuffer;
    public int vertexCount;
    private VertexFormatElement vertexFormatElement;
    private int vertexFormatIndex;

    /**
     * Boolean for whether this renderer needs to be updated or not
     */
    private boolean needsUpdate;
    public int drawMode;
    private double xOffset;
    private double yOffset;
    private double zOffset;
    private VertexFormat vertexFormat;
    private boolean isDrawing;
    private EnumWorldBlockLayer blockLayer = null;
    private boolean[] drawnIcons = new boolean[256];
    private TextureAtlasSprite[] quadSprites = null;
    private TextureAtlasSprite[] quadSpritesPrev = null;
    private TextureAtlasSprite quadSprite = null;
    public SVertexBuilder sVertexBuilder;

    public WorldRenderer(int bufferSizeIn) {
        if (Config.isShaders()) {
            bufferSizeIn *= 2;
        }

        this.byteBuffer = GLAllocation.createDirectByteBuffer(bufferSizeIn * 4);
        this.rawIntBuffer = this.byteBuffer.asIntBuffer();
        this.field_181676_c = this.byteBuffer.asShortBuffer();
        this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
        SVertexBuilder.initVertexBuilder(this);
    }

    private void growBuffer(int increaseAmount) {
        if (Config.isShaders()) {
            increaseAmount *= 2;
        }

        if (increaseAmount > this.rawIntBuffer.remaining()) {
            int i = this.byteBuffer.capacity();
            int j = i % 2097152;
            int k = j + (((this.rawIntBuffer.position() + increaseAmount) * 4 - j) / 2097152 + 1) * 2097152;
            LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + i + " bytes, new size " + k + " bytes.");
            int l = this.rawIntBuffer.position();
            ByteBuffer bytebuffer = GLAllocation.createDirectByteBuffer(k);
            this.byteBuffer.position(0);
            bytebuffer.put(this.byteBuffer);
            bytebuffer.rewind();
            this.byteBuffer = bytebuffer;
            this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
            this.rawIntBuffer = this.byteBuffer.asIntBuffer();
            this.rawIntBuffer.position(l);
            this.field_181676_c = this.byteBuffer.asShortBuffer();
            this.field_181676_c.position(l << 1);

            if (this.quadSprites != null) {
                TextureAtlasSprite[] atextureatlassprite = this.quadSprites;
                int i1 = this.getBufferQuadSize();
                this.quadSprites = new TextureAtlasSprite[i1];
                System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, Math.min(atextureatlassprite.length, this.quadSprites.length));
                this.quadSpritesPrev = null;
            }
        }
    }

    public void sortVertexData(float cameraX, float cameraY, float cameraZ) {
        int i = this.vertexCount / 4;
        float[] afloat = new float[i];

        for (int j = 0; j < i; ++j) {
            afloat[j] = getDistanceSq(this.rawFloatBuffer, (float) ((double) cameraX + this.xOffset), (float) ((double) cameraY + this.yOffset), (float) ((double) cameraZ + this.zOffset), this.vertexFormat.getIntegerSize(), j * this.vertexFormat.getNextOffset());
        }

        Integer[] ainteger = new Integer[i];

        for (int k = 0; k < ainteger.length; ++k) {
            ainteger[k] = k;
        }

        Arrays.sort(ainteger, new WorldRenderer$1(this, afloat));
        BitSet bitset = new BitSet();
        int l = this.vertexFormat.getNextOffset();
        int[] aint = new int[l];

        for (int l1 = 0; (l1 = bitset.nextClearBit(l1)) < ainteger.length; ++l1) {
            int i1 = ainteger[l1];

            if (i1 != l1) {
                this.rawIntBuffer.limit(i1 * l + l);
                this.rawIntBuffer.position(i1 * l);
                this.rawIntBuffer.get(aint);
                int j1 = i1;

                for (int k1 = ainteger[i1]; j1 != l1; k1 = ainteger[k1]) {
                    this.rawIntBuffer.limit(k1 * l + l);
                    this.rawIntBuffer.position(k1 * l);
                    IntBuffer intbuffer = this.rawIntBuffer.slice();
                    this.rawIntBuffer.limit(j1 * l + l);
                    this.rawIntBuffer.position(j1 * l);
                    this.rawIntBuffer.put(intbuffer);
                    bitset.set(j1);
                    j1 = k1;
                }

                this.rawIntBuffer.limit(l1 * l + l);
                this.rawIntBuffer.position(l1 * l);
                this.rawIntBuffer.put(aint);
            }

            bitset.set(l1);
        }

        this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
        this.rawIntBuffer.position(this.func_181664_j());

        if (this.quadSprites != null) {
            TextureAtlasSprite[] atextureatlassprite = new TextureAtlasSprite[this.vertexCount / 4];

            for (int j2 = 0; j2 < ainteger.length; ++j2) {
                int k2 = ainteger[j2];
                atextureatlassprite[j2] = this.quadSprites[k2];
            }

            System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
        }
    }

    public WorldRenderer.State getVertexState() {
        this.rawIntBuffer.rewind();
        int i = this.func_181664_j();
        this.rawIntBuffer.limit(i);
        int[] aint = new int[i];
        this.rawIntBuffer.get(aint);
        this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
        this.rawIntBuffer.position(i);
        TextureAtlasSprite[] atextureatlassprite = null;

        if (this.quadSprites != null) {
            int j = this.vertexCount / 4;
            atextureatlassprite = new TextureAtlasSprite[j];
            System.arraycopy(this.quadSprites, 0, atextureatlassprite, 0, j);
        }

        return new State(aint, new VertexFormat(this.vertexFormat), atextureatlassprite);
    }

    public int func_181664_j() {
        return this.vertexCount * this.vertexFormat.getIntegerSize();
    }

    private static float getDistanceSq(FloatBuffer floatBufferIn, float x, float y, float z, int integerSize, int offset) {
        float f = floatBufferIn.get(offset);
        float f1 = floatBufferIn.get(offset + 1);
        float f2 = floatBufferIn.get(offset + 2);
        float f3 = floatBufferIn.get(offset + integerSize);
        float f4 = floatBufferIn.get(offset + integerSize + 1);
        float f5 = floatBufferIn.get(offset + integerSize + 2);
        float f6 = floatBufferIn.get(offset + integerSize * 2);
        float f7 = floatBufferIn.get(offset + integerSize * 2 + 1);
        float f8 = floatBufferIn.get(offset + integerSize * 2 + 2);
        float f9 = floatBufferIn.get(offset + integerSize * 3);
        float f10 = floatBufferIn.get(offset + integerSize * 3 + 1);
        float f11 = floatBufferIn.get(offset + integerSize * 3 + 2);
        float f12 = (f + f3 + f6 + f9) * 0.25F - x;
        float f13 = (f1 + f4 + f7 + f10) * 0.25F - y;
        float f14 = (f2 + f5 + f8 + f11) * 0.25F - z;
        return f12 * f12 + f13 * f13 + f14 * f14;
    }

    public void setVertexState(WorldRenderer.State state) {
        this.rawIntBuffer.clear();
        this.growBuffer(state.getRawBuffer().length);
        this.rawIntBuffer.put(state.getRawBuffer());
        this.vertexCount = state.getVertexCount();
        this.vertexFormat = new VertexFormat(state.getVertexFormat());

        if (state.stateQuadSprites != null) {
            if (this.quadSprites == null) {
                this.quadSprites = this.quadSpritesPrev;
            }

            if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
                this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
            }

            TextureAtlasSprite[] atextureatlassprite = state.stateQuadSprites;
            System.arraycopy(atextureatlassprite, 0, this.quadSprites, 0, atextureatlassprite.length);
        } else {
            if (this.quadSprites != null) {
                this.quadSpritesPrev = this.quadSprites;
            }

            this.quadSprites = null;
        }
    }

    public void reset() {
        this.vertexCount = 0;
        this.vertexFormatElement = null;
        this.vertexFormatIndex = 0;
        this.quadSprite = null;
    }

    public void begin(int glMode, VertexFormat format) {
        if (this.isDrawing) {
            throw new IllegalStateException("Already building!");
        } else {
            this.isDrawing = true;
            this.reset();
            this.drawMode = glMode;
            this.vertexFormat = format;
            this.vertexFormatElement = format.getElement(this.vertexFormatIndex);
            this.needsUpdate = false;
            this.byteBuffer.limit(this.byteBuffer.capacity());

            if (Config.isShaders()) {
                SVertexBuilder.endSetVertexFormat(this);
            }

            if (Config.isMultiTexture()) {
                if (this.blockLayer != null) {
                    if (this.quadSprites == null) {
                        this.quadSprites = this.quadSpritesPrev;
                    }

                    if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
                        this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
                    }
                }
            } else {
                if (this.quadSprites != null) {
                    this.quadSpritesPrev = this.quadSprites;
                }

                this.quadSprites = null;
            }
        }
    }

    public WorldRenderer tex(double p_181673_1_, double p_181673_3_) {
        if (this.quadSprite != null && this.quadSprites != null) {
            p_181673_1_ = this.quadSprite.toSingleU((float) p_181673_1_);
            p_181673_3_ = this.quadSprite.toSingleV((float) p_181673_3_);
            this.quadSprites[this.vertexCount / 4] = this.quadSprite;
        }

        int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.vertexFormatIndex);

        switch (WorldRenderer.WorldRenderer$2.field_181661_a[this.vertexFormatElement.getType().ordinal()]) {
            case 1:
                this.byteBuffer.putFloat(i, (float) p_181673_1_);
                this.byteBuffer.putFloat(i + 4, (float) p_181673_3_);
                break;

            case 2:
            case 3:
                this.byteBuffer.putInt(i, (int) p_181673_1_);
                this.byteBuffer.putInt(i + 4, (int) p_181673_3_);
                break;

            case 4:
            case 5:
                this.byteBuffer.putShort(i, (short) ((int) p_181673_3_));
                this.byteBuffer.putShort(i + 2, (short) ((int) p_181673_1_));
                break;

            case 6:
            case 7:
                this.byteBuffer.put(i, (byte) ((int) p_181673_3_));
                this.byteBuffer.put(i + 1, (byte) ((int) p_181673_1_));
        }

        this.nextVertexFormatIndex();
        return this;
    }

    public WorldRenderer lightmap(int p_181671_1_, int p_181671_2_) {
        int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.vertexFormatIndex);

        switch (WorldRenderer.WorldRenderer$2.field_181661_a[this.vertexFormatElement.getType().ordinal()]) {
            case 1:
                this.byteBuffer.putFloat(i, (float) p_181671_1_);
                this.byteBuffer.putFloat(i + 4, (float) p_181671_2_);
                break;

            case 2:
            case 3:
                this.byteBuffer.putInt(i, p_181671_1_);
                this.byteBuffer.putInt(i + 4, p_181671_2_);
                break;

            case 4:
            case 5:
                this.byteBuffer.putShort(i, (short) p_181671_2_);
                this.byteBuffer.putShort(i + 2, (short) p_181671_1_);
                break;

            case 6:
            case 7:
                this.byteBuffer.put(i, (byte) p_181671_2_);
                this.byteBuffer.put(i + 1, (byte) p_181671_1_);
        }

        this.nextVertexFormatIndex();
        return this;
    }

    public void putBrightness4(int p_178962_1_, int p_178962_2_, int p_178962_3_, int p_178962_4_) {
        int i = (this.vertexCount - 4) * this.vertexFormat.getIntegerSize() + this.vertexFormat.getUvOffsetById(1) / 4;
        int j = this.vertexFormat.getNextOffset() >> 2;
        this.rawIntBuffer.put(i, p_178962_1_);
        this.rawIntBuffer.put(i + j, p_178962_2_);
        this.rawIntBuffer.put(i + j * 2, p_178962_3_);
        this.rawIntBuffer.put(i + j * 3, p_178962_4_);
    }

    public void putPosition(double x, double y, double z) {
        int i = this.vertexFormat.getIntegerSize();
        int j = (this.vertexCount - 4) * i;

        for (int k = 0; k < 4; ++k) {
            int l = j + k * i;
            int i1 = l + 1;
            int j1 = i1 + 1;
            this.rawIntBuffer.put(l, Float.floatToRawIntBits((float) (x + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(l))));
            this.rawIntBuffer.put(i1, Float.floatToRawIntBits((float) (y + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(i1))));
            this.rawIntBuffer.put(j1, Float.floatToRawIntBits((float) (z + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(j1))));
        }
    }

    /**
     * Takes in the pass the call list is being requested for. Args: renderPass
     */
    public int getColorIndex(int p_78909_1_) {
        return ((this.vertexCount - p_78909_1_) * this.vertexFormat.getNextOffset() + this.vertexFormat.getColorOffset()) / 4;
    }

    public void putColorMultiplier(float red, float green, float blue, int p_178978_4_) {
        int i = this.getColorIndex(p_178978_4_);
        int j = -1;

        if (!this.needsUpdate) {
            j = this.rawIntBuffer.get(i);

            if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                int k = (int) ((float) (j & 255) * red);
                int l = (int) ((float) (j >> 8 & 255) * green);
                int i1 = (int) ((float) (j >> 16 & 255) * blue);
                j = j & -16777216;
                j = j | i1 << 16 | l << 8 | k;
            } else {
                int j1 = (int) ((float) (j >> 24 & 255) * red);
                int k1 = (int) ((float) (j >> 16 & 255) * green);
                int l1 = (int) ((float) (j >> 8 & 255) * blue);
                j = j & 255;
                j = j | j1 << 24 | k1 << 16 | l1 << 8;
            }
        }

        this.rawIntBuffer.put(i, j);
    }

    private void putColor(int argb, int p_178988_2_) {
        int i = this.getColorIndex(p_178988_2_);
        int j = argb >> 16 & 255;
        int k = argb >> 8 & 255;
        int l = argb & 255;
        int i1 = argb >> 24 & 255;
        this.putColorRGBA(i, j, k, l, i1);
    }

    public void putColorRGB_F(float red, float green, float blue, int p_178994_4_) {
        int i = this.getColorIndex(p_178994_4_);
        int j = MathHelper.clamp_int((int) (red * 255.0F), 0, 255);
        int k = MathHelper.clamp_int((int) (green * 255.0F), 0, 255);
        int l = MathHelper.clamp_int((int) (blue * 255.0F), 0, 255);
        this.putColorRGBA(i, j, k, l, 255);
    }

    public void putColorRGBA(int index, int red, int p_178972_3_, int p_178972_4_, int p_178972_5_) {
        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.rawIntBuffer.put(index, p_178972_5_ << 24 | p_178972_4_ << 16 | p_178972_3_ << 8 | red);
        } else {
            this.rawIntBuffer.put(index, red << 24 | p_178972_3_ << 16 | p_178972_4_ << 8 | p_178972_5_);
        }
    }

    /**
     * Marks the current renderer data as dirty and needing to be updated.
     */
    public void markDirty() {
        this.needsUpdate = true;
    }

    public WorldRenderer color(float p_181666_1_, float p_181666_2_, float p_181666_3_, float p_181666_4_) {
        return this.color((int) (p_181666_1_ * 255.0F), (int) (p_181666_2_ * 255.0F), (int) (p_181666_3_ * 255.0F), (int) (p_181666_4_ * 255.0F));
    }

    public WorldRenderer color(int p_181669_1_, int p_181669_2_, int p_181669_3_, int p_181669_4_) {
        if (!this.needsUpdate) {
            int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.vertexFormatIndex);

            switch (WorldRenderer$2.field_181661_a[this.vertexFormatElement.getType().ordinal()]) {
                case 1:
                    this.byteBuffer.putFloat(i, (float) p_181669_1_ / 255.0F);
                    this.byteBuffer.putFloat(i + 4, (float) p_181669_2_ / 255.0F);
                    this.byteBuffer.putFloat(i + 8, (float) p_181669_3_ / 255.0F);
                    this.byteBuffer.putFloat(i + 12, (float) p_181669_4_ / 255.0F);
                    break;

                case 2:
                case 3:
                    this.byteBuffer.putFloat(i, (float) p_181669_1_);
                    this.byteBuffer.putFloat(i + 4, (float) p_181669_2_);
                    this.byteBuffer.putFloat(i + 8, (float) p_181669_3_);
                    this.byteBuffer.putFloat(i + 12, (float) p_181669_4_);
                    break;

                case 4:
                case 5:
                    this.byteBuffer.putShort(i, (short) p_181669_1_);
                    this.byteBuffer.putShort(i + 2, (short) p_181669_2_);
                    this.byteBuffer.putShort(i + 4, (short) p_181669_3_);
                    this.byteBuffer.putShort(i + 6, (short) p_181669_4_);
                    break;

                case 6:
                case 7:
                    if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
                        this.byteBuffer.put(i, (byte) p_181669_1_);
                        this.byteBuffer.put(i + 1, (byte) p_181669_2_);
                        this.byteBuffer.put(i + 2, (byte) p_181669_3_);
                        this.byteBuffer.put(i + 3, (byte) p_181669_4_);
                    } else {
                        this.byteBuffer.put(i, (byte) p_181669_4_);
                        this.byteBuffer.put(i + 1, (byte) p_181669_3_);
                        this.byteBuffer.put(i + 2, (byte) p_181669_2_);
                        this.byteBuffer.put(i + 3, (byte) p_181669_1_);
                    }
            }

            this.nextVertexFormatIndex();
        }
        return this;
    }

    public void addVertexData(int[] vertexData) {
        if (Config.isShaders()) {
            SVertexBuilder.beginAddVertexData(this, vertexData);
        }

        this.growBuffer(vertexData.length);
        this.rawIntBuffer.position(this.func_181664_j());
        this.rawIntBuffer.put(vertexData);
        this.vertexCount += vertexData.length / this.vertexFormat.getIntegerSize();

        if (Config.isShaders()) {
            SVertexBuilder.endAddVertexData(this);
        }
    }

    public void endVertex() {
        ++this.vertexCount;
        this.growBuffer(this.vertexFormat.getIntegerSize());
        this.vertexFormatIndex = 0;
        this.vertexFormatElement = this.vertexFormat.getElement(this.vertexFormatIndex);

        if (Config.isShaders()) {
            SVertexBuilder.endAddVertex(this);
        }
    }

    public WorldRenderer pos(double p_181662_1_, double p_181662_3_, double p_181662_5_) {
        if (Config.isShaders()) {
            SVertexBuilder.beginAddVertex(this);
        }

        int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.vertexFormatIndex);

        switch (WorldRenderer.WorldRenderer$2.field_181661_a[this.vertexFormatElement.getType().ordinal()]) {
            case 1:
                this.byteBuffer.putFloat(i, (float) (p_181662_1_ + this.xOffset));
                this.byteBuffer.putFloat(i + 4, (float) (p_181662_3_ + this.yOffset));
                this.byteBuffer.putFloat(i + 8, (float) (p_181662_5_ + this.zOffset));
                break;

            case 2:
            case 3:
                this.byteBuffer.putInt(i, Float.floatToRawIntBits((float) (p_181662_1_ + this.xOffset)));
                this.byteBuffer.putInt(i + 4, Float.floatToRawIntBits((float) (p_181662_3_ + this.yOffset)));
                this.byteBuffer.putInt(i + 8, Float.floatToRawIntBits((float) (p_181662_5_ + this.zOffset)));
                break;

            case 4:
            case 5:
                this.byteBuffer.putShort(i, (short) ((int) (p_181662_1_ + this.xOffset)));
                this.byteBuffer.putShort(i + 2, (short) ((int) (p_181662_3_ + this.yOffset)));
                this.byteBuffer.putShort(i + 4, (short) ((int) (p_181662_5_ + this.zOffset)));
                break;

            case 6:
            case 7:
                this.byteBuffer.put(i, (byte) ((int) (p_181662_1_ + this.xOffset)));
                this.byteBuffer.put(i + 1, (byte) ((int) (p_181662_3_ + this.yOffset)));
                this.byteBuffer.put(i + 2, (byte) ((int) (p_181662_5_ + this.zOffset)));
        }

        this.nextVertexFormatIndex();
        return this;
    }

    public void putNormal(float x, float y, float z) {
        int i = (byte) ((int) (x * 127.0F)) & 255;
        int j = (byte) ((int) (y * 127.0F)) & 255;
        int k = (byte) ((int) (z * 127.0F)) & 255;
        int l = i | j << 8 | k << 16;
        int i1 = this.vertexFormat.getNextOffset() >> 2;
        int j1 = (this.vertexCount - 4) * i1 + this.vertexFormat.getNormalOffset() / 4;
        this.rawIntBuffer.put(j1, l);
        this.rawIntBuffer.put(j1 + i1, l);
        this.rawIntBuffer.put(j1 + i1 * 2, l);
        this.rawIntBuffer.put(j1 + i1 * 3, l);
    }

    private void nextVertexFormatIndex() {
        ++this.vertexFormatIndex;
        this.vertexFormatIndex %= this.vertexFormat.getElementCount();
        this.vertexFormatElement = this.vertexFormat.getElement(this.vertexFormatIndex);

        if (this.vertexFormatElement.getUsage() == VertexFormatElement.EnumUsage.PADDING) {
            this.nextVertexFormatIndex();
        }
    }

    public WorldRenderer normal(float p_181663_1_, float p_181663_2_, float p_181663_3_) {
        int i = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.vertexFormatIndex);

        switch (WorldRenderer.WorldRenderer$2.field_181661_a[this.vertexFormatElement.getType().ordinal()]) {
            case 1:
                this.byteBuffer.putFloat(i, p_181663_1_);
                this.byteBuffer.putFloat(i + 4, p_181663_2_);
                this.byteBuffer.putFloat(i + 8, p_181663_3_);
                break;

            case 2:
            case 3:
                this.byteBuffer.putInt(i, (int) p_181663_1_);
                this.byteBuffer.putInt(i + 4, (int) p_181663_2_);
                this.byteBuffer.putInt(i + 8, (int) p_181663_3_);
                break;

            case 4:
            case 5:
                this.byteBuffer.putShort(i, (short) ((int) (p_181663_1_ * 32767.0F) & 65535));
                this.byteBuffer.putShort(i + 2, (short) ((int) (p_181663_2_ * 32767.0F) & 65535));
                this.byteBuffer.putShort(i + 4, (short) ((int) (p_181663_3_ * 32767.0F) & 65535));
                break;

            case 6:
            case 7:
                this.byteBuffer.put(i, (byte) ((int) (p_181663_1_ * 127.0F) & 255));
                this.byteBuffer.put(i + 1, (byte) ((int) (p_181663_2_ * 127.0F) & 255));
                this.byteBuffer.put(i + 2, (byte) ((int) (p_181663_3_ * 127.0F) & 255));
        }

        this.nextVertexFormatIndex();
        return this;
    }

    public void setTranslation(double x, double y, double z) {
        this.xOffset = x;
        this.yOffset = y;
        this.zOffset = z;
    }

    public void finishDrawing() {
        if (!this.isDrawing) {
            throw new IllegalStateException("Not building!");
        } else {
            this.isDrawing = false;
            this.byteBuffer.position(0);
            this.byteBuffer.limit(this.func_181664_j() * 4);
        }
    }

    public ByteBuffer getByteBuffer() {
        return this.byteBuffer;
    }

    public VertexFormat getVertexFormat() {
        return this.vertexFormat;
    }

    public int getVertexCount() {
        return this.vertexCount;
    }

    public int getDrawMode() {
        return this.drawMode;
    }

    public void putColor4(int argb) {
        for (int i = 0; i < 4; ++i) {
            this.putColor(argb, i + 1);
        }
    }

    public void putColorRGB_F4(float red, float green, float blue) {
        for (int i = 0; i < 4; ++i) {
            this.putColorRGB_F(red, green, blue, i + 1);
        }
    }

    public void putSprite(TextureAtlasSprite p_putSprite_1_) {
        if (this.quadSprites != null) {
            int i = this.vertexCount / 4;
            this.quadSprites[i - 1] = p_putSprite_1_;
        }
    }

    public void setSprite(TextureAtlasSprite p_setSprite_1_) {
        if (this.quadSprites != null) {
            this.quadSprite = p_setSprite_1_;
        }
    }

    public boolean isMultiTexture() {
        return this.quadSprites != null;
    }

    public void drawMultiTexture() {
        if (this.quadSprites != null) {
            int i = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();

            if (this.drawnIcons.length <= i) {
                this.drawnIcons = new boolean[i + 1];
            }

            Arrays.fill(this.drawnIcons, false);
            int k = -1;
            int l = this.vertexCount / 4;

            for (int i1 = 0; i1 < l; ++i1) {
                TextureAtlasSprite textureatlassprite = this.quadSprites[i1];

                if (textureatlassprite != null) {
                    int j1 = textureatlassprite.getIndexInMap();

                    if (!this.drawnIcons[j1]) {
                        if (textureatlassprite == TextureUtils.iconGrassSideOverlay) {
                            if (k < 0) {
                                k = i1;
                            }
                        } else {
                            i1 = this.drawForIcon(textureatlassprite, i1) - 1;

                            if (this.blockLayer != EnumWorldBlockLayer.TRANSLUCENT) {
                                this.drawnIcons[j1] = true;
                            }
                        }
                    }
                }
            }

            if (k >= 0) {
                this.drawForIcon(TextureUtils.iconGrassSideOverlay, k);
            }
        }
    }

    private int drawForIcon(TextureAtlasSprite p_drawForIcon_1_, int p_drawForIcon_2_) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, p_drawForIcon_1_.glSpriteTextureId);
        int i = -1;
        int j = -1;
        int k = this.vertexCount / 4;

        for (int l = p_drawForIcon_2_; l < k; ++l) {
            TextureAtlasSprite textureatlassprite = this.quadSprites[l];

            if (textureatlassprite == p_drawForIcon_1_) {
                if (j < 0) {
                    j = l;
                }
            } else if (j >= 0) {
                this.draw(j, l);

                if (this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
                    return l;
                }

                j = -1;

                if (i < 0) {
                    i = l;
                }
            }
        }

        if (j >= 0) {
            this.draw(j, k);
        }

        if (i < 0) {
            i = k;
        }

        return i;
    }

    private void draw(int p_draw_1_, int p_draw_2_) {
        int i = p_draw_2_ - p_draw_1_;

        if (i > 0) {
            int j = p_draw_1_ * 4;
            int k = i * 4;
            GL11.glDrawArrays(this.drawMode, j, k);
        }
    }

    public void setBlockLayer(EnumWorldBlockLayer p_setBlockLayer_1_) {
        this.blockLayer = p_setBlockLayer_1_;

        if (p_setBlockLayer_1_ == null) {
            if (this.quadSprites != null) {
                this.quadSpritesPrev = this.quadSprites;
            }

            this.quadSprites = null;
            this.quadSprite = null;
        }
    }

    private int getBufferQuadSize() {
        return this.rawIntBuffer.capacity() * 4 / (this.vertexFormat.getIntegerSize() * 4);
    }

    static final class WorldRenderer$2 {
        static final int[] field_181661_a = new int[VertexFormatElement.EnumType.values().length];

        static {
            try {
                field_181661_a[VertexFormatElement.EnumType.FLOAT.ordinal()] = 1;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_181661_a[VertexFormatElement.EnumType.UINT.ordinal()] = 2;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_181661_a[VertexFormatElement.EnumType.INT.ordinal()] = 3;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_181661_a[VertexFormatElement.EnumType.USHORT.ordinal()] = 4;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_181661_a[VertexFormatElement.EnumType.SHORT.ordinal()] = 5;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_181661_a[VertexFormatElement.EnumType.UBYTE.ordinal()] = 6;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_181661_a[VertexFormatElement.EnumType.BYTE.ordinal()] = 7;
            } catch (NoSuchFieldError ignored) {
            }
        }
    }

    public static class State {
        private final int[] stateRawBuffer;
        private final VertexFormat stateVertexFormat;
        private TextureAtlasSprite[] stateQuadSprites;

        public State(int[] p_i2_2_, VertexFormat p_i2_3_, TextureAtlasSprite[] p_i2_4_) {
            this.stateRawBuffer = p_i2_2_;
            this.stateVertexFormat = p_i2_3_;
            this.stateQuadSprites = p_i2_4_;
        }

        public State(int[] p_i46453_2_, VertexFormat p_i46453_3_) {
            this.stateRawBuffer = p_i46453_2_;
            this.stateVertexFormat = p_i46453_3_;
        }

        public int[] getRawBuffer() {
            return this.stateRawBuffer;
        }

        public int getVertexCount() {
            return this.stateRawBuffer.length / this.stateVertexFormat.getIntegerSize();
        }

        public VertexFormat getVertexFormat() {
            return this.stateVertexFormat;
        }
    }
}
