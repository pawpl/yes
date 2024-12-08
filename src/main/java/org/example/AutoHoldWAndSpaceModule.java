package org.pawpl;

import net.minecraft.client.Minecraft;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.events.client.EventUpdate;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.event.subscribe.Subscribe;
import org.rusherhack.client.api.setting.Setting;
import org.rusherhack.client.api.setting.BooleanSetting;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Holds the W key and spams the Space key module for RusherHack
 */
public class AutoHoldWAndSpaceModule extends ToggleableModule {

    private final Minecraft mc = Minecraft.getInstance(); // To access Minecraft instance
    private final BooleanSetting holdWKey = new BooleanSetting("HoldW", "Hold the W key", true);
    private final BooleanSetting spamSpacebar = new BooleanSetting("SpamSpace", "Spam the Space key every 100ms", true);
    private final Timer spacebarTimer = new Timer();
    private long lastSpacebarPressTime = 0;

    /**
     * Constructor
     */
    public AutoHoldWAndSpaceModule() {
        super("AutoHoldWAndSpace", "Automatically holds W key and spams Spacebar", ModuleCategory.CLIENT);

        // Register settings
        this.registerSettings(holdWKey, spamSpacebar);
    }

    /**
     * On Update event to simulate holding the W key and spamming the Space key
     */
    @Subscribe
    private void onUpdate(EventUpdate event) {
        // Check if the module is enabled and handle actions
        if (this.isEnabled()) {
            // Handle the "Hold W" key behavior
            if (holdWKey.getValue()) {
                Minecraft.getInstance().options.keyUp.setPressed(true); // Simulate pressing the W key
            } else {
                Minecraft.getInstance().options.keyUp.setPressed(false); // Release the W key
            }

            // Handle the "Spam Space" key behavior
            if (spamSpacebar.getValue()) {
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastSpacebarPressTime >= 100) {
                    Minecraft.getInstance().options.keyJump.setPressed(true);  // Simulate pressing the Space key (space is bound to keyJump)
                    lastSpacebarPressTime = currentTime;
                } else {
                    Minecraft.getInstance().options.keyJump.setPressed(false); // Stop pressing Space if the interval hasn't passed
                }
            }
        }
    }

    @Override
    public void onEnable() {
        if (mc.level != null) {
            ChatUtils.print("AutoHoldWAndSpace module is enabled!");
            if (spamSpacebar.getValue()) {
                // Start spamming space at regular intervals
                startSpacebarSpam();
            }
        }
    }

    @Override
    public void onDisable() {
        if (mc.level != null) {
            // Stop holding the W key and spamming the Space key when the module is disabled
            mc.options.keyUp.setPressed(false);
            mc.options.keyJump.setPressed(false);
            ChatUtils.print("AutoHoldWAndSpace module has been disabled.");
        }
        stopSpacebarSpam();  // Stop the timer when disabled
    }

    /**
     * Starts a timer to handle spacebar spamming every 100ms.
     */
    private void startSpacebarSpam() {
        spacebarTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Ensure the space key is being pressed every 100ms if the module is enabled
                if (spamSpacebar.getValue()) {
                    mc.options.keyJump.setPressed(true);  // Simulate pressing the Space key
                }
            }
        }, 0, 100);  // Repeat every 100ms
    }

    /**
     * Stops the timer when the module is disabled or when spacebar spamming is turned off
     */
    private void stopSpacebarSpam() {
        spacebarTimer.cancel(); // Stop the timer
        spacebarTimer.purge();  // Clear any canceled tasks
    }
}
