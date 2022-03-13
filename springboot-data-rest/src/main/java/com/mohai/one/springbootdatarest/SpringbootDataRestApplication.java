package com.mohai.one.springbootdatarest;

import com.mohai.one.springbootdatarest.dao.UserRepository;
import com.mohai.one.springbootdatarest.domain.AddressDTO;
import com.mohai.one.springbootdatarest.domain.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@SpringBootApplication
public class SpringbootDataRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootDataRestApplication.class, args);
	}

	@Autowired
	private UserRepository userRepository;

    /**
     * 初始化，向数据库中插入一条数据
     */
	@PostConstruct
	public void init(){
         UserDTO userDTO = new UserDTO();
         userDTO.setId(1l);
         userDTO.setSex("man");
         userDTO.setFirstName("Jack");
         userDTO.setLastName("Mr");
         userDTO.setPassword("123456");
         userDTO.setCreatedDate(LocalDateTime.now());
         userDTO.setModifiedDate(LocalDateTime.now());
		 userDTO.setAddressDTO(new AddressDTO("the five street", "123456", "New York", "Y"));
         userRepository.save(userDTO);
	}

}
