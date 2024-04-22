package me.a632079.ctalk;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.RandomUtil;
import me.a632079.ctalk.po.User;
import me.a632079.ctalk.service.UserService;
import me.a632079.ctalk.util.Argon2Util;
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

	@Resource
	private Argon2Util argon2Util;

	@Test
	void contextLoads() {
	}

	@Test
	public void fuckMongo() {
		List<User> userList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			User user = User.builder()
							.password(argon2Util.hash(RandomUtil.randomString(64)))
							.avatar(RandomUtil.randomString(20))
							.email(RandomUtil.randomString(10) + "@gmail.com")
							.verify(false)
							.nickName(RandomUtil.randomString(5))
							.build();

			user.setId(snowflake.nextId());
			userList.add(user);
		}

		Flux<User> userFlux = userService.saveAll(userList);
		userFlux.blockLast();
	}
}
