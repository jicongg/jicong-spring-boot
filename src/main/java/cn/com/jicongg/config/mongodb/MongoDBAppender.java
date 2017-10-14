/** */
package cn.com.jicongg.config.mongodb;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.BasicDBObject;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import cn.com.jicongg.config.spring.ApplicationContextProvider;

/**
 * 日志记录适配器.
 * @author cong.ji
 *
 * Date: 2017-10-12
 */
public class MongoDBAppender extends AppenderBase<ILoggingEvent> {

  @Override
  protected void append(ILoggingEvent eventObject) {
    MongoTemplate mongoTemplate = ApplicationContextProvider.getBean(MongoTemplate.class);
    if (mongoTemplate != null) {
      final BasicDBObject doc = new BasicDBObject();
      String loggerName = eventObject.getLoggerName();
      if (loggerName != null && loggerName.contains("cn.com.jicongg")) {
        doc.append("level", eventObject.getLevel().toString());
        doc.append("logger", eventObject.getLoggerName());
        doc.append("thread", eventObject.getThreadName());
        doc.append("message", eventObject.getFormattedMessage());
        doc.append("time", eventObject.getTimeStamp());
        mongoTemplate.insert(doc, "log");
      }
    }
  }
}
