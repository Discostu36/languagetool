/* LanguageTool, a natural language style checker
 * Copyright (C) 2023 Jaume Ortolà
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */

package org.languagetool.tools;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.languagetool.tools.DiffsAsMatches.PseudoMatch;

public class DiffsAsMatchesTest {

  @Test
  public void testDiffsAsMatches() throws IOException {
    String original = "This are a sentence with too mistakes.";
    String revised = "This is a sentence with two mistakes.";
    List<PseudoMatch> matches = DiffsAsMatches.getPseudoMatches(original, revised);
    assertEquals(matches.size(), 2);
    assertEquals(matches.get(0).getReplacement(), "is");
    assertEquals(matches.get(0).getFromPos(), 5);
    assertEquals(matches.get(0).getToPos(), 8);
    assertEquals(matches.get(1).getReplacement(), "two");
    assertEquals(matches.get(1).getFromPos(), 25);
    assertEquals(matches.get(1).getToPos(), 28);

    original = "I am going to er remove one word.";
    revised = "I am going to remove one word.";
    matches = DiffsAsMatches.getPseudoMatches(original, revised);
    assertEquals(1, matches.size());
    assertEquals("", matches.get(0).getReplacement());
    assertEquals(14, matches.get(0).getFromPos());
    assertEquals(17, matches.get(0).getToPos());

    original = "And I am going to remove one word.";
    revised = "I am going to remove one word.";
    matches = DiffsAsMatches.getPseudoMatches(original, revised);
    assertEquals(1, matches.size());
    assertEquals("", matches.get(0).getReplacement());
    assertEquals(0, matches.get(0).getFromPos());
    assertEquals(4, matches.get(0).getToPos());

    original = "I am going to add word.";
    revised = "I am going to add one word.";
    matches = DiffsAsMatches.getPseudoMatches(original, revised);
    assertEquals(1, matches.size());
    assertEquals("add one", matches.get(0).getReplacement());
    assertEquals(14, matches.get(0).getFromPos());
    assertEquals(17, matches.get(0).getToPos());

    original = "a word at the start.";
    revised = "Add a word at the start.";
    matches = DiffsAsMatches.getPseudoMatches(original, revised);
    assertEquals(1, matches.size());
    assertEquals("Add a", matches.get(0).getReplacement());
    assertEquals(0, matches.get(0).getFromPos());
    assertEquals(1, matches.get(0).getToPos());

    original = "Add word at position 1.";
    revised = "Add a word at position 1.";
    matches = DiffsAsMatches.getPseudoMatches(original, revised);
    assertEquals(1, matches.size());
    assertEquals("Add a", matches.get(0).getReplacement());
    assertEquals(0, matches.get(0).getFromPos());
    assertEquals(3, matches.get(0).getToPos());
  }

}
