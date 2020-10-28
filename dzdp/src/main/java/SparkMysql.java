/**
 * @Author: lcz
 * @DateTime: 2020-10-28 13:41
 * @Description: 对Mysql中数据表的读取操作
 */

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

import java.util.Properties;

public class SparkMysql {
    public static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SparkMysql.class);
    private static SparkSession spark = SparkSession.builder()
            .appName("SparkMysql")
            .master("spark://lcz:7077")
            .config("spark.default.parallelism", 100)
            .config("spark.sql.shuffle.partitions", 100)
            .config("spark.driver.maxResultSize", "500m")
            .getOrCreate();
    private static JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());

    public static void main(String[] args) {

        SQLContext sqlContext = new SQLContext(sc);
        //读取mysql数据
        readMySQL(sqlContext);

        //停止SparkContext
        sc.stop();
    }

    private static void readMySQL(SQLContext sqlContext) {
        //jdbc.url=jdbc:mysql://localhost:3306/database
        String url = "jdbc:mysql://lcz:3306/dianping";
        //查找的表名
        String table = "user_test";
        //增加数据库的用户名(user)密码(password),指定test数据库的驱动(driver)
        Properties connectionProperties = new Properties();
        connectionProperties.put("user", "root");
        connectionProperties.put("password", "password");
        connectionProperties.put("driver", "com.mysql.jdbc.Driver");

        //SparkJdbc读取Postgresql的products表内容
        System.out.println("读取test数据库中的user_test表内容");
        // 读取表中所有数据
        Dataset<Row> jdbc = sqlContext.read()
                .jdbc(url, table, connectionProperties);
        //显示数据
        jdbc.show();
    }
}
