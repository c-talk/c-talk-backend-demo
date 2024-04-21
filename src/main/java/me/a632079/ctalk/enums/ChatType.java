package me.a632079.ctalk.enums;

public enum ChatType {
    Private(1),
    Group(2);

    private final int value;

    ChatType(int type) {
        this.value = type;
    }

    public static ChatType valueOf(int value) {
        for (ChatType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        return null;
    }

    public int value() {
        return this.value;
    }
}
