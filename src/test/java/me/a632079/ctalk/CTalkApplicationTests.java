package me.a632079.ctalk;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import me.a632079.ctalk.po.User;
import me.a632079.ctalk.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class CTalkApplicationTests {

	@Resource
	private UserService userService;

	@Resource
	private Snowflake snowflake;

	@Test
	void contextLoads() {
	}

	@Test
	public void fuckMongo() {
		List<User> userList = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			User user = User.builder()
							.id(snowflake.nextId())
							.password(RandomUtil.randomString(64))
							.avatar(RandomUtil.randomString(20))
							.email(RandomUtil.randomString(10) + "@gmail.com")
							.verify(false)
							.nickName(RandomUtil.randomString(5))
							.build();
			userList.add(user);
		}

		Flux<User> userFlux = userService.saveAll(userList);
		userFlux.blockLast();
	}
}
