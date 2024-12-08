package org.pawpl;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.client.api.feature.module.Module;
import org.rusherhack.client.api.feature.module.ModuleCategory;

/**
 * RusherHack plugin that registers the AutoHoldWAndSpace module.
 */
public class AutoHoldWAndSpacePlugin extends Plugin {

    @Override
    public void onLoad() {
        // Log a message when the plugin is loaded
        this.getLogger().info("AutoHoldWAndSpace plugin loaded!");

        // Create and register the AutoHoldWAndSpace module
        final AutoHoldWAndSpaceModule autoHoldModule = new AutoHoldWAndSpaceModule();
        RusherHackAPI.getModuleManager().registerFeature(autoHoldModule);
        
        // Inform the user that the plugin has loaded
        ChatUtils.print("AutoHoldWAndSpace plugin is active!");
    }

    @Override
    public void onUnload() {
        // Log a message when the plugin is unloaded
        this.getLogger().info("AutoHoldWAndSpace plugin unloaded!");
    }
}
