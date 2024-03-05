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

package cc.carm.plugin.mineeconomy.api.manager;

import cc.carm.plugin.mineeconomy.api.currency.EconomyCurrency;
import cc.carm.plugin.mineeconomy.api.currency.option.CurrencyOptionsHolder;
import org.jetbrains.annotations.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public interface CurrencyManager {

    CompletableFuture<Integer> pull();

    @Unmodifiable
    @NotNull Set<EconomyCurrency> currencies();

    @Nullable EconomyCurrency get(@NotNull String key);

    @Nullable EconomyCurrency get(int dataID);

    @NotNull EconomyCurrency defaults() throws NullPointerException;

    void defaults(@NotNull Supplier<EconomyCurrency> defaultCurrency);

    default void defaults(@NotNull EconomyCurrency defaultCurrency) {
        defaults(() -> defaultCurrency);
    }

    CompletableFuture<Integer> initialize(@NotNull EconomyCurrency currency);

    CompletableFuture<@NotNull EconomyCurrency> initialize(
            @NotNull String key, @Nullable @Range(from = 0, to = 255) Integer id,
            @NotNull BigDecimal baseValue, @NotNull CurrencyOptionsHolder options
    );

    default CompletableFuture<@NotNull EconomyCurrency> initialize(
            @NotNull String key, @NotNull BigDecimal baseValue, @NotNull CurrencyOptionsHolder options
    ) {
        return initialize(key, null, baseValue, options);
    }

    CompletableFuture<@Nullable EconomyCurrency> fetch(@NotNull String key);

    CompletableFuture<@Nullable EconomyCurrency> fetch(int dataID);

    CompletableFuture<@Nullable EconomyCurrency> modify(@NotNull String key, @NotNull Consumer<EconomyCurrency> consumer);

    CompletableFuture<@Nullable EconomyCurrency> modify(int dataID, @NotNull Consumer<EconomyCurrency> consumer);

    CompletableFuture<Integer> purge(@NotNull EconomyCurrency currency);

    @TestOnly
    CompletableFuture<Integer> delete(@NotNull EconomyCurrency currency);

}
