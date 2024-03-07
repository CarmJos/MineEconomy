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

package cc.carm.plugin.mineeconomy.api.currency;

import cc.carm.lib.easyplugin.utils.ColorParser;
import cc.carm.plugin.mineeconomy.api.currency.option.CurrencyOptionsHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.math.BigDecimal;
import java.util.Optional;

public class EconomyCurrency {

    public static CurrencyBuilder builderOf(@NotNull String key) {
        return CurrencyBuilder.of(key);
    }

    public static @NotNull EconomyCurrency of(@NotNull String key) {
        return of(key, null, BigDecimal.ZERO, CurrencyOptionsHolder.empty());
    }

    public static @NotNull EconomyCurrency of(@NotNull String key, @Range(from = 0, to = 255) Integer id,
                                              @NotNull BigDecimal baseValue, @NotNull CurrencyOptionsHolder options) {
        return new EconomyCurrency(key, id, baseValue, options);
    }

    protected final @NotNull String key;
    protected final @NotNull BigDecimal baseValue;

    protected final @NotNull CurrencyOptionsHolder options;

    protected @Nullable @Range(from = 0, to = 255) Integer dataID;

    public EconomyCurrency(@NotNull String key, @Nullable @Range(from = 0, to = 255) Integer id,
                           @NotNull BigDecimal baseValue, @NotNull CurrencyOptionsHolder options) {
        this.dataID = id;
        this.key = key;
        this.baseValue = baseValue;
        this.options = options;
    }

    public @NotNull String key() {
        return key;
    }

    public @Range(from = 0, to = 255) int dataID() {
        if (dataID == null) throw new IllegalStateException("Currency not initialized!");
        return dataID;
    }

    public @NotNull BigDecimal baseValue() {
        return baseValue;
    }

    public @NotNull CurrencyOptionsHolder options() {
        return options;
    }

    public @NotNull String name() {
        return ColorParser.parse(options().get(CurrencyOptions.NAME, key()));
    }

    public @Nullable String description() {
        return Optional.ofNullable(options().get(CurrencyOptions.DESCRIPTION)).map(ColorParser::parse).orElse(null);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        EconomyCurrency that = (EconomyCurrency) object;
        return key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }

    @Override
    public String toString() {
        return "Currency{" +
                "key='" + key + '\'' +
                ", baseValue=" + baseValue +
                ", options=" + options +
                ", dataID=" + dataID +
                '}';
    }

}

