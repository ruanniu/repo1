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
package org.app.network;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.app.client.FabricClient;
import org.app.config.Config;
import org.app.user.UserContext;
import org.app.util.Util;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.ChannelConfiguration;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.security.CryptoSuite;

/**
 * 
 * @author Balaji Kadambi
 *
 */

public class CreateChannel {

	public static void main(String[] args) {
		try {
			//设置用到的加密算法
			CryptoSuite.Factory.getCryptoSuite();
			Util.cleanUp();
			
			//用 组织1的管理员用户（Admin@org1.example.com）的管理员证书和私钥登录：相当于登录上fabric网络
			UserContext org1Admin = new UserContext();			
			File pkFolder1 = new File(Config.ORG1_USR_ADMIN_PK);
			File[] pkFiles1 = pkFolder1.listFiles();			
			File certFolder1 = new File(Config.ORG1_USR_ADMIN_CERT);
			File[] certFiles1 = certFolder1.listFiles();			
			Enrollment enrollOrg1Admin = Util.getEnrollment(Config.ORG1_USR_ADMIN_PK, pkFiles1[0].getName(),
					Config.ORG1_USR_ADMIN_CERT, certFiles1[0].getName());
			org1Admin.setEnrollment(enrollOrg1Admin);
			org1Admin.setMspId(Config.ORG1_MSP);
			org1Admin.setName(Config.ADMIN);
			
			//构建一个org1管理员用户身份的客户端对象
			FabricClient fabClient = new FabricClient(org1Admin);
			
			//通道创建命令：peer channel create -o orderer.example.com:7050 -c mychannel -f channel.tx
			//创建通道之参数：orderer对象
			Orderer orderer = fabClient.getInstance().newOrderer(Config.ORDERER_NAME, Config.ORDERER_URL);
			//创建通道之channel.tx参数：
			ChannelConfiguration channelConfiguration = new ChannelConfiguration(new File(Config.CHANNEL_CONFIG_PATH));
            //由于在创世区块配置文件里，指明了读、写、管理功能需要签名，因此需要签名
			byte[] channelConfigurationSignatures = fabClient.getInstance().getChannelConfigurationSignature(channelConfiguration, org1Admin);

			//开始创建通道：涉及到链接的组织节点、ordere、channel.tx、签名
			Channel mychannel = fabClient.getInstance().newChannel(Config.CHANNEL_NAME, orderer, channelConfiguration,channelConfigurationSignatures);

	//****************以上通道已经创建成功，下面是节点peer加入***************************\\		
			//加入组织1:org1的节点
			Peer peer0_org1 = fabClient.getInstance().newPeer(Config.ORG1_PEER_0, Config.ORG1_PEER_0_URL);
			Peer peer1_org1 = fabClient.getInstance().newPeer(Config.ORG1_PEER_1, Config.ORG1_PEER_1_URL);
			Peer peer0_org2 = fabClient.getInstance().newPeer(Config.ORG2_PEER_0, Config.ORG2_PEER_0_URL);
			Peer peer1_org2 = fabClient.getInstance().newPeer(Config.ORG2_PEER_1, Config.ORG2_PEER_1_URL);
			mychannel.joinPeer(peer0_org1);
			mychannel.joinPeer(peer1_org1);
			
			//此处并不是加入orderer节点，通道向orderer注册，并不需要加入orderer节点
			//应用仅仅是添加一个orderer对象，方便管理，具体什么作用不太清楚？？？
			mychannel.addOrderer(orderer);

			//对通道进行初始化工作：加载组织所有peer的CA证书到channel的信任存储中，以便能够验证peer节点消息中的签名，开启事件等
			mychannel.initialize();
						
			//用 组织2的管理员用户（Admin@org2.example.com）的管理员证书和私钥登录：相当于登录上fabric网络
			UserContext org2Admin = new UserContext();
			File pkFolder2 = new File(Config.ORG2_USR_ADMIN_PK);
			File[] pkFiles2 = pkFolder2.listFiles();
			File certFolder2 = new File(Config.ORG2_USR_ADMIN_CERT);
			File[] certFiles2 = certFolder2.listFiles();
			Enrollment enrollOrg2Admin = Util.getEnrollment(Config.ORG2_USR_ADMIN_PK, pkFiles2[0].getName(),
					Config.ORG2_USR_ADMIN_CERT, certFiles2[0].getName());
			org2Admin.setEnrollment(enrollOrg2Admin);
			org2Admin.setMspId(Config.ORG2_MSP);
			org2Admin.setName(Config.ADMIN);
			
			//fabric客户端链接组织2的节点：相当于cli容器切换到org2的节点上
			fabClient.getInstance().setUserContext(org2Admin);
			
			//通过组织2的管理员用户登录到网络获取通道，然后加入org2的节点
			mychannel = fabClient.getInstance().getChannel("mychannel");
			mychannel.joinPeer(peer0_org2);
			mychannel.joinPeer(peer1_org2);
			
			Logger.getLogger(CreateChannel.class.getName()).log(Level.INFO, "Channel created "+mychannel.getName());
          
			//遍历显示下加入通道的peer
			Collection peers = mychannel.getPeers();
            Iterator peerIter = peers.iterator();
            while (peerIter.hasNext())
            {
            	  Peer pr = (Peer) peerIter.next();
            	  Logger.getLogger(CreateChannel.class.getName()).log(Level.INFO,pr.getName()+ " at " + pr.getUrl());
            }
            
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
