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

package cc.carm.plugin.mineeconomy.api.user;

import cc.carm.plugin.mineeconomy.api.currency.EconomyCurrency;
import cc.carm.plugin.mineeconomy.api.operation.OperationMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface EconomyAccount {

    @Unmodifiable
    @NotNull Map<EconomyCurrency, BigDecimal> balances();

    default boolean has(@NotNull EconomyCurrency currency, @NotNull BigDecimal amount) {
        return get(currency).compareTo(amount) >= 0;
    }

    default boolean has(@NotNull EconomyCurrency currency, Number amount) {
        return has(currency, BigDecimal.valueOf(amount.doubleValue()));
    }

    default boolean has(@NotNull Map<EconomyCurrency, BigDecimal> balances) {
        return balances.entrySet().stream().anyMatch(entry -> has(entry.getKey(), entry.getValue()));
    }

    CompletableFuture<BigDecimal> fetch(@NotNull EconomyCurrency currency);

    BigDecimal get(@NotNull EconomyCurrency currency);

    CompletableFuture<Boolean> set(@NotNull Map<EconomyCurrency, BigDecimal> balances,
                                   @Nullable OperationMetadata metadata);

    CompletableFuture<Boolean> update(@NotNull Map<EconomyCurrency, BigDecimal> changes,
                                      @Nullable OperationMetadata metadata);

    void clear(@NotNull EconomyCurrency currency);

}
