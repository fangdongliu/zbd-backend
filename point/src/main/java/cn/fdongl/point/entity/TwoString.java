package cn.fdongl.point.entity;

import java.util.Objects;

public class TwoString {
    String str1;

    String str2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TwoString)) return false;
        TwoString twoString = (TwoString) o;
        return str1.equals(twoString.str1) &&
                str2.equals(twoString.str2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(str1, str2);
    }
}
