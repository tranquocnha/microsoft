package g52.training.controller;

import g52.training.dto.Employee;
import g52.training.repository.PaymentJpaRepository;
import g52.training.service.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/rabbitmq/")
public class RabbitMQWebController {
    @Autowired
    RabbitMQSender rabbitMQSender;

    @Autowired
    PaymentJpaRepository paymentJpaRepository;

    @GetMapping(value = "/producer")
    public String producer(@RequestParam("empName") String empName, @RequestParam("empId") String empId) {

        Employee emp=new Employee();
        emp.setEmpId(empId);
        emp.setEmpName(empName);
//        rabbitMQSender.send(emp);

        return "Message sent to the RabbitMQ JavaInUse Successfully " + emp.getEmpName();
    }
//    http://localhost:8089/rabbitmq/producer?empName=emp1&empId=emp001
//    http://localhost:8089/rabbitmq/producer2?empName=emp1&empId=emp001

    @GetMapping(value = "/producer2")
    public String producer2() {
        return paymentJpaRepository.findByUserId(UUID.fromString("d215b5f8-0249-4dc5-89a3-51fd148cfb41")).get().getUserId().toString();
    }
}
