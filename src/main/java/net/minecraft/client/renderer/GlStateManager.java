package net.minecraft.client.renderer;

import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GlStateManager {
    private static final GlStateManager.AlphaState alphaState = new GlStateManager.AlphaState(null);
    private static final GlStateManager.BooleanState lightingState = new GlStateManager.BooleanState(2896);
    private static final GlStateManager.BooleanState[] lightState = new GlStateManager.BooleanState[8];
    private static final GlStateManager.ColorMaterialState colorMaterialState = new GlStateManager.ColorMaterialState(null);
    private static final GlStateManager.BlendState blendState = new GlStateManager.BlendState(null);
    private static final GlStateManager.DepthState depthState = new GlStateManager.DepthState(null);
    private static final GlStateManager.FogState fogState = new GlStateManager.FogState(null);
    private static final GlStateManager.CullState cullState = new GlStateManager.CullState(null);
    private static final GlStateManager.PolygonOffsetState polygonOffsetState = new GlStateManager.PolygonOffsetState(null);
    private static final GlStateManager.ColorLogicState colorLogicState = new GlStateManager.ColorLogicState(null);
    private static final GlStateManager.TexGenState texGenState = new GlStateManager.TexGenState(null);
    private static final GlStateManager.ClearState clearState = new GlStateManager.ClearState(null);
    private static final GlStateManager.BooleanState normalizeState = new GlStateManager.BooleanState(2977);
    private static int activeTextureUnit = 0;
    private static final GlStateManager.TextureState[] textureState = new GlStateManager.TextureState[32];
    private static int activeShadeModel = 7425;
    private static final GlStateManager.BooleanState rescaleNormalState = new GlStateManager.BooleanState(32826);
    private static final GlStateManager.ColorMask colorMaskState = new GlStateManager.ColorMask(null);
    private static final GlStateManager.Color colorState = new GlStateManager.Color();
    public static boolean clearEnabled = true;

    public static void pushAttrib() {
        GL11.glPushAttrib(8256);
    }

    public static void popAttrib() {
        GL11.glPopAttrib();
    }

    public static void disableAlpha() {
        alphaState.test.setDisabled();
    }

    public static void enableAlpha() {
        alphaState.test.setEnabled();
    }

    public static void alphaFunc(int func, float ref) {
        if (func != alphaState.func || ref != alphaState.ref) {
            alphaState.func = func;
            alphaState.ref = ref;
            GL11.glAlphaFunc(func, ref);
        }
    }

    public static void enableLighting() {
        lightingState.setEnabled();
    }

    public static void disableLighting() {
        lightingState.setDisabled();
    }

    public static void enableLight(int light) {
        lightState[light].setEnabled();
    }

    public static void disableLight(int light) {
        lightState[light].setDisabled();
    }

    public static void enableColorMaterial() {
        colorMaterialState.colorMaterial.setEnabled();
    }

    public static void disableColorMaterial() {
        colorMaterialState.colorMaterial.setDisabled();
    }

    public static void colorMaterial(int face, int mode) {
        if (face != colorMaterialState.face || mode != colorMaterialState.mode) {
            colorMaterialState.face = face;
            colorMaterialState.mode = mode;
            GL11.glColorMaterial(face, mode);
        }
    }

    public static void disableDepth() {
        depthState.depthTest.setDisabled();
    }

    public static void enableDepth() {
        depthState.depthTest.setEnabled();
    }

    public static void depthFunc(int depthFunc) {
        if (depthFunc != depthState.depthFunc) {
            depthState.depthFunc = depthFunc;
            GL11.glDepthFunc(depthFunc);
        }
    }

    public static void depthMask(boolean flagIn) {
        if (flagIn != depthState.maskEnabled) {
            depthState.maskEnabled = flagIn;
            GL11.glDepthMask(flagIn);
        }
    }

    public static void disableBlend() {
        blendState.blend.setDisabled();
    }

    public static void enableBlend() {
        blendState.blend.setEnabled();
    }

    public static void blendFunc(int srcFactor, int dstFactor) {
        if (srcFactor != blendState.srcFactor || dstFactor != blendState.dstFactor) {
            blendState.srcFactor = srcFactor;
            blendState.dstFactor = dstFactor;
            GL11.glBlendFunc(srcFactor, dstFactor);
        }
    }

    public static void tryBlendFuncSeparate(int srcFactor, int dstFactor, int srcFactorAlpha, int dstFactorAlpha) {
        if (srcFactor != blendState.srcFactor || dstFactor != blendState.dstFactor || srcFactorAlpha != blendState.srcFactorAlpha || dstFactorAlpha != blendState.dstFactorAlpha) {
            blendState.srcFactor = srcFactor;
            blendState.dstFactor = dstFactor;
            blendState.srcFactorAlpha = srcFactorAlpha;
            blendState.dstFactorAlpha = dstFactorAlpha;
            OpenGlHelper.glBlendFunc(srcFactor, dstFactor, srcFactorAlpha, dstFactorAlpha);
        }
    }

    public static void enableFog() {
        fogState.fog.setEnabled();
    }

    public static void disableFog() {
        fogState.fog.setDisabled();
    }

    public static void setFog(int param) {
        if (param != fogState.mode) {
            fogState.mode = param;
            GL11.glFogi(GL11.GL_FOG_MODE, param);
        }
    }

    public static void setFogDensity(float param) {
        if (param != fogState.density) {
            fogState.density = param;
            GL11.glFogf(GL11.GL_FOG_DENSITY, param);
        }
    }

    public static void setFogStart(float param) {
        if (param != fogState.start) {
            fogState.start = param;
            GL11.glFogf(GL11.GL_FOG_START, param);
        }
    }

    public static void setFogEnd(float param) {
        if (param != fogState.end) {
            fogState.end = param;
            GL11.glFogf(GL11.GL_FOG_END, param);
        }
    }

    public static void enableCull() {
        cullState.cullFace.setEnabled();
    }

    public static void disableCull() {
        cullState.cullFace.setDisabled();
    }

    public static void cullFace(int mode) {
        if (mode != cullState.mode) {
            cullState.mode = mode;
            GL11.glCullFace(mode);
        }
    }

    public static void enablePolygonOffset() {
        polygonOffsetState.polyOffset.setEnabled();
    }

    public static void disablePolygonOffset() {
        polygonOffsetState.polyOffset.setDisabled();
    }

    public static void doPolygonOffset(float factor, float units) {
        if (factor != polygonOffsetState.factor || units != polygonOffsetState.units) {
            polygonOffsetState.factor = factor;
            polygonOffsetState.units = units;
            GL11.glPolygonOffset(factor, units);
        }
    }

    public static void enableColorLogic() {
        colorLogicState.colorLogicOp.setEnabled();
    }

    public static void disableColorLogic() {
        colorLogicState.colorLogicOp.setDisabled();
    }

    public static void colorLogicOp(int opcode) {
        if (opcode != colorLogicState.logicOpcode) {
            colorLogicState.logicOpcode = opcode;
            GL11.glLogicOp(opcode);
        }
    }

    public static void enableTexGenCoord(GlStateManager.TexGen p_179087_0_) {
        texGenCoord(p_179087_0_).textureGen.setEnabled();
    }

    public static void disableTexGenCoord(GlStateManager.TexGen p_179100_0_) {
        texGenCoord(p_179100_0_).textureGen.setDisabled();
    }

    public static void texGen(GlStateManager.TexGen p_179149_0_, int p_179149_1_) {
        GlStateManager.TexGenCoord glstatemanager$texgencoord = texGenCoord(p_179149_0_);

        if (p_179149_1_ != glstatemanager$texgencoord.mode) {
            glstatemanager$texgencoord.mode = p_179149_1_;
            GL11.glTexGeni(glstatemanager$texgencoord.coord, GL11.GL_TEXTURE_GEN_MODE, p_179149_1_);
        }
    }

    public static void func_179105_a(GlStateManager.TexGen p_179105_0_, int pname, FloatBuffer params) {
        GL11.glTexGen(texGenCoord(p_179105_0_).coord, pname, params);
    }

    private static GlStateManager.TexGenCoord texGenCoord(GlStateManager.TexGen p_179125_0_) {
        switch (GlStateManager.GlStateManager$1.field_179175_a[p_179125_0_.ordinal()]) {
            case 2:
                return texGenState.t;

            case 3:
                return texGenState.r;

            case 4:
                return texGenState.q;

            default:
                return texGenState.s;
        }
    }

    public static void setActiveTexture(int texture) {
        if (activeTextureUnit != texture - OpenGlHelper.defaultTexUnit) {
            activeTextureUnit = texture - OpenGlHelper.defaultTexUnit;
            OpenGlHelper.setActiveTexture(texture);
        }
    }

    public static void enableTexture2D() {
        textureState[activeTextureUnit].texture2DState.setEnabled();
    }

    public static void disableTexture2D() {
        textureState[activeTextureUnit].texture2DState.setDisabled();
    }

    public static int generateTexture() {
        return GL11.glGenTextures();
    }

    public static void deleteTexture(int texture) {
        if (texture != 0) {
            GL11.glDeleteTextures(texture);

            for (GlStateManager.TextureState glstatemanager$texturestate : textureState) {
                if (glstatemanager$texturestate.textureName == texture) {
                    glstatemanager$texturestate.textureName = 0;
                }
            }
        }
    }

    public static void bindTexture(int texture) {
        if (texture != textureState[activeTextureUnit].textureName) {
            textureState[activeTextureUnit].textureName = texture;
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
        }
    }

    public static void bindCurrentTexture() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureState[activeTextureUnit].textureName);
    }

    public static void enableNormalize() {
        normalizeState.setEnabled();
    }

    public static void disableNormalize() {
        normalizeState.setDisabled();
    }

    public static void shadeModel(int mode) {
        if (mode != activeShadeModel) {
            activeShadeModel = mode;
            GL11.glShadeModel(mode);
        }
    }

    public static void enableRescaleNormal() {
        rescaleNormalState.setEnabled();
    }

    public static void disableRescaleNormal() {
        rescaleNormalState.setDisabled();
    }

    public static void viewport(int x, int y, int width, int height) {
        GL11.glViewport(x, y, width, height);
    }

    public static void colorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        if (red != colorMaskState.red || green != colorMaskState.green || blue != colorMaskState.blue || alpha != colorMaskState.alpha) {
            colorMaskState.red = red;
            colorMaskState.green = green;
            colorMaskState.blue = blue;
            colorMaskState.alpha = alpha;
            GL11.glColorMask(red, green, blue, alpha);
        }
    }

    public static void clearDepth(double depth) {
        if (depth != clearState.depth) {
            clearState.depth = depth;
            GL11.glClearDepth(depth);
        }
    }

    public static void clearColor(float red, float green, float blue, float alpha) {
        if (red != clearState.color.red || green != clearState.color.green || blue != clearState.color.blue || alpha != clearState.color.alpha) {
            clearState.color.red = red;
            clearState.color.green = green;
            clearState.color.blue = blue;
            clearState.color.alpha = alpha;
            GL11.glClearColor(red, green, blue, alpha);
        }
    }

    public static void clear(int mask) {
        if (clearEnabled) {
            GL11.glClear(mask);
        }
    }

    public static void matrixMode(int mode) {
        GL11.glMatrixMode(mode);
    }

    public static void loadIdentity() {
        GL11.glLoadIdentity();
    }

    public static void pushMatrix() {
        GL11.glPushMatrix();
    }

    public static void popMatrix() {
        GL11.glPopMatrix();
    }

    public static void getFloat(int pname, FloatBuffer params) {
        GL11.glGetFloat(pname, params);
    }

    public static void ortho(double left, double right, double bottom, double top, double zNear, double zFar) {
        GL11.glOrtho(left, right, bottom, top, zNear, zFar);
    }

    public static void rotate(float angle, float x, float y, float z) {
        GL11.glRotatef(angle, x, y, z);
    }

    public static void scale(float x, float y, float z) {
        GL11.glScalef(x, y, z);
    }

    public static void scale(double x, double y, double z) {
        GL11.glScaled(x, y, z);
    }

    public static void translate(float x, float y, float z) {
        GL11.glTranslatef(x, y, z);
    }

    public static void translate(double x, double y, double z) {
        GL11.glTranslated(x, y, z);
    }

    public static void multMatrix(FloatBuffer matrix) {
        GL11.glMultMatrix(matrix);
    }

    public static void color(float colorRed, float colorGreen, float colorBlue, float colorAlpha) {
        if (colorRed != colorState.red || colorGreen != colorState.green || colorBlue != colorState.blue || colorAlpha != colorState.alpha) {
            colorState.red = colorRed;
            colorState.green = colorGreen;
            colorState.blue = colorBlue;
            colorState.alpha = colorAlpha;
            GL11.glColor4f(colorRed, colorGreen, colorBlue, colorAlpha);
        }
    }

    public static void color(float colorRed, float colorGreen, float colorBlue) {
        color(colorRed, colorGreen, colorBlue, 1.0F);
    }

    public static void resetColor() {
        colorState.red = colorState.green = colorState.blue = colorState.alpha = -1.0F;
    }

    public static void callList(int list) {
        GL11.glCallList(list);
    }

    public static int getActiveTextureUnit() {
        return OpenGlHelper.defaultTexUnit + activeTextureUnit;
    }

    public static int getBoundTexture() {
        return textureState[activeTextureUnit].textureName;
    }

    public static void deleteTextures(IntBuffer p_deleteTextures_0_) {
        p_deleteTextures_0_.rewind();

        while (p_deleteTextures_0_.position() < p_deleteTextures_0_.limit()) {
            int i = p_deleteTextures_0_.get();
            deleteTexture(i);
        }

        p_deleteTextures_0_.rewind();
    }

    static {
        for (int i = 0; i < 8; ++i) {
            lightState[i] = new GlStateManager.BooleanState(16384 + i);
        }

        for (int j = 0; j < textureState.length; ++j) {
            textureState[j] = new GlStateManager.TextureState(null);
        }
    }

    static final class GlStateManager$1 {
        static final int[] field_179175_a = new int[GlStateManager.TexGen.values().length];

        static {
            try {
                field_179175_a[GlStateManager.TexGen.S.ordinal()] = 1;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_179175_a[GlStateManager.TexGen.T.ordinal()] = 2;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_179175_a[GlStateManager.TexGen.R.ordinal()] = 3;
            } catch (NoSuchFieldError ignored) {
            }

            try {
                field_179175_a[GlStateManager.TexGen.Q.ordinal()] = 4;
            } catch (NoSuchFieldError ignored) {
            }
        }
    }

    static class AlphaState {
        public GlStateManager.BooleanState test;
        public int func;
        public float ref;

        private AlphaState() {
            this.test = new GlStateManager.BooleanState(3008);
            this.func = 519;
            this.ref = -1.0F;
        }

        AlphaState(GlStateManager.GlStateManager$1 p_i46489_1_) {
            this();
        }
    }

    static class BlendState {
        public GlStateManager.BooleanState blend;
        public int srcFactor;
        public int dstFactor;
        public int srcFactorAlpha;
        public int dstFactorAlpha;

        private BlendState() {
            this.blend = new GlStateManager.BooleanState(3042);
            this.srcFactor = 1;
            this.dstFactor = 0;
            this.srcFactorAlpha = 1;
            this.dstFactorAlpha = 0;
        }

        BlendState(GlStateManager.GlStateManager$1 p_i46488_1_) {
            this();
        }
    }

    static class BooleanState {
        private final int capability;
        private boolean currentState = false;

        public BooleanState(int capabilityIn) {
            this.capability = capabilityIn;
        }

        public void setDisabled() {
            this.setState(false);
        }

        public void setEnabled() {
            this.setState(true);
        }

        public void setState(boolean state) {
            if (state != this.currentState) {
                this.currentState = state;

                if (state) {
                    GL11.glEnable(this.capability);
                } else {
                    GL11.glDisable(this.capability);
                }
            }
        }
    }

    static class ClearState {
        public double depth;
        public GlStateManager.Color color;
        public int field_179204_c;

        private ClearState() {
            this.depth = 1.0D;
            this.color = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
            this.field_179204_c = 0;
        }

        ClearState(GlStateManager.GlStateManager$1 p_i46487_1_) {
            this();
        }
    }

    static class Color {
        public float red = 1.0F;
        public float green = 1.0F;
        public float blue = 1.0F;
        public float alpha = 1.0F;

        public Color() {
        }

        public Color(float redIn, float greenIn, float blueIn, float alphaIn) {
            this.red = redIn;
            this.green = greenIn;
            this.blue = blueIn;
            this.alpha = alphaIn;
        }
    }

    static class ColorLogicState {
        public GlStateManager.BooleanState colorLogicOp;
        public int logicOpcode;

        private ColorLogicState() {
            this.colorLogicOp = new GlStateManager.BooleanState(3058);
            this.logicOpcode = 5379;
        }

        ColorLogicState(GlStateManager.GlStateManager$1 p_i46486_1_) {
            this();
        }
    }

    static class ColorMask {
        public boolean red;
        public boolean green;
        public boolean blue;
        public boolean alpha;

        private ColorMask() {
            this.red = true;
            this.green = true;
            this.blue = true;
            this.alpha = true;
        }

        ColorMask(GlStateManager.GlStateManager$1 p_i46485_1_) {
            this();
        }
    }

    static class ColorMaterialState {
        public GlStateManager.BooleanState colorMaterial;
        public int face;
        public int mode;

        private ColorMaterialState() {
            this.colorMaterial = new GlStateManager.BooleanState(2903);
            this.face = 1032;
            this.mode = 5634;
        }

        ColorMaterialState(GlStateManager.GlStateManager$1 p_i46484_1_) {
            this();
        }
    }

    static class CullState {
        public GlStateManager.BooleanState cullFace;
        public int mode;

        private CullState() {
            this.cullFace = new GlStateManager.BooleanState(2884);
            this.mode = 1029;
        }

        CullState(GlStateManager.GlStateManager$1 p_i46483_1_) {
            this();
        }
    }

    static class DepthState {
        public GlStateManager.BooleanState depthTest;
        public boolean maskEnabled;
        public int depthFunc;

        private DepthState() {
            this.depthTest = new GlStateManager.BooleanState(2929);
            this.maskEnabled = true;
            this.depthFunc = 513;
        }

        DepthState(GlStateManager.GlStateManager$1 p_i46482_1_) {
            this();
        }
    }

    static class FogState {
        public GlStateManager.BooleanState fog;
        public int mode;
        public float density;
        public float start;
        public float end;

        private FogState() {
            this.fog = new GlStateManager.BooleanState(2912);
            this.mode = 2048;
            this.density = 1.0F;
            this.start = 0.0F;
            this.end = 1.0F;
        }

        FogState(GlStateManager.GlStateManager$1 p_i46481_1_) {
            this();
        }
    }

    static class PolygonOffsetState {
        public GlStateManager.BooleanState polyOffset;
        public GlStateManager.BooleanState lineOffset;
        public float factor;
        public float units;

        private PolygonOffsetState() {
            this.polyOffset = new GlStateManager.BooleanState(32823);
            this.lineOffset = new GlStateManager.BooleanState(10754);
            this.factor = 0.0F;
            this.units = 0.0F;
        }

        PolygonOffsetState(GlStateManager.GlStateManager$1 p_i46480_1_) {
            this();
        }
    }

    static class StencilFunc {
        public int func;
        public int field_179079_b;
        public int mask;

        private StencilFunc() {
            this.func = 519;
            this.field_179079_b = 0;
            this.mask = -1;
        }

        StencilFunc(GlStateManager.GlStateManager$1 p_i46479_1_) {
            this();
        }
    }

    public enum TexGen {
        S("S", 0),
        T("T", 1),
        R("R", 2),
        Q("Q", 3);

        TexGen(String p_i3_3_, int p_i3_4_) {
        }
    }

    static class TexGenCoord {
        public GlStateManager.BooleanState textureGen;
        public int coord;
        public int mode = -1;

        public TexGenCoord(int p_i46254_1_, int p_i46254_2_) {
            this.coord = p_i46254_1_;
            this.textureGen = new GlStateManager.BooleanState(p_i46254_2_);
        }
    }

    static class TexGenState {
        public GlStateManager.TexGenCoord s;
        public GlStateManager.TexGenCoord t;
        public GlStateManager.TexGenCoord r;
        public GlStateManager.TexGenCoord q;

        private TexGenState() {
            this.s = new GlStateManager.TexGenCoord(8192, 3168);
            this.t = new GlStateManager.TexGenCoord(8193, 3169);
            this.r = new GlStateManager.TexGenCoord(8194, 3170);
            this.q = new GlStateManager.TexGenCoord(8195, 3171);
        }

        TexGenState(GlStateManager.GlStateManager$1 p_i46477_1_) {
            this();
        }
    }

    static class TextureState {
        public GlStateManager.BooleanState texture2DState;
        public int textureName;

        private TextureState() {
            this.texture2DState = new GlStateManager.BooleanState(3553);
            this.textureName = 0;
        }

        TextureState(GlStateManager.GlStateManager$1 p_i46476_1_) {
            this();
        }
    }
}
