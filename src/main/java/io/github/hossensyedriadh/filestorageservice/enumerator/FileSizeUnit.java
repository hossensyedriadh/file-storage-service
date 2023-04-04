package io.github.hossensyedriadh.filestorageservice.enumerator;

public enum FileSizeUnit {
    MB ("Megabytes"),
    KB ("Kilobytes"),
    B ("Bytes");

    private final String fullName;

    FileSizeUnit(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
