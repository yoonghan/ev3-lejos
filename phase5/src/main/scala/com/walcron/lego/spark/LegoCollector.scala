import org.apache.spark._
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._
import java.sql.Timestamp
import org.apache.spark.sql._

object LegoCollector{ 


  def init() {
    val spark = SparkSession
                .builder
                .appName("Spark-Kafka-Integration")
                .master("local")
                .getOrCreate()

    import spark.implicits._

    val df = spark
              .readStream
              .format("kafka")
              .option("kafka.bootstrap.servers", "localhost:9092")
              .option("subscribe", "rollerTopic")
              .load()

     val df1 = df.selectExpr("CAST(value AS STRING)", "CAST(timestamp AS TIMESTAMP)").as[(String, Timestamp)]
                 .select(from_json($"value", getStruct).as("data"), $"timestamp")
                 .select("data.*", "timestamp")

     df1.writeStream
        .format("console")
        .option("truncate","false")
        .start()
        .awaitTermination()
  }

  def getStruct() = {
    val rollerStruct = StructType(Array(
                    StructField("id", StringType),
                    StructField("message", StringType),
                    StructField("direction", StringType),
                    StructField("timestamp", TimestampType),
                    StructField("uuid", StringType)
                   ))
    rollerStruct
  }

  def main(args:Array[String]) {
    init()
  }
}
