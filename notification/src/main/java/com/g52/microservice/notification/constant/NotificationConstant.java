package com.g52.microservice.notification.constant;

public interface NotificationConstant {
  // for rabbitMQ
  String NOTIFY_DL_TOPIC_EXCHANGE = "notify.dl.topic.exchange";

  String NOTIFY_ORDER_PAYMENT_TOPIC_QUEUE = "notify.order.payment.topic.queue";
  String NOTIFY_ORDER_PAYMENT_DL_TOPIC_QUEUE = "notify.order.payment.dl.topic.queue";

  String NOTIFY_MAIL_TOPIC_QUEUE = "notify.mail.topic.queue";
  String NOTIFY_MAIL_DL_TOPIC_QUEUE = "notify.mail.dl.topic.queue";

  // for others
}
