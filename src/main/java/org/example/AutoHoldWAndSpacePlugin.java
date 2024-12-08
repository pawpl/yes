package org.pawpl;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

public class AutoHoldWAndSpacePlugin extends Plugin {

    @Override
    public void onLoad() {
        final AutoHoldModule autoHoldModule = new AutoHoldModule();
        RusherHackAPI.getModuleManager().registerFeature(autoHoldModule);
    }

    @Override
    public void onUnload() {
    }
}
