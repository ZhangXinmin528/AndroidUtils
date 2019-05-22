package com.zxm.utils.core.net;

import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.zxm.utils.core.ShellUtils;
import com.zxm.utils.core.log.MLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhangXinmin on 2019/5/17.
 * Copyright (c) 2018 . All rights reserved.
 */
public class PingUtil {
    private static final String TAG = PingUtil.class.getSimpleName();

    private static final String TYPE_SPACE = " ";

    /**
     * Stop ping after a specified number of times.
     */
    private static final String COMMAND_COUNT = "-c";

    /**
     * Time interval to send ping package,default is 1 second.
     */
    private static final String COMMAND_INTERVAL = "-i";

    private PingUtil() {
        throw new UnsupportedOperationException("U con't do this!");
    }

    /**
     * Check ping.
     *
     * @param count    A specified number of times to stop ping.
     * @param interval Time interval to send ping package,default is 1 second.
     * @param ip       Ip address.
     * @param isRoot   Mobile device root state.
     * @return result.
     */
    public static PingResult ping(@IntRange(from = 1) int count,
                                  @FloatRange(from = 0.1) float interval,
                                  @NonNull String ip,
                                  boolean isRoot) {
        return ping(count, interval, ip, isRoot, true);

    }

    /**
     * Check ping.
     *
     * @param count     A specified number of times to stop ping.
     * @param interval  Time interval to send ping package,default is 1 second.
     * @param ip        Ip address.
     * @param isRoot    Mobile device root state.
     * @param isNeedMsg Wheather the result is needed.
     * @return result.
     */
    public static PingResult ping(@IntRange(from = 1) int count,
                                  @FloatRange(from = 0.1) float interval,
                                  @NonNull String ip,
                                  boolean isRoot, boolean isNeedMsg) {
        if (!TextUtils.isEmpty(ip)) {
            final StringBuffer sb = new StringBuffer();
            sb.append("ping");
            sb.append(TYPE_SPACE);
            if (count >= 1) {
                sb.append(COMMAND_COUNT).append(TYPE_SPACE).append(count).append(TYPE_SPACE);
            }
            if (interval >= 0.1) {
                sb.append(COMMAND_INTERVAL).append(TYPE_SPACE).append(interval).append(TYPE_SPACE);
            }
            sb.append(ip);

            final String command = sb.toString();
            final ShellUtils.CommandResult commandResult = ShellUtils.execCmd(command, isRoot, isNeedMsg);
            if (commandResult != null) {
                final PingResult pingResult = new PingResult();
                final PingResponse pingResponse = new PingResponse();
                pingResult.setCode(commandResult.result);
                if (commandResult.result == 0) {
                    pingResponse.setIP(ip);
                    final String successMsg = commandResult.successMsg;
                    MLogger.i(TAG, "msg : " + successMsg);
                    if (!TextUtils.isEmpty(successMsg)) {
                        try {
                            final String[] msgArray = successMsg.trim().split("\n");
                            final int length = msgArray.length;
                            if (length == count + 5) {
                                final List<PingSequence> list = new ArrayList<>();

                                for (int i = 0; i < length; i++) {
                                    final String item = msgArray[i].trim();
                                    if (!TextUtils.isEmpty(item)) {
                                        //序列数据
                                        if (i <= count && i > 0) {
                                            list.add(parsePingSequence(item));
                                        }

                                        //统计数据
                                        if (i == count + 3) {
                                            if (item.contains("transmitted")) {
                                                pingResponse.setPingStatistics(item);
                                            }
                                        }

                                        //时间统计
                                        if (i == length - 1) {
                                            final PingTimeStatistics timeStatistics = new PingTimeStatistics();
                                            if (item.contains("=")) {
                                                final String[] timeStatisticsArray = item.split("=");
                                                if (timeStatisticsArray.length == 2) {
                                                    final String time = timeStatisticsArray[1];
                                                    if (time.contains("/")) {
                                                        final String[] timeArray = time.split("\\/");
                                                        timeStatistics.setMinMillseconds(timeArray[0]);
                                                        timeStatistics.setAvgMillseconds(timeArray[1]);
                                                        timeStatistics.setMaxMillseconds(timeArray[2]);
                                                        timeStatistics.setDeviation(timeArray[3]);
                                                        pingResponse.setTimeStatistics(timeStatistics);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                pingResponse.setSeqList(list);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    pingResponse.setErrorMsg(commandResult.errorMsg);
                }
                pingResult.setResponse(pingResponse);
                return pingResult;
            }
        }
        return null;
    }

    /**
     * 解析序列数据
     *
     * @param data
     * @return
     */
    private static PingSequence parsePingSequence(@NonNull String data) {
        if (!TextUtils.isEmpty(data) && data.contains("icmp_seq")) {
            final PingSequence pingSequence = new PingSequence();
            if (data.contains(":")) {
                final String[] seqArray = data.trim().split(":");
                if (seqArray.length == 2) {
                    final String sumary = seqArray[0];
                    if (sumary.contains("from")) {
                        final String[] sumaryArray = sumary.split("from");
                        pingSequence.setSeqMemory(sumaryArray[0].trim());
                        pingSequence.setSeqIP(sumaryArray[1].trim());
                    }
                    final String detial = seqArray[1];
                    if (detial.contains(" ")) {
                        final String[] detialArray = detial.trim().split(" ");
                        if (detialArray.length == 4) {
                            final String icmp_seq = detialArray[0];
                            final String ttl = detialArray[1];
                            final String time = detialArray[2];

                            if (icmp_seq.contains("=")) {
                                pingSequence.setSeqIndex(icmp_seq.split("=")[1]);
                            }

                            if (ttl.contains("=")) {
                                pingSequence.setTtl(ttl.split("=")[1]);
                            }

                            if (time.contains("=")) {
                                pingSequence.setTime(time.split("=")[1] + " " + detialArray[3]);
                            }

                        }
                    }
                    return pingSequence;
                }
            }
        }
        return null;
    }

    /**
     * Ping result
     */
    public static class PingResult {
        private int code;
        private PingResponse response;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public PingResponse getResponse() {
            return response;
        }

        public void setResponse(PingResponse response) {
            this.response = response;
        }

        @Override
        public String toString() {
            return "PingResult{" +
                    "\n code=" + code +
                    ",\n response=" + response +
                    '}';
        }
    }

    /**
     * Ping response
     */
    public static class PingResponse {
        //地址
        private String IP;
        //序列数据
        private List<PingSequence> seqList;
        //统计数据
        private String pingStatistics;
        //时间统计
        private PingTimeStatistics timeStatistics;
        //错误信息
        private String errorMsg;

        public String getIP() {
            return IP;
        }

        public void setIP(String IP) {
            this.IP = IP;
        }

        public List<PingSequence> getSeqList() {
            return seqList;
        }

        public void setSeqList(List<PingSequence> seqList) {
            this.seqList = seqList;
        }

        public String getErrorMsg() {
            return errorMsg;
        }

        public void setErrorMsg(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public PingTimeStatistics getTimeStatistics() {
            return timeStatistics;
        }

        public void setTimeStatistics(PingTimeStatistics timeStatistics) {
            this.timeStatistics = timeStatistics;
        }

        public String getPingStatistics() {
            return pingStatistics;
        }

        public void setPingStatistics(String pingStatistics) {
            this.pingStatistics = pingStatistics;
        }

        @Override
        public String toString() {
            return "PingResponse{" +
                    "\n IP='" + IP + '\'' +
                    ",\n seqList=" + seqList +
                    ",\n pingStatistics='" + pingStatistics + '\'' +
                    ",\n timeStatistics=" + timeStatistics +
                    ",\n errorMsg='" + errorMsg + '\'' +
                    '}';
        }
    }

    /**
     * Ping序列数据
     */
    public static class PingSequence {
        //序号
        private String seqIndex;
        //序列IP地址
        private String seqIP;
        //数据大小
        private String seqMemory;
        //
        private String ttl;

        private String time;

        public String getSeqIndex() {
            return seqIndex;
        }

        public void setSeqIndex(String seqIndex) {
            this.seqIndex = seqIndex;
        }

        public String getSeqIP() {
            return seqIP;
        }

        public void setSeqIP(String seqIP) {
            this.seqIP = seqIP;
        }

        public String getSeqMemory() {
            return seqMemory;
        }

        public void setSeqMemory(String seqMemory) {
            this.seqMemory = seqMemory;
        }

        public String getTtl() {
            return ttl;
        }

        public void setTtl(String ttl) {
            this.ttl = ttl;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public String toString() {
            return "PingSequence{" +
                    "seqIndex='" + seqIndex + '\'' +
                    ", seqIP='" + seqIP + '\'' +
                    ", seqMemory='" + seqMemory + '\'' +
                    ", ttl='" + ttl + '\'' +
                    ", time='" + time + '\'' +
                    '}';
        }
    }

    public static class PingTimeStatistics {
        //最短时间
        private String minMillseconds;
        //平均时间
        private String avgMillseconds;
        //最大时间
        private String maxMillseconds;
        //平均偏差
        private String deviation;


        public String getMinMillseconds() {
            return minMillseconds;
        }

        public void setMinMillseconds(String minMillseconds) {
            this.minMillseconds = minMillseconds;
        }

        public String getAvgMillseconds() {
            return avgMillseconds;
        }

        public void setAvgMillseconds(String avgMillseconds) {
            this.avgMillseconds = avgMillseconds;
        }

        public String getMaxMillseconds() {
            return maxMillseconds;
        }

        public void setMaxMillseconds(String maxMillseconds) {
            this.maxMillseconds = maxMillseconds;
        }

        public String getDeviation() {
            return deviation;
        }

        public void setDeviation(String deviation) {
            this.deviation = deviation;
        }

        @Override
        public String toString() {
            return "PingTimeStatistics{" +
                    "\n minMillseconds='" + minMillseconds + '\'' +
                    ",\n avgMillseconds='" + avgMillseconds + '\'' +
                    ",\n maxMillseconds='" + maxMillseconds + '\'' +
                    ",\n deviation='" + deviation + '\'' +
                    '}';
        }
    }
}
