package com.atguigu.realtime.flink_test;

import org.apache.flink.runtime.state.hashmap.HashMapStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * description:
 * Created by 铁盾 on 2022/12/30
 */
public class Test {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.enableCheckpointing(5000, CheckpointingMode.EXACTLY_ONCE);
        env.setStateBackend(new HashMapStateBackend());
        env.getCheckpointConfig().setCheckpointStorage(
                "hdfs://mycluster/deployment-test/flink-1.15.2"
        );
        System.setProperty("HADOOP_USER_NAME", "atguigu");

        DataStreamSource<String> source = env.socketTextStream("hadoop102", 12345);
        source.print();


        env.execute();
    }
}
