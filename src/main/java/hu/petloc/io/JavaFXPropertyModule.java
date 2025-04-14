package hu.petloc.io;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

/**
 * Jackson modul a JavaFX tulajdonságok szerializálásához és deszerializálásához.
 */
public class JavaFXPropertyModule extends SimpleModule {

    public JavaFXPropertyModule() {
        super("JavaFXPropertyModule");

        // StringProperty szerializálók és deszerializálók
        addSerializer(StringProperty.class, new StringPropertySerializer());
        addDeserializer(StringProperty.class, new StringPropertyDeserializer());

        // IntegerProperty szerializálók és deszerializálók
        addSerializer(IntegerProperty.class, new IntegerPropertySerializer());
        addDeserializer(IntegerProperty.class, new IntegerPropertyDeserializer());
    }

    /**
     * StringProperty szerializáló.
     */
    private static class StringPropertySerializer extends StdSerializer<StringProperty> {

        public StringPropertySerializer() {
            super(StringProperty.class);
        }

        @Override
        public void serialize(StringProperty value, JsonGenerator gen, SerializerProvider provider)
                throws IOException {
            gen.writeString(value.get());
        }
    }

    /**
     * StringProperty deszerializáló.
     */
    private static class StringPropertyDeserializer extends StdDeserializer<StringProperty> {

        public StringPropertyDeserializer() {
            super(StringProperty.class);
        }

        @Override
        public StringProperty deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            String value = p.getText();
            return new SimpleStringProperty(value);
        }
    }

    /**
     * IntegerProperty szerializáló.
     */
    private static class IntegerPropertySerializer extends StdSerializer<IntegerProperty> {

        public IntegerPropertySerializer() {
            super(IntegerProperty.class);
        }

        @Override
        public void serialize(IntegerProperty value, JsonGenerator gen, SerializerProvider provider)
                throws IOException {
            gen.writeNumber(value.get());
        }
    }

    /**
     * IntegerProperty deszerializáló.
     */
    private static class IntegerPropertyDeserializer extends StdDeserializer<IntegerProperty> {

        public IntegerPropertyDeserializer() {
            super(IntegerProperty.class);
        }

        @Override
        public IntegerProperty deserialize(JsonParser p, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            int value = p.getIntValue();
            return new SimpleIntegerProperty(value);
        }
    }
}