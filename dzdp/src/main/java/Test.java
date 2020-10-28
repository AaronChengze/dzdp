import avro.shaded.com.google.common.collect.Lists;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.spark.SparkConf;
import org.apache.spark.deploy.yarn.Client;
import org.apache.spark.deploy.yarn.ClientArguments;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @Author: lcz
 * @DateTime: 2020-10-28 17:14
 * @Description: TODO
 */
public class Test {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        YarnSubmitConditions conditions = new YarnSubmitConditions();
        conditions.setAppName("test yarn submit app");
        conditions.setMaster("yarn");
        conditions.setSparkHome("spark://lcz:7077");
        conditions.setDeployMode("cluster");
        conditions.setDriverMemory("500m");
        conditions.setExecutorMemory("500m");
        conditions.setExecutorCores("1");
        conditions.setNumExecutors("3");

        // /etc/hadoop/conf.cloudera.yarn/core-site.xml
        conditions.setYarnResourcemanagerAddress("gxq:8088");
        // /etc/hadoop/conf.cloudera.yarn/yarn-site.xml
//        conditions.setSparkFsDefaultFS("hdfs://vm192.168.0.141.com.cn:8020");
        conditions.setFiles(new String[] { "/home/hadoop/hadoop-2.7.7/etc/hadoop/hdfs-site.xml",
                "/home/hadoop/hadoop-2.7.7/etc/hadoop/mapred-site.xml",
                "/home/hadoop/hadoop-2.7.7/etc/hadoop/yarn-site.xml",
        });
        conditions.setApplicationJar("/opt/jar/spark-streaming-driver.jar");
        conditions.setMainClass("Test");
        conditions.setOtherArgs(Arrays.asList("RSRP", "TestBroadcastDriver"));
//        List<String> sparkJars = getSparkJars("/opt/sparkjars/");
//        conditions.setAdditionalJars(sparkJars.toArray(new String[sparkJars.size()]));

        Map<String, String> propertiesMap = null;
        try {
            propertiesMap = getSparkProperties("/home/spark/spark-2.4.6-bin-hadoop2.7/conf/spark-defaults.conf");
        } catch (IOException e) {
            e.printStackTrace();
        }
        conditions.setSparkProperties(propertiesMap);

        String appId = submitSpark(conditions);

