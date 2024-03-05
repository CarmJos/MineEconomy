package cc.carm.plugin.mineeconomy.api.user;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;

public record UserKey(
        long id, @NotNull UUID uuid,
        @NotNull String name
) {

    public String getValue(KeyType<?> keyType) {
        return String.valueOf(keyType.get(this));
    }

    public boolean isInstance(KeyType<?> type, Object param) {
        return type.check(this, param);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserKey userKey = (UserKey) o;
        return id == userKey.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public interface KeyTypes {


        KeyType<Long> ID = new KeyType<>("id", Long.class) {
            @Override
            public Long get(UserKey key) {
                return key.id();
            }
        };

        KeyType<UUID> UUID = new KeyType<>("uuid", java.util.UUID.class) {
            @Override
            public java.util.UUID get(UserKey key) {
                return key.uuid();
            }
        };

        /**
         * @deprecated Use {@link KeyTypes#ID} of {@link KeyTypes#UUID} for query.
         */
        @Deprecated
        KeyType<String> NAME = new KeyType<>("name", String.class) {
            @Override
            public String get(UserKey key) {
                return key.name();
            }
        };

        KeyType<?>[] TYPES = new KeyType[]{ID, UUID, NAME};

        static KeyType<?>[] values() {
            return TYPES;
        }


    }

    public abstract static class KeyType<T> {

        private final @NotNull String column;
        private final @NotNull Class<T> valueClass;

        protected KeyType(@NotNull String column,
                          @NotNull Class<T> valueClass) {
            this.column = column;
            this.valueClass = valueClass;
        }

        public boolean isInstance(Object v) {
            return valueClass.isInstance(v);
        }

        public @NotNull String column() {
            return column;
        }

        public @NotNull Class<T> valueClass() {
            return valueClass;
        }

        public abstract T get(UserKey key);

        public boolean check(UserKey key, Object param) {
            if (!isInstance(param)) return false;
            return get(key).equals(valueClass.cast(param));
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (KeyType<?>) obj;
            return Objects.equals(this.column, that.column) &&
                    Objects.equals(this.valueClass, that.valueClass);
        }

        @Override
        public int hashCode() {
            return Objects.hash(column, valueClass);
        }

        @Override
        public String toString() {
            return "KeyType[" +
                    "column=" + column + ", " +
                    "valueClass=" + valueClass + ']';
        }

    }

}
