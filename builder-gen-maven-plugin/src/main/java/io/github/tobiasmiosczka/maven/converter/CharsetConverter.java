package io.github.tobiasmiosczka.maven.converter;

import org.codehaus.plexus.component.configurator.ComponentConfigurationException;
import org.codehaus.plexus.component.configurator.converters.basic.AbstractBasicConverter;

import java.nio.charset.Charset;

public class CharsetConverter extends AbstractBasicConverter {

    @Override
    public boolean canConvert(Class<?> type) {
        return Charset.class.isAssignableFrom(type);
    }

    @Override
    public Object fromString(String value) throws ComponentConfigurationException {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Charset.forName(value.trim());
        } catch (Exception e) {
            throw new ComponentConfigurationException("Could not parse charset '" + value + "'.", e);
        }
    }
}