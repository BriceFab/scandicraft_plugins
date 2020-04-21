package net.scandicraft.mods.impl.togglesprintsneak;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.scandicraft.mods.ModInstances;

import java.text.DecimalFormat;

public class ClientMovementInput extends MovementInput {

    private boolean sprint = false;
    private GameSettings gameSettings;
    private int sneakWasPressed = 0;
    private int sprintWasPressed = 0;
    private EntityPlayerSP player;
    private Minecraft mc;
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.D");

    public ClientMovementInput(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.mc = Minecraft.getMinecraft();
    }

    @Override
    public void updatePlayerMoveState() {

        player = mc.thePlayer;
        moveStrafe = 0.0F;
        moveForward = 0.0F;

        if (gameSettings.keyBindForward.isKeyDown()) {
            moveForward++;
        }

        if (gameSettings.keyBindBack.isKeyDown()) {
            moveForward--;
        }

        if (gameSettings.keyBindLeft.isKeyDown()) {
            moveStrafe++;
        }

        if (gameSettings.keyBindRight.isKeyDown()) {
            moveStrafe--;
        }

        jump = gameSettings.keyBindJump.isKeyDown();

        //ToggleSneak
        if (ModInstances.getModToggleSprintSneak().isEnabled()) {

            if (gameSettings.keyBindSneak.isKeyDown()) {
                if (sneakWasPressed == 0) {
                    if (sneak) {
                        sneakWasPressed = -1;
                    } else if (player.isRiding() || player.capabilities.isFlying) {
                        sneakWasPressed = ModInstances.getModToggleSprintSneak().keyHoldTicks + 1;
                    } else {
                        sneakWasPressed = 1;
                    }
                    sneak = !sneak;
                } else if (sneakWasPressed > 0) {
                    sneakWasPressed++;
                }
            } else {
                if ((ModInstances.getModToggleSprintSneak().keyHoldTicks > 0) && (sneakWasPressed > ModInstances.getModToggleSprintSneak().keyHoldTicks)) {
                    sneak = false;
                }
                sneakWasPressed = 0;
            }

        } else {
            sneak = gameSettings.keyBindSneak.isKeyDown();
        }

        if (sneak) {
            moveStrafe *= 0.4F;
            moveForward *= 0.3F;
        }

        //ToggleSprint
        if (ModInstances.getModToggleSprintSneak().isEnabled()) {
            if (gameSettings.keyBindSprint.isKeyDown()) {
                if (sprintWasPressed == 0) {
                    if (sprint) {
                        sprintWasPressed = -1;
                    } else if (player.capabilities.isFlying) {
                        sprintWasPressed = ModInstances.getModToggleSprintSneak().keyHoldTicks = 1;
                    } else {
                        sprintWasPressed = 1;
                    }
                    sprint = !sprint;
                } else if (sprintWasPressed > 0) {
                    sprintWasPressed++;
                }
            } else {
                if ((ModInstances.getModToggleSprintSneak().keyHoldTicks > 0) && (sprintWasPressed > ModInstances.getModToggleSprintSneak().keyHoldTicks)) {
                    sprint = false;
                }
                sprintWasPressed = 0;
            }
        } else {
            sprint = false;
        }

        if (sprint && moveForward == 1.0F && player.onGround && !player.isUsingItem() && !player.isPotionActive(Potion.blindness)) {
            player.setSprinting(true);
        }

    }

    public String getDisplayText() {
        String displayText = "";

        boolean isFlying = mc.thePlayer.capabilities.isFlying;
        boolean isRiding = mc.thePlayer.isRiding();
        boolean isHoldingSneak = gameSettings.keyBindSneak.isKeyDown();
        boolean isHoldingSrpint = gameSettings.keyBindSprint.isKeyDown();

        if (!isFlying && !isRiding) {
            if (sneak) {
                if (isHoldingSneak) {
                    displayText = "[Sneaking (Key Held)]";
                } else {
                    displayText = "[Sneaking (Key Toggled)]";
                }
            } else if (sprint) {
                if (isHoldingSrpint) {
                    displayText = "[Sprinting (Key Held)]";
                } else {
                    displayText = "[Sprinting (Key Toggled)]";
                }
            }
        }

        return displayText.trim();
    }


}
