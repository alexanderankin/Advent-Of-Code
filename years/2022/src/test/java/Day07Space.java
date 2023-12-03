import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("NewClassNamingConvention")
class Day07Space {
    static final String EXAMPLE_INPUT = """
            $ cd /
            $ ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            $ cd a
            $ ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            $ cd e
            $ ls
            584 i
            $ cd ..
            $ cd ..
            $ cd d
            $ ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k
            """;

    int sumDirectoryContentsOverLimit(String representation, int limit) {
        Dir root = parse(representation);
        return root.lsLAR().stream().mapToInt(Dir::sumFileSizes).filter(i -> i < limit).sum();
    }

    private Dir parse(String representation) {
        Dir root = new Dir();
        Dir current = root;
        boolean listing = false;
        for (String s : representation.split("\r?\n")) {
            if (s.startsWith("$ ")) listing = false;
            switch (s.substring(0, Math.min(4, s.length()))) {
                case "$ cd" -> current = current.ensure(Arrays.asList(s.substring(5).split("/")));
                case "$ ls" -> listing = true;
                default -> {
                    var parts = s.split(" ");
                    var sizeOrDir = parts[0];
                    var name = parts[1];
                    if (sizeOrDir.equals("dir")) {
                        current.ensure(List.of(name));
                    } else {
                        current.entry(name, Integer.parseInt(sizeOrDir));
                    }
                }
            }
        }
        return root;
    }

    @Test
    void test_sumDirectoryContentsOverLimit() {
        assertEquals(95437, sumDirectoryContentsOverLimit(EXAMPLE_INPUT, 100000));
    }

    @Test
    void submit_sumDirectoryContentsOverLimit() {
        assertEquals(1447046, sumDirectoryContentsOverLimit(readValue(), 100000));
    }

    @SneakyThrows
    private String readValue() {
        return new String(getClass().getResourceAsStream("/p7.txt").readAllBytes());
    }

    int findSmallestDirectoryToFreeEnoughSpace(String representation, int available, int needFree) {
        var dir = parse(representation);
        var total = dir.sumFileSizes();
        int free = available - total;
        var needMore = needFree - free;
        return dir.lsLAR().stream()
                .sorted(Comparator.comparing(Dir::sumFileSizes))
                .dropWhile(i -> i.sumOfFileSizes < needMore)
                .findAny()
                .map(Dir::sumFileSizes).orElseThrow();
    }

    @Test
    void test_findSmallestDirectoryToFreeEnoughSpace() {
        assertEquals(24933642,
                findSmallestDirectoryToFreeEnoughSpace(
                        EXAMPLE_INPUT, 70000000, 30000000));
    }

    @Test
    void submit_findSmallestDirectoryToFreeEnoughSpace() {
        assertEquals(578710,
                findSmallestDirectoryToFreeEnoughSpace(
                        readValue(), 70000000, 30000000));
    }

    @Data
    @Accessors(chain = true)
    static class Dir {
        String name;
        @ToString.Exclude
        transient Dir parent;
        Map<String, Dir> children;
        List<FileEnt> f;
        transient Integer sumOfFileSizes;

        List<Dir> lsLAR() {
            ArrayList<Dir> cumulative = new ArrayList<>();
            cumulative.add(this);
            return lsLAR(cumulative, this);
        }

        List<Dir> lsLAR(List<Dir> cumulative, Dir dir) {
            if (dir.children == null) return cumulative;
            cumulative.addAll(dir.children.values());
            dir.children.values().forEach(c -> lsLAR(cumulative, c));
            return cumulative;
        }

        public Dir ensure(List<String> list) {
            Dir cwd = this;
            for (String s : list) {
                if (s.equals("..")) {
                    cwd = cwd.parent;
                } else {
                    cwd = cwd.children().computeIfAbsent(s, this::fromName);
                }
            }
            return cwd;
        }

        private Dir fromName(String s) {
            return new Dir().setName(s).setParent(this);
        }

        public Map<String, Dir> children() {
            if (children == null) children = new LinkedHashMap<>();
            return children;
        }

        public void entry(String name, int i) {
            f().add((FileEnt) new FileEnt()
                    .setSize(i)
                    .setName(name)
                    .setParent(this));
        }

        public int sumFileSizes() {
            var us = Objects.requireNonNullElse(getF(), List.<FileEnt>of())
                    .stream()
                    .mapToInt(FileEnt::getSize).sum();
            var children = Objects.requireNonNullElse(getChildren(), Map.<String, Dir>of())
                    .values()
                    .stream()
                    .mapToInt(Dir::sumFileSizes).sum();
            sumOfFileSizes = us + children;
            return us + children;
        }

        List<FileEnt> f() {
            if (f == null) f = new ArrayList<>();
            return f;
        }

        @EqualsAndHashCode(callSuper = true)
        @Data
        @Accessors(chain = true)
        static class FileEnt extends Dir {
            int size;

            @Override
            public String toString() {
                return getName() + " <" + size + ">";
            }
        }
    }
}
