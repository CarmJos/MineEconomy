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

package cc.carm.plugin.mineeconomy.api.account;

import cc.carm.plugin.mineeconomy.api.currency.EconomyCurrency;
import cc.carm.plugin.mineeconomy.api.service.storage.AccountStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public interface AccountManager<K, R extends EconomyAccount<K>> {

    @NotNull AccountStorage<K, R> storage();

    @NotNull ExecutorService executor();

    @Unmodifiable
    @NotNull Map<K, R> loadedAccounts();

    void unload(@NotNull K key);

    @Nullable R get(@NotNull K key);

    default @Nullable BigDecimal get(@NotNull K key, @NotNull EconomyCurrency currency) {
        R account = get(key);
        return account == null ? null : account.get(currency);
    }

    CompletableFuture<@NotNull R> fetch(K key);

    CompletableFuture<@NotNull BigDecimal> fetch(@NotNull K key, @NotNull EconomyCurrency currency);

    CompletableFuture<@NotNull R> peek(@NotNull K key);

}
