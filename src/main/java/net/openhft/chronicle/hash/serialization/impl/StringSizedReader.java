/*
 *      Copyright (C) 2015  higherfrequencytrading.com
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.openhft.chronicle.hash.serialization.impl;

import net.openhft.chronicle.bytes.Bytes;
import net.openhft.chronicle.bytes.BytesUtil;
import net.openhft.chronicle.hash.serialization.SizedReader;
import net.openhft.chronicle.hash.serialization.StatefulCopyable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.ObjectInputStream;

public class StringSizedReader implements SizedReader<String>, StatefulCopyable<StringSizedReader> {

    /** Cache field */
    private transient StringBuilder sb;

    public StringSizedReader() {
        initTransients();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initTransients();
    }

    private void initTransients() {
        sb = new StringBuilder();
    }

    @NotNull
    @Override
    public String read(@NotNull Bytes in, long size, @Nullable String using) {
        if (0 > size || size > Integer.MAX_VALUE)
            throw new IllegalStateException("positive int size expected, " + size + " given");
        sb.setLength(0);
        BytesUtil.parseUtf8(in, sb, (int) size);
        return sb.toString();
    }

    @Override
    public StringSizedReader copy() {
        return new StringSizedReader();
    }
}
