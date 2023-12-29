package com.engima.shopeymart.service.impl;

import com.engima.shopeymart.constant.ERole;
import com.engima.shopeymart.dto.request.CustomerRequest;
import com.engima.shopeymart.dto.response.CustomerResponse;
import com.engima.shopeymart.entity.Customer;
import com.engima.shopeymart.entity.Role;
import com.engima.shopeymart.entity.UserCredential;
import com.engima.shopeymart.repository.CustomerRepository;
import com.engima.shopeymart.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomerServiceImplTest {
    @InjectMocks
    private final CustomerRepository customerRepository = mock(CustomerRepository.class);

    @Mock
    private  CustomerService customerService = new CustomerServiceImpl(customerRepository);

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    void create() {
        Customer dummyCust = new Customer();
//        dummyCust.setId("1");
//        dummyCust.setName("raka");
//        dummyCust.setEmail("Raka@gmail.com");
//        dummyCust.setMobilePhone("081272385");
//        dummyCust.setAddress("jalan damai");
        CustomerRequest dummyCustomer = new CustomerRequest();
        //kalau ga buat dummy bisa pakai any(Customer.class)
        dummyCustomer.setId("1");
        dummyCustomer.setName("raka");
        dummyCustomer.setEmail("Raka@gmail.com");
        dummyCustomer.setMobilePhone("081272385");
        dummyCustomer.setAddress("jalan damai");
        when(customerRepository.save(any())).thenReturn(dummyCust);

        CustomerResponse createCustomer = customerService.create(dummyCustomer);

        verify(customerRepository, times(1)).save(any(Customer.class));
        assertEquals(dummyCustomer.getId(), createCustomer.getId());
        assertEquals(dummyCustomer.getName(), createCustomer.getFullName());
    }

    @Test
    void update() {
        CustomerRequest dummyCustomerReq = new CustomerRequest();
        dummyCustomerReq.setId("1");
        dummyCustomerReq.setName("andi");
        dummyCustomerReq.setEmail("andi@gmail.com");
        dummyCustomerReq.setAddress("ragunan");
        dummyCustomerReq.setMobilePhone("07328498324");

        Customer existingCustomer = new Customer("1", "surya", "surya@gmail.com", "bogor", "0923839845");

        when(customerRepository.findById(dummyCustomerReq.getId())).thenReturn(Optional.of(existingCustomer));

        CustomerResponse customerResponse = customerService.update(dummyCustomerReq);

        verify(customerRepository, times(1)).findById(dummyCustomerReq.getId());

        verify(customerRepository, times(1)).save(any(Customer.class));

        assertNotNull(customerResponse);
        assertEquals(dummyCustomerReq.getId(), customerResponse.getId());
        assertEquals(dummyCustomerReq.getName(), customerResponse.getFullName());
    }

    @Test
    void createNewCustomer() {
        Customer dummyCustomer = new Customer();
        //kalau ga buat dummy bisa pakai any(Customer.class)
//        dummyCustomer.setId("1");
//        dummyCustomer.setName("raka");
//        dummyCustomer.setEmail("Raka@gmail.com");
//        dummyCustomer.setMobilePhone("081272385");
//        dummyCustomer.setAddress("jalan damai");
        when(customerRepository.saveAndFlush(any(Customer.class))).thenReturn(dummyCustomer);

        CustomerResponse createCustomer = customerService.createNewCustomer(dummyCustomer);

        verify(customerRepository, times(1)).saveAndFlush(dummyCustomer);
        assertEquals(dummyCustomer.getId(), createCustomer.getId());
        assertEquals(dummyCustomer.getName(), createCustomer.getFullName());

    }

    @Test
    void delete() {
        String id ="1";
        customerService.delete(id);
        verify(customerRepository, times(1)).deleteById(id);
    }

    @Test
    void getAll() {
        List<Customer> dummyCust = new ArrayList<>();
        dummyCust.add(new Customer("1", "surya", "surya@gmail.com", "bogor", "0923839845"));
        dummyCust.add(new Customer("2", "adam", "adam@gmail.com", "bogor", "6745636356"));
        dummyCust.add(new Customer("3", "suseno", "suseno@gmail.com", "bogor", "345346456"));

        when(customerRepository.findAll()).thenReturn(dummyCust);

        List<CustomerResponse> retrieveStore = customerService.getAll();

        assertEquals(dummyCust.size(), retrieveStore.size());

        for (int i = 0; i < dummyCust.size(); i++) {
            assertEquals(dummyCust.get(i).getName(), retrieveStore.get(i).getFullName());

        }
    }

    @Test
    void getById() {
        String custId = "1";
        Role roleDummy= new Role("1",ERole.ROLE_CUSTOMER);
        UserCredential dummy = new UserCredential("1","test","123abc",roleDummy);
        Customer cust = new Customer("1","surya", "surya@gmail.com", "bogor", "0923839845",dummy);
        when(customerRepository.findById(custId)).thenReturn(Optional.of(cust));
        CustomerResponse customerResponse = customerService.getById(custId);

        verify(customerRepository).findById(custId);
        assertNotNull(customerResponse);
        assertEquals(custId, customerResponse.getId());
        assertEquals("surya", customerResponse.getFullName());

    }
}