package customer;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate ;
//    private final FraudClient fraudClient;
//    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
       Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();
        // todo: check if email valid
        // todo: check if email not taken
        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse=restTemplate.getForObject("http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,customer.getId());
        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");

        }


//        FraudCheckResponse fraudCheckResponse =
//                fraudClient.isFraudster(customer.getId());
//
//        if (fraudCheckResponse.isFraudster()) {
//            throw new IllegalStateException("fraudster");
//        }

//        NotificationRequest notificationRequest = new NotificationRequest(
//                customer.getId(),
//                customer.getEmail(),
//                String.format("Hi %s, welcome to Amigoscode...",
//                        customer.getFirstName())
//        );
//        rabbitMQMessageProducer.publish(
//                notificationRequest,
//                "internal.exchange",
//                "internal.notification.routing-key"
//        );
//
    }

}
