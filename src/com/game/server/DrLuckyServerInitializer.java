package com.game.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;

public class DrLuckyServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public DrLuckyServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline cp = ch.pipeline();

        cp.addLast(sslCtx.newHandler(ch.alloc()));

        cp.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        cp.addLast(new StringDecoder());
        cp.addLast(new StringEncoder());

        cp.addLast(new DrLuckyServerHandler());
    }


}
