/****************************************************** 
 *  Copyright 2018 IBM Corporation 
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  you may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at 
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *  Unless required by applicable law or agreed to in writing, software 
 *  distributed under the License is distributed on an "AS IS" BASIS, 
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *  See the License for the specific language governing permissions and 
 *  limitations under the License.
 */ 
package org.app.chaincode.invocation;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.protobuf.ByteString;
import org.apache.commons.codec.binary.Hex;
import org.app.client.CAClient;
import org.app.client.ChannelClient;
import org.app.client.FabricClient;
import org.app.config.Config;
import org.app.user.UserContext;
import org.app.util.Util;
import org.hyperledger.fabric.protos.common.Common;
import org.hyperledger.fabric.protos.common.Common.Envelope;
import org.hyperledger.fabric.protos.peer.FabricTransaction;
import org.hyperledger.fabric.protos.peer.FabricTransaction.ProcessedTransaction;
import org.hyperledger.fabric.protos.peer.Query;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.transaction.ProtoUtils;
import org.hyperledger.fabric.sdk.transaction.TransactionContext;

//链码操作同crypto-config没有关系：只同CA服务的地址和登录的用户名、密码有关
//只有登录成功，就可以进行链码操作
public class QueryChaincode {

	private static final byte[] EXPECTED_EVENT_DATA = "!".getBytes(UTF_8);
	private static final String EXPECTED_EVENT_NAME = "event";

	public static void main(String args[]) {
		try {
			//先清理缓存到当前工程目录下users目录中的用户本地序列化数据
            Util.cleanUp();
			 
            //***第一步：构建用户
           	//User接口的实现类，代表fabric网络的一个用户user:内部包含一个登录凭据enrollment
            //需要通过ca客户端登录成功后来获取enrollment（包括登录成功后的证书和公私钥）
			UserContext adminUserContext = new UserContext();
			adminUserContext.setName(Config.ADMIN);
			adminUserContext.setAffiliation(Config.ORG1);
			adminUserContext.setMspId(Config.ORG1_MSP);
			
			//***第二步：构建CA客户端
			//构建个CA客户端：用来登录CA服务端（指定组织的ca节点）
            String caUrl = Config.CA_ORG1_URL;//组织1(org1)CA节点的url
			CAClient caClient = new CAClient(caUrl, null);
			caClient.setAdminUserContext(adminUserContext);
			
			//***第三步：通过CA客户端登录CA服务端，从而构建一个登录成功用户
			//注册一个管理员用户：根据ca节点的url、用户名、密码
		    //只要有这3个条件就可登录：登录地址、用户名、密码，登录成功后返回：用户登录凭证类似我们平常登录的sessionid
			//UserContext就类似用户登录成功后的凭证（session）
			adminUserContext = caClient.enrollAdminUser(Config.ADMIN, Config.ADMIN_PASSWORD);
				
			//***第四步：构建一个fabric网络客户端：通过登录成功的用户（具有权限的cli容器）
			//类似fabrc网络启动好了：即网络节点：peer\oderder都启动了，但还没有channel的时候，一个cli
			//用这个cli可以创建通过、节点加入通道、连码安装和实例化
			FabricClient fabClient = new FabricClient(adminUserContext);
			
			//***第五步：创建一个通道客户端，对该通道的连码进行操作：需要关联要操作的节点和orderer节点
			//类似于cli调用时，要指明orderer和peer,查询时只要指明一个peer即可，调用时，需要指明所有背书的节点
			ChannelClient channelClient = fabClient.createChannelClient(Config.CHANNEL_NAME);
			Channel channel = channelClient.getChannel();
			Peer peer = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
			EventHub eventHub = fabClient.getInstance().newEventHub("eventhub01", "grpc://"+Config.baseUrl+":7053");
			Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
			channel.addPeer(peer);
			//channel.addEventHub(eventHub);
			channel.addOrderer(orderer);
			channel.initialize();

			//***第六步：通过通道客户端实现链码查询
//			Collection<ProposalResponse>  responsesQuery = channelClient.queryByChainCode("mycc", "readAsset", new String[] {"11111"});
//			for (ProposalResponse pres : responsesQuery) {
//				//获取响应的负载：链码中放入到success中的内容：shim.Success(nil)
//				String stringResponse = new String(pres.getChaincodeActionResponsePayload());
//				Logger.getLogger(QueryChaincode.class.getName()).log(Level.INFO, stringResponse);
//				System.out.println("响应："+stringResponse);
//			}
			
	  //***第七步：查询通过区块链相关信息：用channel对象查询
			//查询区块链信息
			BlockchainInfo bci = channel.queryBlockchainInfo(peer);//查询区块链信息
			System.out.println("区块高度: " + bci.getHeight());//显示账本的区块高度
			//查询第2个区块信息
			BlockInfo bi = channel.queryBlockByNumber(2);
			ByteString blockhash = bi.getBlock().getHeader().getPreviousHash();
			String blocakhashStr = Hex.encodeHexString(bi.getPreviousHash());
		
			
			//System.out.println("前驱块hash值："+blocakhashStr);
			
			//System.out.println("区块数据："+new String(bi.getBlock().getData().toByteArray()));
			
			
			//查询指定节点的链码信息
			List<Query.ChaincodeInfo> ccdl = channel.queryInstantiatedChaincodes(peer);
			for (Query.ChaincodeInfo a : ccdl) {
				String name  = a.getName();
				String path = a.getPath();
				System.out.println(name + path);
			}
			
			BlockInfo biinfo=channel.queryBlockByTransactionID("a80dcb77382b2d645522e46e93bdd531ba1070a39408c9ed489bb134a883c749");
            System.out.println(biinfo.getBlockNumber());
          //  System.out.println("biinfo区块数据："+new String(biinfo.getBlock().getData().toByteArray()));
            
            TransactionInfo info = channel.queryTransactionByID("a80dcb77382b2d645522e46e93bdd531ba1070a39408c9ed489bb134a883c749");
           // System.out.println("交易信息："+info.getProcessedTransaction().getTransactionEnvelope());
            ProcessedTransaction transaction =info.getProcessedTransaction();
            FabricTransaction.ProcessedTransaction.newBuilder(transaction);
            
            Envelope envlope = info.getProcessedTransaction().getTransactionEnvelope();
            System.out.println("交易****信息："+info.getEnvelope().getPayload().toString(UTF_8));
           // System.out.println("交易****信息888："+info.getEnvelope().getPayload());
            
            ProtoUtils.getCurrentFabricTimestamp();
         
            
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
