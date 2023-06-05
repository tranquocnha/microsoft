package com.g52.microservice.notification.model.shared.constant;

public interface NotificationSharedConstant {
  // for rabbitMQ
  String NOTIFY_TOPIC_EXCHANGE = "notify.topic.exchange";
  String NOTIFY_ORDER_PAYMENT_ROUTING_KEY = "notify.order.payment.routing.key";
  String NOTIFY_MAIL_ROUTING_KEY = "notify.mail.routing.key";

  // datetime format
  String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  // for others
}
