package org.pawpl;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.client.api.feature.module.Module;
import org.rusherhack.client.api.feature.module.ModuleCategory;

/**
 * RusherHack plugin that registers the AutoHoldWAndSpace module, a HUD element, and a command.
 */
public class AutoHoldWAndSpacePlugin extends Plugin {

    @Override
    public void onLoad() {
        // Log a message when the plugin is loaded
        this.getLogger().info("AutoHoldWAndSpace plugin loaded!");

        // Create and register the AutoHoldWAndSpace module
        final AutoHoldWAndSpaceModule autoHoldModule = new AutoHoldWAndSpaceModule();
        RusherHackAPI.getModuleManager().registerFeature(autoHoldModule);
        
        // Create and register a HUD element to display module status
        final AutoHoldWAndSpaceHudElement hudElement = new AutoHoldWAndSpaceHudElement();
        RusherHackAPI.getHudManager().registerFeature(hudElement);
        
        // Create and register a command to toggle the module
        final AutoHoldWAndSpaceCommand toggleCommand = new AutoHoldWAndSpaceCommand();
        RusherHackAPI.getCommandManager().registerFeature(toggleCommand);

        // Inform the user that the plugin has loaded
        ChatUtils.print("AutoHoldWAndSpace plugin is active!");
    }

    @Override
    public void onUnload() {
        // Log a message when the plugin is unloaded
        this.getLogger().info("AutoHoldWAndSpace plugin unloaded!");
    }
}
