package jpabook.jpashop;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module.Feature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpashopApplication.class, args);
	}

	@Bean
	Hibernate5Module hibernate5Module() {
		return new Hibernate5Module(); //초기화 되지 않은 프록시 객체는 null
	}

//	@Bean
//	Hibernate5Module hibernate5Module() {
//		Hibernate5Module hibernate5Module = new Hibernate5Module();
//		hibernate5Module.configure(Feature.FORCE_LAZY_LOADING, true); //레이지 로딩들을 강제로 로딩시킴, 연관된 것 까지 모두
//		return hibernate5Module;
//	}
}
