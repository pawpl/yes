package org.pawpl;

import net.minecraft.client.Minecraft;
import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.events.client.EventUpdate;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.core.event.subscribe.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Holds the W key and spams the Space key module for RusherHack
 */
public class AutoHoldWAndSpaceModule extends ToggleableModule {

    private final Minecraft mc = Minecraft.getInstance(); // To access Minecraft instance
    private boolean holdWKey = true; // Default is to hold W
    private boolean spamSpacebar = true; // Default is to spam spacebar
    private final Timer spacebarTimer = new Timer();
    private long lastSpacebarPressTime = 0;

    /**
     * Constructor
     */
    public AutoHoldWAndSpaceModule() {
        super("AutoHoldWAndSpace", "Automatically holds W key and spams Spacebar", ModuleCategory.CLIENT);
    }

    /**
     * On Update event to simulate holding the W key and spamming the Space key
     */
    @Subscribe
    private void onUpdate(EventUpdate event) {
        // Check if the module is enabled and handle actions
        if (this.isEnabled()) {
            // Handle the "Hold W" key behavior
            if (holdWKey) {
                Minecraft.getInstance().options.keyUp.setPressed(true); // Simulate pressing the W key
            } else {
                Minecraft.getInstance().options.keyUp.setPressed(false); // Release the W key
            }

            // Handle the "Spam Space" key behavior
            if (spamSpacebar) {
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
            if (spamSpacebar) {
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
                if (spamSpacebar) {
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

    // Setter methods to change the behavior (these could be tied to a command or config file)
    public void setHoldWKey(boolean holdWKey) {
        this.holdWKey = holdWKey;
    }

    public void setSpamSpacebar(boolean spamSpacebar) {
        this.spamSpacebar = spamSpacebar;
    }
}