        System.out.println("application id is " + appId);
        System.out.println("Complete ....");
    }

    /**
     * 加载sparkjars下的jar文件
     * */
    private static List<String> getSparkJars(String dir) {
        List<String> items = new ArrayList<String>();

        File file = new File(dir);
        for (File item : file.listFiles()) {
            items.add(item.getPath());
        }

        return items;
    }

    /**
     * 加载spark-properties.conf配置文件
     * */
    private static Map<String, String> getSparkProperties(String filePath) throws IOException {
        Map<String, String> propertiesMap = new HashMap<String, String>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (line.trim().length() > 0 && !line.startsWith("#") && line.indexOf("=") != -1) {
                String[] fields = line.split("=");
                propertiesMap.put(fields[0], fields[1]);
            }
        }
        reader.close();

        return propertiesMap;
    }

    /**
     * 提交任务到yarn集群
     *
     * @param conditions
     *            yarn集群，spark，hdfs具体信息，参数等
     * @return appid
     */
    public static String submitSpark(YarnSubmitConditions conditions) {
        logger.info("初始化spark on yarn参数");
        // 初始化yarn客户端￿
        logger.info("初始化spark on yarn客户端");

        List<String> args = Lists.newArrayList(
                "--jar", conditions.getApplicationJar(),
                "--class", conditions.getMainClass()
        );
        if (conditions.getOtherArgs() != null && conditions.getOtherArgs().size() > 0) {
            for (String s : conditions.getOtherArgs()) {
                args.add("--arg");
                args.add(org.apache.commons.lang.StringUtils.join(new String[] { s }, ","));
            }
        }

        // identify that you will be using Spark as YARN mode
        System.setProperty("SPARK_YARN_MODE", "true");

        System.out.println("SPARK_YARN_MODE:" + System.getenv("SPARK_YARN_MODE"));
        System.out.println("SPARK_CONF_DIR:" + System.getenv("SPARK_CONF_DIR"));
        System.out.println("HADOOP_CONF_DIR:" + System.getenv("HADOOP_CONF_DIR"));
        System.out.println("YARN_CONF_DIR:" + System.getenv("YARN_CONF_DIR"));
        System.out.println("SPARK_KAFKA_VERSION:" + System.getenv("SPARK_KAFKA_VERSION"));
        System.out.println("HADOOP_HOME:" + System.getenv("HADOOP_HOME"));
        System.out.println("HADOOP_COMMON_HOME:" + System.getenv("HADOOP_COMMON_HOME"));
        System.out.println("SPARK_HOME:" + System.getenv("SPARK_HOME"));
        System.out.println("SPARK_DIST_CLASSPATH:" + System.getenv("SPARK_DIST_CLASSPATH"));
        System.out.println("SPARK_EXTRA_LIB_PATH:" + System.getenv("SPARK_EXTRA_LIB_PATH"));
        System.out.println("LD_LIBRARY_PATH:" + System.getenv("LD_LIBRARY_PATH"));

        SparkConf sparkConf = new SparkConf();

        sparkConf.setSparkHome(conditions.getSparkHome());
        sparkConf.setMaster(conditions.getMaster());
        sparkConf.set("spark.submit.deployMode", conditions.getDeployMode());
        sparkConf.setAppName(conditions.getAppName());

        // --driver-memory
        sparkConf.set("spark.driver.memory", conditions.getDriverMemory());
        // --executor-memory
        sparkConf.set("spark.executor.memory", conditions.getExecutorMemory());
        // --executor-cores
        sparkConf.set("spark.executor.cores", conditions.getExecutorCores());
        // --num-executors
        sparkConf.set("spark.executor.instance", conditions.getNumExecutors());
        // The folder '.sparkStaging' will be created auto.
        // System.out.println("SPARK_YARN_STAGING_DIR:"+System.getenv("SPARK_YARN_STAGING_DIR"))
        sparkConf.set("spark.yarn.stagingDir", "hdfs://vm192.168.0.141.com.cn:8020/user/");
        // sparkConf.set("spark.jars",);
        // sparkConf.set("spark.yarn.jars", conditions.getSparkYarnJars());
        if (conditions.getAdditionalJars() != null && conditions.getAdditionalJars().length > 0) {
            sparkConf.set("spark.repl.local.jars", org.apache.commons.lang.StringUtils.join(conditions.getAdditionalJars(), ","));
            sparkConf.set("spark.yarn.dist.jars", org.apache.commons.lang.StringUtils.join(conditions.getAdditionalJars(), ","));
        }

        // "--files","hdfs://node1:8020/user/root/yarn-site.xml",
        if (conditions.getFiles() != null && conditions.getFiles().length > 0) {
            sparkConf.set("spark.files", org.apache.commons.lang.StringUtils.join(conditions.getFiles(), ","));
        }

        for (Map.Entry<String, String> e : conditions.getSparkProperties().entrySet()) {
            sparkConf.set(e.getKey().toString(), e.getValue().toString());
        }

        // mapred-site.xml
        // 指定使用yarn框架
        sparkConf.set("mapreduce.framework.name", "yarn");
        // 指定historyserver
        sparkConf.set("mapreduce.jobhistory.address", "vm192.168.0.141.com.cn:10020");

        // yarn-site.xml
        // 添加这个参数，不然spark会一直请求0.0.0.0:8030,一直重试
        sparkConf.set("yarn.resourcemanager.hostname", conditions.getYarnResourcemanagerAddress().split(":")[0]);
        // 指定资源分配器
        sparkConf.set("yarn.resourcemanager.scheduler.address", "vm192.168.0.141.com.cn:8030");
        // 设置为true，不删除缓存的jar包，因为现在提交yarn任务是使用的代码配置，没有配置文件，删除缓存的jar包有问题，
        sparkConf.set("spark.yarn.preserve.staging.files", "false");

        // spark2.2
        // 初始化 yarn的配置
        // Configuration cf = new Configuration();
        // String cross_platform = "false";
        // String os = System.getProperty("os.name");
        //     if (os.contains("Windows")) {
        //     cross_platform = "true";
        // }
        // 配置使用跨平台提交任务
        // cf.set("mapreduce.app-submission.cross-platform", cross_platform);
        // 设置yarn资源，不然会使用localhost:8032
        // cf.set("yarn.resourcemanager.address",
        // conditions.getYarnResourcemanagerAddress());
        // 设置namenode的地址，不然jar包会分发，非常恶心
        // cf.set("fs.defaultFS", conditions.getSparkFsDefaultFS());

        // spark2.2
        // Client client = new Client(cArgs, cf, sparkConf);
        // spark2.3
        ClientArguments cArgs = new ClientArguments(args.toArray(new String[args.size()]));
        org.apache.spark.deploy.yarn.Client client = new Client(cArgs, sparkConf);

        logger.info("提交任务，任务名称：" + conditions.getAppName());

        try {
            ApplicationId appId = client.submitApplication();
            return appId.toString();
        } catch (Exception e) {
            logger.error("提交spark任务失败", e);
            return null;
        } finally {
            if (client != null) {
                client.stop();
            }
        }
    }
}
