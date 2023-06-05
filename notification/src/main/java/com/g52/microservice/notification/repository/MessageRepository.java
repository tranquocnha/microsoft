package com.g52.microservice.notification.repository;

import com.g52.microservice.notification.model.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
  Optional<Message> findByReceiverAndBookingId(String receiver, String bookingId);
  List<Message> findAllByReceiverOrderByIdDesc(String receiver);

}
