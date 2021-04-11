
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

package com.lzp.registry.common.constant;

/**
 * Description:常量类
 *
 * @author: Zeping Lu
 * @date: 2021/3/17 16:26
 */
public class Cons {
    public static final String FOLLOWER = "FOLLOWER";
    public static final String CANDIDATE = "CANDIDATE";
    public static final String LEADER = "LEADER";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String COMMAND_SEPARATOR = "-:";
    public static final String SPECIFICORDER_SEPARATOR = "-=";
    public static final String CLU_PRO = "cluster.properites";
    public static final String RPC_ASKFORVOTE = "reqforvote";
    public static final String RPC_FROMCLIENT = "fromcli";
    public static final String RPC_REPLICATION = "replication";
    public static final String RPC_COMMIT = "commit";
    public static final String RPC_SYNC = "sync";
    public static final String RPC_TOBESLAVE = "tobeslave";
    public static final String GET = "get";
    public static final String ADD = "add";
    public static final String REM = "rem";
    public static final String YES = "yes";
    public static final String FALSE = "false";
    public static final String TRUE = "true";
    public static final String EXCEPTION = "E";
    public static final String CLUSTER_DOWN_MESSAGE = "Less than half of the nodes survive, and the cluster does not provide services";
}
