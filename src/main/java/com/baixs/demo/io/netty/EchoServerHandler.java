package com.baixs.demo.io.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.IOException;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // Discard the received data silently.
        try {
            // Do something with msg
            //ByteBuf in = (ByteBuf) msg;
            /*while (in.isReadable()) {
                System.out.print((char) in.readByte());
                System.out.flush();
            }*/
            ctx.writeAndFlush(msg);
            throw new IOException("故意抛出的异常");
        } finally {
            // ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        //ctx.writeAndFlush("throw a exception!");
        ctx.close();
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
