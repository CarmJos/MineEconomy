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

package cc.carm.plugin.mineeconomy.account;

import cc.carm.plugin.mineeconomy.api.account.EconomyAccount;
import cc.carm.plugin.mineeconomy.api.currency.EconomyCurrency;
import cc.carm.plugin.mineeconomy.api.operation.OperationDetails;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractEconomyAccount<K> implements EconomyAccount<K> {

    protected final ReentrantReadWriteLock locker = new ReentrantReadWriteLock();
    protected final @NotNull Map<EconomyCurrency, BigDecimal> accounts = new ConcurrentHashMap<>();

    @Override
    public @Unmodifiable @NotNull Map<EconomyCurrency, BigDecimal> balances() {
        return Collections.unmodifiableMap(accounts);
    }

    @Override
    public CompletableFuture<BigDecimal> fetch(@NotNull EconomyCurrency currency) {
        return null;
    }

    @Override
    public @NotNull BigDecimal get(@NotNull EconomyCurrency currency) {
        try {
            locker.readLock().lock();
            BigDecimal data = accounts.get(currency);
            if (data != null) return currency.baseValue().add(data);
            else return currency.baseValue();
        } finally {
            locker.readLock().unlock();
        }
    }

    @Override
    public CompletableFuture<Boolean> set(@NotNull Map<EconomyCurrency, BigDecimal> balances,
                                          @Nullable OperationDetails details) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> update(@NotNull Map<EconomyCurrency, BigDecimal> changes,
                                             @Nullable OperationDetails details) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> clear(@NotNull EconomyCurrency currency) {
        return null;
    }

    private synchronized boolean applyChanges(Map<EconomyCurrency, Double> finalValues,
                                              @NotNull OperationDetails details) {

        return false;
    }


}
