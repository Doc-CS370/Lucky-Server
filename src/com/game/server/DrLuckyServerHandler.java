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
import java.util.Map;
import java.util.Set;

/**
 * "endturn"
 * "good"
 * "endgame"
 * "newplayer"
 * "
 *
 */


public class DrLuckyServerHandler extends SimpleChannelInboundHandler<String> {
    static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    static ArrayList<Channel> connectedPlayers = new ArrayList<>();
    static int desiredPlayers = 0;
    static Channel currentPlayer = null;
    static Map<Channel, Set<Integer>> playerPositions; //Map the channel to their coordinates
    static int currentTurn = 0;
    static boolean gameHasStarted = false;
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
                    if(channels.size() > 1){
                        ctx.writeAndFlush("currentplayers");
                        for(Channel ch: channels){
                            if(ch != ctx.channel()){
                                ch.writeAndFlush("newplayer\n");
                                ctx.writeAndFlush(playerPositions.get(ch) + "\n");
                            }

                        }

                    }
                });
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg){
        System.out.println("User has said something: " + msg);
        if(!gameHasStarted){ //Handle when game has not started
            desiredPlayers = Integer.parseInt(msg); //First message should be first player saying how many players they want.

        }
        if(gameHasStarted){ //Make sure game has started
            if(isCurrentPlayer(ctx.channel())){ //Make sure its the current player
                if(msg.equalsIgnoreCase("endturn")){
                    endTurn();
                }
                for (Channel c: channels) {
                    if (c != ctx.channel()) { //send to all other channels that isnt the one who sent the message

                    } else {
                        //If its the channel that sent the message.

                    }
                }
            }
        }

    }

    public boolean canGameStart(){
        return connectedPlayers.size() != 0 && connectedPlayers.size() == desiredPlayers;
    }

    public void endTurn(){ //Cycle to the next player if the current player has issued the command
        if(playerNum != connectedPlayers.size() - 1){
            currentPlayer = connectedPlayers.get(playerNum);
            playerNum++;
        } else {
            currentPlayer = connectedPlayers.get(0);
            playerNum = 0;
        }
    }

    public boolean isCurrentPlayer(Channel ch){
        return ch == currentPlayer;
    }
}