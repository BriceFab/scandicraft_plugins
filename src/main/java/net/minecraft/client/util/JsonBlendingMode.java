package net.minecraft.client.util;

import com.google.gson.JsonObject;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.JsonUtils;
import org.lwjgl.opengl.GL14;

public class JsonBlendingMode
{
    private static JsonBlendingMode lastApplied = null;
    private final int srcColorFactor;
    private final int srcAlphaFactor;
    private final int destColorFactor;
    private final int destAlphaFactor;
    private final int blendFunction;
    private final boolean separateBlend;
    private final boolean opaque;

    private JsonBlendingMode(boolean separateBlendIn, boolean opaqueIn, int srcColorFactorIn, int destColorFactorIn, int srcAlphaFactorIn, int destAlphaFactorIn, int blendFunctionIn)
    {
        this.separateBlend = separateBlendIn;
        this.srcColorFactor = srcColorFactorIn;
        this.destColorFactor = destColorFactorIn;
        this.srcAlphaFactor = srcAlphaFactorIn;
        this.destAlphaFactor = destAlphaFactorIn;
        this.opaque = opaqueIn;
        this.blendFunction = blendFunctionIn;
    }

    public JsonBlendingMode()
    {
        this(false, true, 1, 0, 1, 0, 32774);
    }

    public JsonBlendingMode(int srcFactor, int dstFactor, int blendFunctionIn)
    {
        this(false, false, srcFactor, dstFactor, srcFactor, dstFactor, blendFunctionIn);
    }

    public JsonBlendingMode(int srcColorFactorIn, int destColorFactorIn, int srcAlphaFactorIn, int destAlphaFactorIn, int blendFunctionIn)
    {
        this(true, false, srcColorFactorIn, destColorFactorIn, srcAlphaFactorIn, destAlphaFactorIn, blendFunctionIn);
    }

    public void apply()
    {
        if (!this.equals(lastApplied))
        {
            if (lastApplied == null || this.opaque != lastApplied.isOpaque())
            {
                lastApplied = this;

                if (this.opaque)
                {
                    GlStateManager.disableBlend();
                    return;
                }

                GlStateManager.enableBlend();
            }

            GL14.glBlendEquation(this.blendFunction);

            if (this.separateBlend)
            {
                GlStateManager.tryBlendFuncSeparate(this.srcColorFactor, this.destColorFactor, this.srcAlphaFactor, this.destAlphaFactor);
            }
            else
            {
                GlStateManager.blendFunc(this.srcColorFactor, this.destColorFactor);
            }
        }
    }

    public boolean equals(Object p_equals_1_)
    {
        if (this == p_equals_1_)
        {
            return true;
        }
        else if (!(p_equals_1_ instanceof JsonBlendingMode))
        {
            return false;
        }
        else
        {
            JsonBlendingMode jsonblendingmode = (JsonBlendingMode)p_equals_1_;
            return this.blendFunction != jsonblendingmode.blendFunction ? false : (this.destAlphaFactor != jsonblendingmode.destAlphaFactor ? false : (this.destColorFactor != jsonblendingmode.destColorFactor ? false : (this.opaque != jsonblendingmode.opaque ? false : (this.separateBlend != jsonblendingmode.separateBlend ? false : (this.srcAlphaFactor != jsonblendingmode.srcAlphaFactor ? false : this.srcColorFactor == jsonblendingmode.srcColorFactor)))));
        }
    }

    public int hashCode()
    {
        int i = this.srcColorFactor;
        i = 31 * i + this.srcAlphaFactor;
        i = 31 * i + this.destColorFactor;
        i = 31 * i + this.destAlphaFactor;
        i = 31 * i + this.blendFunction;
        i = 31 * i + (this.separateBlend ? 1 : 0);
        i = 31 * i + (this.opaque ? 1 : 0);
        return i;
    }

    public boolean isOpaque()
    {
        return this.opaque;
    }

    public static JsonBlendingMode func_148110_a(JsonObject p_148110_0_)
    {
        if (p_148110_0_ == null)
        {
            return new JsonBlendingMode();
        }
        else
        {
            int i = 32774;
            int j = 1;
            int k = 0;
            int l = 1;
            int i1 = 0;
            boolean flag = true;
            boolean flag1 = false;

            if (JsonUtils.isString(p_148110_0_, "func"))
            {
                i = stringToBlendFunction(p_148110_0_.get("func").getAsString());

                if (i != 32774)
                {
                    flag = false;
                }
            }

            if (JsonUtils.isString(p_148110_0_, "srcrgb"))
            {
                j = stringToBlendFactor(p_148110_0_.get("srcrgb").getAsString());

                if (j != 1)
                {
                    flag = false;
                }
            }

            if (JsonUtils.isString(p_148110_0_, "dstrgb"))
            {
                k = stringToBlendFactor(p_148110_0_.get("dstrgb").getAsString());

                if (k != 0)
                {
                    flag = false;
                }
            }

            if (JsonUtils.isString(p_148110_0_, "srcalpha"))
            {
                l = stringToBlendFactor(p_148110_0_.get("srcalpha").getAsString());

                if (l != 1)
                {
                    flag = false;
                }

                flag1 = true;
            }

            if (JsonUtils.isString(p_148110_0_, "dstalpha"))
            {
                i1 = stringToBlendFactor(p_148110_0_.get("dstalpha").getAsString());

                if (i1 != 0)
                {
                    flag = false;
                }

                flag1 = true;
            }

            return flag ? new JsonBlendingMode() : (flag1 ? new JsonBlendingMode(j, k, l, i1, i) : new JsonBlendingMode(j, k, i));
        }
    }

    private static int stringToBlendFunction(String funcName)
    {
        String s = funcName.trim().toLowerCase();
        return s.equals("add") ? 32774 : (s.equals("subtract") ? 32778 : (s.equals("reversesubtract") ? 32779 : (s.equals("reverse_subtract") ? 32779 : (s.equals("min") ? 32775 : (s.equals("max") ? 32776 : 32774)))));
    }

    private static int stringToBlendFactor(String factorName)
    {
        String s = factorName.trim().toLowerCase();
        s = s.replaceAll("_", "");
        s = s.replaceAll("one", "1");
        s = s.replaceAll("zero", "0");
        s = s.replaceAll("minus", "-");
        return s.equals("0") ? 0 : (s.equals("1") ? 1 : (s.equals("srccolor") ? 768 : (s.equals("1-srccolor") ? 769 : (s.equals("dstcolor") ? 774 : (s.equals("1-dstcolor") ? 775 : (s.equals("srcalpha") ? 770 : (s.equals("1-srcalpha") ? 771 : (s.equals("dstalpha") ? 772 : (s.equals("1-dstalpha") ? 773 : -1)))))))));
    }
}
