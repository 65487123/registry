
/* Copyright zeping lu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.lzp.registry.server.netty;

import com.lzp.registry.common.constant.Cons;
import com.lzp.registry.server.raft.LogService;
import com.lzp.registry.server.raft.RaftNode;
import com.lzp.registry.server.util.Data;
import com.lzp.registry.server.util.DataSearialUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * Description:处理rpc结果的handler
 *
 * @author: Zeping Lu
 * @date: 2021/3/24 19:48
 */
public class ResultHandler extends SimpleChannelInboundHandler<byte[]> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] bytes) {
        String[] message = new String(bytes).split(Cons.COLON);

        if (Cons.YES.equals(message[1])) {
            RaftNode.cidAndResultMap.get(message[0]).countDown();
        } else if (Cons.RPC_TOBESLAVE.equals(message[0])) {
            //选举时,远端节点任期比本端节点新,会发这个消息
            RaftNode.downgradeToSlaveNode(Long.parseLong(message[1]));
        } else if (Cons.COPY_LOG_REQ.equals(message[0])) {
            //放到server的从reactor中执行,以满足单线程模型
            NettyServer.workerGroup.execute(() -> sendOwnState(Long.parseLong(message[1]), channelHandlerContext));
        }
    }


    /**
     * Description:
     * 把本节点状态(状态机、日志等)传到对端
     */
    private void sendOwnState(long remoteCommittedIndex, ChannelHandlerContext channelHandlerContext) {
        if (LogService.getCommittedLogIndex() == remoteCommittedIndex) {
            //说明状态机一样,只需要同步未提交日志就行
            channelHandlerContext.writeAndFlush("x" + Cons.COMMAND_SEPARATOR + "1" +
                    Cons.COMMAND_SEPARATOR + LogService.getFileContentOfUncommittedEntry());
        } else {
            //需要全量同步
            channelHandlerContext.writeAndFlush(("x" + Cons.COMMAND_SEPARATOR + "1" +
                    Cons.COMMAND_SEPARATOR + LogService.getFileContentOfCommittedEntry()
                    + Cons.COMMAND_SEPARATOR + LogService.getFileContentOfUncommittedEntry()
                    + Cons.COMMAND_SEPARATOR + new String(DataSearialUtil.serialize(new Data(RaftNode.data)))
                    + Cons.COMMAND_SEPARATOR + LogService.getCoveredIndex()).getBytes());
        }
    }
}
