package net.scandicraft.settings.buttons;

import net.scandicraft.settings.ScandiCraftSettings;
import optifine.IOptionControl;

public class BooleanButton extends GuiOptionButton implements IOptionControl {
    private ScandiCraftSettings.Options option = null;

    public BooleanButton(int p_i49_1_, int p_i49_2_, int p_i49_3_, ScandiCraftSettings.Options p_i49_4_, String p_i49_5_) {
        super(p_i49_1_, p_i49_2_, p_i49_3_, p_i49_4_, p_i49_5_);
        this.option = p_i49_4_;
    }

    @Override
    public ScandiCraftSettings.Options getOption() {
        return this.option;
    }
}
