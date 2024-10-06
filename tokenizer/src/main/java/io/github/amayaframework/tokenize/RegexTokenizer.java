package io.github.amayaframework.tokenize;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * {@link Tokenizer} implementation using {@link Matcher}.
 */
public final class RegexTokenizer implements Tokenizer {

    @Override
    public Iterable<String> tokenize(String target, String delim) {

        return () -> {
            var pattern = Pattern.compile(delim);
            var matcher = pattern.matcher(target);
            // If the input is an empty string then the result can only be a
            // stream of the input.  Induce that by setting the empty
            // element count to 1
            var emptyElementCount = target.isEmpty() ? 1 : 0;
            return new MatcherIterator(matcher, target, emptyElementCount);
        };
    }

    /*
     * Copyright (c) 1999, 2018, Oracle and/or its affiliates. All rights reserved.
     * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
     *
     * This code is free software; you can redistribute it and/or modify it
     * under the terms of the GNU General Public License version 2 only, as
     * published by the Free Software Foundation.  Oracle designates this
     * particular file as subject to the "Classpath" exception as provided
     * by Oracle in the LICENSE file that accompanied this code.
     *
     * This code is distributed in the hope that it will be useful, but WITHOUT
     * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
     * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
     * version 2 for more details (a copy is included in the LICENSE file that
     * accompanied this code).
     *
     * You should have received a copy of the GNU General Public License version
     * 2 along with this work; if not, write to the Free Software Foundation,
     * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
     *
     * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
     * or visit www.oracle.com if you need additional information or have any
     * questions.
     */
    private final static class MatcherIterator implements Iterator<String> {
        private final Matcher matcher;
        private final String input;
        // The start position of the next sub-sequence of input
        // when current == input.length there are no more elements
        private int current;
        // null if the next element, if any, needs to obtained
        private String nextElement;
        // > 0 if there are N next empty elements
        private int emptyElementCount;

        private MatcherIterator(Matcher matcher, String input, int emptyElementCount) {
            this.matcher = matcher;
            this.input = input;
            this.emptyElementCount = emptyElementCount;
        }

        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            if (emptyElementCount == 0) {
                var n = nextElement;
                nextElement = null;
                return n;
            }
            emptyElementCount--;
            return "";
        }

        public boolean hasNext() {
            if (nextElement != null || emptyElementCount > 0) {
                return true;
            }
            if (current == input.length()) {
                return false;
            }
            // Consume the next matching element
            // Count sequence of matching empty elements
            while (matcher.find()) {
                nextElement = input.subSequence(current, matcher.start()).toString();
                current = matcher.end();
                if (!nextElement.isEmpty()) {
                    return true;
                }
                if (current > 0) { // no empty leading substring for zero-width
                    // match at the beginning of the input
                    emptyElementCount++;
                }
            }
            // Consume last matching element
            nextElement = input.subSequence(current, input.length()).toString();
            current = input.length();
            if (!nextElement.isEmpty()) {
                return true;
            }
            // Ignore a terminal sequence of matching empty elements
            emptyElementCount = 0;
            nextElement = null;
            return false;
        }
    }
}
