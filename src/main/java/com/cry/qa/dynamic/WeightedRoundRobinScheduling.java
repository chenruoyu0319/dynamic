package com.cry.qa.dynamic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Chen ruoyu
 * @Description: 权重轮询调度算法(WeightedRound - RobinScheduling)-Java实现
 * @Date Created in:  2021-02-07 12:38
 * @Modified By:
 */
public class WeightedRoundRobinScheduling {

    /**
     * 上一次选择的服务器
     */
    private int currentIndex = -1;

    /**
     * 当前调度的权值
     */
    private int currentWeight = 0;

    /**
     * 最大权重
     */
    private int maxWeight = 0;

    /**
     * 所有服务器权重的最大公约数
     */
    private int gcdWeight = 0;

    /**
     * 服务器数量
     */
    private int serverCount = 2;

    /**
     * 服务器集合
     */
    private List<Server> serverList;

    /**
     * 返回最大公约数
     *
     * @param a
     * @param b
     * @return
     */
    private static int gcd(int a, int b) {
        BigInteger b1 = new BigInteger(String.valueOf(a));
        BigInteger b2 = new BigInteger(String.valueOf(b));
        BigInteger gcd = b1.gcd(b2);
        return gcd.intValue();
    }

    /**
     * 返回所有服务器权重的最大公约数
     *
     * @param serverList
     * @return
     */
    private static int getGCDForServers(List<Server> serverList) {
        int w = 0;
        for (int i = 0, len = serverList.size(); i < len - 1; i++) {
            if (w == 0) {
                w = gcd(serverList.get(i).weight, serverList.get(i + 1).weight);
            } else {
                w = gcd(w, serverList.get(i + 1).weight);
            }
        }
        return w;
    }

    /**
     * 返回所有服务器中的最大权重
     *
     * @param serverList
     * @return
     */
    public static int getMaxWeightForServers(List<Server> serverList) {
        int w = 0;
        for (int i = 0, len = serverList.size(); i < len - 1; i++) {
            if (w == 0) {
                w = Math.max(serverList.get(i).weight, serverList.get(i + 1).weight);
            } else {
                w = Math.max(w, serverList.get(i + 1).weight);
            }
        }
        return w;
    }

    /**
     * 算法流程：
     * 假设有一组服务器 S = {S0, S1, …, Sn-1}
     * 有相应的权重，变量currentIndex表示上次选择的服务器
     * 权值currentWeight初始化为0，currentIndex初始化为-1 ，当第一次的时候返回 权值取最大的那个服务器，
     * 通过权重的不断递减 寻找 适合的服务器返回，直到轮询结束，权值返回为0
     * @return
     */
    public Server getServer() {
        while (true) {
            currentIndex = (currentIndex + 1) % serverCount;
            if (currentIndex == 0) {
                currentWeight = currentWeight - gcdWeight;
                if (currentWeight <= 0) {
                    currentWeight = maxWeight;
                    if (currentWeight == 0) {
                        return null;
                    }
                }
            }
            if (serverList.get(currentIndex).weight >= currentWeight) {
                return serverList.get(currentIndex);
            }
        }
    }

    /**
     * 服务器对象
     */
    class Server {
        public String slaveId;
        /**
         * 轮询权重
         */
        public int weight;

        public Server(String slaveId, int weight) {
            this.slaveId = slaveId;
            this.weight = weight;
        }

        public String getSlaveId() {
            return slaveId;
        }

        public void setSlaveId(String ip) {
            this.slaveId = slaveId;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }

    public void init() {
        Server s1 = new Server(DynamicDataSourceHolder.DB_SLAVE_1, 1);
        Server s2 = new Server(DynamicDataSourceHolder.DB_SLAVE_1, 2);
        serverList = new ArrayList<>();
        serverList.add(s1);
        serverList.add(s2);

        currentIndex = -1;
        currentWeight = 0;
        serverCount = serverList.size();
        maxWeight = getMaxWeightForServers(serverList);
        gcdWeight = getGCDForServers(serverList);
    }
}
