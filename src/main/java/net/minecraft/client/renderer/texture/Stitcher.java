package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import net.minecraft.client.renderer.StitcherException;
import net.minecraft.util.MathHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Stitcher {
    private final int mipmapLevelStitcher;
    private final Set setStitchHolders = Sets.newHashSetWithExpectedSize(256);
    private final List stitchSlots = Lists.newArrayListWithCapacity(256);
    private int currentWidth;
    private int currentHeight;
    private final int maxWidth;
    private final int maxHeight;
    private final boolean forcePowerOf2;

    /**
     * Max size (width or height) of a single tile
     */
    private final int maxTileDimension;

    public Stitcher(int maxTextureWidth, int maxTextureHeight, boolean p_i45095_3_, int p_i45095_4_, int mipmapLevel) {
        this.mipmapLevelStitcher = mipmapLevel;
        this.maxWidth = maxTextureWidth;
        this.maxHeight = maxTextureHeight;
        this.forcePowerOf2 = p_i45095_3_;
        this.maxTileDimension = p_i45095_4_;
    }

    public int getCurrentWidth() {
        return this.currentWidth;
    }

    public int getCurrentHeight() {
        return this.currentHeight;
    }

    public void addSprite(TextureAtlasSprite p_110934_1_) {
        Stitcher.Holder stitcher$holder = new Stitcher.Holder(p_110934_1_, this.mipmapLevelStitcher);

        if (this.maxTileDimension > 0) {
            stitcher$holder.setNewDimension(this.maxTileDimension);
        }

        this.setStitchHolders.add(stitcher$holder);
    }

    public void doStitch() {
        Stitcher.Holder[] astitcher$holder = (Holder[]) this.setStitchHolders.toArray(new Holder[0]);
        Arrays.sort(astitcher$holder);

        for (Stitcher.Holder stitcher$holder : astitcher$holder) {
            if (!this.allocateSlot(stitcher$holder)) {
                String s = String.format("Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?", stitcher$holder.getAtlasSprite().getIconName(), Integer.valueOf(stitcher$holder.getAtlasSprite().getIconWidth()), Integer.valueOf(stitcher$holder.getAtlasSprite().getIconHeight()), Integer.valueOf(this.currentWidth), Integer.valueOf(this.currentHeight), Integer.valueOf(this.maxWidth), Integer.valueOf(this.maxHeight));
                throw new StitcherException(s);
            }
        }

        if (this.forcePowerOf2) {
            this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
            this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
        }
    }

    public List getStichSlots() {
        ArrayList arraylist = Lists.newArrayList();

        for (Object stitcher$slot : this.stitchSlots) {
            ((Slot) stitcher$slot).getAllStitchSlots(arraylist);
        }

        ArrayList arraylist1 = Lists.newArrayList();

        for (Object stitcher$slot10 : arraylist) {
            Slot stitcher$slot1 = (Slot) stitcher$slot10;
            Stitcher.Holder stitcher$holder = stitcher$slot1.getStitchHolder();
            TextureAtlasSprite textureatlassprite = stitcher$holder.getAtlasSprite();
            textureatlassprite.initSprite(this.currentWidth, this.currentHeight, stitcher$slot1.getOriginX(), stitcher$slot1.getOriginY(), stitcher$holder.isRotated());
            arraylist1.add(textureatlassprite);
        }

        return arraylist1;
    }

    private static int getMipmapDimension(int dimensionIn, int mipmapLevelIn) {
        return (dimensionIn >> mipmapLevelIn) + ((dimensionIn & (1 << mipmapLevelIn) - 1) == 0 ? 0 : 1) << mipmapLevelIn;
    }

    /**
     * Attempts to find space for specified tile
     */
    private boolean allocateSlot(Stitcher.Holder holderIn) {
        for (Object stitchSlot : this.stitchSlots) {
            if (((Slot) stitchSlot).addSlot(holderIn)) {
                return true;
            }

            holderIn.rotate();

            if (((Slot) stitchSlot).addSlot(holderIn)) {
                return true;
            }

            holderIn.rotate();
        }

        return this.expandAndAllocateSlot(holderIn);
    }

    /**
     * Expand stitched texture in order to make space for specified tile
     */
    private boolean expandAndAllocateSlot(Stitcher.Holder holderIn) {
        int i = Math.min(holderIn.getWidth(), holderIn.getHeight());
        boolean flag = this.currentWidth == 0 && this.currentHeight == 0;
        boolean flag1;

        if (this.forcePowerOf2) {
            int j = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
            int k = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
            int l = MathHelper.roundUpToPowerOfTwo(this.currentWidth + i);
            int i1 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + i);
            boolean flag2 = l <= this.maxWidth;
            boolean flag3 = i1 <= this.maxHeight;

            if (!flag2 && !flag3) {
                return false;
            }

            boolean flag4 = j != l;
            boolean flag5 = k != i1;

            if (flag4 ^ flag5) {
                flag1 = !flag4;
            } else {
                flag1 = flag2 && j <= k;
            }
        } else {
            boolean flag6 = this.currentWidth + i <= this.maxWidth;
            boolean flag7 = this.currentHeight + i <= this.maxHeight;

            if (!flag6 && !flag7) {
                return false;
            }

            flag1 = flag6 && (flag || this.currentWidth <= this.currentHeight);
        }

        int j1 = Math.max(holderIn.getWidth(), holderIn.getHeight());

        if (MathHelper.roundUpToPowerOfTwo((!flag1 ? this.currentHeight : this.currentWidth) + j1) > (!flag1 ? this.maxHeight : this.maxWidth)) {
            return false;
        } else {
            Stitcher.Slot stitcher$slot;

            if (flag1) {
                if (holderIn.getWidth() > holderIn.getHeight()) {
                    holderIn.rotate();
                }

                if (this.currentHeight == 0) {
                    this.currentHeight = holderIn.getHeight();
                }

                stitcher$slot = new Stitcher.Slot(this.currentWidth, 0, holderIn.getWidth(), this.currentHeight);
                this.currentWidth += holderIn.getWidth();
            } else {
                stitcher$slot = new Stitcher.Slot(0, this.currentHeight, this.currentWidth, holderIn.getHeight());
                this.currentHeight += holderIn.getHeight();
            }

            stitcher$slot.addSlot(holderIn);
            this.stitchSlots.add(stitcher$slot);
            return true;
        }
    }

    public static class Holder implements Comparable {
        private final TextureAtlasSprite theTexture;
        private final int width;
        private final int height;
        private final int mipmapLevelHolder;
        private boolean rotated;
        private float scaleFactor = 1.0F;

        public Holder(TextureAtlasSprite p_i45094_1_, int p_i45094_2_) {
            this.theTexture = p_i45094_1_;
            this.width = p_i45094_1_.getIconWidth();
            this.height = p_i45094_1_.getIconHeight();
            this.mipmapLevelHolder = p_i45094_2_;
            this.rotated = Stitcher.getMipmapDimension(this.height, p_i45094_2_) > Stitcher.getMipmapDimension(this.width, p_i45094_2_);
        }

        public TextureAtlasSprite getAtlasSprite() {
            return this.theTexture;
        }

        public int getWidth() {
            return this.rotated ? Stitcher.getMipmapDimension((int) ((float) this.height * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.getMipmapDimension((int) ((float) this.width * this.scaleFactor), this.mipmapLevelHolder);
        }

        public int getHeight() {
            return this.rotated ? Stitcher.getMipmapDimension((int) ((float) this.width * this.scaleFactor), this.mipmapLevelHolder) : Stitcher.getMipmapDimension((int) ((float) this.height * this.scaleFactor), this.mipmapLevelHolder);
        }

        public void rotate() {
            this.rotated = !this.rotated;
        }

        public boolean isRotated() {
            return this.rotated;
        }

        public void setNewDimension(int p_94196_1_) {
            if (this.width > p_94196_1_ && this.height > p_94196_1_) {
                this.scaleFactor = (float) p_94196_1_ / (float) Math.min(this.width, this.height);
            }
        }

        public String toString() {
            return "Holder{width=" + this.width + ", height=" + this.height + ", name=" + this.theTexture.getIconName() + '}';
        }

        public int compareTo(Stitcher.Holder p_compareTo_1_) {
            int i;

            if (this.getHeight() == p_compareTo_1_.getHeight()) {
                if (this.getWidth() == p_compareTo_1_.getWidth()) {
                    if (this.theTexture.getIconName() == null) {
                        return p_compareTo_1_.theTexture.getIconName() == null ? 0 : -1;
                    }

                    return this.theTexture.getIconName().compareTo(p_compareTo_1_.theTexture.getIconName());
                }

                i = this.getWidth() < p_compareTo_1_.getWidth() ? 1 : -1;
            } else {
                i = this.getHeight() < p_compareTo_1_.getHeight() ? 1 : -1;
            }

            return i;
        }

        public int compareTo(Object p_compareTo_1_) {
            return this.compareTo((Stitcher.Holder) p_compareTo_1_);
        }
    }

    public static class Slot {
        private final int originX;
        private final int originY;
        private final int width;
        private final int height;
        private List subSlots;
        private Stitcher.Holder holder;

        public Slot(int originXIn, int originYIn, int widthIn, int heightIn) {
            this.originX = originXIn;
            this.originY = originYIn;
            this.width = widthIn;
            this.height = heightIn;
        }

        public Stitcher.Holder getStitchHolder() {
            return this.holder;
        }

        public int getOriginX() {
            return this.originX;
        }

        public int getOriginY() {
            return this.originY;
        }

        public boolean addSlot(Stitcher.Holder holderIn) {
            if (this.holder != null) {
                return false;
            } else {
                int i = holderIn.getWidth();
                int j = holderIn.getHeight();

                if (i <= this.width && j <= this.height) {
                    if (i == this.width && j == this.height) {
                        this.holder = holderIn;
                        return true;
                    } else {
                        if (this.subSlots == null) {
                            this.subSlots = Lists.newArrayListWithCapacity(1);
                            this.subSlots.add(new Stitcher.Slot(this.originX, this.originY, i, j));
                            int k = this.width - i;
                            int l = this.height - j;

                            if (l > 0 && k > 0) {
                                int i1 = Math.max(this.height, k);
                                int j1 = Math.max(this.width, l);

                                if (i1 >= j1) {
                                    this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + j, i, l));
                                    this.subSlots.add(new Stitcher.Slot(this.originX + i, this.originY, k, this.height));
                                } else {
                                    this.subSlots.add(new Stitcher.Slot(this.originX + i, this.originY, k, j));
                                    this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + j, this.width, l));
                                }
                            } else if (k == 0) {
                                this.subSlots.add(new Stitcher.Slot(this.originX, this.originY + j, i, l));
                            } else if (l == 0) {
                                this.subSlots.add(new Stitcher.Slot(this.originX + i, this.originY, k, j));
                            }
                        }

                        for (Object stitcher$slot : this.subSlots) {
                            if (((Slot) stitcher$slot).addSlot(holderIn)) {
                                return true;
                            }
                        }

                        return false;
                    }
                } else {
                    return false;
                }
            }
        }

        public void getAllStitchSlots(List p_94184_1_) {
            if (this.holder != null) {
                p_94184_1_.add(this);
            } else if (this.subSlots != null) {
                for (Object stitcher$slot : this.subSlots) {
                    ((Slot) stitcher$slot).getAllStitchSlots(p_94184_1_);
                }
            }
        }

        public String toString() {
            return "Slot{originX=" + this.originX + ", originY=" + this.originY + ", width=" + this.width + ", height=" + this.height + ", texture=" + this.holder + ", subSlots=" + this.subSlots + '}';
        }
    }
}
