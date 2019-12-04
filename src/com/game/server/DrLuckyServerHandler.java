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
    static Map<Channel, Player> playerObjects;
    static ArrayList<Player> players; //Would be bots
    static int currentTurn = 0;
    static boolean gameHasStarted = false;
    static int playerNum = 0;
    static int[] boardSize = new int[]{0, 0};
    static ArrayList<Card> deckToReference;
    static ArrayList<Card> deck; //Instantiated in the Server class

    static ArrayList<Room> rooms;

    /**
     * Flow of the game
     * Player 1 joins, server is locked onto that channel, is given a coordinate, also is sent a good flag to make sure they connected fine
     * Player 1 lets server know how many players to play
     * Players can join
     * Player(s) is given a coordinate
     * Player(s) is also given coordinates of the other players in game
     * @param ctx
     */

    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        // Once session is secured, send a greeting and register the channel to the global channel
        // list so the channel received the messages from others.
        ctx.pipeline().get(SslHandler.class).handshakeFuture().addListener(
                (GenericFutureListener<Future<Channel>>) future -> {
                    ctx.writeAndFlush(
                            "good\n"); //send a "good" flag to the just joining channel

                    if(currentPlayer == null){ //Check if its the first player
                        currentPlayer = ctx.channel();
                    }

                    connectedPlayers.add(ctx.channel());
                    channels.add(ctx.channel());

                    Player playerToAdd = new Player();
                    //playerToAdd.addCard(deck.get()); //Implement
                    playerObjects.put(ctx.channel(), playerToAdd);
                    players.add(playerToAdd);

                    if(channels.size() > 1){
                        ctx.writeAndFlush("currentplayers\n");
                        for(Channel ch: channels){
                            if(ch != ctx.channel()){
                                ch.writeAndFlush("newplayer\n"); //Sends a new player flag to players in game
                                ctx.writeAndFlush(playerObjects.get(ch).playerLocation + "\n"); //Sends positions to newly joined player
                            }

                        }

                    } else {
                        ctx.writeAndFlush("Hello!\nHow many players in this game?\n");
                    }

                });
    }


    /**
     * First message received should always be number of players in game
     *
     * Flags
     * Move - "move #" with the number of the room
     * End the turn - "endturn"
     * Use a card -  "use #"
     * Attempt assassination - "kill"
     *
     *
     *
     * @param ctx
     * @param msg
     */

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg){
        System.out.println("User has said something: " + msg);
        if(!gameHasStarted){ //Handle when game has not started
            if(isCurrentPlayer(ctx.channel())){ //Make sure its the current player
                desiredPlayers = Integer.parseInt(msg); //First message should be first player saying how many players they want.
            }
        }
        if(gameHasStarted){ //Make sure game has started
            if(isCurrentPlayer(ctx.channel())){ //Make sure its the current player
                if(msg.toLowerCase().contains("move")){ //move #
                    String[] command = msg.split(" ");
                    int roomToMove = Integer.parseInt(command[1]);

                    if(MoveCheck.isMoveValid(rooms, players, roomToMove, currentTurn)){
                        players.get(currentTurn).playerLocation = roomToMove;
                        for(Channel c: channels){
                            c.writeAndFlush("moved " + players.get(currentTurn).playerLocation + "\n"); // "moved #"
                        }
                    }

                }
                if(msg.toLowerCase().contains("kill")){

                }
                if(msg.toLowerCase().contains("use")){ //use #
                    String[] command = msg.split(" ");
                    int cardNumber = Integer.parseInt(command[1]);
                    Player playerToMutate = playerObjects.get(ctx.channel());
                    //Remove the card
                    playerToMutate.removeCard(getTrueCard(cardNumber));
                    //Card logic

                }
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

    public Card getTrueCard(int cardNumber){
        for(int i = 0; i < deckToReference.size(); i++){
            Card c = deckToReference.get(i);
            if(c.getCardNumber() == cardNumber){
                return c;
            }
        }
        return new Card(); //Would be default
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