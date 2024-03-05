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

package cc.carm.plugin.mineeconomy.api.currency.option;


import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record CurrencyOptionsHolder(Map<CurrencyOption<?>, Object> data) {

    public static CurrencyOptionsHolder empty() {
        return new CurrencyOptionsHolder(new HashMap<>());
    }

    /**
     * Get the value of option.
     *
     * @param type {@link CurrencyOption}
     * @param <V>  Value type
     * @return Value of option
     */
    @SuppressWarnings("unchecked")
    @Contract("_,!null->!null")
    public <V> @Nullable V get(@NotNull CurrencyOption<V> type, V defaults) {
        return Optional.ofNullable(data().get(type)).map(v -> (V) v).orElse(defaults);
    }

    /**
     * Get the value of option.
     *
     * @param type {@link CurrencyOption}
     * @param <V>  Value type
     * @return Value of option
     */
    public <V> @Nullable V get(@NotNull CurrencyOption<V> type) {
        return get(type, type.defaults());
    }

    /**
     * Set the value of option.
     *
     * @param type  {@link CurrencyOption}
     * @param value Value of option
     * @param <V>   Value type
     * @return Previous value of option
     */
    @SuppressWarnings("unchecked")
    public <V> @Nullable V set(@NotNull CurrencyOption<V> type, @Nullable V value) {
        if (value == null) {
            return (V) data().remove(type);
        } else {
            return (V) data().put(type, value);
        }
    }

    /**
     * Set the value of option to option's {@link CurrencyOption#defaults()}.
     *
     * @param type {@link CurrencyOption}
     * @param <V>  Value type
     * @return Previous value of option
     */
    public <V> @Nullable V clear(@NotNull CurrencyOption<V> type) {
        return set(type, null);
    }


}
