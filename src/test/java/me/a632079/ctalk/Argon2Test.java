package me.a632079.ctalk;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import me.a632079.ctalk.util.Argon2Util;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.data.cassandra.DataCassandraTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @className: Argon2Test
 * @description: Argon2Test - argon2哈希
 * @version: v1.0.0
 * @author: haoduor
 */

@SpringBootTest
public class Argon2Test {

    @Resource
    private Argon2Util argon2Util;

    @Test
    public void testHash() {
        String pass = "qweuiorr";
        String hash = argon2Util.hash(pass);
        System.out.println(hash);
        System.out.println(argon2Util.verify(hash, pass));
    }

    @Test
    public void verifyTest() {
        String pass = "asd";

        Argon2 argon2 = Argon2Factory.create();
        String hash = argon2.hash(10, 10240, 1, pass.toCharArray());

        System.out.println(argon2.verify(hash, pass.toCharArray()));
        System.out.println(hash);
        Argon2 argon2Clone = Argon2Factory.create();
        hash = argon2Clone.hash(10, 10240, 1, pass.toCharArray());
        System.out.println(hash);
        System.out.println(argon2Clone.verify(hash, pass.toCharArray()));
    }
}
