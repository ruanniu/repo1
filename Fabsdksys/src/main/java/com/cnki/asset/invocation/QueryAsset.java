package com.cnki.asset.invocation;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Hex;
import org.app.chaincode.invocation.QueryChaincode;
import org.app.client.CAClient;
import org.app.client.ChannelClient;
import org.app.client.FabricClient;
import org.app.config.Config;
import org.app.user.UserContext;
import org.app.util.Util;
import org.hyperledger.fabric.protos.peer.Query;
import org.hyperledger.fabric.sdk.BlockInfo;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.EventHub;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.ProposalResponse;
import org.hyperledger.fabric.sdk.ChaincodeResponse.Status;

import com.google.protobuf.ByteString;

//查询资产
public class QueryAsset {

	public static void main(String args[]) {
		try {
			//先清理缓存到当前工程目录下users目录中的用户本地序列化数据
          //  Util.cleanUp();
			 
            //***第一步：构建用户：fabric-ca-server，客户端可以注册的类型有client,user,peer,validator和auditor
            //此处要注册user类型的客户端用来对区块链进行操作
           	//User接口的实现类，代表fabric网络的一个用户user:内部包含一个登录凭据enrollment
            //需要通过ca客户端登录成功后来获取enrollment（包括登录成功后的证书和公私钥）
			String username="user1000";
			UserContext adminUserContext = new UserContext();
			adminUserContext.setName(username);   //新注册的节点名称    
			adminUserContext.setAffiliation(Config.ORG1); //设置这个cli用户所属的组织
			adminUserContext.setMspId(Config.ORG1_MSP);   //设置组织的mspid
			adminUserContext = Util.readUserContext(adminUserContext.getAffiliation(),adminUserContext.getName());
			//***第二步：构建CA客户端:即fabric-ca-server启动时设置的引导节点
			//构建个CA客户端：用来登录CA服务端（指定组织的ca节点）
           /* String caUrl = Config.CA_ORG1_URL;//组织1(org1)CA节点的url
			CAClient caClient = new CAClient(caUrl, null);
			caClient.setAdminUserContext(adminUserContext);*/
			
			//***第三步：通过CA客户端引导节点登录CA服务端，引导节点进行登记
			//同时其内部又注册了一个user节点，并且user节点进行登记
			//adminUserContext就是通过引导节点注册、并登记的user节点，该user节点可以实现对区块链的操作			
			//adminUserContext = caClient.enrollAdminUser(username, Config.ADMIN_PASSWORD);
				
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
			Collection<ProposalResponse>  responsesQuery = channelClient.queryByChainCode("mycc", "queryAsset", new String[] {"pkrai101"});
			//Collection<ProposalResponse>  responsesQuery = channelClient.queryByChainCode("mycc", "queryHistoryByPkrid", new String[] {"pkr101"});
			
			
			for (ProposalResponse pres : responsesQuery) {
				//获取响应的负载：链码中放入到success中的内容：shim.Success(nil)
				Status status = pres.getStatus();
				if(status.getStatus() == 500){
					System.out.println("查询失败："+pres.getMessage());
					return ;
				}
				
				String stringResponse = new String(pres.getChaincodeActionResponsePayload());
				//pres.getChaincodeActionResponseReadWriteSetInfo();
				Logger.getLogger(QueryChaincode.class.getName()).log(Level.INFO, stringResponse);
				//System.out.println("响应："+stringResponse);
			}				 

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
