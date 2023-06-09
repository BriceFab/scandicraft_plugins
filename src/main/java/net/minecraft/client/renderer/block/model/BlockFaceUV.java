package net.minecraft.client.renderer.block.model;

import com.google.gson.*;
import net.minecraft.util.JsonUtils;

import java.lang.reflect.Type;

public class BlockFaceUV {
    public float[] uvs;
    public final int rotation;

    public BlockFaceUV(float[] uvsIn, int rotationIn) {
        this.uvs = uvsIn;
        this.rotation = rotationIn;
    }

    public float getVertexU(int indexIn) {
        if (this.uvs == null) {
            throw new NullPointerException("uvs");
        } else {
            int i = this.getVertexRotated(indexIn);
            return i != 0 && i != 1 ? this.uvs[2] : this.uvs[0];
        }
    }

    public float getVertexV(int indexIn) {
        if (this.uvs == null) {
            throw new NullPointerException("uvs");
        } else {
            int i = this.getVertexRotated(indexIn);
            return i != 0 && i != 3 ? this.uvs[3] : this.uvs[1];
        }
    }

    private int getVertexRotated(int indexIn) {
        return (indexIn + this.rotation / 90) % 4;
    }

    public int getVertexRotatedRev(int indexIn) {
        return (indexIn + (4 - this.rotation / 90)) % 4;
    }

    public void setUvs(float[] uvsIn) {
        if (this.uvs == null) {
            this.uvs = uvsIn;
        }
    }

    static class Deserializer implements JsonDeserializer<BlockFaceUV> {
        public BlockFaceUV deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
            JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
            float[] afloat = this.parseUV(jsonobject);
            int i = this.parseRotation(jsonobject);
            return new BlockFaceUV(afloat, i);
        }

        protected int parseRotation(JsonObject object) {
            int i = JsonUtils.getInt(object, "rotation", 0);

            if (i >= 0 && i % 90 == 0 && i / 90 <= 3) {
                return i;
            } else {
                throw new JsonParseException("Invalid rotation " + i + " found, only 0/90/180/270 allowed");
            }
        }

        private float[] parseUV(JsonObject object) {
            if (!object.has("uv")) {
                return null;
            } else {
                JsonArray jsonarray = JsonUtils.getJsonArray(object, "uv");

                if (jsonarray.size() != 4) {
                    throw new JsonParseException("Expected 4 uv values, found: " + jsonarray.size());
                } else {
                    float[] afloat = new float[4];

                    for (int i = 0; i < afloat.length; ++i) {
                        afloat[i] = JsonUtils.getFloat(jsonarray.get(i), "uv[" + i + "]");
                    }

                    return afloat;
                }
            }
        }
    }
}
