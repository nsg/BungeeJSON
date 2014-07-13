/**
 * This file is part of BungeeJSON.
 *
 * BungeeJSON is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BungeeJSON is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BungeeJSON.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.imaginarycode.minecraft.bungeejson;

import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import com.imaginarycode.minecraft.bungeejson.api.RequestManager;
import com.imaginarycode.minecraft.bungeejson.impl.httpserver.NettyBootstrap;
import com.imaginarycode.minecraft.bungeejson.impl.BungeeJSONRequestManager;
import com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeecord.*;
import com.imaginarycode.minecraft.bungeejson.impl.handlers.bungeejson.Version;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.*;

public class BungeeJSONPlugin extends Plugin {
    private NettyBootstrap nb = new NettyBootstrap();
    protected static BungeeJSONPlugin plugin;

    private static RequestManager requestManager = new BungeeJSONRequestManager();
    @Getter
    private Gson gson = new Gson();

    public static BungeeJSONPlugin getPlugin() {
        return plugin;
    }

    /**
     * Fetch the {@link com.imaginarycode.minecraft.bungeejson.api.RequestManager}.
     * @return the {@link com.imaginarycode.minecraft.bungeejson.api.RequestManager}
     */
    public static RequestManager getRequestManager() {
        return requestManager;
    }

    @Override
    public void onLoad() {
        plugin = this;
        requestManager.registerEndpoint("/bungeecord/connect", new Connect());
        requestManager.registerEndpoint("/bungeecord/find_server_for", new FindServerFor());
        requestManager.registerEndpoint("/bungeecord/player_count", new PlayerCount());
        requestManager.registerEndpoint("/bungeecord/players_online", new PlayersOnline());
        requestManager.registerEndpoint("/bungeecord/server_list", new ServerList());
        requestManager.registerEndpoint("/bungeejson/version", new Version());
    }

    @Override
    public void onEnable() {
        getProxy().getScheduler().runAsync(this, new Runnable() {
            @Override
            public void run() {
                nb.initialize();
            }
        });
    }

    @Override
    public void onDisable() {
        nb.getChannelFuture().channel().close().syncUninterruptibly();
        nb.getGroup().shutdownGracefully();
    }
}
