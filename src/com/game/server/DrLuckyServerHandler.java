package com.game.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;


public class DrLuckyServerHandler extends SimpleChannelInboundHandler<String> {
    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    static ArrayList<Channel> connectedPlayers = new ArrayList<>();
    static Channel currentPlayer = null;
    static int playerNum = 0;

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        // Once session is secured, send a greeting and register the channel to the global channel
        // list so the channel received the messages from others.
        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                (GenericFutureListener<Future<Channel>>) future -> {
                    ctx.writeAndFlush(
                            "good\n"); //send a "good" flag to the just joining channel

                    if(currentPlayer == null){
                        currentPlayer = ctx.channel();
                    }
                    connectedPlayers.add(ctx.channel());
                    channels.add(ctx.channel());
                });
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg){
        System.out.println("User has said something: " + msg);
        //Loop through all connected channels
        for (Channel c: channels) {
            if (c != ctx.channel()) { //send to all other channels that isnt the one who sent the message
                c.writeAndFlush("[" + ctx.channel().remoteAddress() + "] " + msg + '\n');
            } else {
                //If its the channel that sent the message.
                c.writeAndFlush("[you] " + msg + '\n');
            }
        }
    }
}