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

import cc.carm.plugin.mineeconomy.api.account.user.EconomyUser;
import cc.carm.plugin.mineeconomy.api.account.user.UserKey;
import cc.carm.plugin.mineeconomy.api.currency.EconomyCurrency;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserAccountManager extends AccountManager<UserKey, EconomyUser> {

    void unload(@NotNull UUID userUUID);

    @Nullable EconomyUser get(@NotNull UUID userUUID);

    @Nullable EconomyUser get(long uid);

    CompletableFuture<@NotNull EconomyUser> fetch(@NotNull UUID userUUID);

    CompletableFuture<@NotNull EconomyUser> fetch(long uid);

    CompletableFuture<@NotNull BigDecimal> fetch(@NotNull UUID userUUID, @NotNull EconomyCurrency currency);

    CompletableFuture<@NotNull BigDecimal> fetch(long uid, @NotNull EconomyCurrency currency);

    default CompletableFuture<@NotNull EconomyUser> peek(@NotNull UUID userUUID) {
        EconomyUser loaded = get(userUUID);
        if (loaded != null) return CompletableFuture.completedFuture(loaded);
        else return fetch(userUUID);
    }

    default CompletableFuture<@NotNull EconomyUser> peek(long uid) {
        EconomyUser loaded = get(uid);
        if (loaded != null) return CompletableFuture.completedFuture(loaded);
        else return fetch(uid);
    }

    default CompletableFuture<@NotNull EconomyUser> peek(@NotNull UserKey key) {
        EconomyUser loaded = get(key);
        if (loaded != null) return CompletableFuture.completedFuture(loaded);
        else return fetch(key);
    }

}
