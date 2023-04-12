package org.apache.kylin.common;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import edu.illinois.confuzz.internal.ConfuzzGenerator;

import java.util.Properties;

public class ConfigurationGenerator extends Generator<Properties> {
    private static Properties generatedConf = null;
    private static String setMethodName = "setProperty";
    private static Class<?>[] setParameterTypes = {String.class, String.class};

    public ConfigurationGenerator() {
        super(Properties.class);
    }

    private static Properties copyProperty() {
        Properties copy = new Properties();
        copy.putAll(generatedConf);
        return copy;
    }

    public static Properties getGeneratedConfig() {
        if (generatedConf == null) {
            return new Properties();
        }
        return copyProperty();
    }

    /**
     * Generates a value, possibly influenced by a source of randomness and
     * metadata about the generation.
     *
     * @param random source of randomness to be used when generating the value
     * @param status an object that can be used to influence the generated
     *               value. For example, generating lists can use the {@link
     *               GenerationStatus#size() size} method to generate lists with a given
     *               number of elements.
     * @return the generated value
     */
    @Override
    public Properties generate(SourceOfRandomness random, GenerationStatus status) {
        Properties conf = new Properties();
        try {
            generatedConf = (Properties) ConfuzzGenerator.generate(random, conf, conf.getClass(), setMethodName, setParameterTypes);
            return generatedConf;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
