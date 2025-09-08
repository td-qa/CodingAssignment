package codingassignment.tests.util;

public final class NameFormatter {
    private NameFormatter() {}

    public static String convertNameToInitials(String name) {
        if (name == null) return "";
        String trimmed = name.trim();
        if (trimmed.isEmpty()) return "";

        String[] parts = trimmed.split("\\s+");
        StringBuilder stringBuilder = new StringBuilder(parts.length * 2);

        for (String part : parts) {
            int cp = part.codePointAt(0);
            stringBuilder.appendCodePoint(Character.toUpperCase(cp))
                    .append('.');
        }
        return stringBuilder.toString();
    }
}
