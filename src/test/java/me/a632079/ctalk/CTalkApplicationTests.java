package me.a632079.ctalk;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import me.a632079.ctalk.po.Token;
import me.a632079.ctalk.po.User;
import me.a632079.ctalk.repository.TokenRepository;
import me.a632079.ctalk.repository.UserRepository;
import me.a632079.ctalk.util.Argon2Util;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class CTalkApplicationTests {

	@Resource
	private UserRepository userService;

	@Resource
	private TokenRepository tokenRepository;

	@Resource
	private Snowflake snowflake;

	@Resource
	private Argon2Util argon2Util;

	@Test
	void contextLoads() {
	}

	@Test
	@Disabled
	public void addUserTest() {
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

		List<User> users = userService.saveAll(userList);
	}

	@Test
	public void tokenTest() {
		for (int i = 0; i < 10; i++) {
			Token token = Token.builder()
							   .expire(LocalDateTime.now())
							   .token(RandomUtil.randomString(12))
							   .uid(snowflake.nextId())
							   .build();

			tokenRepository.insert(token);
			ThreadUtil.sleep(5, TimeUnit.SECONDS);
		}
	}
}
