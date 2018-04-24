# Digital-Platform-Health-Monitoring
Digital Platform Health Monitoring using Kafka,Flume,Hive &amp; Spark Streaming .The architecture diagram provides the holistic view of the implementation details of capturing the Vstat details of the digital platform linux server by using the Flume data ingestion tool .
Flume captures the data every 5 seconds and puts it in the kafka cluster server in a topic.

Spark Streaming application pulls the topic from Kafka server and loads it into the Hive database for further analysis and remedial steps to proactively come up with measures to avoid system outages.

Once implemented you can observe in the spark shell the following output and same getting stored in a vmstat hive table

OUTPUT:
+---+---+-------+-----+----+-----+---+---+-----+---+----+----+---+---+---+---+
|  r|  b|   swpd| free|buff|cache| si| so|   bi| bo| ins|  cs| us| sy| id| wa|
+---+---+-------+-----+----+-----+---+---+-----+---+----+----+---+---+---+---+
|  1|  1|1236056|94084| 136|29332|  0|  0|10472| 24|1599|3715|  4|  4|  0| 91|
|  0|  1|1236032|94076| 216|28728| 32|  0|12852| 72|1633|4036|  4|  7|  0| 89|
|  0|  1|1236020|96788|  92|25492|  0|  0|11640|  4|1796|4689|  8|  7|  0| 86|
|  0|  1|1236016|94680| 396|26660|  0|  0|24824| 48|2420|5135| 33| 20|  0| 47|
|  3|  3|1236012|85132| 360|37284|  0|  0|26756|144|2234|4865|  6| 13|  0| 82|
+---+---+-------+-----+----+-----+---+---+-----+---+----+----+---+---+---+---+


  
only showing top 5 rows

+---+---+-------+------+----+-----+---+---+-----+---+----+----+---+---+---+---+
|  r|  b|   swpd|  free|buff|cache| si| so|   bi| bo| ins|  cs| us| sy| id| wa|
+---+---+-------+------+----+-----+---+---+-----+---+----+----+---+---+---+---+
|  2|  1|1236380|100216|  96|21416|492|  0|10100|  0|1568|3876|  5|  6|  0| 88|
|  2|  5|1236376| 87752| 348|33028|  0|  0|49520| 40|3424|6006| 28| 28|  0| 44|
|  0|  2|1236352| 93836| 248|27724|  0|  0|29664|620|2809|5142| 11| 18|  0| 71|
|  0|  2|1236136| 92332| 164|28844|260|  0|24012|148|2408|4750|  3| 14|  0| 83|
|  1|  1|1236084| 97804| 120|22880|268|  0|12380| 32|1674|3868|  2|  5|  0| 92|
+---+---+-------+------+----+-----+---+---+-----+---+----+----+---+---+---+---+

+---+---+-------+------+----+-----+---+---+-----+---+----+----+---+---+---+---+
|  r|  b|   swpd|  free|buff|cache| si| so|   bi| bo| ins|  cs| us| sy| id| wa|
+---+---+-------+------+----+-----+---+---+-----+---+----+----+---+---+---+---+
|  0|  1|1236076|100780| 120|20168|  0|  0| 2412|  0|1269|3340|  3|  0|  0| 97|
|  0|  1|1236068| 92844| 524|28016|  0|  0|40316| 60|3063|5866| 34| 29|  0| 37|
|  0|  1|1236056| 95200| 152|25444|  0|  0| 9928|160|1628|3766|  3|  4|  0| 92|
+---+---+-------+------+----+-----+---+---+-----+---+----+----+---+---+---+---+



