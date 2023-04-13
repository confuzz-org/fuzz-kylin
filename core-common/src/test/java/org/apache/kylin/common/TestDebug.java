package org.apache.kylin.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDebug extends HotLoadKylinPropertiesTestCase {

    @Test
    public void test() throws Exception {
        KylinConfig config = KylinConfig.getInstanceFromEnv();
        int count = 0;
        String name = config.getOptional("myname");

        String age = config.getOptional("myage");
        System.out.println("name: " + name + ", age: " + age);

        String str = config.getOptional("fake-param");
        config.setProperty("fake2", "200");
        config.setProperty("fake3", "300");
        assertEquals("200", config.getOptional("fake2"));
        //System.out.println(str);
        if (str == null) {
            System.out.println("Not fuzzing round");
        } else if (str.equals("always")) {
            System.out.println("always");
            count ++;
            // This should fail if the fuzzer is not specificed with -DexpectedException=java.lang.AssertionError
            assertEquals("200", config.getOptional("fake3"));
        } else if (str.equals("asneeded")) {
            System.out.println("asneeded");
            config.getOptional("fake-config1","15");
            count --;
            throw new Exception("Fake Bug asneeded");
        } else {
            System.out.println(str);
        }
        //System.out.println("Conf Length : " + conf.size());
    }
}
