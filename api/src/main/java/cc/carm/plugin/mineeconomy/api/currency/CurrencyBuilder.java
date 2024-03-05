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

import cc.carm.plugin.mineeconomy.api.currency.option.CurrencyOptionsHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.math.BigDecimal;

public class CurrencyBuilder {

    public static CurrencyBuilder of(@NotNull String key) {
        return new CurrencyBuilder(key);
    }

    protected final @NotNull String key;

    protected Integer id;
    protected BigDecimal defaults;
    protected CurrencyOptionsHolder optionsHolder;

    public CurrencyBuilder(@NotNull String key) {
        this.key = key;
    }

    public CurrencyBuilder defaults(@NotNull BigDecimal baseValue) {
        this.defaults = baseValue;
        return this;
    }

    public CurrencyBuilder defaults(@NotNull Number value) {
        return defaults(BigDecimal.valueOf(value.doubleValue()));
    }

    public CurrencyBuilder dataID(@Nullable @Range(from = 0, to = 255) Integer dataID) {
        this.id = dataID;
        return this;
    }

    public EconomyCurrency build() {
        return new EconomyCurrency(this.key, this.id, this.defaults, this.optionsHolder);
    }

}
