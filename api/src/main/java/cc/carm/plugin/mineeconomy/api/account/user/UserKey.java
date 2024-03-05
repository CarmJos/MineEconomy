/*
 * This file is part of MineEconomy, licensed under the GPL v3.0.
 *
 * Copyright (c) 2024 Carm <carm@carm.cc>
 * Copyright (c) 2024 Contributors
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cc.carm.plugin.mineeconomy.api.account.user;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

public record UserKey(
        long id, @NotNull UUID uuid,
        @NotNull String name
) {

    public String getValue(UserKeyType<?> userKeyType) {
        return String.valueOf(userKeyType.get(this));
    }

    public boolean isInstance(UserKeyType<?> userKeyType, Object param) {
        return userKeyType.check(this, param);
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

        UserKeyType<Long> ID = new UserKeyType<>("id", Long.class, UserKey::id);
        UserKeyType<UUID> UUID = new UserKeyType<>("uuid", java.util.UUID.class, UserKey::uuid);

        /**
         * @deprecated Use {@link KeyTypes#ID} of {@link KeyTypes#UUID} for query.
         */
        @Deprecated
        UserKeyType<String> NAME = new UserKeyType<>("name", String.class, UserKey::name);

        UserKeyType<?>[] USER_KEY_TYPES = new UserKeyType[]{ID, UUID, NAME};

        static UserKeyType<?>[] values() {
            return USER_KEY_TYPES;
        }

    }

    public static class UserKeyType<T> {
        private final @NotNull String column;
        private final @NotNull Class<T> valueClass;
        private final @NotNull Function<UserKey, T> getter;

        protected UserKeyType(@NotNull String column, @NotNull Class<T> valueClass,
                              @NotNull Function<UserKey, T> getter) {
            this.column = column;
            this.valueClass = valueClass;
            this.getter = getter;
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

        public T get(UserKey key) {
            return getter.apply(key);
        }

        public boolean check(UserKey key, Object param) {
            if (!isInstance(param)) return false;
            return get(key).equals(valueClass.cast(param));
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (UserKeyType<?>) obj;
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
