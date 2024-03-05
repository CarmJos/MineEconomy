/*
 * This file is part of EasyOptions, licensed under the MIT License.
 *
 * Copyright (c) 2024 Carm
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package cc.carm.plugin.mineeconomy.api.currency.option;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CurrencyOption<V> {

    @SuppressWarnings("unchecked")
    public static <T> CurrencyOption<T> of(@NotNull String id, @NotNull T defaultValue) {
        return of(id, (Class<T>) defaultValue.getClass(), defaultValue);
    }

    public static <T> CurrencyOption<T> of(@NotNull String id, @NotNull Class<T> valueClazz, @Nullable T defaultValue) {
        return new CurrencyOption<>(id, valueClazz, defaultValue);
    }

    protected final @NotNull String id;
    protected final @NotNull Class<V> valueClazz;
    protected @Nullable V defaultValue;

    protected @NotNull Function<V, Object> serializer;
    protected @NotNull Function<Object, V> parser;

    public CurrencyOption(@NotNull String id, @NotNull Class<V> valueClazz, @Nullable V defaultValue) {
        this.id = id;
        this.valueClazz = valueClazz;
        this.defaultValue = defaultValue;
    }

    public @NotNull Class<V> valueClass() {
        return valueClazz;
    }

    public @Nullable V defaults() {
        return defaultValue;
    }

    /**
     * Set the default value of option.
     *
     * @param defaultValue Default value
     */
    public void defaults(@Nullable V defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isDefault(@NotNull V value) {
        return value.equals(defaultValue);
    }
}