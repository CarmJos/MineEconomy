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

import cc.carm.plugin.mineeconomy.api.currency.option.CurrencyOption;

public interface CurrencyOptions {

    CurrencyOption<String> NAME = CurrencyOption.of("name", String.class, null);

    CurrencyOption<String> DESCRIPTION = CurrencyOption.of("description", String.class, null);

    CurrencyOption<String> SYMBOL = CurrencyOption.of("symbol", String.class, "$");

    interface QUANTIFIER {
        CurrencyOption<String> SINGULAR = CurrencyOption.of("quantifier.singular", String.class, "dollar");
        CurrencyOption<String> PLURAL = CurrencyOption.of("quantifier.plural", String.class, "dollars");
    }

}
